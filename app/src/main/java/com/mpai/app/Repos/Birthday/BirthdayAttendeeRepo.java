package com.mpai.app.Repos.Birthday;

import com.mpai.app.Models.BirthdayParty.BirthdayPartyAttendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BirthdayAttendeeRepo extends JpaRepository<BirthdayPartyAttendee, Long> {
    public default void deleteBirthdayAttendee(long id) {
        this.delete(this.findById(id).get());
    }

}
