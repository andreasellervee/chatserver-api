package chatserver.message.service.impl;

import chatserver.message.enums.MessageStatus;
import chatserver.message.model.Message;
import chatserver.message.model.MessageStatusChange;
import chatserver.message.repository.MessageRepository;
import chatserver.message.service.MessagePushService;
import chatserver.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MessagePushServiceImpl implements MessagePushService {

    public static final String MESSAGE_DESTINATION = "/message";
    public static final String NOTIFICATION_DESTINATION = "/notification";

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private SimpMessagingTemplate template;

    @Override
    @Async
    public void pushNewMessage(Message message) {
        MessageStatus oldStatus = message.getMessageStatus();
        message.setMessageStatus(MessageStatus.SENT);
        template.convertAndSendToUser(message.getTo().getName(), MESSAGE_DESTINATION, message);
        messageRepository.save(message);
        notifyStatusChange(message.getFrom(), new MessageStatusChange(message.getId(), oldStatus, message.getMessageStatus()));
    }

    @Override
    @Async
    public void notifyStatusChange(User userToNotify, MessageStatusChange messageStatusChange) {
        template.convertAndSendToUser(userToNotify.getName(), NOTIFICATION_DESTINATION, messageStatusChange);
    }

}
