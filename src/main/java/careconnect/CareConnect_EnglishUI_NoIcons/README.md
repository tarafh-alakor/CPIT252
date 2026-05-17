# CareConnect Platform

A Java Swing desktop application for coordinating care communication for children with disabilities.

## Project Idea

CareConnect connects the parent, doctor, speech therapist, school teacher, and special teacher in one shared system.  
Each care team member can add reports, view the child's profile, see appointments, search reports, and receive notifications.

## Technology Stack

- Java
- Java Swing
- Object-Oriented Programming
- Design Patterns

## Design Patterns Used

### 1. Factory Method - Creational Pattern

Used in `UserFactory`.

**Why?**  
The system has different user roles such as Parent, Doctor, School Teacher, Special Teacher, and Speech Therapist.  
Instead of creating each user directly inside the GUI, `UserFactory` creates the correct object based on the selected role.

### 2. Facade - Structural Pattern

Used in `CarePlatformFacade`.

**Why?**  
The GUI should not deal with many subsystem classes such as repository, reports, appointments, and notifications.  
The facade provides one simple interface for the GUI.

### 3. Observer - Behavioral Pattern

Used in `NotificationCenter`, `NotificationObserver`, and `DashboardNotificationPanel`.

**Why?**  
When a new report, appointment, or child profile is added, the notification panel updates automatically without tightly coupling the GUI to the data logic.

## Main Features

- Role-based login
- Child profile management
- Report management
- Appointment management
- Timeline dashboard
- Notifications
- Search reports
- Care team view
- English-only interface
- Purple and peach-pink GUI inspired by the reference dashboard
- No text icons in the interface
- Custom drawn child picture inside the dashboard

## How to Run

Run this class:

```java
careconnect.CareConnectApp
```

## Testing

Run this class:

```java
careconnect.PatternDemoTest
```

It tests the three design patterns without opening the GUI.
