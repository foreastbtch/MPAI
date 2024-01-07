package com.mpai.app.Models.BirthdayParty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mpai.app.Models.Attendee;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class BirthdayPartyAttendee implements Attendee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attendee_id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String phoneNumber;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "attendees")
    @JsonIgnore
    private Set<BirthdayParty> events = new HashSet<>();

    public BirthdayPartyAttendee() {
    }

    public BirthdayPartyAttendee(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return attendee_id;
    }

    public void setId(int id) {
        this.attendee_id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<BirthdayParty> getEvents() {
        return events;
    }

    public void setEvents(Set<BirthdayParty> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Birthday Party Attendee";
    }
}
