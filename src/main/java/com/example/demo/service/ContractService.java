package com.example.demo.service;

import com.example.demo.model.Contract;

import java.util.List;

public interface ContractService {

    public Iterable<Contract> findAll();
    public Contract save(Contract contract)throws Exception;
    public Contract update(Contract contract)throws Exception;
    public void delete(Long id);
    public Contract findById(Long id);
    public List<String> getReports(Long id);
}
