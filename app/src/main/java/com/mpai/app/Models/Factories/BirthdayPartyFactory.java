package com.mpai.app.Models.Factories;

import com.mpai.app.Models.Attendee;
import com.mpai.app.Models.BirthdayParty.BirthdayParty;
import com.mpai.app.Models.BirthdayParty.BirthdayPartyAttendee;
import com.mpai.app.Models.Event;
import com.mpai.app.Models.Venue;
import com.mpai.app.Models.BirthdayParty.BirthdayPartyVenue;
import org.springframework.stereotype.Service;

@Service
public class BirthdayPartyFactory implements EventFactory {
    @Override
    public BirthdayParty createEvent(Event bp) {
        return new BirthdayParty((BirthdayParty) bp);
    }

    @Override
    public Attendee createAttendee() {
        return new BirthdayPartyAttendee();
    }

    @Override
    public Venue createVenue() {
        return new BirthdayPartyVenue();
    }
}
