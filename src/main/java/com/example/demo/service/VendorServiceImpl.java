package com.example.demo.service;

import com.example.demo.model.Contract;
import com.example.demo.model.Invoice;
import com.example.demo.model.Vendor;
import com.example.demo.repository.IContractRepository;
import com.example.demo.repository.IInvoiceRepository;
import com.example.demo.repository.IVendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private IVendorRepository repository;

    @Autowired
    private IInvoiceRepository invoiceRepository;

    @Autowired
    private IContractRepository contractRepository;

    @Override
    @Transactional
    public Page<Vendor> findAll(Integer page, Integer size, Boolean enablePagination) {
        return repository.findAll(enablePagination? PageRequest.of(page,size): Pageable.unpaged());
    }

    @Override
    @Transactional
    public Vendor save(Vendor vendor) throws Exception {
       try {
        return repository.save(vendor);
       }catch (Exception e){
           throw  new Exception(e.getLocalizedMessage());
       }
    }

    @Override
    @Transactional
    public Vendor update(Vendor vendor) throws Exception {
        try {
            return repository.save(vendor);
        }catch (Error e){
            throw  new Exception("Something get wrong");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(id!=null && id>0){
            repository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public Optional<Vendor> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public boolean existById(Long id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional
    public Invoice createInvoice(Invoice invoice, Long idVendor)throws Exception {
        try {
           create(invoice,idVendor);
           return invoiceRepository.save(invoice);
        }catch(Exception e){
            throw new Exception("Something went wrong");
        }
    }

    @Override
    public String sendInvoice(Long idInvoice, Long idVendor) throws Exception {
        Invoice actualInvoice=null;
        Contract pointerContract=null;
        try {
            Vendor vendor=repository.getById(idVendor);
            int posActualInvoice=Math.toIntExact(idInvoice);
            System.out.println(posActualInvoice);
            posActualInvoice=posActualInvoice-1;
            System.out.println(posActualInvoice);
            actualInvoice=vendor.getInvoices().get(posActualInvoice);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        try {
            pointerContract=contractRepository.getById(actualInvoice.getContractID());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
            actualInvoice.setStatus("Sumbmitted");
            String reporte="";
            LocalDateTime myDateObj = LocalDateTime.now();
            System.out.println("Before formatting: " + myDateObj);
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String invoiceValue=new DecimalFormat("#").format(actualInvoice.getTotalValue());
            String beforeInvoice=new DecimalFormat("#").format(pointerContract.getMaxValue());
            String afterInvoice="";
            if(pointerContract.getMaxValue()-actualInvoice.getTotalValue()>0){
                reporte="Transaction state: Approved"+"\n"+
                        "Value of the invoice: "+invoiceValue+"\n"+
                        "Value of the contract before apply the invoice: "+beforeInvoice+"\n";
                try{
                   afterInvoice=new DecimalFormat("#").format(pointerContract.discountInvoidBill(actualInvoice.getTotalValue()));
                }catch(Exception e){
                    throw new Exception("Something get wrong");
                }
                reporte+="Value of the contract after apply the invoice: "+afterInvoice+"\n"
                        +"Client responsible: "+pointerContract.getClient().getName()+"\n"
                        +"Vendor who send the invoice: "+pointerContract.getVendor().getName()+"\n"
                        +"Approbation Date: "+myDateObj.format(myFormatObj);
                try{
                    pointerContract.getReports().add(reporte);
                }catch (Exception e){
                    throw new Exception("Something get wrong");
                }
                actualInvoice.setStatus("Approved");
            }else{
                actualInvoice.setStatus("Rejected");
            }
            System.out.println(pointerContract.getReports().get(0));
            return actualInvoice.getStatus();


    }

    public void create(Invoice invoice, Long idVendor){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Vendor vendor=repository.findById(idVendor).get();
        String tracker=formatter.format(invoice.getCreatedDate());
        tracker=tracker+"/"+vendor.getName()+"/"+vendor.getDni();
        invoice.setTrackSerial(tracker);
        invoice.setVendor(vendor);
        double total=(vendor.getRatePerHour()* invoice.getHoursWorked())+invoice.getMaterials();
        invoice.setTotalValue(total);
        invoice.setStatus("In Progress");
        vendor.addInvoice(invoice);
    }
    @Transactional
    public Invoice sendInvoicePart1(Long idInvoice,Long idVendor){
        Vendor vendor=repository.getById(idVendor);
        int posActualInvoice=Math.toIntExact(idInvoice);
        System.out.println(posActualInvoice);
        posActualInvoice=posActualInvoice-1;
        System.out.println(posActualInvoice);
        Invoice actualInvoice=vendor.getInvoices().get(posActualInvoice);
        return actualInvoice;
    }
    @Transactional
    public Contract getContractTransactional(Invoice actualInvoice){
        Contract pointerContract=contractRepository.getById(actualInvoice.getContractID());
        return pointerContract;
    }


}
