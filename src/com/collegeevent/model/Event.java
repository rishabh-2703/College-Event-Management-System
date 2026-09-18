package com.collegeevent.model;

import java.time.LocalDate;

public class Event {
    private int id;
    private String title;
    private String description;
    private LocalDate eventDate;
    private String venue;
    private int capacity;
    private int organizerId;
    private String status;

    public Event() {}

    public Event(int id, String title, String description, LocalDate eventDate,
                 String venue, int capacity, int organizerId, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.venue = venue;
        this.capacity = capacity;
        this.organizerId = organizerId;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getOrganizerId() { return organizerId; }
    public void setOrganizerId(int organizerId) { this.organizerId = organizerId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "\n-----------------------------" +
                "\nEvent ID: " + id +
                "\nTitle: " + title +
                "\nDescription: " + description +
                "\nDate: " + eventDate +
                "\nVenue: " + venue +
                "\nCapacity: " + capacity +
                "\nStatus: " + status +
                "\n-----------------------------";
    }
}