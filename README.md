# College Event Management System - Pure Java

A console-based College Event Management System developed using **Core Java only**.

## No PostgreSQL. No JDBC. No Maven.

### Features
- Student, Organizer and Admin roles
- User registration and login
- Event creation
- Admin approval/rejection
- Event registration
- Capacity checking
- Duplicate registration prevention
- Registration cancellation
- Participant list
- Feedback
- File handling using TXT files
- Exception handling
- Inheritance and method overriding
- Collections using ArrayList
- Multithreading
- Synchronization
- Packages and access modifiers

## Requirements

- JDK 17 or newer
- Any terminal
- No external library required

## Run on macOS/Linux

From the project folder:

```bash
rm -rf out
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out com.collegeevent.Main
```

## Run on Windows CMD

```cmd
rmdir /s /q out
mkdir out
javac -d out src\com\collegeevent\Main.java src\com\collegeevent\model\*.java src\com\collegeevent\service\*.java src\com\collegeevent\exception\*.java src\com\collegeevent\util\*.java
java -cp out com.collegeevent.Main
```

If `out` does not exist, the first `rmdir` command may show an error; that is harmless.

## Demo Accounts

**Admin**
- Email: `admin@college.com`
- Password: `admin123`

**Organizer**
- Email: `organizer@college.com`
- Password: `org123`

**Student**
- Email: `student@college.com`
- Password: `student123`

## Data Storage

The application automatically creates:

```text
data/users.txt
data/events.txt
data/registrations.txt
data/feedback.txt
```

No database is required.

## Project Structure

```text
src/com/collegeevent/
├── Main.java
├── model/
├── service/
├── exception/
└── util/
```

## Reset Demo Data

Delete the files inside `data/` and run the application again. Demo accounts will be recreated.
