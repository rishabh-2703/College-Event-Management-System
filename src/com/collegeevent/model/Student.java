package com.collegeevent.model;

public class Student extends User {
    public Student(int id, String name, String email, String password) {
        super(id, name, email, password, "STUDENT");
    }

    @Override
    public String toString() {
        return "Student: " + getName() + " | Email: " + getEmail();
    }
}