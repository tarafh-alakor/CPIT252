package careconnect;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/*
 * Concrete Observer in the Observer Design Pattern.
 *
 * This class listens to NotificationCenter.
 * When NotificationCenter sends a new update, this panel adds it directly
 * to the JList model. This keeps the notification UI separated from the
 * business logic.
 */
public class DashboardNotificationPanel implements NotificationObserver {

    // Swing model that stores notification text shown in the list.
    private final DefaultListModel<String> model;

    // The visual list used by the dashboard.
    private final JList<String> notificationList;

    public DashboardNotificationPanel() {
        model = new DefaultListModel<>();
        notificationList = new JList<>(model);

        // Initial sample message to make the page useful when the app starts.
        model.addElement("System ready. Notifications will appear here.");
    }

    public JList<String> getNotificationList() {
        return notificationList;
    }

    @Override
    public void update(String message) {
        // Observer update method: every new message is displayed automatically.
        model.addElement(message);
    }
}
