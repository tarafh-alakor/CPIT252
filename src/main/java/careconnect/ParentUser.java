package careconnect;

// Concrete Product used by UserFactory.
public class ParentUser extends BaseUser {

    public ParentUser(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "Parent";
    }
}
