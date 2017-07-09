package chatserver.message.enums;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MessageStatusTest {

    @Test
    public void canChangeTo() throws Exception {
        assertTrue(MessageStatus.CREATED.canChangeTo(MessageStatus.SENT));
        assertTrue(MessageStatus.SENT.canChangeTo(MessageStatus.RECEIVED));
        assertTrue(MessageStatus.RECEIVED.canChangeTo(MessageStatus.SEEN));

        assertFalse(MessageStatus.CREATED.canChangeTo(MessageStatus.SEEN));
    }

}