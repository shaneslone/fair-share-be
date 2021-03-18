package me.shaneslone.fairshare.controllers;

import me.shaneslone.fairshare.models.MonthlyBill;
import me.shaneslone.fairshare.services.MonthlyBillService;
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
@RequestMapping("/monthlybills")
public class MonthlyBillController {

    @Autowired
    private MonthlyBillService monthlyBillService;

    @GetMapping(value = "/monthlybills", produces = "application/json")
    public ResponseEntity<?> listAllMonthlyBills(){
        List<MonthlyBill> monthlyBills = monthlyBillService.findAll();
        return new ResponseEntity<>(monthlyBills, HttpStatus.OK);
    }

    @GetMapping(value = "/monthlybill/{monthlybillid}", produces = "application/json")
    public ResponseEntity<?> getMonthlyBillById(@PathVariable long monthlybillid){
        MonthlyBill monthlyBill = monthlyBillService.findByMonthlyBillId(monthlybillid);
        return new ResponseEntity<>(monthlyBill, HttpStatus.OK);
    }

    @PostMapping(value = "/monthlybill", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewMonthlyBill(@Valid @RequestBody MonthlyBill newmonthlybill){
        newmonthlybill.setMonthlybillid(0);
        newmonthlybill = monthlyBillService.save(newmonthlybill);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newMonthlyBillUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{monthlybillid}")
                .buildAndExpand(newmonthlybill.getMonthlybillid())
                .toUri();
        responseHeaders.setLocation(newMonthlyBillUri);
        return new ResponseEntity<>(newmonthlybill, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/monthlybill/{monthlybillid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateFullMonthlyBill(@Valid @RequestBody MonthlyBill updateMonthlyBill,
                                                   @PathVariable long monthlybillid){
        updateMonthlyBill.setMonthlybillid(monthlybillid);
        updateMonthlyBill = monthlyBillService.save(updateMonthlyBill);
        return new ResponseEntity<>(updateMonthlyBill, HttpStatus.OK);
    }

    @PatchMapping(value = "/monthlybill/{monthlybillid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateMonthlyBill(@RequestBody MonthlyBill updateMonthlyBill, @PathVariable long monthlybillid){
        updateMonthlyBill = monthlyBillService.update(updateMonthlyBill, monthlybillid);
        return new ResponseEntity<>(updateMonthlyBill, HttpStatus.OK);
    }

    @DeleteMapping(value = "monthlybill/{monthlybillid}", consumes = "application/json")
    public ResponseEntity<?> deleteMonthlyBillById(@PathVariable long monthlybillid){
        monthlyBillService.delete(monthlybillid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
