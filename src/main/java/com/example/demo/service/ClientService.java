package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.model.Invoice;
import com.example.demo.model.Vendor;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    public Page<Client> findAll(Integer page, Integer size,Boolean enablePagination);
    public Client save(Client client) throws Exception;
    public Client update(Client client)throws Exception;
    public void delete(Long id);
    public Optional<Client> findById(Long id);
    public List<Contract> getContracts(Long id);
    public Contract searchContractById(Long id);
    public Contract searchContractByDate(Long idClient,Date date);
    public boolean existById(Long id);
    public Contract createContract(Contract contract, Long vendorID, Long clientID)throws Exception;
}
