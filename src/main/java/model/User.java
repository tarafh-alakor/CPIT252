package model;

// Product interface for Factory Method.

import model.Report;

// All user roles in the system must follow this same interface.
public interface User {
    String getName();
    String getRole();

    // Each user can write a report, but the role is attached automatically.
    Report createReport(String childName, String reportText);
}
