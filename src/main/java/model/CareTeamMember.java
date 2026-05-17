package model;

// Represents one person in the child's care team.
public class CareTeamMember {
    private final String name;
    private final String role;
    private final String phone;
    private final String email;
    private final String notes;

    public CareTeamMember(String name, String role, String phone, String email, String notes) {
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.notes = notes;
    }

    public String getName() { return name; }
    public String getRole() { return role; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getNotes() { return notes; }

    @Override
    public String toString() {
        return role + ": " + name + " | Phone: " + phone + " | Email: " + email
                + "\nNotes: " + notes;
    }
}
