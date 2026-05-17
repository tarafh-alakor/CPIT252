package careconnect;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Represents an appointment for the child.
public class Appointment {
    private final String title;
    private final String specialist;
    private final LocalDateTime dateTime;

    public Appointment(String title, String specialist, LocalDateTime dateTime) {
        this.title = title;
        this.specialist = specialist;
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return formatter.format(dateTime) + " | " + title + " with " + specialist;
    }
}
