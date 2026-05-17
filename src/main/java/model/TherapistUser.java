package model;

// Concrete Product used by UserFactory.

import model.BaseUser;

public class TherapistUser extends BaseUser {

    public TherapistUser(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "Speech Therapist";
    }
}
