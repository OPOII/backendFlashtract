package com.example.demo.controller;

import com.example.demo.model.Contract;
import com.example.demo.model.Invoice;
import com.example.demo.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/service")
@CrossOrigin
public class ServiceController {

    @Autowired
    private IService service;

   @PostMapping
    public ResponseEntity saveService(@PathVariable("id")Long idClient, @PathVariable("id")Long idVendor, @RequestBody Contract contract){
       return ResponseEntity.status(HttpStatus.CREATED).body(service.createContract(idClient,idVendor, contract));
   }


}
