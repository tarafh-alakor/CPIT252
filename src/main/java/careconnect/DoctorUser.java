package careconnect;

// Concrete Product used by UserFactory.
public class DoctorUser extends BaseUser {

    public DoctorUser(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "Doctor";
    }
}
