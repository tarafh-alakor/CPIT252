package careconnect;

import facade.CarePlatformFacade;
import factory.UserFactory;
import model.Report;
import model.User;
import observer.NotificationCenter;
import repository.CareRepository;
import strategy.SearchByAuthor;
import strategy.SearchContext;
import strategy.SearchStrategy;

public class PatternDemoTest {

    public static void main(String[] args) {

        // Factory Method
        UserFactory factory = new UserFactory();

        User doctor =
                factory.createUser(
                        "Doctor",
                        "Sarah"
                );

        System.out.println(
                "Factory Created: "
                        + doctor.getName()
        );

        // Observer + Facade
        NotificationCenter center =
                new NotificationCenter();

        center.attach(message ->
                System.out.println(
                        "Notification: "
                                + message
                )
        );

        CareRepository repository =
                new CareRepository();

        CarePlatformFacade facade =
                new CarePlatformFacade(
                        repository,
                        center
                );

        facade.addReport(
                doctor,
                "Jana Ahmad",
                "Speech progress improved."
        );

        // Strategy
        SearchStrategy strategy =
                new SearchByAuthor();

        SearchContext context = new SearchContext(strategy);
        System.out.println(
                "Strategy search results: "
                        + context.executeSearch(facade.getReports(), "Sarah").size()
        );

    }
}
