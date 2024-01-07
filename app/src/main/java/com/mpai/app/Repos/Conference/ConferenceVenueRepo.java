package com.mpai.app.Repos.Conference;

import com.mpai.app.Models.Conference.ConferenceVenue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceVenueRepo extends JpaRepository<ConferenceVenue, Long> {
    public default void deleteConferenceVenue(long id) {
        this.delete(this.findById(id).get());
    }
}
