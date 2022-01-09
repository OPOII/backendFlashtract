package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Invoice;
import com.example.demo.model.Vendor;
import com.example.demo.repository.IInvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

class InvoiceServiceImplTest {

    @Mock
    private IInvoiceRepository repository;

    @InjectMocks
    private InvoiceServiceImpl service;

    private Invoice invoice;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Vendor vendor=new Vendor();
        vendor.setId(Long.valueOf("1"));
        vendor.setName("Juan");
        vendor.setRatePerHour(500);
        vendor.setLocation("Colombia");
        vendor.setDni("114420899785");
        vendor.setDescription("Software system engineer");

        invoice=new Invoice();
        invoice.setId(Long.valueOf("1"));
        invoice.setMaterials(6168);
        invoice.setHoursWorked(200);
        invoice.setTotalValue(vendor.getRatePerHour()*invoice.getHoursWorked()+(invoice.getMaterials()));
        invoice.setCreatedDate(new Date());
        invoice.setStatus("Created");
        invoice.setVendor(vendor);
        invoice.setDescription("bla bla bla");
        invoice.setTrackSerial("askfjasfafa");
        invoice.setContractID(Long.valueOf("1"));
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(invoice));
        assertNotNull(service.findAll());
    }

    @Test
    void save() {
        when(repository.save(any(Invoice.class))).thenReturn(invoice);
        assertNotNull(service.save(invoice));
        when(repository.existsById(any(Long.class))).thenReturn(true);
        assertEquals(service.existById(Long.valueOf("1")),true);
    }

    @Test
    void update() {
        Vendor vendor=new Vendor();
        vendor.setId(Long.valueOf("1"));
        vendor.setName("Juan");
        vendor.setRatePerHour(500);
        vendor.setLocation("Colombia");
        vendor.setDni("114420899785");
        vendor.setDescription("Software system engineer");

        Invoice update=new Invoice();
        update.setId(Long.valueOf("1"));
        update.setMaterials(6168);
        update.setHoursWorked(200);
        update.setTotalValue(vendor.getRatePerHour()*update.getHoursWorked()+(update.getMaterials()));
        update.setCreatedDate(new Date());
        update.setStatus("Created");
        update.setVendor(vendor);
        update.setDescription("El señor debe de hacer el curso de MEAN");
        update.setTrackSerial("askfjasfafa");
        update.setContractID(Long.valueOf("1"));

        when(repository.saveAndFlush(any(Invoice.class))).thenReturn(update);
        when(repository.findById(any(Long.class))).thenReturn(java.util.Optional.of(update));
        assertEquals(service.findById(Long.valueOf(1)).getDescription(),"El señor debe de hacer el curso de MEAN");
    }

    @Test
    void delete() {
        Mockito.doNothing().when(repository).delete(any(Invoice.class));
        when(repository.existsById(any(Long.class))).thenReturn(false);
        assertEquals(service.existById(Long.valueOf("1")),false);
    }

    @Test
    void findById() {
        when(repository.findById(any(Long.class))).thenReturn(java.util.Optional.of(invoice));
        assertEquals(service.findById(Long.valueOf(1)).getDescription(),"bla bla bla");
    }

    @Test
    void existById() {
        when(repository.existsById(any(Long.class))).thenReturn(true);
        assertEquals(service.existById(Long.valueOf("1")),true);
    }


}