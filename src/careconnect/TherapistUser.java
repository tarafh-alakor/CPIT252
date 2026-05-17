package careconnect;

// Concrete Product used by UserFactory.
public class TherapistUser extends BaseUser {

    public TherapistUser(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "Speech Therapist";
    }
}
