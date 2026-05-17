package careconnect;

import java.util.ArrayList;
import java.util.List;

// BEHAVIORAL PATTERN: Observer Subject.
// It stores observers and notifies them when the system changes.
public class NotificationCenter {
    private final List<NotificationObserver> observers = new ArrayList<>();
    private final List<String> notificationHistory = new ArrayList<>();

    public void attach(NotificationObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        notificationHistory.add(message);
        for (NotificationObserver observer : observers) {
            observer.update(message);
        }
    }

    public List<String> getNotificationHistory() {
        return notificationHistory;
    }
}
