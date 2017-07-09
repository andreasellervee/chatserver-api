package chatserver.message.service;

import chatserver.message.enums.MessageStatus;
import chatserver.message.model.Message;
import chatserver.message.model.SimpleMessage;

import java.util.List;

public interface MessageService {

    Message sendMessage(SimpleMessage simpleMessage);

    void setMessageStatus(Long id, MessageStatus status);

    List<Message> getUnreadMessages(String from, String to);

    List<Message> getPreviousMessages(String from, String to);
}
