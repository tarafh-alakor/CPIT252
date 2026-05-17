package careconnect;

// CREATIONAL PATTERN: Factory Method.
// It creates the correct User object based on the selected role.
// The GUI does not need to know which concrete class to instantiate.
public class UserFactory {

    public User createUser(String role, String name) {
        if (role == null || name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Role and name are required.");
        }

        switch (role) {
            case "Parent":
                return new ParentUser(name);
            case "Doctor":
                return new DoctorUser(name);
            case "School Teacher":
                return new TeacherUser(name);
            case "Special Teacher":
                return new SpecialTeacherUser(name);
            case "Speech Therapist":
                return new TherapistUser(name);
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}
