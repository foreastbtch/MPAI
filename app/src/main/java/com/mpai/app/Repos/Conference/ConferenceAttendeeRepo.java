package com.mpai.app.Repos.Conference;

import com.mpai.app.Models.Conference.ConferenceAttendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceAttendeeRepo extends JpaRepository<ConferenceAttendee, Long> {
    public default void deleteConferenceAttendee(long id) {
        this.delete(this.findById(id).get());
    }
}
