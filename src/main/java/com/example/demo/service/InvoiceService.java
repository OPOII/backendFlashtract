package com.example.demo.service;

import com.example.demo.model.Invoice;
import com.example.demo.model.Vendor;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface InvoiceService {

    public Iterable<Invoice> findAll();
    public Invoice save(Invoice invoice, Long idVendor)throws Exception;
    public Invoice update(Invoice invoice)throws Exception;
    public void delete(Long id);
    public Invoice findById(Long id);
    public boolean existById(Long id);
    public Vendor getVendorByID(Long id)throws Exception;

}
