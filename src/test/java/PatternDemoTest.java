

// Simple test class for demonstrating the design patterns.

import facade.CarePlatformFacade;
import factory.UserFactory;
import model.User;
import observer.NotificationCenter;
import repository.CareRepository;
import strategy.SearchByAuthor;
import strategy.SearchContext;

public class PatternDemoTest {

    public static void main(String[] args) {

        System.out.println("=== Design Pattern Demo Test ===");

        // Factory Method Pattern
        UserFactory factory = new UserFactory();

        User doctor = factory.createUser("Doctor", "Sarah");

        System.out.println("Factory created user: "
                + doctor.getName());

        // Observer Pattern
        NotificationCenter center = new NotificationCenter();

        center.attach(message ->
                System.out.println("Observer received: " + message));

        // Facade Pattern
        CareRepository repository = new CareRepository();

        CarePlatformFacade facade =
                new CarePlatformFacade(repository, center);

        facade.addReport(
                doctor,
                "Jana Ahmad",
                "Medical status is stable."
        );

        SearchContext context = new SearchContext(new SearchByAuthor());
        System.out.println("Strategy search results: "
                + facade.searchReports(context, "Sarah").size());

        System.out.println("Reports count: "
                + facade.getReports().size());

        System.out.println("=== Test Completed Successfully ===");
    }
}