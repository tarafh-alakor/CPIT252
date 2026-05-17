package repository;

import model.Appointment;
import model.ChildProfile;
import model.CareTeamMember;
import model.MotherProfile;
import model.Report;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Shared application data storage.
// Kept simple for the course project: in-memory storage.
public class CareRepository {
    private final List<ChildProfile> children = new ArrayList<>();
    private final List<Report> reports = new ArrayList<>();
    private final List<Appointment> appointments = new ArrayList<>();
    private final List<CareTeamMember> careTeam = new ArrayList<>();
    private final MotherProfile motherProfile = new MotherProfile("Tarafh", "05XXXXXXXX", "mother@example.com", "Main guardian for the child.");

    public CareRepository() {
        // Sample data to make the demo look complete from the first run.
        children.add(new ChildProfile("Jana Ahmad", 6, "Hearing impairment", "Walaa Nasser", 75));
        reports.add(new Report("Jana Ahmad", "Speech Therapist", "Amani", "Speech session completed. The child improved pronunciation."));
        reports.add(new Report("Jana Ahmad", "School Teacher", "Reem", "Good classroom participation today."));
        appointments.add(new Appointment("Speech Session", "Amani", LocalDateTime.now().plusDays(1).withHour(10).withMinute(0)));
        appointments.add(new Appointment("Medical Follow-up", "Dr. Sarah", LocalDateTime.now().plusDays(3).withHour(11).withMinute(30)));
        careTeam.add(new CareTeamMember("Dr. Sarah", "Doctor", "0500000001", "sarah@example.com", "ENT follow-up."));
        careTeam.add(new CareTeamMember("Amani", "Speech Therapist", "0500000002", "amani@example.com", "Speech therapy sessions."));
        careTeam.add(new CareTeamMember("Reem", "Teacher", "0500000003", "reem@example.com", "School progress notes."));
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
        return Collections.unmodifiableList(children);
    }

    public List<Report> getReports() {
        return Collections.unmodifiableList(reports);
    }

    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(appointments);
    }

    public MotherProfile getMotherProfile() {
        return motherProfile;
    }

    public void addCareTeamMember(CareTeamMember member) {
        careTeam.add(member);
    }

    public List<CareTeamMember> getCareTeam() {
        return Collections.unmodifiableList(careTeam);
    }
}
