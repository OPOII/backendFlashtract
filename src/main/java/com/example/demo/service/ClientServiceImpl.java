package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
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
        return repository.findAll();
    }
    @Override
    public Client save(@Valid Client client) throws Exception {
        if(client.getId()!=null && client!=null){
           return repository.save(client);
        }else{
            throw new Exception("Client can't be null");
        }

    }
    @Override
    public Client update(Client client) throws Exception {
        if(client.getId() !=null &&  repository.existsById(client.getId())){
            Client actual=repository.findById(client.getId()).get();
            actual=client;
          return  repository.save(actual);
        }else{
            throw new Exception("Something got wrong while updating client");
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
        Client actual=repository.findById(id).get();
        List<Contract> list=actual.getContracts();
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
