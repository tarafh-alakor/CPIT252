package careconnect;

// Concrete Product used by UserFactory.
public class SpecialTeacherUser extends BaseUser {

    public SpecialTeacherUser(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "Special Teacher";
    }
}
