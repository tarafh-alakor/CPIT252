package model;

// Common parent class to avoid repeating code in each user role class.

public abstract class BaseUser implements User {
    private final String name;

    public BaseUser(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Report createReport(String childName, String reportText) {
        return new Report(childName, getRole(), name, reportText);
    }
}
