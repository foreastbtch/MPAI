package com.mpai.app.Models.Conference;

import com.mpai.app.Models.Event;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Conference implements Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String date_planned;

    public Conference() {
    }

    public Conference(Conference c) {
        this.name = c.name;
        this.description = c.description;
        this.date_planned = c.date_planned;
        this.attendees = c.attendees.stream().map(a -> new ConferenceAttendee(a.getFirstName(), a.getLastName(), a.getEmail(), a.getPhoneNumber(), a.getCompany(), a.isVIP())).collect(Collectors.toSet());
        this.venues = c.venues.stream().map(v -> new ConferenceVenue(v.getName(), v.getAddress(), v.getCapacity())).collect(Collectors.toSet());
    }

    @ManyToMany
    @JoinTable(
            name = "conference_x_attendee",
            joinColumns = @JoinColumn(name = "conference_id"),
            inverseJoinColumns = @JoinColumn(name = "conference_attendee_id"))
    private Set<ConferenceAttendee> attendees = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "conference_x_venues",
            joinColumns = @JoinColumn(name = "conference_id"),
            inverseJoinColumns = @JoinColumn(name = "conference_venue_id"))
    private Set<ConferenceVenue> venues = new HashSet<>();

    public Set<ConferenceVenue> getVenues() {
        return venues;
    }

    public void setVenues(Set<ConferenceVenue> venues) {
        this.venues = venues;
    }

    public Set<ConferenceAttendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(Set<ConferenceAttendee> attendees) {
        this.attendees = attendees;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_planned() {
        return date_planned;
    }

    public void setDate_planned(String date_planned) {
        this.date_planned = date_planned;
    }

    @Override
    public String toString() {
        return "Conference";
    }
}
