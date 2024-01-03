package com.mpai.app.Repos;

import com.mpai.app.Models.Conference.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceRepo extends JpaRepository<Conference, Long> {
    public default void deleteConference(long id) {
        this.delete(this.findById(id).get());
    }
}
