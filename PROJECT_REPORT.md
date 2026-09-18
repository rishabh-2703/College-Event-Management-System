# PROJECT REPORT

# College Event Management System

## 1. Abstract
The College Event Management System is a console-based application developed using Core Java. It provides a structured way to manage college events, users and registrations. The system uses object-oriented programming, collections, exception handling, file handling, multithreading and synchronization.

## 2. Objectives
- Automate college event registration.
- Reduce manual record keeping.
- Prevent duplicate registrations.
- Enforce event capacity.
- Provide separate student, organizer and admin functions.
- Demonstrate Core Java concepts in a practical project.

## 3. Functional Requirements
- User registration and login.
- Student event browsing and registration.
- Organizer event creation.
- Admin event approval/rejection.
- Registration cancellation.
- Participant listing.
- Feedback submission.
- File-based persistent storage.

## 4. Non-Functional Requirements
- Simple console interface.
- Reliable local storage.
- Input validation.
- Maintainable object-oriented structure.
- No external database dependency.

## 5. Modules
### User Module
Handles registration and login.

### Event Module
Handles creation and management of events.

### Registration Module
Handles student registrations, duplicate checking and capacity.

### Admin Module
Handles event approval and rejection.

### Feedback Module
Stores student feedback.

### File Management Module
Reads and writes persistent text files.

## 6. OOP Concepts
### Encapsulation
Class data is private and accessed through getters/setters.

### Inheritance
Student, Organizer and Admin extend User.

### Polymorphism
The child classes override `toString()`.

### Abstraction
Service classes hide file-management and business logic from the UI.

## 7. Collections
`ArrayList` is used to maintain users, events and result lists.

## 8. Exception Handling
The project uses:
- `try-catch`
- `throw`
- custom exceptions
- multiple exception handling
- input validation

Custom exceptions:
- `EventFullException`
- `DuplicateRegistrationException`

## 9. File I/O
The `FileManager` uses:
- FileReader
- FileWriter
- BufferedReader
- BufferedWriter

Data is stored in TXT files.

## 10. Multithreading
A background `NotificationThread` runs after successful event registration to demonstrate thread creation.

## 11. Synchronization
Important file operations and registration operations use `synchronized` methods to protect shared data during concurrent execution.

## 12. Algorithms
### Event Registration
1. Find the event.
2. Check approval status.
3. Check duplicate registration.
4. Count active registrations.
5. Compare count with capacity.
6. Save registration.
7. Start notification thread.

## 13. Testing
The system should be tested for:
- Valid login
- Invalid login
- User registration
- Event creation
- Admin approval
- Successful registration
- Duplicate registration
- Full event
- Cancellation
- Feedback validation

## 14. Advantages
- Easy to run.
- No database configuration.
- Demonstrates major Core Java concepts.
- Persistent local data.
- Simple and modular.

## 15. Limitations
- Console interface only.
- Text files are not suitable for large-scale deployment.
- Passwords are stored as plain text for academic demonstration.
- Single-machine application.

## 16. Future Enhancements
- GUI using JavaFX/Swing.
- Web interface.
- Database integration.
- Password hashing.
- Email notifications.
- Attendance QR codes.
- Reports and analytics.

## 17. Conclusion
The College Event Management System successfully demonstrates how Core Java can be used to build a practical event management application. It combines OOP, collections, exception handling, file I/O, multithreading and synchronization in one project.
