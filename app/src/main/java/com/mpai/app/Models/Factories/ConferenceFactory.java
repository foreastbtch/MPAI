package com.mpai.app.Models.Factories;

import com.mpai.app.Models.Attendee;
import com.mpai.app.Models.Conference.Conference;
import com.mpai.app.Models.Event;
import com.mpai.app.Models.Venue;
import com.mpai.app.Models.Conference.ConferenceAttendee;
import com.mpai.app.Models.Conference.ConferenceVenue;
import org.springframework.stereotype.Service;

@Service
public class ConferenceFactory implements EventFactory {
    @Override
    public Conference createEvent(Event c) {
        return new Conference((Conference) c);
    }

    @Override
    public Attendee createAttendee() {
        return new ConferenceAttendee();
    }

    @Override
    public Venue createVenue() {
        return new ConferenceVenue();
    }
}
