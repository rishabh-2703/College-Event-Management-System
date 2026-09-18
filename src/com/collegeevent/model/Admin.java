package com.collegeevent.model;

public class Admin extends User {
    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password, "ADMIN");
    }

    @Override
    public String toString() {
        return "Admin: " + getName() + " | Email: " + getEmail();
    }
}