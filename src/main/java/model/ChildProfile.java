package model;

// Represents the child's main profile information.
public class ChildProfile {

    private String name;
    private int age;
    private String condition;
    private String guardianName;
    private int progressPercentage;
    private String information;

    public ChildProfile(String name, int age, String condition, String guardianName, int progressPercentage) {
        this(name, age, condition, guardianName, progressPercentage, "No extra information added.");
    }

    public ChildProfile(String name, int age, String condition, String guardianName, int progressPercentage, String information) {
        this.name = name;
        this.age = age;
        this.condition = condition;
        this.guardianName = guardianName;
        this.information = information;
        setProgressPercentage(progressPercentage);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCondition() {
        return condition;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public String getInformation() {
        return information;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    // Update method used in Observer pattern notifications
    public void update(String name, int age, String condition, String guardianName, int progressPercentage, String information) {
        this.name = name;
        this.age = age;
        this.condition = condition;
        this.guardianName = guardianName;
        this.information = information;
        setProgressPercentage(progressPercentage);
    }

    public void setProgressPercentage(int progressPercentage) {
        if (progressPercentage < 0) {
            progressPercentage = 0;
        }
        if (progressPercentage > 100) {
            progressPercentage = 100;
        }
        this.progressPercentage = progressPercentage;
    }

    @Override
    public String toString() {
        return name + " | Age: " + age + " | Condition: " + condition
                + " | Guardian: " + guardianName + " | Progress: " + progressPercentage + "%"
                + "\nInfo: " + information;
    }
}
