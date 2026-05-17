package model;

// Concrete Product used by UserFactory.

import model.BaseUser;

public class DoctorUser extends BaseUser {

    public DoctorUser(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "Doctor";
    }
}
