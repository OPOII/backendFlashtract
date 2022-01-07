package com.example.demo.service;

import com.example.demo.model.Invoice;
import com.example.demo.repository.IInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.logging.SimpleFormatter;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    private IInvoiceRepository repository;

    @Override
    public Iterable<Invoice> findAll() {

        return repository.findAll();
    }

    @Override
    public Invoice save(Invoice invoice)throws Exception {
        if(invoice!=null && invoice.getId()!=null){
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String tracker=formatter.format(invoice.getCreatedDate())+invoice.getVendor().getDni()+invoice.getVendor().getName();
            invoice.setTrackSerial(tracker);
            return repository.save(invoice);
        }else{
            throw new NullPointerException("The invoice can't be null");
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
        Optional<Invoice> invoice=repository.findById(id);
        return invoice.get();
    }


}
