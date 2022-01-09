package com.example.demo.service;

import com.example.demo.model.Invoice;
import com.example.demo.model.Vendor;

import java.util.List;
import java.util.Optional;

public interface VendorService {

    public List<Vendor> findAll();
    public Vendor save(Vendor vendor);
    public Vendor update(Vendor vendor);
    public void delete(Long id);
    public Optional<Vendor> findById(Long id);
    public boolean existById(Long id);
    public Invoice createInvoice(Invoice invoice, Long idVendor);
    public String sendInvoice(Long idInvoice, Long idVendor);
}
