package com.example.demo.controller;

import com.example.demo.model.Invoice;
import com.example.demo.model.Vendor;
import com.example.demo.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/invoice")
@CrossOrigin
public class InvoiceController {
    @Autowired
    private InvoiceService service;

    @PostMapping
    public ResponseEntity<Invoice> saveInvoice(@RequestBody Invoice invoice, @RequestParam Long idVendor) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(invoice,idVendor));
    }
    @GetMapping
    public ResponseEntity<Page<Invoice>> getAllClients(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10")Integer size,
            @RequestParam(required = false, defaultValue = "false")Boolean enablePagination){
        return ResponseEntity.ok(service.findAll(page,size,enablePagination));
    }
    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity deleteClient(@PathVariable ("id") Long id){
        service.delete(id);
        ResponseEntity.ok(service.existById(id));
        return ResponseEntity.ok(service.existById(id));
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Optional<Invoice>> findById(@PathVariable ("id") Long id){
        service.findById(id);
        ResponseEntity.ok(!service.existById(id));
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PutMapping
    public ResponseEntity<Invoice> editInvoice(@RequestBody Invoice invoice) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(invoice));
    }

    @GetMapping(value = "/vendor/{id}")
    public ResponseEntity<Vendor> findContractVendor(@RequestParam Long id)throws Exception{
       return ResponseEntity.status(HttpStatus.OK).body(service.getVendorByID(id));
    }
}
