package careconnect;

// Represents the child's main profile information.
public class ChildProfile {
    private final String name;
    private final int age;
    private final String condition;
    private final String guardianName;
    private int progressPercentage;

    public ChildProfile(String name, int age, String condition, String guardianName, int progressPercentage) {
        this.name = name;
        this.age = age;
        this.condition = condition;
        this.guardianName = guardianName;
        this.progressPercentage = progressPercentage;
    }

    public String getName() {
        return name;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(int progressPercentage) {
        if (progressPercentage < 0) progressPercentage = 0;
        if (progressPercentage > 100) progressPercentage = 100;
        this.progressPercentage = progressPercentage;
    }

    @Override
    public String toString() {
        return name + " | Age: " + age + " | Condition: " + condition
                + " | Guardian: " + guardianName + " | Progress: " + progressPercentage + "%";
    }
}
