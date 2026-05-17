package careconnect;

// Concrete Product used by UserFactory.
public class TeacherUser extends BaseUser {

    public TeacherUser(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "School Teacher";
    }
}
