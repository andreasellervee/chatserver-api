package chatserver.message.model;

import chatserver.message.enums.MessageStatus;
import chatserver.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User from;
    @ManyToOne(fetch = FetchType.EAGER)
    private User to;
    private String data;
    private Date timestamp;
    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    public Message(User from, User to, String data) {
        this.from = from;
        this.to = to;
        this.data = data;
        this.timestamp = new Date();
        this.messageStatus = MessageStatus.CREATED;
    }

    public boolean statusChangePermitted(MessageStatus newStatus) {
        return this.getMessageStatus().canChangeTo(newStatus);
    }

}
