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
    public Page<Invoice> findAll(Integer page, Integer size, Boolean enablePagination) {
        return repository.findAll(enablePagination? PageRequest.of(page,size): Pageable.unpaged());
    }

    @Override
    public Invoice save(Invoice invoice, Long idVendor)throws Exception {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String tracker=formatter.format(invoice.getCreatedDate());
            invoice.setTrackSerial(tracker);
            Vendor owner=vendorRepo.findById(idVendor).get();
            owner.addInvoice(invoice);
            invoice.setVendor(owner);
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
    public Optional<Invoice> findById(Long id) {
        Optional<Invoice> invoice=repository.findById(id);
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
