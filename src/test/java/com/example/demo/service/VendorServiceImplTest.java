package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.model.Invoice;
import com.example.demo.model.Vendor;
import com.example.demo.repository.IVendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

class VendorServiceImplTest {

    @Mock
    private IVendorRepository repository;

    @InjectMocks
    private VendorServiceImpl service;

    private Client client;

    private Contract contract;

    private Invoice invoice;

    private Vendor vendor;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        vendor=new Vendor();
        Long id= 1L;
        vendor.setId(id);
        vendor.setName("Juan");
        vendor.setRatePerHour(500);
        vendor.setLocation("Colombia");
        vendor.setDni("114420899785");
        vendor.setDescription("Software system engineer");


        client=new Client();
        Long clientID=1L;
        client.setId(clientID);
        client.setName("Jeniffer");
        client.setSecondName("Vastidas Velasquez");
        client.setCompany("Flash Tract");
        client.setProfessionalCard(248258);

        contract=new Contract();
        contract.setName("Java Developer Contract");
        contract.setMaxValue(454848655);
        contract.setDescription("The functions of the programer is to develope a backend for a company in USA");
        contract.setTerms("Not assigned");
        Date actual=new Date();
        Calendar contratationTime=Calendar.getInstance();
        contratationTime.setTime(actual);
        contratationTime.add(Calendar.MONTH,7);
        Date endDate=contratationTime.getTime();
        contract.setCreatedDate(actual);
        contract.setFinishedDate(endDate);

        invoice=new Invoice();
        invoice.setId(1L);
        invoice.setMaterials(6168);
        invoice.setHoursWorked(200);
        invoice.setTotalValue(vendor.getRatePerHour()*invoice.getHoursWorked()+(invoice.getMaterials()));
        invoice.setCreatedDate(new Date());
        invoice.setStatus("Created");
        invoice.setVendor(vendor);
        invoice.setDescription("bla bla bla");
        invoice.setTrackSerial("askfjasfafa");
        invoice.setContractID(1L);



    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(vendor));
        assertNotNull(service.findAll());
        assertEquals(service.findAll().size(),1);
    }

    @Test
    void save() {
        when(repository.save(any(Vendor.class))).thenReturn(vendor);
        assertNotNull(service.save(vendor));
    }


    @Test
    void update() {
        vendor.setName("Prueba con esto");
        when(repository.saveAndFlush(any(Vendor.class))).thenReturn(vendor);
        assertNotNull(service.update(vendor));
        assertEquals(service.findById(1L).get().getName(),"Prueba con esto");
    }

    @Test
    void delete() {
    }

    @Test
    void findById() {
    }

    @Test
    void existById() {
    }

    @Test
    void createInvoice() {
    }

    @Test
    void sendInvoice() {
    }
}