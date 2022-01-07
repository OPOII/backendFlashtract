package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.model.Vendor;
import com.example.demo.repository.IClientRepository;
import com.example.demo.repository.IContractRepository;
import com.example.demo.repository.IVendorRepository;
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

    @Autowired
    private IContractRepository contractRepository;

    @Autowired
    private IVendorRepository vendorRepository;


    @Override
    public Page<Client> findAll(Integer page, Integer size,Boolean enablePagination) {
        return repository.findAll(enablePagination? PageRequest.of(page,size): Pageable.unpaged());
    }
    @Override
    public Client save(Client client) throws Exception {
        if(client!=null){
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

        Client actual=repository.findById(id).get();
        return actual.getContracts();

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

    @Override
    public Contract createContract(Contract contract, Long vendorID, Long clientID)throws Exception {
        try{
            Vendor vendor=vendorRepository.findById(vendorID).get();
            Client client=repository.findById(clientID).get();
            contract.setVendor(vendor);
            contract.setClient(client);

            vendor.addContract(contract);

            client.addContract(contract);

           return contractRepository.save(contract);
        }catch (Exception e){
            throw new Exception("Something went wrong");
        }
    }


}
