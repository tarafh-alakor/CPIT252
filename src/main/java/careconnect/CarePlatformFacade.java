package careconnect;

import java.time.LocalDateTime;
import java.util.List;

// STRUCTURAL PATTERN: Facade.
// It gives the GUI one simple interface instead of dealing directly with
// repository, reports, children, appointments, and notifications.
public class CarePlatformFacade {
    private final CareRepository repository;
    private final NotificationCenter notificationCenter;

    public CarePlatformFacade(CareRepository repository, NotificationCenter notificationCenter) {
        this.repository = repository;
        this.notificationCenter = notificationCenter;
    }

    public void addChild(String name, int age, String condition, String guardian, int progress) {
        ChildProfile child = new ChildProfile(name, age, condition, guardian, progress);
        repository.addChild(child);
        notificationCenter.notifyObservers("New child profile added: " + name);
    }

    public void addReport(User user, String childName, String reportText) {
        Report report = user.createReport(childName, reportText);
        repository.addReport(report);
        notificationCenter.notifyObservers(user.getRole() + " added a new report for " + childName);
    }

    public void addAppointment(String title, String specialist, LocalDateTime dateTime) {
        Appointment appointment = new Appointment(title, specialist, dateTime);
        repository.addAppointment(appointment);
        notificationCenter.notifyObservers("New appointment scheduled: " + title);
    }

    public List<ChildProfile> getChildren() {
        return repository.getChildren();
    }

    public List<Report> getReports() {
        return repository.getReports();
    }

    public List<Appointment> getAppointments() {
        return repository.getAppointments();
    }

    public List<Report> searchReports(String keyword) {
        return repository.searchReports(keyword);
    }
}
