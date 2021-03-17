package me.shaneslone.fairshare.controllers;

import me.shaneslone.fairshare.models.Household;
import me.shaneslone.fairshare.services.HouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
