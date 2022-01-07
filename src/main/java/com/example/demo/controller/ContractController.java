package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/contract")
@CrossOrigin
public class ContractController {

    @Autowired
    private ContractService service;

    @PostMapping
    public ResponseEntity<Contract> saveContract(@RequestBody Contract client) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(client));
    }

    @GetMapping
    public ResponseEntity<Page<Contract>> getAllClients(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10")Integer size,
            @RequestParam(required = false, defaultValue = "false")Boolean enablePagination){
        return ResponseEntity.ok(service.findAll(page,size,enablePagination));
    }
    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity deleteContract(@PathVariable ("id") Long id){
        service.delete(id);
        ResponseEntity.ok(service.existById(id));
        return ResponseEntity.ok(service.existById(id));
    }
    @GetMapping(value="/{id}")
    public ResponseEntity<Optional<Contract>> findById(@PathVariable ("id") Long id){
        service.findById(id);
        ResponseEntity.ok(!service.existById(id));
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PutMapping
    public ResponseEntity<Contract> editContract(@RequestBody Contract contract) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(contract));
    }

}
