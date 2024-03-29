package com.mpai.app.Controllers;

import com.mpai.app.Models.Attendee;
import com.mpai.app.Models.BirthdayParty.BirthdayParty;
import com.mpai.app.Models.BirthdayParty.BirthdayPartyAttendee;
import com.mpai.app.Models.Conference.Conference;
import com.mpai.app.Repos.Birthday.BirthdayAttendeeRepo;
import com.mpai.app.Repos.Conference.ConferenceAttendeeRepo;
import com.mpai.app.Models.Conference.ConferenceAttendee;
import com.mpai.app.Models.EventType;
import com.mpai.app.Repos.Birthday.BirthdayPartyRepo;
import com.mpai.app.Repos.Conference.ConferenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AttendeeController {
    @Autowired
    private ConferenceAttendeeRepo conferenceAttendeeRepo;
    @Autowired
    private BirthdayAttendeeRepo birthdayAttendeeRepo;
    @Autowired
    private BirthdayPartyRepo birthdayPartyRepo;
    @Autowired
    private ConferenceRepo conferenceRepo;

    @GetMapping(value = "/attendees/{type}")
    public ResponseEntity<? extends List<? extends Attendee>> getEventAttendees(@PathVariable EventType type) {
        return switch (type) {
            case CONFERENCE -> ResponseEntity.status(HttpStatus.FOUND).body(conferenceAttendeeRepo.findAll());

            case BIRTHDAYPARTY -> ResponseEntity.status(HttpStatus.FOUND).body(birthdayAttendeeRepo.findAll());
        };
    }

    @GetMapping(value = "/attendees/{type}/{id}")
    public ResponseEntity<Attendee> getEventAttendeeById(@PathVariable EventType type, @PathVariable long id) {
        return switch (type) {
            case CONFERENCE -> ResponseEntity.status(HttpStatus.FOUND).body(conferenceAttendeeRepo.findById(id).isPresent() ? conferenceAttendeeRepo.findById(id).get() : null);

            case BIRTHDAYPARTY -> ResponseEntity.status(HttpStatus.FOUND).body(birthdayAttendeeRepo.findById(id).isPresent() ? birthdayAttendeeRepo.findById(id).get() : null);
        };
    }

    @PostMapping(value = "/attendees/birthday/add/{attendeeId}")
    public ResponseEntity addBirthdayParty(@PathVariable long attendeeId, @RequestBody BirthdayParty birthdayParty) {
        BirthdayPartyAttendee attendee = birthdayAttendeeRepo.findById(attendeeId).get();
        birthdayParty.getAttendees().add(attendee);

        attendee.getEvents().add(birthdayParty);
        birthdayPartyRepo.save(birthdayParty);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(value = "/attendees/conference/{id}")
    public ResponseEntity updateConferenceAttendee(@PathVariable long id, @RequestBody ConferenceAttendee conferenceAttendee) {
        ConferenceAttendee attendeeToUpdate = conferenceAttendeeRepo.findById(id).get();
        attendeeToUpdate.setCompany(conferenceAttendee.getCompany());
        attendeeToUpdate.setEmail(conferenceAttendee.getEmail());
        attendeeToUpdate.setVIP(conferenceAttendee.isVIP());
        attendeeToUpdate.setFirstName(conferenceAttendee.getFirstName());
        attendeeToUpdate.setLastName(conferenceAttendee.getLastName());
        attendeeToUpdate.setPhoneNumber(conferenceAttendee.getPhoneNumber());
        conferenceAttendeeRepo.save(attendeeToUpdate);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/attendees/birthday/{id}")
    public ResponseEntity updateBirthdayAttendee(@PathVariable long id, @RequestBody BirthdayPartyAttendee birthdayPartyAttendee) {
        BirthdayPartyAttendee attendeeToUpdate = birthdayAttendeeRepo.findById(id).get();
        attendeeToUpdate.setFirstName(birthdayPartyAttendee.getFirstName());
        attendeeToUpdate.setLastName(birthdayPartyAttendee.getLastName());
        attendeeToUpdate.setPhoneNumber(birthdayPartyAttendee.getPhoneNumber());
        birthdayAttendeeRepo.save(attendeeToUpdate);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/attendees/{type}/{id}")
    public ResponseEntity deleteEventAttendee(@PathVariable EventType type, @PathVariable long id) {

        switch (type) {
            case CONFERENCE -> {
                List<Conference> conferenceList = conferenceRepo.findAll().stream().
                        filter(x -> x.getAttendees().contains(conferenceAttendeeRepo.findById(id).get())).toList();

                for(Conference c : conferenceList){
                    c.setAttendees(c.getAttendees().stream().filter(x -> x.getId() != id).collect(Collectors.toSet()));
                }

                conferenceAttendeeRepo.deleteConferenceAttendee(id);
            }

            case BIRTHDAYPARTY -> {
                List<BirthdayParty> birthdayPartyList = birthdayPartyRepo.findAll().stream().
                        filter(x -> x.getAttendees().contains(birthdayAttendeeRepo.findById(id).get())).toList();

                for(BirthdayParty c : birthdayPartyList){
                    c.setAttendees(c.getAttendees().stream().filter(x -> x.getId() != id).collect(Collectors.toSet()));
                }
                birthdayAttendeeRepo.deleteBirthdayAttendee(id);
            }

        }
        ;

        return new ResponseEntity(HttpStatus.OK);
    }
}
