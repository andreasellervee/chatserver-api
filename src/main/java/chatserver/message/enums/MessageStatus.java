package chatserver.message.enums;

public enum MessageStatus {

    CREATED(0),
    SENT(1),
    RECEIVED(2),
    SEEN(3);

    private final int order;
    MessageStatus(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public boolean canChangeTo(MessageStatus status) {
        return this.getOrder() + 1 == status.getOrder();
    }
}
