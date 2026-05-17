# Weam Platform

A Java Swing desktop application designed to support communication and coordination between parents and care team members responsible for children with disabilities.

---

## Project Idea

The Weam platform connects:
- Parent
- Doctor
- Speech Therapist
- School Teacher
- Special Teacher

in one shared system to improve communication and care coordination.

Each care team member can:
- Add reports
- View child profiles
- View appointments
- Search reports
- Receive notifications

The platform is designed around a centralized dashboard that allows the mother to manage child care information from one place.

---

## Technology Stack

- Java
- Java Swing
- Maven
- JUnit 5
- Object-Oriented Programming (OOP)
- Design Patterns

---

## Design Patterns Used

The project uses several design patterns to improve code organization, flexibility, maintainability, and scalability.

---

### 1. Factory Method Pattern

**Type:** Creational Design Pattern

The Factory Method Pattern is used to dynamically create different user roles without tightly coupling the GUI to concrete classes.

Supported user roles:
- Parent
- Doctor
- School Teacher
- Special Teacher
- Speech Therapist

Each role is created through a corresponding factory class.

**Why we used it:**
- To organize object creation
- To reduce repeated `new` statements
- To simplify adding new user roles
- To improve code maintainability

---

### 2. Facade Pattern

**Type:** Structural Design Pattern

The Facade Pattern simplifies communication between the GUI and the internal system classes.

Instead of interacting directly with repositories and subsystems, the GUI communicates through a single facade class.

**Why we used it:**
- To simplify communication between layers
- To reduce coupling between classes
- To hide internal complexity
- To organize the system architecture

---

### 3. Observer Pattern

**Type:** Behavioral Design Pattern

The Observer Pattern is used to automatically update notifications when changes occur in the system.

Notifications are triggered when:
- A child profile is added
- A report is uploaded
- A care team member is added
- An appointment is requested

The notification panel updates automatically through observer communication.

**Why we used it:**
- To support automatic updates
- To reduce direct dependencies
- To improve system flexibility
- To separate notification logic from business logic

---

### 4. Strategy Pattern

**Type:** Behavioral Design Pattern

The Strategy Pattern is used to support multiple report search algorithms dynamically.

Supported search strategies:
- Search by Content
- Search by Author
- Search by Role
- Search by Child Name
- Search All

Each search behavior is implemented in a separate strategy class and selected dynamically at runtime.

**Why we used it:**
- To support multiple search methods
- To avoid large `if/else` statements
- To improve flexibility
- To make the search feature easier to extend

---

## Main Features

- Parent-based login system
- Dashboard-centered interface
- Child profile management
- Mother profile management
- Care team member management
- Report creation and viewing
- Appointment requests
- Notifications system
- Communication section
- Dynamic report searching
- Child progress tracking

---

## Project Structure

```text
app           -> Main application and GUI navigation
facade        -> Facade Pattern implementation
factory       -> Factory Method Pattern classes
model         -> System entities and data models
observer      -> Observer Pattern classes
repository    -> In-memory data storage
strategy      -> Strategy Pattern classes
tests         -> JUnit test classes
ui            -> GUI panels and dashboard cards

---

## How to Run

Run the main application class:

```java
app.CareConnectApp

The system will launch the graphical user interface (GUI) for the Weam platform.
---

## Testing

The project includes separate JUnit test classes for each implemented design pattern and overall system functionality.

Run the test classes inside the `tests` package:

```java
tests.FactoryPatternTest
tests.StrategyPatternTest
tests.ObserverPatternTest
tests.FacadePatternTest
tests.ApplicationTest

## These tests verify:

- Factory Method Pattern behavior
- Strategy Pattern search functionality
- Observer Pattern notifications
- Facade integration
- Overall system operations

## Summary

The design patterns used in Weam helped improve the quality and structure of the project.

- Factory Method simplified object creation
- Facade simplified communication between layers
- Observer enabled automatic notifications
- Strategy improved flexibility of the search system

These patterns solved real system problems and helped make the code cleaner, more modular, and easier to maintain.

## Authors
- Amal Aljohani
- Ghaida Alhadi
- Tarafh Alakor
