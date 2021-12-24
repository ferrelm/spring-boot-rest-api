package com.example.demo.boats;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import com.example.demo.MyResourceNotFoundException;
import com.example.demo.security.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/boats")
@RolesAllowed(Role.USER_ADMIN)
public class BoatController {

    @Autowired
    private BoatService boatService;

    @GetMapping
    public HashMap<String, Boat> getAll() {
        return boatService.getAll();
    }

    @GetMapping("/{id}")
    public Boat getBoat(@PathVariable String id) {
        return boatService.getBoat(id);
    }

    @PostMapping
    public ResponseEntity<Boat> createBoat(@RequestBody Boat boat) {
        System.out.println("createBoat");
        return ResponseEntity.status(HttpStatus.CREATED).body(boatService.createBoat(boat));
    }

    @PutMapping("/{id}")
    public Boat updateBoat(@PathVariable String id, @RequestBody Boat boat) throws Exception {
        return boatService.updateBoat(id, boat);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Boolean deleteBoat(@PathVariable String id) {
        return boatService.deleteBoat(id);
        // return ResponseEntity.status(HttpStatus.NO_CONTENT).body(_boat);
    }

    /*
     * @ResponseStatus(HttpStatus.NOT_FOUND)
     * 
     * @ExceptionHandler({ MyResourceNotFoundException.class,
     * NoSuchElementException.class }) public void handleNotFound(Exception ex,
     * HttpServletRequest req) { System.out.println("URL: " + req.getRequestURI());
     * System.out.println("Message: " + ex.getMessage()); // return ex.getMessage();
     * }
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ IndexOutOfBoundsException.class })
    public void myIndexExceptionHandler() {
        // System.out.println("A");
    }

}
