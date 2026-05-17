# Weam Platform

A Java Swing desktop application for coordinating care communication for children with disabilities.

## Project Idea

The platform connects the parent, doctor, speech therapist, school teacher, and special teacher in one shared system.  
Each care team member can add reports, view the child's profile, see appointments, search reports, and receive notifications.

## Technology Stack

- Java
- Java Swing
- Object-Oriented Programming
- Design Patterns


## Design Patterns Used

In the Weam project, we used several design patterns to make the code more organized, easier to maintain, and easier to extend in the future.

### 1. Factory Pattern

**Type:** Creational Design Pattern

We used the Factory Pattern to create objects in a cleaner way instead of creating them directly in different parts of the program.

In our project, the Factory Pattern is useful for creating different system objects such as users, reports, or care team members. This helps reduce repeated code and keeps object creation in one clear place.

**Why we used it:**
- To organize object creation.
- To reduce repeated `new` statements.
- To make it easier to add new object types later.
- To keep the UI code cleaner.

---

### 2. Facade Pattern

**Type:** Structural Design Pattern

We used the Facade Pattern to simplify the communication between the user interface and the internal system classes.

Instead of making the UI call many classes directly, the UI communicates with one main class that handles the required operations. This makes the system easier to use and understand.

**Why we used it:**
- To hide complex internal operations.
- To make the UI code simpler.
- To reduce dependency between classes.
- To keep the project structure organized.

---

### 3. Observer Pattern

**Type:** Behavioral Design Pattern

We used the Observer Pattern to update notifications when something changes in the system.

For example, when the mother adds a child, adds a care team member, uploads a report, or requests an appointment, the notification area can be updated automatically.

**Why we used it:**
- To notify related parts of the system when changes happen.
- To update notifications automatically.
- To reduce direct connection between classes.
- To make the system more flexible.

---

### 4. Strategy Pattern

**Type:** Behavioral Design Pattern

We used the Strategy Pattern to support different ways of searching and filtering data.

For example, the system can search reports or information using different strategies, such as searching by content, author, role, or child name. Each search method is separated into its own strategy class.

**Why we used it:**
- To support more than one search method.
- To avoid many `if/else` statements.
- To make the search feature easier to extend.
- To keep each search behavior in a separate class.

---

## Summary

The design patterns used in Weam helped improve the quality of the project. Factory helped with object creation, Facade simplified communication between the UI and the system, Observer helped with automatic notifications, and Strategy made the search feature more flexible.

These patterns were chosen because they solve real problems in the project and make the code cleaner, more organized, and easier to maintain.
## Main Features

- Mother-based login and registration
- The system is designed around the mother dashboard
- The mother can manage her profile and children profiles
- The mother can add care team members
- The mother can request appointments from care team members
- The mother can view reports, notifications, communication, and child progress from one dashboard

## How to Run

Run this class:

```java
app.CareConnectApp
```

## Testing

Run this class:

```java
careconnect.PatternDemoTest
```

It tests the three design patterns without opening the GUI.
