package com.example.demo.service;

import com.example.demo.model.Contract;
import com.example.demo.repository.IContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService{

    @Autowired
    private IContractRepository repository;

    @Override
    @Transactional
    public Page<Contract> findAll(Integer page, Integer size, Boolean enablePagination) {
        return repository.findAll(enablePagination? PageRequest.of(page,size): Pageable.unpaged());
    }

    @Override
    @Transactional
    public Contract save(Contract contract) throws Exception{

        try {
           return repository.save(contract);

        }catch (Exception e) {
            throw new Exception(e.getLocalizedMessage());
        }

    }

    @Override
    @Transactional
    public Contract update(Contract contract) throws Exception{
        if(contract!=null && contract.getId()!=null){
            return repository.save(contract);
        }else{
            throw new Exception("Contract can't be null");
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
    @Transactional
    public Optional<Contract> findById(Long id) {
        Optional<Contract> contract=repository.findById(id);
        return contract;
    }

    @Override
    @Transactional
    public List<String> getReports(Long id) {
        Optional<Contract> contract=repository.findById(id);
        return contract.get().getReports();
    }

    @Override
    @Transactional
    public boolean existById(Long id) {
        return repository.existsById(id);
    }

    public boolean validateContractData(Contract contract){
        boolean validate=false;
        if(Double.valueOf(contract.getMaxValue())!=null && contract.getMaxValue()>0 &&
        contract.getDescription()!=null && !contract.getDescription().isEmpty() && contract.getCreatedDate()!=null
                && contract.getFinishedDate()!=null){
        validate=true;
        }
        return validate;
    }
}
