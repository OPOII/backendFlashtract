package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.repository.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private IClientRepository repository;


    @Override
    public Page<Client> findAll(Integer page, Integer size,Boolean enablePagination) {
        return repository.findAll(enablePagination? PageRequest.of(page,size): Pageable.unpaged());
    }
    @Override
    public Client save(Client client) throws Exception {
        if(client.getId()!=null && client!=null){
           return repository.save(client);
        }else{
            throw new Exception("Client can't be null");
        }

    }
    @Override
    public Client update(Client client) throws Exception {
        if(client.getId() !=null &&  repository.existsById(client.getId())){
          return  repository.save(client);
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
    public Optional<Client> findById(Long id) {
       return repository.findById(id);

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

    @Override
    public boolean existById(Long id) {
        return repository.existsById(id);
    }


}
