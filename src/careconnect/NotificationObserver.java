package careconnect;

// BEHAVIORAL PATTERN: Observer.
// Any class that wants to receive notifications implements this interface.
public interface NotificationObserver {
    void update(String message);
}
