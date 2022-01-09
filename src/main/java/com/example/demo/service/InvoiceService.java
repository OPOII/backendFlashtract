package com.example.demo.service;

import com.example.demo.model.Invoice;
import com.example.demo.model.Vendor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {

    public List<Invoice> findAll();
    public Invoice save(Invoice invoice);
    public Invoice update(Invoice invoice);
    public void delete(Long id);
    public Invoice findById(Long id);
    public boolean existById(Long id);
    public Vendor getVendorByID(Long id);

}
