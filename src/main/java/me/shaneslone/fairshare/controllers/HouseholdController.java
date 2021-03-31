package me.shaneslone.fairshare.controllers;

import me.shaneslone.fairshare.models.Household;
import me.shaneslone.fairshare.models.User;
import me.shaneslone.fairshare.services.HelperFunctions;
import me.shaneslone.fairshare.services.HouseholdService;
import me.shaneslone.fairshare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/households")
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;

    @Autowired
    private UserService userService;

    @Autowired
    private HelperFunctions helperFunctions;

    @GetMapping(value = "/households", produces = "application/json")
    public ResponseEntity<?> listAllHouseholds(){
        List<Household> households = householdService.findAll();
        return new ResponseEntity<>(households, HttpStatus.OK);
    }

    @GetMapping(value = "/household/{householdid}", produces = "application/json")
    public ResponseEntity<?> getHouseholdById(@PathVariable long householdid){
        Household household = householdService.findByHouseholdId(householdid);
        return new ResponseEntity<>(household, HttpStatus.OK);
    }

    @PostMapping(value = "/household", produces = "application/json")
    public  ResponseEntity<?> addNewHousehold(Authentication authentication){
        User user = userService.findByName(authentication.getName());
        Household newHousehold = new Household();
        newHousehold.setHouseholdid(0);
        newHousehold.setHouseholdKey(helperFunctions.generateHouseholdKey());
        newHousehold.getUsers().add(user);
        newHousehold = householdService.save(newHousehold);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newHouseholdURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{householdid}")
                .buildAndExpand(newHousehold.getHouseholdid())
                .toUri();
        responseHeaders.setLocation(newHouseholdURI);
        return new ResponseEntity<>(newHousehold, responseHeaders, HttpStatus.OK);
    }

    @PutMapping(value = "/household/{householdid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateFullHousehold(@Valid @RequestBody Household updateHousehold, @PathVariable long householdid){
        updateHousehold.setHouseholdid(householdid);
        updateHousehold = householdService.save(updateHousehold);
        return new ResponseEntity<>(updateHousehold, HttpStatus.OK);
    }

    @PutMapping(value = "/household/{householdkey}/adduser", produces = "application/json")
    public ResponseEntity<?> addUserToHousehold(@PathVariable String householdkey, Authentication authentication){
        Household household = householdService.findByHouseholdKey(householdkey);
        User user = userService.findByName(authentication.getName());
        household.getUsers().add(user);
        household = householdService.save(household);
        return new ResponseEntity<>(household, HttpStatus.OK);
    }

    @DeleteMapping(value = "/household/{householdid}")
    public ResponseEntity<?> deleteHouseholdById(@PathVariable long householdid){
        householdService.delete(householdid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
