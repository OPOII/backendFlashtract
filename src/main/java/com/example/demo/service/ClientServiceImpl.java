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

    /**
     * find all the clients created
     * @return list of clients
     */
    @Override
    @Transactional
    public List<Client> findAll() {
        return repository.findAll();
    }

    /**
     * The service validate the logic of the buissness in case that some wrong data filter in the object
     * @Pre the client can't be null and the fields must have all the parameters
     * can't have negative values the fields that have numeric attributes
     * can't be null or in blank the alphanumeric fields
     * @param client
     * @return client
     */
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

    /**
     * Update an existing client
     * @param client
     * @return
     */
    @Override
    @Transactional
    public Client update(Client client)  {
        if(client.getId() !=null &&  repository.existsById(client.getId())){
          return  repository.saveAndFlush(client);
        }else{
            throw new ApiRequestException("Something got wrong while updating client");
        }
    }

    /**
     * Delete a client by ID
     * @param id
     */
    @Override
    @Transactional
    public void delete(Long id) {
        if(id!=null && id>0){
            repository.deleteById(id);
        }
    }

    /**
     * Find a client by ID
     * @param id
     * @return
     */
    @Override
    public Optional<Client> findById(Long id) {
       return repository.findById(id);

    }

    /**
     * Search the client and return it's contracts
     * @param id
     * @return List of contracts
     */
    @Override
    @Transactional
    public List<Contract> getContracts(Long id) {

        Client actual=repository.findById(id).get();
        return actual.getContracts();

    }

    /**
     * Search an specific contract by the ID
     * @param id
     * @return contract
     */
    @Override
    @Transactional
    public Contract searchContractById(Long id) {
        Client actual=repository.findById(id).get();
        List<Contract> list=actual.getContracts();
        Contract filter= (Contract) list.stream().filter(contract->contract.getId()==id);
        return filter;

    }

    /**
     * Search a contract by a given date when was created
     * @param idClient
     * @param date
     * @return contract
     */
    @Override
    @Transactional
    public Contract searchContractByDate(Long idClient,Date date) {
        Optional<Client>actual=repository.findById(idClient);
        List<Contract> list=actual.get().getContracts();
        Contract filter=(Contract) list.stream().filter(contract->contract.getCreatedDate()==date);
        return filter;
    }

    /**
     * Search if the client exist and return a boolean
     * @param id
     * @return boolean
     */
    @Override
    @Transactional
    public boolean existById(Long id) {
        return repository.existsById(id);
    }

    /**
     * Create a contract based in the contract object and need the ids to search the actual client who is creating the contract
     * and the vendor to view if exist. Also, the verifications are valid
     * @Pre cotnract isn't null, vendor id and client id are different of 0
     * @param contract
     * @param vendorID
     * @param clientID
     * @return
     */
    @Override
    @Transactional
    public Contract createContract(Contract contract, Long vendorID, Long clientID) {
        try{
            if(vendorRepository.existsById(vendorID)==false) {
                throw new ApiRequestException("The vendor you are trying to get doesn't exist, check that is created");
            }
        }catch(Exception e){
            throw new ApiRequestException(e.getLocalizedMessage());
        }
        try{
            if(repository.existsById(clientID)==false) {
                throw new ApiRequestException("The client you are trying to get doesn't exist, check that is created");
            }
        }catch(Exception e){
            throw new ApiRequestException(e.getLocalizedMessage());
        }
        if(contract.getName().isBlank()|| contract.getName().isEmpty()){
            throw new ApiRequestException("Check the contract name");
        }if(contract.getMaxValue()<=0){
            throw new ApiRequestException("Check the contract value, can't be less than 1");
        }if(contract.getDescription().isBlank()|| contract.getDescription().isEmpty()){
            throw new ApiRequestException("Check the contract description");
        }if(contract.getTerms().isBlank()|| contract.getTerms().isEmpty()){
            throw new ApiRequestException("Check the contract description");
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

            return contractRepository.save(contract);
        }catch (Exception e){
            throw new ApiRequestException("Something went wrong creating the contract");
        }
    }

    /**
     * Search the actual user and the contract that want to see, return the historial of the bills
     * @param idClient
     * @param idContract
     * @return listf of string
     */
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
