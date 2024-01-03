package com.mpai.app.Models.Factories;

import com.mpai.app.Models.Event;
import com.mpai.app.Models.Attendee;
import com.mpai.app.Models.Venue;
import org.springframework.stereotype.Service;

@Service
public interface EventFactory {
    Event createEvent(Event e);

    Attendee createAttendee();

    Venue createVenue();
}
