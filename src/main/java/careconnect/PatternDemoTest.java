package careconnect;

// Simple test class for the report / demo.
// It proves that the three selected design patterns work without opening the GUI.
public class PatternDemoTest {
    public static void main(String[] args) {
        // Factory Method test
        UserFactory factory = new UserFactory();
        User doctor = factory.createUser("Doctor", "Sarah");

        // Observer test
        NotificationCenter center = new NotificationCenter();
        center.attach(message -> System.out.println("Observer received: " + message));

        // Facade test
        CareRepository repository = new CareRepository();
        CarePlatformFacade facade = new CarePlatformFacade(repository, center);
        facade.addReport(doctor, "Jana Ahmad", "Medical status is stable.");

        System.out.println("Reports count: " + facade.getReports().size());
    }
}
