package com.example.demo.service;

import com.example.demo.model.Invoice;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface InvoiceService {

    public Page<Invoice> findAll(Integer page, Integer size, Boolean enablePagination);
    public Invoice save(Invoice invoice)throws Exception;
    public Invoice update(Invoice invoice)throws Exception;
    public void delete(Long id);
    public Optional<Invoice> findById(Long id);
    public boolean existById(Long id);

}
