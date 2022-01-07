package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.model.Invoice;
import com.example.demo.model.Vendor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    public Iterable<Client> findAll();
    public Client save(Client client) throws Exception;
    public Client update(Client client)throws Exception;
    public void delete(Long id);
    public Client findById(Long id);
    public List<Contract> getContracts(Long id);
    public Contract searchContractById(Long id);
    public Contract searchContractByDate(Long idClient,Date date);
}
