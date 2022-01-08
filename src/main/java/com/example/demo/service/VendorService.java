package com.example.demo.service;

import com.example.demo.model.Invoice;
import com.example.demo.model.Vendor;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface VendorService {

    public Page<Vendor> findAll(Integer page, Integer size, Boolean enablePagination);
    public Vendor save(Vendor vendor);
    public Vendor update(Vendor vendor);
    public void delete(Long id);
    public Optional<Vendor> findById(Long id);
    public boolean existById(Long id);
    public Invoice createInvoice(Invoice invoice, Long idVendor);
    public String sendInvoice(Long idInvoice, Long idVendor);
}
