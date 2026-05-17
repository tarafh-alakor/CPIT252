package facade;

import model.Appointment;
import model.CareTeamMember;
import model.MotherProfile;
import repository.CareRepository;
import model.ChildProfile;
import observer.NotificationCenter;
import model.Report;
import model.User;
import strategy.SearchContext;
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
        addChild(name, age, condition, guardian, progress, "");
    }

    public void addChild(String name, int age, String condition, String guardian, int progress, String information) {
        validateText(name, "Child name");
        validateText(condition, "Condition");
        validateText(guardian, "Guardian name");

        if (age <= 0) {
            throw new IllegalArgumentException("Age must be greater than zero.");
        }

        ChildProfile child = new ChildProfile(name.trim(), age, condition.trim(), guardian.trim(), progress);
        child.update(name.trim(), age, condition.trim(), guardian.trim(), progress, safeText(information));
        repository.addChild(child);
        notificationCenter.notifyObservers("New child profile added: " + name.trim());
    }

    public void addReport(User user, String childName, String reportText) {
        validateText(childName, "Child name");
        validateText(reportText, "Report text");

        Report report = user.createReport(childName.trim(), reportText.trim());
        repository.addReport(report);
        notificationCenter.notifyObservers(user.getRole() + " added a new report for " + childName.trim());
    }

    public void addAppointment(String title, String specialist, LocalDateTime dateTime) {
        validateText(title, "Appointment title");
        validateText(specialist, "Specialist");

        Appointment appointment = new Appointment(title.trim(), specialist.trim(), dateTime);
        repository.addAppointment(appointment);
        notificationCenter.notifyObservers("New appointment scheduled: " + title.trim());
    }

    public void updateChild(ChildProfile child, String name, int age, String condition, String guardian, int progress, String information) {
        validateText(name, "Child name");
        validateText(condition, "Condition");
        validateText(guardian, "Guardian name");
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be greater than zero.");
        }
        child.update(name.trim(), age, condition.trim(), guardian.trim(), progress, information.trim());
        notificationCenter.notifyObservers("Child profile updated: " + name.trim());
    }

    public void updateMotherProfile(String name, String phone, String email, String information) {
        validateText(name, "Mother name");
        validateText(phone, "Phone");
        validateText(email, "Email");
        repository.getMotherProfile().update(name.trim(), phone.trim(), email.trim(), information.trim());
        notificationCenter.notifyObservers("Mother profile updated.");
    }

    public void addCareTeamMember(String name, String role, String phone, String email, String notes) {
        validateText(name, "Name");
        validateText(role, "Role");
        CareTeamMember member = new CareTeamMember(name.trim(), role.trim(), phone.trim(), email.trim(), notes.trim());
        repository.addCareTeamMember(member);
        notificationCenter.notifyObservers("Care team member added: " + name.trim() + " (" + role.trim() + ")");
    }

    public MotherProfile getMotherProfile() {
        return repository.getMotherProfile();
    }

    public List<CareTeamMember> getCareTeam() {
        return repository.getCareTeam();
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

    public List<Report> searchReports(SearchContext context, String keyword) {
        validateText(keyword, "Search keyword");
        return context.executeSearch(repository.getReports(), keyword.trim());
    }

    private String safeText(String value) {
        return value == null ? "" : value.trim();
    }

    private void validateText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
    }
}
