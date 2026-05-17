package factory;

import model.DoctorUser;
import model.ParentUser;
import model.SpecialTeacherUser;
import model.TeacherUser;
import model.TherapistUser;
import model.User;

// CREATIONAL PATTERN: Factory Method.
// The abstract factory defines the factory method createUser().
// Each concrete factory decides which concrete User class to instantiate.
// This avoids putting object creation decisions inside the GUI.
public abstract class UserFactory {

    public abstract User createUser(String name);

    protected void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name is required.");
        }
    }

    public static UserFactory forRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role is required.");
        }

        switch (role.trim()) {
            case "Parent":
            case "Mother":
                return new ParentUserFactory();
            case "Doctor":
                return new DoctorUserFactory();
            case "School Teacher":
            case "Teacher":
                return new TeacherUserFactory();
            case "Special Teacher":
                return new SpecialTeacherUserFactory();
            case "Speech Therapist":
                return new TherapistUserFactory();
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}

class ParentUserFactory extends UserFactory {

    @Override
    public User createUser(String name) {
        validateName(name);
        return new ParentUser(name.trim());
    }
}

class DoctorUserFactory extends UserFactory {

    @Override
    public User createUser(String name) {
        validateName(name);
        return new DoctorUser(name.trim());
    }
}

class TeacherUserFactory extends UserFactory {

    @Override
    public User createUser(String name) {
        validateName(name);
        return new TeacherUser(name.trim());
    }
}

class SpecialTeacherUserFactory extends UserFactory {

    @Override
    public User createUser(String name) {
        validateName(name);
        return new SpecialTeacherUser(name.trim());
    }
}

class TherapistUserFactory extends UserFactory {

    @Override
    public User createUser(String name) {
        validateName(name);
        return new TherapistUser(name.trim());
    }
}
