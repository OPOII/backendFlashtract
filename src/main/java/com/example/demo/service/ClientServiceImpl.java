package com.example.demo.service;

import com.example.demo.exception.ApiRequestException;
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

import javax.transaction.Transactional;
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
    @Transactional
    public Page<Client> findAll(Integer page, Integer size,Boolean enablePagination) {
        return repository.findAll(enablePagination? PageRequest.of(page,size): Pageable.unpaged());
    }
    @Override
    @Transactional
    public Client save(Client client)  {
        String message="";
        if(client.getName().isBlank() || client.getName().isEmpty()){
            message="Check the client name field,"+"error caused by: "+"cliengName "+client.getName();
            throw new ApiRequestException(message);
        }else if(client.getCompany().isBlank() || client.getCompany().isEmpty()){
            message="Check the company name field,"+"error caused by: "+" clientCompany " +client.getCompany();
            throw new ApiRequestException(message);
        }else if(client.getProfessionalCard()<=0 ){
            message="Check the professional card field,"+"error caused by: "+"client professionalCard "+client.getProfessionalCard();
            throw new ApiRequestException(message);
        }else{
            return repository.save(client);
        }
    }
    @Override
    @Transactional
    public Client update(Client client)  {
        if(client.getId() !=null &&  repository.existsById(client.getId())){
          return  repository.save(client);
        }else{
            throw new ApiRequestException("Something got wrong while updating client");
        }
    }
    @Override
    @Transactional
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
    @Transactional
    public List<Contract> getContracts(Long id) {

        Client actual=repository.findById(id).get();
        return actual.getContracts();

    }
    @Override
    @Transactional
    public Contract searchContractById(Long id) {
        Client actual=repository.findById(id).get();
        List<Contract> list=actual.getContracts();
        Contract filter= (Contract) list.stream().filter(contract->contract.getId()==id);
        return filter;

    }
    @Override
    @Transactional
    public Contract searchContractByDate(Long idClient,Date date) {
        Optional<Client>actual=repository.findById(idClient);
        List<Contract> list=actual.get().getContracts();
        Contract filter=(Contract) list.stream().filter(contract->contract.getCreatedDate()==date);
        return filter;
    }

    @Override
    @Transactional
    public boolean existById(Long id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional
    public Contract createContract(Contract contract, Long vendorID, Long clientID) {
        try{
            if(vendorRepository.existsById(vendorID)==false) {
                throw new ApiRequestException("The vendor you are trying to get doesn't exist, check that is created");
            }
        }catch(Exception e){
            throw new ApiRequestException("Something went wrong creating the contract");
        }
        try{
            if(repository.existsById(clientID)==false) {
                throw new ApiRequestException("The client you are trying to get doesn't exist, check that is created");
            }
        }catch(Exception e){
            throw new ApiRequestException("Something went wrong creating the contract");
        }
        try{
            Vendor vendor=vendorRepository.findById(vendorID).get();
            Client client=repository.findById(clientID).get();
            contract.setVendor(vendor);
            contract.setClient(client);

            vendor.addContract(contract);
            System.out.println("Contratos vendedor: "+vendor.getContracts().size());
            client.addContract(contract);
            System.out.println("Contratos cliente: "+client.getContracts().size());
            /**
             * Some problem here
             */
            return contractRepository.save(contract);
        }catch (Exception e){
            throw new ApiRequestException("Something went wrong creating the contract");
        }
    }

    @Override
    public List<String> getHistoryBills(Long idClient, Long idContract) {
        Client client=null;
        int posContract=Math.toIntExact(idContract);
        posContract-=1;
        try{
            client=repository.findById(idClient).get();
            return client.getContracts().get(posContract).getReports();
        }catch (Exception e){
            throw new ApiRequestException("Something went wrong when getting the bills");
        }

    }


}
