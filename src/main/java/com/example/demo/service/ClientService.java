package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.model.Invoice;

import java.util.List;

public interface ClientService {

    public Iterable<Client> findAll();
    public void save(Client client);
    public List<Contract> getContracts();
    public Client findById(Long id);
    public void update(Client client);
    public void searcContractById(Long id);
    public void delete(Client client);

}
