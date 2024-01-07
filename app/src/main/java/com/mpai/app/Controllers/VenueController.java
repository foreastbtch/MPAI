package com.mpai.app.Controllers;

import com.mpai.app.Models.BirthdayParty.BirthdayParty;
import com.mpai.app.Models.Conference.Conference;
import com.mpai.app.Models.EventType;
import com.mpai.app.Models.Venue;
import com.mpai.app.Repos.Birthday.BirthdayPartyRepo;
import com.mpai.app.Repos.Birthday.BirthdayVenueRepo;
import com.mpai.app.Repos.Conference.ConferenceRepo;
import com.mpai.app.Repos.Conference.ConferenceVenueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VenueController {
    @Autowired
    private ConferenceVenueRepo conferenceVenueRepo;

    @Autowired
    private BirthdayVenueRepo birthdayVenueRepo;

    @Autowired
    private ConferenceRepo conferenceRepo;

    @Autowired
    private BirthdayPartyRepo birthdayPartyRepo;


    @GetMapping(value = "/venues/{type}")
    public ResponseEntity<? extends List<? extends Venue>> getEventVenues(@PathVariable EventType type) {
        return switch (type) {
            case CONFERENCE -> ResponseEntity.status(HttpStatus.FOUND).body(conferenceVenueRepo.findAll());

            case BIRTHDAYPARTY -> ResponseEntity.status(HttpStatus.FOUND).body(birthdayVenueRepo.findAll());
        };
    }
    @GetMapping(value = "/venues/{type}/{id}")
    public ResponseEntity<Venue> getEventVenueById(@PathVariable EventType type, @PathVariable long id) {
        return switch (type) {
            case CONFERENCE -> ResponseEntity.status(HttpStatus.FOUND).body(conferenceVenueRepo.findById(id).get());

            case BIRTHDAYPARTY -> ResponseEntity.status(HttpStatus.FOUND).body(birthdayVenueRepo.findById(id).get());
        };
    }

    @DeleteMapping(value = "/venues/{type}/{id}")
    public ResponseEntity deleteEventVenue(@PathVariable EventType type, @PathVariable long id) {

        switch (type) {
            case CONFERENCE -> {
                List<Conference> conferenceList = conferenceRepo.findAll().stream().
                        filter(x -> x.getAttendees().contains(conferenceVenueRepo.findById(id).get())).toList();

                for(Conference c : conferenceList){
                    c.setAttendees(c.getAttendees().stream().filter(x -> x.getId() != id).collect(Collectors.toSet()));
                }
                conferenceVenueRepo.deleteConferenceVenue(id);}

            case BIRTHDAYPARTY -> {
                List<BirthdayParty> birthdayPartyList = birthdayPartyRepo.findAll().stream().
                        filter(x -> x.getAttendees().contains(birthdayVenueRepo.findById(id).get())).toList();

                for(BirthdayParty c : birthdayPartyList){
                    c.setAttendees(c.getAttendees().stream().filter(x -> x.getId() != id).collect(Collectors.toSet()));
                }
                birthdayVenueRepo.deleteBirthdayVenue(id);}
        }
        ;

        return new ResponseEntity(HttpStatus.OK);
    }
}
