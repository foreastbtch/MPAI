package com.mpai.app.Models.Conference;

import com.mpai.app.Models.Attendee;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class ConferenceAttendee implements Attendee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String company;
    private boolean isVIP;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "attendees")
    private Set<Conference> events = new HashSet<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }

    public Set<Conference> getEvents() {
        return events;
    }

    public void setEvents(Set<Conference> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Conference Attendee";
    }
}

