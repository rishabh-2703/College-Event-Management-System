package com.collegeevent.util;

import com.collegeevent.model.Event;
import com.collegeevent.model.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = DATA_DIR + File.separator + "users.txt";
    private static final String EVENTS_FILE = DATA_DIR + File.separator + "events.txt";
    private static final String REG_FILE = DATA_DIR + File.separator + "registrations.txt";
    private static final String FEEDBACK_FILE = DATA_DIR + File.separator + "feedback.txt";

    public FileManager() {
        new File(DATA_DIR).mkdirs();
        createIfMissing(USERS_FILE);
        createIfMissing(EVENTS_FILE);
        createIfMissing(REG_FILE);
        createIfMissing(FEEDBACK_FILE);
    }

    private void createIfMissing(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) f.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Could not create data file: " + e.getMessage(), e);
        }
    }

    public synchronized List<User> loadUsers() {
        List<User> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] p = line.split("\\|", -1);
                if (p.length >= 5) {
                    list.add(new User(Integer.parseInt(p[0]), p[1], p[2], p[3], p[4]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Could not read users: " + e.getMessage(), e);
        }
        return list;
    }

    public synchronized void saveUser(User u) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            bw.write(u.getId() + "|" + clean(u.getName()) + "|" + clean(u.getEmail()) + "|" +
                    clean(u.getPassword()) + "|" + u.getRole());
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Could not save user: " + e.getMessage(), e);
        }
    }

    public synchronized List<Event> loadEvents() {
        List<Event> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(EVENTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] p = line.split("\\|", -1);
                if (p.length >= 8) {
                    list.add(new Event(
                            Integer.parseInt(p[0]), p[1], p[2], LocalDate.parse(p[3]),
                            p[4], Integer.parseInt(p[5]), Integer.parseInt(p[6]), p[7]
                    ));
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Could not read events: " + e.getMessage(), e);
        }
        return list;
    }

    public synchronized void saveEvent(Event e) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(EVENTS_FILE, true))) {
            bw.write(e.getId() + "|" + clean(e.getTitle()) + "|" + clean(e.getDescription()) + "|" +
                    e.getEventDate() + "|" + clean(e.getVenue()) + "|" + e.getCapacity() + "|" +
                    e.getOrganizerId() + "|" + e.getStatus());
            bw.newLine();
        } catch (IOException ex) {
            throw new RuntimeException("Could not save event: " + ex.getMessage(), ex);
        }
    }

    public synchronized void updateEventStatus(int eventId, String status) {
        List<Event> events = loadEvents();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(EVENTS_FILE))) {
            for (Event e : events) {
                if (e.getId() == eventId) e.setStatus(status);
                bw.write(e.getId() + "|" + clean(e.getTitle()) + "|" + clean(e.getDescription()) + "|" +
                        e.getEventDate() + "|" + clean(e.getVenue()) + "|" + e.getCapacity() + "|" +
                        e.getOrganizerId() + "|" + e.getStatus());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not update event: " + e.getMessage(), e);
        }
    }

    public synchronized void saveRegistration(int studentId, int eventId) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(REG_FILE, true))) {
            bw.write(studentId + "|" + eventId + "|ACTIVE");
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Could not save registration: " + e.getMessage(), e);
        }
    }

    public synchronized List<String> loadRegistrations() {
        return readLines(REG_FILE);
    }

    public synchronized void cancelRegistration(int studentId, int eventId) {
        List<String> lines = loadRegistrations();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(REG_FILE))) {
            for (String line : lines) {
                String[] p = line.split("\\|", -1);
                if (p.length >= 3 && Integer.parseInt(p[0]) == studentId &&
                        Integer.parseInt(p[1]) == eventId && "ACTIVE".equals(p[2])) {
                    p[2] = "CANCELLED";
                    line = String.join("|", p);
                }
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Could not cancel registration: " + e.getMessage(), e);
        }
    }

    public synchronized void saveFeedback(int studentId, int eventId, int rating, String comment) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FEEDBACK_FILE, true))) {
            bw.write(studentId + "|" + eventId + "|" + rating + "|" + clean(comment));
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Could not save feedback: " + e.getMessage(), e);
        }
    }

    private List<String> readLines(String path) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) list.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read file: " + e.getMessage(), e);
        }
        return list;
    }

    private String clean(String value) {
        if (value == null) return "";
        return value.replace("|", "/").replace("\n", " ").replace("\r", " ");
    }
}