package com.collegeevent.service;

import com.collegeevent.exception.DuplicateRegistrationException;
import com.collegeevent.exception.EventFullException;
import com.collegeevent.model.Event;
import com.collegeevent.model.User;
import com.collegeevent.util.FileManager;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventService {
    private final FileManager fileManager;
    private final List<User> users;
    private final List<Event> events;

    public EventService() {
        fileManager = new FileManager();
        users = fileManager.loadUsers();
        events = fileManager.loadEvents();

        if (users.isEmpty()) {
            users.add(new User(1, "Admin", "admin@college.com", "admin123", "ADMIN"));
            users.add(new User(2, "College Organizer", "organizer@college.com", "org123", "ORGANIZER"));
            users.add(new User(3, "Demo Student", "student@college.com", "student123", "STUDENT"));
            for (User u : users) fileManager.saveUser(u);
        }
    }

    public User login(String email, String password) {
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public synchronized User registerUser(String name, String email, String password, String role) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("All fields are required.");
        }
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Email already registered.");
            }
        }
        int id = nextUserId();
        User user = new User(id, name, email, password, role);
        users.add(user);
        fileManager.saveUser(user);
        return user;
    }

    public synchronized int createEvent(String title, String description, LocalDate date,
                                         String venue, int capacity, int organizerId) {
        if (title.isBlank() || venue.isBlank()) {
            throw new IllegalArgumentException("Title and venue are required.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Event date cannot be in the past.");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }

        int id = nextEventId();
        Event e = new Event(id, title, description, date, venue, capacity,
                organizerId, "PENDING");
        events.add(e);
        fileManager.saveEvent(e);
        return id;
    }

    public List<Event> approvedEvents() {
        List<Event> result = new ArrayList<>();
        for (Event e : events) {
            if ("APPROVED".equals(e.getStatus())) result.add(e);
        }
        return result;
    }

    public List<Event> allEvents() {
        return new ArrayList<>(events);
    }

    public List<Event> organizerEvents(int organizerId) {
        List<Event> result = new ArrayList<>();
        for (Event e : events) {
            if (e.getOrganizerId() == organizerId) result.add(e);
        }
        return result;
    }

    public synchronized void registerForEvent(int studentId, int eventId)
            throws EventFullException, DuplicateRegistrationException {
        Event event = findEvent(eventId);
        if (event == null) throw new IllegalArgumentException("Event not found.");
        if (!"APPROVED".equals(event.getStatus())) {
            throw new IllegalArgumentException("Only approved events can be registered.");
        }

        for (String line : fileManager.loadRegistrations()) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 3 && Integer.parseInt(p[0]) == studentId &&
                    Integer.parseInt(p[1]) == eventId && "ACTIVE".equals(p[2])) {
                throw new DuplicateRegistrationException("You are already registered.");
            }
        }

        int count = activeRegistrationCount(eventId);
        if (count >= event.getCapacity()) {
            throw new EventFullException("Event is full.");
        }

        fileManager.saveRegistration(studentId, eventId);

        Thread notification = new NotificationThread(studentId, event.getTitle());
        notification.start();
    }

    public synchronized boolean cancelRegistration(int studentId, int eventId) {
        for (String line : fileManager.loadRegistrations()) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 3 && Integer.parseInt(p[0]) == studentId &&
                    Integer.parseInt(p[1]) == eventId && "ACTIVE".equals(p[2])) {
                fileManager.cancelRegistration(studentId, eventId);
                return true;
            }
        }
        return false;
    }

    public List<String> studentRegistrations(int studentId) {
        List<String> result = new ArrayList<>();
        for (String line : fileManager.loadRegistrations()) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 3 && Integer.parseInt(p[0]) == studentId) {
                Event e = findEvent(Integer.parseInt(p[1]));
                if (e != null) {
                    result.add("Event ID: " + e.getId() + " | " + e.getTitle() +
                            " | Date: " + e.getEventDate() + " | Venue: " +
                            e.getVenue() + " | Status: " + p[2]);
                }
            }
        }
        return result;
    }

    public List<String> participants(int eventId) {
        List<String> result = new ArrayList<>();
        for (String line : fileManager.loadRegistrations()) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 3 && Integer.parseInt(p[1]) == eventId && "ACTIVE".equals(p[2])) {
                User u = findUser(Integer.parseInt(p[0]));
                if (u != null) result.add("Student ID: " + u.getId() +
                        " | Name: " + u.getName() + " | Email: " + u.getEmail());
            }
        }
        return result;
    }

    public synchronized boolean updateStatus(int eventId, String status) {
        Event e = findEvent(eventId);
        if (e == null) return false;
        e.setStatus(status);
        fileManager.updateEventStatus(eventId, status);
        return true;
    }

    public void addFeedback(int studentId, int eventId, int rating, String comment) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        if (findEvent(eventId) == null) {
            throw new IllegalArgumentException("Event not found.");
        }
        fileManager.saveFeedback(studentId, eventId, rating, comment);
    }

    private int activeRegistrationCount(int eventId) {
        int count = 0;
        for (String line : fileManager.loadRegistrations()) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 3 && Integer.parseInt(p[1]) == eventId && "ACTIVE".equals(p[2])) {
                count++;
            }
        }
        return count;
    }

    private Event findEvent(int id) {
        for (Event e : events) if (e.getId() == id) return e;
        return null;
    }

    private User findUser(int id) {
        for (User u : users) if (u.getId() == id) return u;
        return null;
    }

    private int nextUserId() {
        int max = 0;
        for (User u : users) max = Math.max(max, u.getId());
        return max + 1;
    }

    private int nextEventId() {
        int max = 0;
        for (Event e : events) max = Math.max(max, e.getId());
        return max + 1;
    }

    private static class NotificationThread extends Thread {
        private final int studentId;
        private final String eventName;

        NotificationThread(int studentId, String eventName) {
            this.studentId = studentId;
            this.eventName = eventName;
        }

        @Override
        public void run() {
            System.out.println("\n[Background Notification] Student " + studentId +
                    ": Registration confirmed for " + eventName);
        }
    }
}