package com.mpai.app.Controllers;

import com.mpai.app.Models.BirthdayParty.BirthdayParty;
import com.mpai.app.Models.BirthdayParty.BirthdayPartyAttendee;
import com.mpai.app.Models.BirthdayParty.BirthdayPartyVenue;
import com.mpai.app.Models.Conference.Conference;
import com.mpai.app.Models.Conference.ConferenceAttendee;
import com.mpai.app.Models.Conference.ConferenceVenue;
import com.mpai.app.Models.Event;
import com.mpai.app.Models.Factories.BirthdayPartyFactory;
import com.mpai.app.Models.Factories.ConferenceFactory;
import com.mpai.app.Models.EventType;
import com.mpai.app.Repos.Birthday.BirthdayAttendeeRepo;
import com.mpai.app.Repos.Birthday.BirthdayPartyRepo;
import com.mpai.app.Repos.Birthday.BirthdayVenueRepo;
import com.mpai.app.Repos.Conference.ConferenceAttendeeRepo;
import com.mpai.app.Repos.Conference.ConferenceRepo;
import com.mpai.app.Repos.Conference.ConferenceVenueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private ConferenceRepo conferenceRepo;
    @Autowired
    private BirthdayPartyRepo birthdayPartyRepo;
    @Autowired
    private ConferenceAttendeeRepo conferenceAttendeeRepo;
    @Autowired
    private BirthdayAttendeeRepo birthdayAttendeeRepo;
    @Autowired
    private BirthdayPartyFactory birthdayPartyFactory;
    @Autowired
    private ConferenceFactory conferenceFactory;
    @Autowired
    private ConferenceVenueRepo conferenceVenueRepo;
    @Autowired
    private BirthdayVenueRepo birthdayVenueRepo;

    //abstract factory + Dependency injection + multiton
    @GetMapping(value = "/")
    public String getPage() {
        return "Welcome";
    }

    @GetMapping(value = "/events/{type}")
    public ResponseEntity<? extends List<? extends Event>> getEvents(@PathVariable EventType type) {
        return switch (type) {
            case CONFERENCE ->
                    ResponseEntity.status(HttpStatus.FOUND).body(conferenceRepo.findAll().stream().map(e -> new Conference(e)).toList());

            case BIRTHDAYPARTY ->
                    ResponseEntity.status(HttpStatus.FOUND).body(birthdayPartyRepo.findAll().stream().map(e -> new BirthdayParty(e)).toList());
        };
    }

    @GetMapping(value = "/events/{type}/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable EventType type, @PathVariable long id) {
        return switch (type) {
            case CONFERENCE -> ResponseEntity.status(HttpStatus.FOUND).body(conferenceRepo.findById(id).isPresent() ? conferenceRepo.findById(id).get() : null);
            case BIRTHDAYPARTY -> ResponseEntity.status(HttpStatus.FOUND).body(birthdayPartyRepo.getBirthday(id));
        };
    }

    @PostMapping(value = "/events/conference")
    public ResponseEntity addConference(@RequestBody Conference conference) {
        conferenceRepo.save(conference);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(value = "/events/birthday")
    public ResponseEntity addBirthday(@RequestBody BirthdayParty birthdayParty) {
        birthdayPartyRepo.addBirthday(birthdayParty);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(value = "/attendees/birthday/add/{eventId}")
    public ResponseEntity addBirthdayAttendee(@PathVariable long eventId, @RequestBody BirthdayPartyAttendee birthdayPartyAttendee) {
        BirthdayParty party = birthdayPartyRepo.findById(eventId).get();
        birthdayPartyAttendee.getEvents().add(party);

        party.getAttendees().add(birthdayPartyAttendee);
        birthdayAttendeeRepo.save(birthdayPartyAttendee);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(value = "/venues/birthday/add/{eventId}")
    public ResponseEntity addBirthdayVenue(@PathVariable long eventId, @RequestBody BirthdayPartyVenue birthdayPartyVenue) {
        BirthdayParty party = birthdayPartyRepo.findById(eventId).get();
        birthdayPartyVenue.getEvents().add(party);

        party.getVenues().add(birthdayPartyVenue);
        birthdayVenueRepo.save(birthdayPartyVenue);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(value = "/attendees/conference/add/{eventId}")
    public ResponseEntity addConferenceAttendee(@PathVariable long eventId, @RequestBody ConferenceAttendee conferenceAttendee) {
        Conference conference = conferenceRepo.findById(eventId).get();
        conferenceAttendee.getEvents().add(conference);

        conference.getAttendees().add(conferenceAttendee);
        conferenceAttendeeRepo.save(conferenceAttendee);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping(value = "/venues/conference/add/{eventId}")
    public ResponseEntity addConferenceVenue(@PathVariable long eventId, @RequestBody ConferenceVenue conferenceVenue) {
        Conference conference = conferenceRepo.findById(eventId).get();
        conferenceVenue.getEvents().add(conference);

        conference.getVenues().add(conferenceVenue);
        conferenceVenueRepo.save(conferenceVenue);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(value = "/events/conference/{id}")
    public ResponseEntity updateConference(@PathVariable long id, @RequestBody Conference conference) {
        Conference eventToUpdate = conferenceRepo.findById(id).get();
        eventToUpdate.setDate_planned(conference.getDate_planned());
        eventToUpdate.setDescription(conference.getDescription());
        eventToUpdate.setName(conference.getName());
        conferenceRepo.save(eventToUpdate);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/events/birthday/{id}")
    public ResponseEntity updateBirthday(@PathVariable long id, @RequestBody BirthdayParty birthdayParty) {
        birthdayPartyRepo.updateBirthday(id, birthdayParty);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/events/{type}/{id}")
    public ResponseEntity deleteEvent(@PathVariable EventType type, @PathVariable long id) {

        switch (type) {
            case CONFERENCE -> conferenceRepo.deleteConference(id);

            case BIRTHDAYPARTY -> birthdayPartyRepo.deleteBirthday(id);
        }
        ;

        return new ResponseEntity(HttpStatus.OK);
    }
}
