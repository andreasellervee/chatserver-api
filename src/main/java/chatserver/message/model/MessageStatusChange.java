package chatserver.message.model;

import chatserver.message.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
public class MessageStatusChange {

    private Long messageId;
    @Enumerated(EnumType.STRING)
    private MessageStatus oldStatus;
    @Enumerated(EnumType.STRING)
    private MessageStatus newStatus;
}
