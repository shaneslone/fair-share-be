package me.shaneslone.fairshare.controllers;

import me.shaneslone.fairshare.models.Bill;
import me.shaneslone.fairshare.services.BillService;
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
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping(value = "/bills", produces = "application/json")
    public ResponseEntity<?> listAllBills()
    {
        List<Bill> bills = billService.findAll();
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    @GetMapping(value = "/bill/{billid}", produces = "application/json")
    public ResponseEntity<?> getBillById(@PathVariable long billid){
        Bill bill = billService.findByBillId(billid);
        return new ResponseEntity<>(bill, HttpStatus.OK);
    }

    @PostMapping(value = "/bill", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewBill(@Valid @RequestBody Bill newbill){
        newbill.setBillid(0);
        newbill = billService.save(newbill);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newBillUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{billid}")
                .buildAndExpand(newbill.getBillid())
                .toUri();
        responseHeaders.setLocation(newBillUri);
        return new ResponseEntity<>(newbill, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/bill/{billid}", consumes = "application/json", produces = "application/json")
    public  ResponseEntity<?> updateFullBill(@Valid @RequestBody Bill updateBill, @PathVariable long billid){
        updateBill.setBillid(billid);
        updateBill = billService.save(updateBill);
        return new ResponseEntity<>(updateBill, HttpStatus.OK);
    }

    @DeleteMapping(value = "/bill/{billid}", consumes = "application/json")
    public ResponseEntity<?> deleteBillById(@PathVariable long billid){
        billService.delete(billid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
