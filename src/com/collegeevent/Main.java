package com.collegeevent;

import com.collegeevent.exception.DuplicateRegistrationException;
import com.collegeevent.exception.EventFullException;
import com.collegeevent.model.Event;
import com.collegeevent.model.User;
import com.collegeevent.service.EventService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final EventService service = new EventService();

    public static void main(String[] args) {
        System.out.println("\n======================================");
        System.out.println("   COLLEGE EVENT MANAGEMENT SYSTEM");
        System.out.println("          PURE JAVA VERSION");
        System.out.println("======================================");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> login();
                case "2" -> register();
                case "0" -> {
                    System.out.println("Thank you for using the system.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void login() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = service.login(email, password);

        if (user == null) {
            System.out.println("Invalid email or password.");
            return;
        }

        System.out.println("\nWelcome, " + user.getName());

        switch (user.getRole()) {
            case "STUDENT" -> studentMenu(user);
            case "ORGANIZER" -> organizerMenu(user);
            case "ADMIN" -> adminMenu();
            default -> System.out.println("Unknown role.");
        }
    }

    private static void register() {
        System.out.println("\n--- New User Registration ---");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.println("1. Student");
        System.out.println("2. Organizer");
        System.out.print("Select role: ");
        String choice = sc.nextLine();

        String role = switch (choice) {
            case "1" -> "STUDENT";
            case "2" -> "ORGANIZER";
            default -> "";
        };

        if (role.isEmpty()) {
            System.out.println("Invalid role.");
            return;
        }

        try {
            service.registerUser(name, email, password, role);
            System.out.println("Registration successful.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void studentMenu(User user) {
        while (true) {
            System.out.println("\n===== STUDENT MENU =====");
            System.out.println("1. View Approved Events");
            System.out.println("2. Register for Event");
            System.out.println("3. My Registrations");
            System.out.println("4. Cancel Registration");
            System.out.println("5. Give Feedback");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1" -> showEvents(service.approvedEvents());

                    case "2" -> {
                        int eventId = readInt("Event ID: ");
                        service.registerForEvent(user.getId(), eventId);
                        System.out.println("Registration successful.");
                    }

                    case "3" -> {
                        List<String> list = service.studentRegistrations(user.getId());
                        if (list.isEmpty()) System.out.println("No registrations found.");
                        else list.forEach(System.out::println);
                    }

                    case "4" -> {
                        int eventId = readInt("Event ID: ");
                        System.out.println(service.cancelRegistration(user.getId(), eventId)
                                ? "Registration cancelled."
                                : "Registration not found.");
                    }

                    case "5" -> {
                        int eventId = readInt("Event ID: ");
                        int rating = readInt("Rating (1-5): ");
                        System.out.print("Comment: ");
                        String comment = sc.nextLine();
                        service.addFeedback(user.getId(), eventId, rating, comment);
                        System.out.println("Feedback submitted.");
                    }

                    case "0" -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (EventFullException | DuplicateRegistrationException |
                     IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void organizerMenu(User user) {
        while (true) {
            System.out.println("\n===== ORGANIZER MENU =====");
            System.out.println("1. Create Event");
            System.out.println("2. My Events");
            System.out.println("3. View Participants");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1" -> createEvent(user);
                    case "2" -> showEvents(service.organizerEvents(user.getId()));
                    case "3" -> {
                        int eventId = readInt("Event ID: ");
                        List<String> list = service.participants(eventId);
                        if (list.isEmpty()) System.out.println("No participants.");
                        else list.forEach(System.out::println);
                    }
                    case "0" -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void createEvent(User user) {
        System.out.print("Event title: ");
        String title = sc.nextLine();

        System.out.print("Description: ");
        String description = sc.nextLine();

        System.out.print("Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(sc.nextLine());

        System.out.print("Venue: ");
        String venue = sc.nextLine();

        int capacity = readInt("Capacity: ");

        int id = service.createEvent(
                title, description, date, venue, capacity, user.getId()
        );

        System.out.println("Event created with ID: " + id);
        System.out.println("Waiting for admin approval.");
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. View All Events");
            System.out.println("2. Approve Event");
            System.out.println("3. Reject Event");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            try {
                switch (choice) {
                    case "1" -> showEvents(service.allEvents());
                    case "2" -> updateStatus("APPROVED");
                    case "3" -> updateStatus("REJECTED");
                    case "0" -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void updateStatus(String status) {
        int eventId = readInt("Event ID: ");
        if (service.updateStatus(eventId, status)) {
            System.out.println("Event status changed to " + status);
        } else {
            System.out.println("Event not found.");
        }
    }

    private static void showEvents(List<Event> events) {
        if (events.isEmpty()) {
            System.out.println("No events found.");
            return;
        }
        for (Event event : events) System.out.println(event);
    }

    private static int readInt(String message) {
        System.out.print(message);
        return Integer.parseInt(sc.nextLine());
    }
}