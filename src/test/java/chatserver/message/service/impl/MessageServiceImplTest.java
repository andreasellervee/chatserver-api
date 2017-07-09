package chatserver.message.service.impl;

import chatserver.message.enums.MessageStatus;
import chatserver.message.exception.StatusChangeNotPermittedException;
import chatserver.message.model.Message;
import chatserver.message.model.SimpleMessage;
import chatserver.message.repository.MessageRepository;
import chatserver.message.service.MessageService;
import chatserver.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class MessageServiceImplTest {

    public static final String ANDREAS = "Andreas";
    public static final String PRIIT = "Priit";
    public static final String TEST_MESSAGE = "Test message";

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @Before
    public void setup() {
        createSenderAndReceiver(ANDREAS, PRIIT);
    }

    @Test
    public void sendMessage() throws Exception {
        Message sentMessage = createMessage(ANDREAS, PRIIT, TEST_MESSAGE);
        assertEquals(ANDREAS, sentMessage.getFrom().getName());
        assertEquals(PRIIT, sentMessage.getTo().getName());
        assertEquals(MessageStatus.SENT, sentMessage.getMessageStatus());
    }

    @Test
    public void setMessageStatus_sentToReceived() throws Exception {
        Message message = createMessage(ANDREAS, PRIIT, TEST_MESSAGE);
        messageService.setMessageStatus(message.getId(), MessageStatus.RECEIVED);

        message = messageRepository.findOne(message.getId());
        assertEquals(ANDREAS, message.getFrom().getName());
        assertEquals(PRIIT, message.getTo().getName());
        assertEquals(MessageStatus.RECEIVED, message.getMessageStatus());
    }

    @Test
    public void setMessageStatus_receivedToSeen() throws Exception {
        Message message = createMessage(ANDREAS, PRIIT, TEST_MESSAGE);
        messageService.setMessageStatus(message.getId(), MessageStatus.RECEIVED);
        messageService.setMessageStatus(message.getId(), MessageStatus.SEEN);

        message = messageRepository.findOne(message.getId());
        assertEquals(ANDREAS, message.getFrom().getName());
        assertEquals(PRIIT, message.getTo().getName());
        assertEquals(MessageStatus.SEEN, message.getMessageStatus());

    }

    @Test
    public void setMessageStatus_unpermittedChange() throws Exception {
        Message message = createMessage(ANDREAS, PRIIT, TEST_MESSAGE);
        try {
            messageService.setMessageStatus(message.getId(), MessageStatus.SEEN);
        } catch (Exception e) {
            assertTrue(e instanceof StatusChangeNotPermittedException);
        }
        Message checkNotChanged = messageRepository.findOne(message.getId());
        assertEquals(MessageStatus.SENT, checkNotChanged.getMessageStatus());
    }

    @Test
    public void getPreviousMessages() {
        Message message = createMessage(ANDREAS, PRIIT, TEST_MESSAGE, MessageStatus.SEEN);
        createMessage(ANDREAS, PRIIT, TEST_MESSAGE, MessageStatus.SENT);

        List<Message> previousMessages = messageService.getPreviousMessages(ANDREAS, PRIIT);
        assertEquals(1, previousMessages.size());

        Message previousMessage = previousMessages.get(0);
        assertEquals(message.getId(), previousMessage.getId());
        assertEquals(ANDREAS, previousMessage.getFrom().getName());
        assertEquals(PRIIT, previousMessage.getTo().getName());
        assertEquals(TEST_MESSAGE, previousMessage.getData());
        assertEquals(MessageStatus.SEEN, previousMessage.getMessageStatus());
    }

    @Test
    public void getUnreadMessages() {
        Message message1 = createMessage(ANDREAS, PRIIT, TEST_MESSAGE, MessageStatus.SENT);
        Message message2 = createMessage(ANDREAS, PRIIT, TEST_MESSAGE, MessageStatus.RECEIVED);
        createMessage(ANDREAS, PRIIT, TEST_MESSAGE, MessageStatus.SEEN);

        List<Message> unreadMessages = messageService.getUnreadMessages(ANDREAS, PRIIT);
        assertEquals(2, unreadMessages.size());

        Message unreadMessage = unreadMessages.get(0);
        assertEquals(message1.getId(), unreadMessage.getId());
        assertEquals(ANDREAS, unreadMessage.getFrom().getName());
        assertEquals(PRIIT, unreadMessage.getTo().getName());
        assertEquals(TEST_MESSAGE, unreadMessage.getData());
        assertEquals(MessageStatus.SENT, unreadMessage.getMessageStatus());

        unreadMessage = unreadMessages.get(1);
        assertEquals(message2.getId(), unreadMessage.getId());
        assertEquals(ANDREAS, unreadMessage.getFrom().getName());
        assertEquals(PRIIT, unreadMessage.getTo().getName());
        assertEquals(TEST_MESSAGE, unreadMessage.getData());
        assertEquals(MessageStatus.RECEIVED, unreadMessage.getMessageStatus());
    }

    private Message createMessage(String from, String to, String data) {
        return createMessage(from, to, data, null);
    }

    private Message createMessage(String from, String to, String data, MessageStatus status) {
        Message message = messageService.sendMessage(new SimpleMessage(from, to, data));
        if (status != null) {
            message.setMessageStatus(status);
            message = messageRepository.save(message);
        }
        return message;
    }

    private void createSenderAndReceiver(String from, String to) {
        userService.createUser(from);
        userService.createUser(to);
    }

}