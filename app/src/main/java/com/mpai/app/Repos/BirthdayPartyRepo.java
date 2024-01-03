package com.mpai.app.Repos;

import com.mpai.app.Models.BirthdayParty.BirthdayParty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.Map;

public interface BirthdayPartyRepo extends JpaRepository<BirthdayParty, Long> {

    Map<Long, BirthdayParty> storage = new HashMap<>();

    //de facut asta pe toate operatiile in afara de get all, si de dus schimbarile si pe celelalte controllere
    //eventual sa expire?
    public default BirthdayParty getBirthday(long id) {
        if (!storage.containsKey(id)) {
            BirthdayParty bp = this.findById(id).get();
            storage.put(id, bp);
        }
        return new BirthdayParty(storage.get(id));
    }

    public default void saveEntity(BirthdayParty entity) {
        storage.put(entity.getId(), entity);
    }

    public default BirthdayParty findEntity(Long id) {
        return storage.get(id);
    }

    public default void deleteBirthday(long id) {
        this.delete(this.findById(id).get());
    }

}
