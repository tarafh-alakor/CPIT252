package model;

// Concrete Product used by UserFactory.

import model.BaseUser;

public class TeacherUser extends BaseUser {

    public TeacherUser(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "School Teacher";
    }
}
