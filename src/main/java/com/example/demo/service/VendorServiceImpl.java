package com.example.demo.service;

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

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private IVendorRepository repository;

    @Autowired
    private IInvoiceRepository invoiceRepository;

    @Override
    public Page<Vendor> findAll(Integer page, Integer size, Boolean enablePagination) {
        return repository.findAll(enablePagination? PageRequest.of(page,size): Pageable.unpaged());
    }

    @Override
    public Vendor save(Vendor vendor) throws Exception {
       try {
        return repository.save(vendor);
       }catch (Exception e){
           throw  new Exception(e.getLocalizedMessage());
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

    @Override
    public Invoice createInvoice(Invoice invoice, Long idVendor)throws Exception {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Vendor vendor=repository.findById(idVendor).get();
            String tracker=formatter.format(invoice.getCreatedDate());
            tracker=tracker+"/"+vendor.getName()+"/"+vendor.getDni();
            invoice.setTrackSerial(tracker);
            invoice.setVendor(vendor);
            double total=vendor.getRatePerHour()* invoice.getHoursWorked();
            invoice.setTotalValue(total);
            invoice.setStatus("In Progress");
            vendor.addInvoice(invoice);
           return invoiceRepository.save(invoice);
        }catch(Exception e){
            throw new Exception(e.getLocalizedMessage());
        }
    }


}
