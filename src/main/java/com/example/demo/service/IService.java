package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.model.Vendor;

public interface IService {

    public void createContract(Long idClient, Long idVendor, Contract contractData);

    public void sendInvoice(Vendor vendor);


}
