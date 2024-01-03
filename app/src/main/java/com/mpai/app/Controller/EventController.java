package com.mpai.app.Controller;

import com.mpai.app.Models.BirthdayParty.BirthdayParty;
import com.mpai.app.Models.BirthdayParty.BirthdayPartyAttendee;
import com.mpai.app.Models.Conference.Conference;
import com.mpai.app.Models.Event;
import com.mpai.app.Models.Factories.BirthdayPartyFactory;
import com.mpai.app.Models.Factories.ConferenceFactory;
import com.mpai.app.Models.EventType;
import com.mpai.app.Repos.BirthdayAttendeeRepo;
import com.mpai.app.Repos.BirthdayPartyRepo;
import com.mpai.app.Repos.ConferenceAttendeeRepo;
import com.mpai.app.Repos.ConferenceRepo;
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

    //abstract factory + Dependency injection + multiton
    //de ce nu mergea cu id?? => mergea dar erau 2 join uri, in fiecare fisier cate unul
    //de ce nu se salva in tabela de legatura?
    //daca vreau sa fac 2 save uri in acelasi endpoint?
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
            case CONFERENCE -> ResponseEntity.status(HttpStatus.FOUND).body(conferenceRepo.findById(id).get());
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
        birthdayPartyRepo.save(birthdayParty);
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
        BirthdayParty eventToUpdate = birthdayPartyRepo.findById(id).get();
        eventToUpdate.setDate_planned(birthdayParty.getDate_planned());
        eventToUpdate.setDescription(birthdayParty.getDescription());
        eventToUpdate.setName(birthdayParty.getName());
        birthdayPartyRepo.save(eventToUpdate);

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
