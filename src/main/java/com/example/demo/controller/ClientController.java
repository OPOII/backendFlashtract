package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
@CrossOrigin
public class ClientController {

    @Autowired
    private ClientService service;

    @PostMapping
    public ResponseEntity<Client> saveClient(@RequestBody Client client) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(client));
    }
    @GetMapping
    public ResponseEntity<Page<Client>> getAllClients(
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
    public ResponseEntity<Optional<Client>> findById(@PathVariable ("id") Long id){
        service.findById(id);
        ResponseEntity.ok(!service.existById(id));
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PutMapping
    public ResponseEntity<Client> editClient(@RequestBody Client client) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(client));
    }

    @PostMapping(value="/createContract")
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract, @RequestParam Long vendorID, @RequestParam Long clientID)throws Exception{
       return ResponseEntity.status(HttpStatus.CREATED).body( service.createContract(contract,vendorID,clientID));
    }

    @GetMapping(value="/{id}/contracts")
    public ResponseEntity<List<Contract>>getContracts(@RequestParam Long clientID){
        return ResponseEntity.status(HttpStatus.OK).body(service.getContracts(clientID));
    }
}
