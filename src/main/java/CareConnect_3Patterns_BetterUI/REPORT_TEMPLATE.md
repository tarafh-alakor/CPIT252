# CareConnect Project Report

## Project Title
CareConnect Platform for Children with Disabilities

## Problem
Children with disabilities often interact with multiple parties: parent, doctor, speech therapist, school teacher, and special teacher.  
Communication is often handled only through the parent, which may cause missing updates, delayed reports, and incomplete understanding of the child’s condition.

## Proposed Solution
CareConnect is a Java Swing desktop platform that allows all care team members to share reports, appointments, updates, and notifications in one place.

## Platform
Desktop Application

## Technology Stack
Java + Java Swing

## Design Patterns

### Factory Method
Class: `UserFactory`

Used to create users dynamically depending on the selected role.

### Facade
Class: `CarePlatformFacade`

Used to simplify communication between the GUI and the system logic.

### Observer
Classes: `NotificationCenter`, `NotificationObserver`, `DashboardNotificationPanel`

Used to automatically notify the dashboard when new actions happen.

## Main Features
- Login by role
- Child profile management
- Report management
- Appointment management
- Timeline
- Notifications
- Search

## Code Quality
- Clear class responsibilities
- Encapsulation
- Comments
- Separation between GUI and logic
- Design patterns solve real design problems

## Demo Scenario
1. Login as Doctor.
2. Open child profile.
3. Add a new medical report.
4. View report timeline.
5. Check notification panel.
6. Search for the report.
