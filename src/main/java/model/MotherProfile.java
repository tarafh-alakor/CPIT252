package model;

// Stores the mother's editable profile information.
public class MotherProfile {
    private String name;
    private String phone;
    private String email;
    private String information;

    public MotherProfile(String name, String phone, String email, String information) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.information = information;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getInformation() { return information; }

    public void update(String name, String phone, String email, String information) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.information = information;
    }

    @Override
    public String toString() {
        return "Mother: " + name + " | Phone: " + phone + " | Email: " + email
                + "\nInfo: " + information;
    }
}
