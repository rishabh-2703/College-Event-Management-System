package com.collegeevent.model;

public class Organizer extends User {
    public Organizer(int id, String name, String email, String password) {
        super(id, name, email, password, "ORGANIZER");
    }

    @Override
    public String toString() {
        return "Organizer: " + getName() + " | Email: " + getEmail();
    }
}