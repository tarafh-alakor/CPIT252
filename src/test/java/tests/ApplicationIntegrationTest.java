package tests;

import facade.CarePlatformFacade;

import factory.UserFactory;

import model.Report;
import model.User;

import observer.NotificationCenter;
import observer.NotificationObserver;

import repository.CareRepository;

import strategy.SearchByAuthor;
import strategy.SearchContext;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Integration test for the entire application workflow.
 *
 * This test demonstrates how all selected design
 * patterns cooperate together inside the system.
 */
public class ApplicationIntegrationTest {

    /*
     * Tests:
     * Factory Method
     * Observer
     * Facade
     * Strategy
     *
     * in one complete workflow.
     */
    @Test
    public void testCompleteWorkflow() {

        // =========================
        // Setup System
        // =========================
        CareRepository repository
                = new CareRepository();

        NotificationCenter center
                = new NotificationCenter();

        CarePlatformFacade facade
                = new CarePlatformFacade(
                        repository,
                        center
                );

        // =========================
        // Observer Pattern
        // =========================
        TestObserver observer
                = new TestObserver();

        center.attach(observer);

        // =========================
        // Factory Method Pattern
        // =========================
        UserFactory factory
                = UserFactory.forRole("Doctor");

        User doctor
                = factory.createUser("Sarah");

        assertNotNull(doctor);

        assertEquals(
                "Doctor",
                doctor.getRole()
        );

        // =========================
        // Facade Pattern
        // =========================
        facade.addChild(
                "Jana Ahmad",
                6,
                "Hearing Impairment",
                "Tarafh",
                75
        );

        assertTrue(
                facade.getChildren().size() >= 1
        );

        facade.addReport(
                doctor,
                "Jana Ahmad",
                "Speech improvement observed."
        );

        List<Report> reports
                = facade.getReports();

        assertTrue(
                reports.size() >= 1
        );

        Report lastReport
                = reports.get(
                        reports.size() - 1
                );

        assertEquals(
                "Doctor",
                lastReport.getRole()
        );

        // =========================
        // Observer Verification
        // =========================
        assertTrue(observer.updated);

        assertFalse(
                center.getNotificationHistory()
                        .isEmpty()
        );

        // =========================
        // Add Appointment
        // =========================
        facade.addAppointment(
                "Speech Session",
                "Amani",
                LocalDateTime.now()
        );

        assertTrue(
                facade.getAppointments().size() >= 1
        );

        // =========================
        // Strategy Pattern
        // =========================
        SearchContext context
                = new SearchContext(
                        new SearchByAuthor()
                );

        List<Report> results
                = facade.searchReports(
                        context,
                        "Sarah"
                );

        assertFalse(results.isEmpty());

        assertEquals(
                "Sarah",
                results.get(0).getAuthorName()
        );
    }

    /*
     * Fake observer used for testing.
     */
    private static class TestObserver
            implements NotificationObserver {

        boolean updated = false;

        @Override
        public void update(String message) {

            updated = true;
        }
    }
}
