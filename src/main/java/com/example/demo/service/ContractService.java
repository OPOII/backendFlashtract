package com.example.demo.service;

import com.example.demo.model.Contract;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ContractService {

    public Page<Contract> findAll(Integer page, Integer size, Boolean enablePagination);
    public Contract save(Contract contract);
    public Contract update(Contract contract);
    public void delete(Long id);
    public Optional<Contract> findById(Long id);
    public List<String> getReports(Long id);
    public boolean existById(Long id);
}
