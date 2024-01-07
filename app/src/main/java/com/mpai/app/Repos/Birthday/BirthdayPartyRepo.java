package com.mpai.app.Repos.Birthday;

import com.mpai.app.Models.BirthdayParty.BirthdayParty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.Map;

public interface BirthdayPartyRepo extends JpaRepository<BirthdayParty, Long> {

    Map<Long, BirthdayParty> storage = new HashMap<>();

    public default BirthdayParty getBirthday(long id) {
        if (!storage.containsKey(id)) {
            BirthdayParty bp = this.findById(id).isPresent() ? this.findById(id).get() : null;
            if (bp != null)
                saveEntity(id, bp);
        }
        return new BirthdayParty(findEntity(id));
    }

    public default void addBirthday(BirthdayParty bp) {
        this.save(bp);
        saveEntity(bp.getId(), bp);
    }

    public default void updateBirthday(long id, BirthdayParty birthdayParty) {
        BirthdayParty eventToUpdate = this.findById(id).get();
        eventToUpdate.setDate_planned(birthdayParty.getDate_planned());
        eventToUpdate.setDescription(birthdayParty.getDescription());
        eventToUpdate.setName(birthdayParty.getName());
        this.save(eventToUpdate);
        saveEntity(id, birthdayParty);
    }

    public default void deleteBirthday(long id) {
        storage.remove(id);
        BirthdayParty bp = this.findById(id).isPresent() ? this.findById(id).get() : null;
        if (bp != null)
            this.delete(bp);

    }

    public default void saveEntity(long id, BirthdayParty entity) {
        storage.put(id, entity);
    }

    public default BirthdayParty findEntity(Long id) {
        return storage.get(id);
    }
}
