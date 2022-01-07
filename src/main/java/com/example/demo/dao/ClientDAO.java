package com.example.demo.dao;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;

import java.util.Date;
import java.util.List;

public interface ClientDAO {

    public Iterable<Client> findAll();
    public void save(Client client);
    public void update(Client client);
    public void delete(Client client);
    public Client findById(Long id);
    public List<Contract> getContracts();
    public void searcContractById(Long id);
    public void searcContractByDate(Date date);
    public void searchContractBetweenDays(Date start, Date end);

}
