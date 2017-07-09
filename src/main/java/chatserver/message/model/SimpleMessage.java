package chatserver.message.model;

import lombok.*;
import org.apache.commons.lang3.Validate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleMessage {

    String from;
    String to;
    String data;

    public void validate() {
        Validate.notBlank(this.from, "Sender ('from') not specified");
        Validate.notBlank(this.to, "Receiver ('to') not specified");
        Validate.notBlank(this.data, "Message data ('data') not specified");
    }
}
