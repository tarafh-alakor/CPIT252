package model;

// Concrete Product used by UserFactory.

import model.BaseUser;

public class ParentUser extends BaseUser {

    public ParentUser(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "Parent";
    }
}
