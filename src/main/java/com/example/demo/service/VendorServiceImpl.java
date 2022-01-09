package com.example.demo.service;

import com.example.demo.exception.ApiRequestException;
import com.example.demo.model.Contract;
import com.example.demo.model.Invoice;
import com.example.demo.model.Vendor;
import com.example.demo.repository.IContractRepository;
import com.example.demo.repository.IInvoiceRepository;
import com.example.demo.repository.IVendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Find all the vendors in the repository
     * @return a list of all the vendors
     */
    @Override
    @Transactional
    public List<Vendor> findAll() {
        return repository.findAll();
    }

    /**
     * Save all contract in the repository.
     * This method validate that the fields of the vendor doesn´t have wrong data
     * @param vendor
     * @return Vendor
     */
    @Override
    @Transactional
    public Vendor save(Vendor vendor){
        if(vendor==null){
            throw  new ApiRequestException("The vendor can't be null");
        }if(vendor.getName().isBlank() || vendor.getName().isEmpty()){
            throw  new ApiRequestException("Check the vendor name field, error caused by vendorName "+vendor.getName());
        }if(vendor.getDni().length()<10){
            throw  new ApiRequestException("Check the vendor dni field, error caused by vendorDNI "+vendor.getDni());
        }if(vendor.getRatePerHour()<=0){
            throw  new ApiRequestException("Check the vendor ratePerHour field, error caused by vendorRatePerHour "+vendor.getRatePerHour());
        }if(vendor.getLocation().isBlank() || vendor.getLocation().isEmpty()){
            throw  new ApiRequestException("Check the vendor location field, error caused by vendorLocation "+vendor.getLocation());
        }if(vendor.getDescription().isBlank() || vendor.getDescription().isEmpty()){
            throw  new ApiRequestException("Check the vendor description field, error caused by vendorDescription "+vendor.getDescription());
        }
       try {
        return repository.save(vendor);
       }catch (Exception e){
           throw  new ApiRequestException("Something went wrong and can't save the vendor");
       }
    }

    /**
     * Update the vendor in the repository
     *
     * @param vendor
     * @return vendor
     */
    @Override
    @Transactional
    public Vendor update(Vendor vendor){
        try {
            return repository.saveAndFlush(vendor);
        }catch (Error e){
            throw  new ApiRequestException("Something get wrong");
        }
    }

    /**
     * Delete the vendor of the repository
     * @param id
     */
    @Override
    @Transactional
    public void delete(Long id) {
        if(id!=null && id>0){
            repository.deleteById(id);
        }else{
            throw  new ApiRequestException("Something get wrong");
        }
    }

    /**
     * Search the vendor by the id
     * @param id
     * @return vendor
     */
    @Override
    @Transactional
    public Vendor findById(Long id) {
        return repository.findById(id).get();
    }

    /**
     * Search if the vendor exist
     * @param id
     * @return boolean
     */
    @Override
    @Transactional
    public boolean existById(Long id) {
        return repository.existsById(id);
    }

    /**
     * Create the invoice verifying that the invoice have correct data and that the vendor exist in the repository
     * @param invoice
     * @param idVendor
     * @return invoice
     */
    @Override
    @Transactional
    public Invoice createInvoice(Invoice invoice, Long idVendor){
        try {
           create(invoice,idVendor);
           return invoiceRepository.save(invoice);
        }catch(Exception e){
            throw new ApiRequestException(e.getLocalizedMessage());
        }
    }

    /**
     * Create the method that send the invoice and discount the bill in the contract.
     * Also, validate the fields and if the vendor, contract exist
     * @param idInvoice
     * @param idVendor
     * @return
     */
    @Override
    public String sendInvoice(Long idInvoice, Long idVendor){
        Invoice actualInvoice=null;
        Contract pointerContract=null;
        String message="";
        try {
            Vendor vendor=repository.getById(idVendor);
            int posActualInvoice=Math.toIntExact(idInvoice);
            posActualInvoice=posActualInvoice-1;
            actualInvoice=vendor.getInvoices().get(posActualInvoice);
        }catch (Exception e){
            throw new ApiRequestException("The vendor with that id doesn't exist");
        }
        try {
            pointerContract=contractRepository.getById(actualInvoice.getContractID());

        }catch (Exception e){
            throw new ApiRequestException("The contract with that id doesn't exist");
        }
        try {
            sending(actualInvoice, pointerContract);
        }catch (Exception e){
            throw new ApiRequestException("Can't do the tings");
        }
        return actualInvoice.getStatus();
    }

    /**
     * This method is the continuation of the sendInvoice
     * It wrapped in another method to not use the propagation and isolation to avoid a 500 status, and wrapped in a try catch
     * @param actualInvoice
     * @param pointerContract
     * @return the status of the invoice
     */
    private String sending(Invoice actualInvoice, Contract pointerContract) {
        actualInvoice.setStatus("Sumbmitted");
        String reporte="";
        LocalDateTime myDateObj = LocalDateTime.now();
        System.out.println("Before formatting: " + myDateObj);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String invoiceValue=new DecimalFormat("#").format(actualInvoice.getTotalValue());
        String beforeInvoice=new DecimalFormat("#").format(pointerContract.getMaxValue());
        String afterInvoice="";
        if(pointerContract.getMaxValue()- actualInvoice.getTotalValue()>0){
            reporte="Transaction state: Approved"+"\n"+
                    "Value of the invoice: "+invoiceValue+"\n"+
                    "Value of the contract before apply the invoice: "+beforeInvoice+"\n";
            try{
               afterInvoice=new DecimalFormat("#").format(pointerContract.discountInvoidBill(actualInvoice.getTotalValue()));
            }catch(Exception e){
                throw new ApiRequestException("Something get wrong");
            }
            reporte+="Value of the contract after apply the invoice: "+afterInvoice+"\n"
                    +"Client responsible: "+ pointerContract.getClient().getName()+"\n"
                    +"Vendor who send the invoice: "+ pointerContract.getVendor().getName()+"\n"
                    +"Approbation Date: "+myDateObj.format(myFormatObj);
            pointerContract.getReports().add(reporte);
            actualInvoice.setStatus("Approved");
        }else{
            actualInvoice.setStatus("Rejected");
        }
        try {
            contractRepository.saveAndFlush(pointerContract);
        }catch(Exception e){
            throw new ApiRequestException("Some problem merging the data");
        }
        return reporte;
    }

    /**
     * This method validate the invoice, the fields and if the vendor exist
     * @param invoice
     * @param idVendor
     */
    public void create(Invoice invoice, Long idVendor){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Vendor vendor=null;
        try{
            vendor=repository.findById(idVendor).get();
            if(vendor==null){
                throw new ApiRequestException("The vendor you are trying to associate the invoice doesn't exist, vendorID: "+Math.toIntExact(idVendor));
            }
        }catch (Exception e){
            throw new ApiRequestException("The vendor you are trying to associate the invoice doesn't exist, vendorID: "+Math.toIntExact(idVendor));
        }
        if(invoice.getHoursWorked() <= 0){
            throw  new ApiRequestException("Check the invoice hours worked field, error caused by invoiceWorkedHours "+invoice.getHoursWorked());
        }if(invoice.getDescription().isBlank() || invoice.getDescription().isEmpty()){
            throw  new ApiRequestException("Check the invoice description field, error caused by invoiceDescription "+invoice.getDescription());
        }if(invoice.getMaterials() <= 0){
            throw  new ApiRequestException("Check the invoice materials field, error caused by invoiceGetMaterials "+invoice.getMaterials());
        }if(invoice.getContractID() <= 0){
            throw  new ApiRequestException("Check the invoice contract ID field, error caused by invoiceContractID "+invoice.getContractID());
        }
        try{
            Contract contract=contractRepository.findById(invoice.getContractID()).get();
            if(contract==null){
                throw  new ApiRequestException("The contract that you are creating the bill doesn't exist, please verify an existing contract");
            }
        }catch (Exception e){
            throw  new ApiRequestException("Something went wrong");
        }
        String tracker=formatter.format(invoice.getCreatedDate());
        tracker=tracker+"/"+vendor.getName()+"/"+vendor.getDni();
        invoice.setTrackSerial(tracker);
        invoice.setVendor(vendor);
        double total=(vendor.getRatePerHour()* invoice.getHoursWorked())+invoice.getMaterials();
        invoice.setTotalValue(total);
        invoice.setStatus("In Progress");
        vendor.addInvoice(invoice);
    }



}
