package me.shaneslone.fairshare.controllers;

import me.shaneslone.fairshare.models.Household;
import me.shaneslone.fairshare.services.HouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(name = "/households")
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;

    @GetMapping(name = "/households", produces = "application/json")
    public ResponseEntity<?> listAllHouseholds(){
        List<Household> households = householdService.findAll();
        return new ResponseEntity<>(households, HttpStatus.OK);
    }

    @GetMapping(name = "/household/{householdid}", produces = "application/json")
    public ResponseEntity<?> getHouseholdById(@PathVariable long householdid){
        Household household = householdService.findByHouseholdId(householdid);
        return new ResponseEntity<>(household, HttpStatus.OK);
    }

    @PostMapping(value = "/household", consumes = "application/json", produces = "application/json")
    public  ResponseEntity<?> addNewHousehold(@Valid @RequestBody Household newHousehold){
        newHousehold.setHouseholdid(0);
        newHousehold = householdService.save(newHousehold);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newHouseholdURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{householdid}")
                .buildAndExpand()
                .toUri();
        responseHeaders.setLocation(newHouseholdURI);
        return new ResponseEntity<>(newHousehold, HttpStatus.OK);
    }

    @PutMapping(value = "/household/{householdid}", consumes = "application/json", produces = "application/sjon")
    public ResponseEntity<?> updateFullHousehold(@Valid @RequestBody Household updateHousehold, @PathVariable long householdid){
        updateHousehold.setHouseholdid(householdid);
        updateHousehold = householdService.save(updateHousehold);
        return new ResponseEntity<>(updateHousehold, HttpStatus.OK);
    }

    @DeleteMapping(value = "/household/{householdid}", consumes = "application/json")
    public ResponseEntity<?> deleteHouseholdById(@PathVariable long householdid){
        householdService.delete(householdid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
