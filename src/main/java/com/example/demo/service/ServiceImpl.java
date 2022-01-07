package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.model.Vendor;
import com.example.demo.repository.IClientRepository;
import com.example.demo.repository.IContractRepository;
import com.example.demo.repository.IInvoiceRepository;
import com.example.demo.repository.IVendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceImpl implements IService {
    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IContractRepository contractRepository;

    @Autowired
    private IInvoiceRepository invoiceRepository;

    @Autowired
    private IVendorRepository vendorRepository;

    @Override
    public void createContract(Long idClient, Long idVendor, Contract contract) {
        if(idVendor==null){
            throw new NullPointerException("The id can't be null");
        }
        Vendor vendor=vendorRepository.findById(idVendor).get();
        if(vendor==null){
            throw new NullPointerException("The vendor doesn't exist");
        }
        if(idClient==null){
            throw new NullPointerException("The idClient can't be null");
        }
       Client client=clientRepository.findById(idClient).get();
        if(client==null){
            throw new NullPointerException("The client can't be null");
        }

        contract.setClient(client);
        contract.setVendor(vendor);

        vendor.getContracts().add(contract);
        client.getContracts().add(contract);

        contractRepository.save(contract);

    }

    @Override
    public void sendInvoice(Vendor vendor) {

    }
}
