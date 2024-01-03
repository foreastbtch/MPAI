package com.mpai.app.Models.BirthdayParty;

import com.mpai.app.Models.Event;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class BirthdayParty implements Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String date_planned;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "birthday_x_attendee",
            joinColumns = @JoinColumn(name = "birthday_party_id"),
            inverseJoinColumns = @JoinColumn(name = "birthday_attendee_id"))
    private Set<BirthdayPartyAttendee> attendees = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "birthday_x_venues",
            joinColumns = @JoinColumn(name = "birthday_party_id"),
            inverseJoinColumns = @JoinColumn(name = "birthday_venue_id"))
    private Set<BirthdayPartyVenue> venues = new HashSet<>();

    public BirthdayParty() {
    }

    public BirthdayParty(BirthdayParty bp) {
        this.name = bp.name;
        this.description = bp.description;
        this.date_planned = bp.date_planned;
        this.attendees = bp.attendees.stream().map(a -> new BirthdayPartyAttendee(a.getFirstName(), a.getLastName(), a.getPhoneNumber())).collect(Collectors.toSet());
        this.venues = bp.venues.stream().map(v -> new BirthdayPartyVenue(v.getName(), v.getAddress(), v.getCapacity())).collect(Collectors.toSet());
    }

    public Set<BirthdayPartyVenue> getVenues() {
        return venues;
    }

    public void setVenues(Set<BirthdayPartyVenue> venues) {
        this.venues = venues;
    }

    public Set<BirthdayPartyAttendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(Set<BirthdayPartyAttendee> attendees) {
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
        return "Birthday Party";
    }
}
