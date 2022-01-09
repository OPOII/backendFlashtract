package com.example.demo.service;

import com.example.demo.exception.ApiRequestException;
import com.example.demo.model.Invoice;
import com.example.demo.model.Vendor;
import com.example.demo.repository.IInvoiceRepository;
import com.example.demo.repository.IVendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService{
    /**
     * invoice repository
     */
    @Autowired
    private IInvoiceRepository repository;
    /**
     * vendor repository
     */
    @Autowired
    private IVendorRepository vendorRepo;

    /**
     * Get all the invoices that are in the repository
     * @return list of invoices
     */
    @Override
    @Transactional
    public List<Invoice> findAll() {
        return repository.findAll();
    }

    /**
     * Save the invoice in the repository.
     * The validation of the creation of the invoice is already created in the vendorService
     * @param invoice
     * @return invoice saved
     */
    @Override
    @Transactional
    public Invoice save(Invoice invoice){

        try {
            return repository.save(invoice);
        }catch (Exception e) {
            throw new ApiRequestException("Something get wrong");
        }

    }

    /**
     * Update the invoice in the repository
     * @param invoice
     * @return invoice updated
     */
    @Override
    @Transactional
    public Invoice update(Invoice invoice){
        if(invoice!=null && invoice.getId()!=null){
            return repository.saveAndFlush(invoice);
        }else{
            throw new ApiRequestException("The invoice can't be null");
        }
    }

    /**
     * Delete the invoice in the repository by the id
     * @param id
     */
    @Override
    @Transactional
    public void delete(Long id) {
        if(id!=null && id>0){
            repository.deleteById(id);
        }
    }

    /**
     * Search the invoice in the repository
     * @param id
     * @return invoice
     */
    @Override
    @Transactional
    public Invoice findById(Long id) {
        Invoice invoice=repository.findById(id).get();
        return invoice;
    }

    /**
     * Search if the invoice exist in the repository using the id
     * @param id
     * @return boolean
     */
    @Override
    @Transactional
    public boolean existById(Long id) {
        return repository.existsById(id);
    }

    /**
     * Search the vendor by the id in the repository and return it
     * @param id
     * @return vendor
     */
    @Override
    @Transactional
    public Vendor getVendorByID(Long id){
        try {
            Invoice actual=repository.findById(id).get();
            return actual.getVendor();
        }catch (Exception e) {
            throw new ApiRequestException("Something went wrong");
        }
    }



}
