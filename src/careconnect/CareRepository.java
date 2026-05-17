package careconnect;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Shared application data storage.
// Kept simple for the course project: in-memory storage.
public class CareRepository {
    private final List<ChildProfile> children = new ArrayList<>();
    private final List<Report> reports = new ArrayList<>();
    private final List<Appointment> appointments = new ArrayList<>();

    public CareRepository() {
        // Sample data to make the demo look complete from the first run.
        children.add(new ChildProfile("Jana Ahmad", 6, "Hearing impairment", "Walaa Nasser", 75));
        reports.add(new Report("Jana Ahmad", "Speech Therapist", "Amani", "Speech session completed. The child improved pronunciation."));
        reports.add(new Report("Jana Ahmad", "School Teacher", "Reem", "Good classroom participation today."));
        appointments.add(new Appointment("Speech Session", "Amani", LocalDateTime.now().plusDays(1).withHour(10).withMinute(0)));
        appointments.add(new Appointment("Medical Follow-up", "Dr. Sarah", LocalDateTime.now().plusDays(3).withHour(11).withMinute(30)));
    }

    public void addChild(ChildProfile child) {
        children.add(child);
    }

    public void addReport(Report report) {
        reports.add(report);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public List<ChildProfile> getChildren() {
        return children;
    }

    public List<Report> getReports() {
        return reports;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<Report> searchReports(String keyword) {
        List<Report> result = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Report report : reports) {
            if (report.getText().toLowerCase().contains(lowerKeyword)
                    || report.getRole().toLowerCase().contains(lowerKeyword)
                    || report.getChildName().toLowerCase().contains(lowerKeyword)) {
                result.add(report);
            }
        }

        return result;
    }
}
