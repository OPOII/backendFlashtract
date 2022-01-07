package com.example.demo.dao;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
public class ClientDAOImpl implements ClientDAO{


    @Override
    public Iterable<Client> findAll() {
        return null;
    }

    @Override
    public void save(Client client) {
    }

    @Override
    public void update(Client client) {
    }

    @Override
    public void delete(Client client) {
    }

    @Override
    public Client findById(Long id) {
        return null;
    }

    @Override
    public List<Contract> getContracts() {
        return null;
    }

    @Override
    public void searcContractById(Long id) {

    }

    @Override
    public void searcContractByDate(Date date) {

    }

    @Override
    public void searchContractBetweenDays(Date start, Date end) {

    }
}
