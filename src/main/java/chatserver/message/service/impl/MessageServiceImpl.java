package chatserver.message.service.impl;

import chatserver.message.enums.MessageStatus;
import chatserver.message.exception.StatusChangeNotPermittedException;
import chatserver.message.model.Message;
import chatserver.message.model.MessageStatusChange;
import chatserver.message.model.SimpleMessage;
import chatserver.message.repository.MessageRepository;
import chatserver.message.service.MessagePushService;
import chatserver.message.service.MessageService;
import chatserver.user.model.User;
import chatserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    public static final int PREVIOUS_MESSAGE_LIMIT = 15;

    @Autowired
    private UserService userService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessagePushService messagePushService;


    @Override
    public Message sendMessage(SimpleMessage simpleMessage) {
        simpleMessage.validate();
        User from = userService.getUserByName(simpleMessage.getFrom());
        User to = userService.getUserByName(simpleMessage.getTo());
        Message message = new Message(from, to, simpleMessage.getData());
        message = messageRepository.save(message);
        messagePushService.pushNewMessage(message);
        return message;
    }

    @Override
    public void setMessageStatus(Long id, MessageStatus status) {
        Message message = messageRepository.findOne(id);
        if (!message.statusChangePermitted(status)) {
            throw new StatusChangeNotPermittedException();
        }
        MessageStatus oldStatus = message.getMessageStatus();
        message.setMessageStatus(status);
        messageRepository.save(message);
        messagePushService.notifyStatusChange(
                message.getFrom(),
                new MessageStatusChange(id, oldStatus, message.getMessageStatus()));
    }

    @Override
    public List<Message> getUnreadMessages(String from, String to) {
        List<Message> unreadMessagesFromTo = messageRepository.findUnreadMessagesFromTo(
                userService.getUserByName(from),
                userService.getUserByName(to));
        Collections.reverse(unreadMessagesFromTo);
        return unreadMessagesFromTo;
    }

    @Override
    public List<Message> getPreviousMessages(String from, String to) {
        List<Message> previousSeenMessagesFromTo = messageRepository.findPreviousSeenMessagesFromTo(
                userService.getUserByName(from),
                userService.getUserByName(to),
                new PageRequest(0, PREVIOUS_MESSAGE_LIMIT));
        Collections.reverse(previousSeenMessagesFromTo);
        return previousSeenMessagesFromTo;
    }
}
