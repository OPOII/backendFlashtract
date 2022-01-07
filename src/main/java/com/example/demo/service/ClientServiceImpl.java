package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.model.Vendor;
import com.example.demo.repository.IClientRepository;
import com.example.demo.repository.IContractRepository;
import com.example.demo.repository.IVendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private IClientRepository repository;


    @Override
    public Iterable<Client> findAll() {
        return null;
    }
    @Override
    public void save(@Valid Client client) {
        if(client.getId()!=null && client!=null){
            repository.save(client);
        }
    }
    @Override
    public void update(Client client) {
        if(client.getId() !=null &&  repository.existsById(client.getId())){
            repository.save(client);
        }
    }
    @Override
    public void delete(Long id) {
        if(id!=null && id>0){
            repository.deleteById(id);
        }
    }
    @Override
    public Client findById(Long id) {
       return repository.findById(id).get();

    }
    @Override
    public List<Contract> getContracts(Long id) {

        Optional<Client> actual=repository.findById(id);
        return actual.get().getContracts();

    }
    @Override
    public Contract searchContractById(Long id) {
        Optional<Client>actual=repository.findById(id);
        List<Contract> list=actual.get().getContracts();
        Contract filter= (Contract) list.stream().filter(contract->contract.getId()==id);
        return filter;

    }
    @Override
    public Contract searchContractByDate(Long idClient,Date date) {
        Optional<Client>actual=repository.findById(idClient);
        List<Contract> list=actual.get().getContracts();
        Contract filter=(Contract) list.stream().filter(contract->contract.getCreatedDate()==date);
        return filter;
    }



}
