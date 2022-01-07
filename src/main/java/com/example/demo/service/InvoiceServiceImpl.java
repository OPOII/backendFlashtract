package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Invoice;
import com.example.demo.model.Vendor;
import com.example.demo.repository.IInvoiceRepository;
import com.example.demo.repository.IVendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.logging.SimpleFormatter;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    private IInvoiceRepository repository;

    @Autowired
    private IVendorRepository vendorRepo;

    @Override
    public Iterable<Invoice> findAll() {
        return repository.findAll();
    }

    @Override
    public Invoice save(Invoice invoice)throws Exception {

        try {
            return repository.save(invoice);
        }catch (Exception e) {
            throw new Exception("Something get wrong");
        }

    }

    @Override
    public Invoice update(Invoice invoice) throws Exception{
        if(invoice!=null && invoice.getId()!=null){
            return repository.save(invoice);
        }else{
            throw new NullPointerException("The invoice can't be null");
        }
    }

    @Override
    public void delete(Long id) {
        if(id!=null && id>0){
            repository.deleteById(id);
        }
    }

    @Override
    public Invoice findById(Long id) {
        Invoice invoice=repository.findById(id).get();
        return invoice;
    }

    @Override
    public boolean existById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Vendor getVendorByID(Long id) throws Exception{
        try {
            Invoice actual=repository.findById(id).get();
            return actual.getVendor();
        }catch (Exception e) {
            throw new Exception("Something went wrong");
        }
    }



}
