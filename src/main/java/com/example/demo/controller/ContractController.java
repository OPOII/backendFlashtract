package com.example.demo.controller;

import com.example.demo.model.Contract;
import com.example.demo.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contract")
@CrossOrigin
public class ContractController {

    @Autowired
    private ContractService service;

    @PostMapping
    public ResponseEntity<Contract> save(@RequestBody Contract contract) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(contract));
    }

    @GetMapping
    public ResponseEntity<List<Contract>> findAll(){
        return ResponseEntity.ok(service.findAll());
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
    public ResponseEntity<Contract> update(@RequestBody Contract contract) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(contract));
    }

}
