package model;

// Concrete Product used by UserFactory.

import model.BaseUser;

public class SpecialTeacherUser extends BaseUser {

    public SpecialTeacherUser(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "Special Teacher";
    }
}
