package tests;

import facade.CarePlatformFacade;

import factory.UserFactory;

import model.User;
import model.Report;
import model.ChildProfile;

import observer.NotificationCenter;

import repository.CareRepository;

import strategy.SearchByAuthor;
import strategy.SearchContext;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Tests the Facade Pattern implementation.
 */
public class FacadePatternTest {

    /*
     * Facade should contain repository field.
     */
    @Test
    public void testFacadeContainsRepository() {

        boolean found = false;

        for (Field field
                : CarePlatformFacade.class
                        .getDeclaredFields()) {

            if (field.getType()
                    .equals(CareRepository.class)) {

                found = true;
            }
        }

        assertTrue(found);
    }

    /*
     * Facade should contain NotificationCenter field.
     */
    @Test
    public void testFacadeContainsNotificationCenter() {

        boolean found = false;

        for (Field field
                : CarePlatformFacade.class
                        .getDeclaredFields()) {

            if (field.getType()
                    .equals(NotificationCenter.class)) {

                found = true;
            }
        }

        assertTrue(found);
    }

    /*
     * Facade should add child successfully.
     */
    @Test
    public void testAddChild() {

        CareRepository repository
                = new CareRepository();

        NotificationCenter center
                = new NotificationCenter();

        CarePlatformFacade facade
                = new CarePlatformFacade(
                        repository,
                        center
                );

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

        ChildProfile child
                = facade.getChildren().get(0);

        assertEquals(
                "Jana Ahmad",
                child.getName()
        );
    }

    /*
     * Facade should add report successfully.
     */
    @Test
    public void testAddReport() {

        CareRepository repository
                = new CareRepository();

        NotificationCenter center
                = new NotificationCenter();

        CarePlatformFacade facade
                = new CarePlatformFacade(
                        repository,
                        center
                );

        UserFactory factory
                = UserFactory.forRole("Doctor");

        User doctor
                = factory.createUser("Sarah");

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
    }

    /*
     * Facade should add appointment successfully.
     */
    @Test
    public void testAddAppointment() {

        CareRepository repository
                = new CareRepository();

        NotificationCenter center
                = new NotificationCenter();

        CarePlatformFacade facade
                = new CarePlatformFacade(
                        repository,
                        center
                );

        facade.addAppointment(
                "Speech Session",
                "Amani",
                LocalDateTime.now()
        );

        assertTrue(
                facade.getAppointments().size() >= 1
        );
    }

    /*
     * Facade should search reports using Strategy Pattern.
     */
    @Test
    public void testSearchReports() {

        CareRepository repository
                = new CareRepository();

        NotificationCenter center
                = new NotificationCenter();

        CarePlatformFacade facade
                = new CarePlatformFacade(
                        repository,
                        center
                );

        UserFactory factory
                = UserFactory.forRole("Doctor");

        User doctor
                = factory.createUser("Sarah");

        facade.addReport(
                doctor,
                "Jana Ahmad",
                "Excellent progress."
        );

        SearchContext context
                = new SearchContext(
                        new SearchByAuthor()
                );

        List<Report> results
                = facade.searchReports(
                        context,
                        "Sarah"
                );

        assertEquals(
                1,
                results.size()
        );
    }

    /*
     * Facade should reject invalid child data.
     */
    @Test
    public void testInvalidChildData() {

        CareRepository repository
                = new CareRepository();

        NotificationCenter center
                = new NotificationCenter();

        CarePlatformFacade facade
                = new CarePlatformFacade(
                        repository,
                        center
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> facade.addChild(
                        "",
                        0,
                        "",
                        "",
                        50
                )
        );
    }

    /*
     * Facade should reject empty report text.
     */
    @Test
    public void testInvalidReport() {

        CareRepository repository
                = new CareRepository();

        NotificationCenter center
                = new NotificationCenter();

        CarePlatformFacade facade
                = new CarePlatformFacade(
                        repository,
                        center
                );

        UserFactory factory
                = UserFactory.forRole("Doctor");

        User doctor
                = factory.createUser("Sarah");

        assertThrows(
                IllegalArgumentException.class,
                () -> facade.addReport(
                        doctor,
                        "Jana Ahmad",
                        ""
                )
        );
    }
}
