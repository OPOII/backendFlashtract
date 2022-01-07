package com.example.demo.controller;

import com.example.demo.model.Invoice;
import com.example.demo.model.Vendor;
import com.example.demo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/invoice")
@CrossOrigin
public class InvoiceController {
    @Autowired
    private InvoiceService service;

    @PostMapping
    public ResponseEntity<Invoice> saveInvoice(@RequestBody Invoice invoice) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(invoice));
    }
    @GetMapping
    public ResponseEntity<Iterable<Invoice>> getAllClients(){
        return ResponseEntity.ok(service.findAll());
    }
    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity deleteClient(@PathVariable ("id") Long id){
        service.delete(id);
        ResponseEntity.ok(!service.existById(id));
        return ResponseEntity.ok(service.existById(id));
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Invoice> findById(@PathVariable ("id") Long id){
        service.findById(id);
        ResponseEntity.ok(!service.existById(id));
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PutMapping
    public ResponseEntity<Invoice> editInvoice(@RequestBody Invoice invoice) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(invoice));
    }

    @GetMapping(value = "/{id}/vendor")
    public ResponseEntity<Vendor> findContractVendor(@RequestParam Long id)throws Exception{
       return ResponseEntity.status(HttpStatus.OK).body(service.getVendorByID(id));
    }
}
