package com.mpai.app.Repos.Birthday;

import com.mpai.app.Models.BirthdayParty.BirthdayPartyVenue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirthdayVenueRepo  extends JpaRepository<BirthdayPartyVenue, Long> {
    public default void deleteBirthdayVenue(long id) {
        this.delete(this.findById(id).get());
    }
}
