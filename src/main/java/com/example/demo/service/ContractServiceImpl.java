package com.example.demo.service;

import com.example.demo.model.Contract;
import com.example.demo.repository.IContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService{

    @Autowired
    private IContractRepository repository;

    @Override
    public Iterable<Contract> findAll() {
        return repository.findAll();
    }

    @Override
    public Contract save(Contract contract) throws Exception{
        if(contract!=null && contract.getId()!=null){
           return repository.save(contract);
        }else{
            throw new Exception("Contract can't be null");
        }
    }

    @Override
    public Contract update(Contract contract) throws Exception{
        if(contract!=null && contract.getId()!=null){
            return repository.save(contract);
        }else{
            throw new Exception("Contract can't be null");
        }
    }

    @Override
    public void delete(Long id) {
        if(id!=null && id>0){
            repository.deleteById(id);
        }
    }

    @Override
    public Contract findById(Long id) {
        Optional<Contract> contract=repository.findById(id);
        return contract.get();
    }

    @Override
    public List<String> getReports(Long id) {
        Optional<Contract> contract=repository.findById(id);
        return contract.get().getReports();
    }
}
