package com.example.demo.service;

import com.example.demo.model.Vendor;
import com.example.demo.repository.IVendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private IVendorRepository repository;

    @Override
    public Page<Vendor> findAll(Integer page, Integer size, Boolean enablePagination) {
        return repository.findAll(enablePagination? PageRequest.of(page,size): Pageable.unpaged());
    }

    @Override
    public Vendor save(Vendor vendor) throws Exception {
       try {
        return repository.save(vendor);
       }catch (Exception e){
           throw  new Exception("Something get wrong");
       }
    }

    @Override
    public Vendor update(Vendor vendor) throws Exception {
        try {
            return repository.save(vendor);
        }catch (Error e){
            throw  new Exception("Something get wrong");
        }
    }

    @Override
    public void delete(Long id) {
        if(id!=null && id>0){
            repository.deleteById(id);
        }
    }

    @Override
    public Optional<Vendor> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public boolean existById(Long id) {
        return repository.existsById(id);
    }




}
