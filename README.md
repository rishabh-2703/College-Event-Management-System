# College Event Management System

## Overview

The **College Event Management System** is a command-line-based application developed using **Pure Java**. It provides a centralized system for managing college events, student registrations, users, and feedback.

The system supports three types of users:

- **Student** – View events, register for events, and submit feedback.
- **Organizer** – Create and manage events and view registrations.
- **Admin** – Manage users and monitor events.

The project uses Java's file handling mechanism for persistent storage and does not require an external database.

---

## Features

### Student

- Register and log in
- View available events
- View event details
- Register for events
- View registered events
- Submit event feedback
- Prevention of duplicate registrations

### Organizer

- Log in as an organizer
- Create events
- View created events
- Manage event information
- View registered students
- Set event capacity

### Admin

- Admin login
- View users
- View events
- Manage system information

### System Features

- Role-based access
- Object-oriented design
- Inheritance and polymorphism
- Exception handling
- Custom exceptions
- Java Collections
- File I/O
- Input validation
- Event capacity management
- Persistent text-file storage

---

## Technologies Used

- **Java**
- Object-Oriented Programming
- Java Collections Framework
- Exception Handling
- Custom Exceptions
- File I/O
- Enums
- Multithreading
- Git & GitHub

### External Dependencies

No external dependencies are required.

The project does **not** require:

- PostgreSQL
- MySQL
- JDBC
- Maven
- Third-party Java libraries

---

## Requirements

### Software

- Java JDK 17 or later
- Terminal / Command Prompt

Check Java installation:

```bash
java -version
```

Check Java compiler:

```bash
javac -version
```

---

# Installation & Execution

There are two ways to run the project.

## Method 1: Using Run Script

### macOS / Linux

Navigate to the project directory:

```bash
cd CollegeEventManagement_PureJava-2
```

Give execution permission:

```bash
chmod +x run.sh
```

Run:

```bash
./run.sh
```

### Windows

Open Command Prompt or PowerShell inside the project directory and run:

```cmd
run.bat
```

---

## Method 2: Manual Compilation

### macOS / Linux

Navigate to the project:

```bash
cd CollegeEventManagement_PureJava-2
```

Create an output directory:

```bash
mkdir -p out
```

Compile:

```bash
javac -d out $(find src -name "*.java")
```

Run:

```bash
java -cp out com.collegeevent.Main
```

### Windows

Create an output directory:

```cmd
mkdir out
```

Compile:

```cmd
javac -d out -sourcepath src src\com\collegeevent\Main.java
```

Run:

```cmd
java -cp out com.collegeevent.Main
```

---

# Demo Credentials

The following accounts can be used for testing.

### Admin

```text
Email: admin@college.com
Password: admin123
```

### Organizer

```text
Email: organizer@college.com
Password: org123
```

### Student

```text
Email: student@college.com
Password: student123
```

---

# Data Storage

The application stores data locally using text files.

```text
data/
├── users.txt
├── events.txt
├── registrations.txt
└── feedback.txt
```

This allows the application to retain information between executions without requiring a database server.

---

# Project Structure

```text
CollegeEventManagement_PureJava-2/
│
├── src/
│   └── com/
│       └── collegeevent/
│           ├── Main.java
│           │
│           ├── model/
│           │   ├── User.java
│           │   ├── Student.java
│           │   ├── Organizer.java
│           │   ├── Admin.java
│           │   └── Event.java
│           │
│           ├── service/
│           │   └── EventService.java
│           │
│           ├── exception/
│           │   ├── EventFullException.java
│           │   └── DuplicateRegistrationException.java
│           │
│           └── util/
│               └── FileManager.java
│
├── data/
│   ├── users.txt
│   ├── events.txt
│   ├── registrations.txt
│   └── feedback.txt
│
├── logs/
│   └── .gitkeep
│
├── README.md
├── PROJECT_REPORT.md
├── PROJECT_STATEMENT.md
├── TEST_CASES.md
├── VIVA_QUESTIONS.md
├── PRESENTATION_SPEECH.md
├── REQUIREMENTS.md
├── LICENSE.md
│
├── run.sh
├── run.bat
└── run.ps1
```

---

# Testing

The application can be tested through the command line using the provided demo accounts.

Important test scenarios include:

| Test | Expected Result |
|------|-----------------|
| Valid login | User dashboard opens |
| Invalid login | Error message displayed |
| View events | Available events displayed |
| Register for event | Registration successful |
| Register twice | Duplicate registration prevented |
| Register for full event | Event-full error displayed |
| Create event | Event created successfully |
| Submit feedback | Feedback stored successfully |

Detailed test cases are available in:

```text
TEST_CASES.md
```

---

# Documentation

Additional project documentation is available in the repository:

- `PROJECT_REPORT.md` – Detailed academic project report
- `PROJECT_STATEMENT.md` – Problem statement and project scope
- `TEST_CASES.md` – Testing documentation
- `VIVA_QUESTIONS.md` – Viva preparation
- `PRESENTATION_SPEECH.md` – Presentation material
- `REQUIREMENTS.md` – Project requirements

---

# GitHub Repository

Repository:

https://github.com/rishabh-2703/College-Event-Management-System

---

# Author

**Rishabh Rai**

B.Tech Computer Science and Engineering  
Specialization: Artificial Intelligence & Machine Learning

---

