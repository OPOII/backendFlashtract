package com.example.demo.service;

import com.example.demo.exception.ApiRequestException;
import com.example.demo.model.Contract;
import com.example.demo.repository.IContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService{

    /**
     * Contract repository
     */
    @Autowired
    private IContractRepository repository;

    /**
     * Get all the contracts in the repository
     * @return
     */
    @Override
    @Transactional
    public List<Contract> findAll() {
        return repository.findAll();
    }

    /**
     * Save the contract
     * @Pre contract is not null and doesn't have bad fields
     * @param contract
     * @return
     */
    @Override
    @Transactional
    public Contract save(Contract contract){

        try {
           return repository.save(contract);

        }catch (Exception e) {
            throw new ApiRequestException(e.getLocalizedMessage());
        }

    }

    /**
     * Update the contract
     * @param contract
     * @return contract
     */
    @Override
    @Transactional
    public Contract update(Contract contract){
        if(contract!=null && contract.getId()!=null){
            return repository.saveAndFlush(contract);
        }else{
            throw new ApiRequestException("Contract can't be null");
        }
    }

    /**
     * Delete a contract by the id
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
     * Find the contract by id
     * @param id
     * @return contract
     */
    @Override
    @Transactional
    public Optional<Contract> findById(Long id) {
        Optional<Contract> contract=repository.findById(id);
        return contract;
    }

    /**
     * Get the list of the reports of the contracts
     * @param id
     * @return list of strings
     */
    @Override
    @Transactional
    public List<String> getReports(Long id) {
        Optional<Contract> contract=repository.findById(id);
        return contract.get().getReports();
    }

    /**
     * Search if the Contract exist by the id
     * @param id
     * @return boolean with the answer
     */
    @Override
    @Transactional
    public boolean existById(Long id) {
        return repository.existsById(id);
    }


}
