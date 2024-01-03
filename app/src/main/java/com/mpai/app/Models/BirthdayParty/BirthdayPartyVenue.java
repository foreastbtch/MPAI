package com.mpai.app.Models.BirthdayParty;

import com.mpai.app.Models.Venue;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class BirthdayPartyVenue implements Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private int capacity;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "venues")
    private Set<BirthdayParty> events = new HashSet<>();

    public BirthdayPartyVenue() {
    }

    public BirthdayPartyVenue(String name, String address, int capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

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

    public Set<BirthdayParty> getEvents() {
        return events;
    }

    public void setEvents(Set<BirthdayParty> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Birthday Party Venue";
    }
}
