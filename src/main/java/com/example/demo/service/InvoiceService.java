package com.example.demo.service;

import com.example.demo.model.Invoice;

public interface InvoiceService {

    public Iterable<Invoice>findAll();
    public Invoice save(Invoice invoice)throws Exception;
    public Invoice update(Invoice invoice)throws Exception;
    public void delete(Long id);
    public Invoice findById(Long id);

}
