package com.example.demo.controller;

import com.example.demo.model.Invoice;
import com.example.demo.model.Vendor;
import com.example.demo.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/vendor")
@CrossOrigin
public class VendorController {

    @Autowired
    private VendorService service;

    @PostMapping
    public ResponseEntity<Vendor>saveVendor(@RequestBody Vendor vendor){
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(vendor));
    }
    @GetMapping
    public ResponseEntity<Page<Vendor>> getAllClients(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10")Integer size,
            @RequestParam(required = false, defaultValue = "false")Boolean enablePagination){
        return ResponseEntity.ok(service.findAll(page,size,enablePagination));
    }

    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity deleteContract(@PathVariable ("id") Long id){
        service.delete(id);
        ResponseEntity.ok(!service.existById(id));
        return ResponseEntity.ok(service.existById(id));
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Optional<Vendor>> findById(@PathVariable ("id") Long id){
        service.findById(id);
        ResponseEntity.ok(!service.existById(id));
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PutMapping
    public ResponseEntity<Vendor> update(@RequestBody Vendor vendor) throws Exception {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.update(vendor));
    }

    @PostMapping(value="/{id}/createInvoice")
    public ResponseEntity<Invoice>createInvoice(@RequestBody Invoice invoice, @RequestParam Long idVendor){
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createInvoice(invoice,idVendor));
    }

    @PatchMapping(value="/{id}/sendInvoice")
    public ResponseEntity sendInvoice(@RequestParam Long idInvoice, @RequestParam Long idVendor){
            return ResponseEntity.status(HttpStatus.OK).body(service.sendInvoice(idInvoice,idVendor));
    }




}
