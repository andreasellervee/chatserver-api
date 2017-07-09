package chatserver.message.service;

import chatserver.message.enums.MessageStatus;
import chatserver.message.model.Message;
import chatserver.message.model.MessageStatusChange;
import chatserver.user.model.User;

public interface MessagePushService {

    void pushNewMessage(Message message);

    void notifyStatusChange(User userToNotify, MessageStatusChange messageStatusChange);

}
