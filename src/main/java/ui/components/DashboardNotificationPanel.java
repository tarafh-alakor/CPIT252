package ui.components;

import observer.NotificationObserver;

import javax.swing.*;

/* Concrete Observer in the Observer Design Pattern.
 * Responsibility:
 * - Listen for notifications
 * - Update notification list automatically*/
public class DashboardNotificationPanel
        implements NotificationObserver {

    // Stores notification text
    private final DefaultListModel<String> model;

    // Visual notification list
    private final JList<String> notificationList;

    public DashboardNotificationPanel() {

        model = new DefaultListModel<>();

        notificationList = new JList<>(model);

        initializeDefaults();
    }
//Initial startup notification.

    private void initializeDefaults() {

        model.addElement(
                "System ready. Notifications will appear here."
        );
    }
// Returns visual notification list.

    public JList<String> getNotificationList() {

        return notificationList;
    }
// Automatically updates the dashboard, when a new notification is received.

    @Override
    public void update(String message) {

        model.addElement(message);
    }
}
