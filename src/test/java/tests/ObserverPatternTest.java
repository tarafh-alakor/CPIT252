package tests;

import observer.NotificationCenter;
import observer.NotificationObserver;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Tests the Observer Pattern implementation.
 */
public class ObserverPatternTest {

    /*
     * NotificationObserver should be an interface.
     */
    @Test
    public void testObserverIsInterface() {

        assertTrue(
                Modifier.isInterface(
                        NotificationObserver.class
                                .getModifiers()
                )
        );
    }

    /*
     * NotificationObserver should contain update method.
     */
    @Test
    public void testUpdateMethodExists() {

        boolean passed = false;

        for (Method method
                : NotificationObserver.class
                        .getDeclaredMethods()) {

            if (method.getName()
                    .equals("update")) {

                assertEquals(
                        1,
                        method.getParameterCount()
                );

                passed = true;
            }
        }

        assertTrue(passed);
    }

    /*
     * Observer should receive notifications automatically.
     */
    @Test
    public void testObserverReceivesNotification() {

        NotificationCenter center
                = new NotificationCenter();

        TestObserver observer
                = new TestObserver();

        center.attach(observer);

        center.notifyObservers(
                "New report added"
        );

        assertTrue(observer.updated);

        assertEquals(
                "New report added",
                observer.lastMessage
        );
    }

    /*
     * Multiple observers should receive updates.
     */
    @Test
    public void testMultipleObserversReceiveNotifications() {

        NotificationCenter center
                = new NotificationCenter();

        TestObserver observer1
                = new TestObserver();

        TestObserver observer2
                = new TestObserver();

        center.attach(observer1);

        center.attach(observer2);

        center.notifyObservers(
                "Appointment updated"
        );

        assertTrue(observer1.updated);

        assertTrue(observer2.updated);
    }

    /*
     * Notification history should store messages.
     */
    @Test
    public void testNotificationHistory() {

        NotificationCenter center
                = new NotificationCenter();

        center.notifyObservers(
                "First notification"
        );

        center.notifyObservers(
                "Second notification"
        );

        assertEquals(
                2,
                center.getNotificationHistory()
                        .size()
        );

        assertEquals(
                "First notification",
                center.getNotificationHistory()
                        .get(0)
        );
    }

    /*
     * Fake observer used only for testing.
     */
    private static class TestObserver
            implements NotificationObserver {

        boolean updated = false;

        String lastMessage = "";

        @Override
        public void update(String message) {

            updated = true;

            lastMessage = message;
        }
    }
}
