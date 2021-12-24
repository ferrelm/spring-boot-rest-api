package com.example.demo.boats;

import java.util.HashMap;
import java.util.UUID;

import com.example.demo.MyResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class BoatService {

    HashMap<String, Boat> boats = new HashMap<>();

    public HashMap<String, Boat> getAll() {
        return boats;
    }

    /**
     * This method creats a boat
     * 
     * @param boat Barco
     * @return Barco
     */

    public Boat createBoat(Boat boat) {
        String uniqueID = UUID.randomUUID().toString();
        boat.setId(uniqueID);
        return boats.put(uniqueID, boat);
    }

    public Boat getBoat(String id) throws MyResourceNotFoundException {

        if (boats.containsKey(id)) {
            return boats.get(id);
        }

        throw new MyResourceNotFoundException("BoatService.getBoat");
    }

    public Boat updateBoat(String id, Boat boat) {

        if (!boats.containsKey(id)) {
            return boats.put(id, boat);
        }

        throw new MyResourceNotFoundException("BoatService.updateBoat");
    }

    public Boolean deleteBoat(String id) {
        if (boats.containsKey(id)) {
            boats.remove(id);
            return true;
        }

        throw new MyResourceNotFoundException("CarService.delete");
    }
}
