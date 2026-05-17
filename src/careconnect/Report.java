package careconnect;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Represents a report added by any care team member.
public class Report {
    private final String childName;
    private final String role;
    private final String authorName;
    private final String text;
    private final LocalDateTime createdAt;

    public Report(String childName, String role, String authorName, String text) {
        this.childName = childName;
        this.role = role;
        this.authorName = authorName;
        this.text = text;
        this.createdAt = LocalDateTime.now();
    }

    public String getChildName() {
        return childName;
    }

    public String getRole() {
        return role;
    }

    public String getText() {
        return text;
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createdAt.format(formatter);
    }

    @Override
    public String toString() {
        return getFormattedDate() + " | " + role + " (" + authorName + ")"
                + "\nChild: " + childName
                + "\nReport: " + text + "\n";
    }
}
