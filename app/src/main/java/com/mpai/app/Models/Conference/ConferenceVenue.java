package com.mpai.app.Models.Conference;

import com.mpai.app.Models.Venue;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class ConferenceVenue implements Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private int capacity;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "venues")
    private Set<Conference> events = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Set<Conference> getEvents() {
        return events;
    }

    public void setEvents(Set<Conference> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Conference Venue";
    }
}

