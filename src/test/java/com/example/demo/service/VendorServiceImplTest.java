package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.model.Vendor;
import com.example.demo.repository.IVendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VendorServiceImplTest {

    @Mock
    private IVendorRepository repository;

    @InjectMocks
    private VendorServiceImpl service;

    private Vendor vendor;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        vendor=new Vendor();
        vendor.setId(Long.valueOf("1"));
        vendor.setName("Juan");
        vendor.setRatePerHour(500);
        vendor.setLocation("Colombia");
        vendor.setDni("114420899785");
        vendor.setDescription("Software system engineer");

    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(vendor));
        assertNotNull(service.findAll());
    }

    @Test
    void save(){
        when(repository.save(any(Vendor.class))).thenReturn(vendor);
        assertNotNull(service.save(vendor));
    }

    @Test
    void update() {
        Vendor updated=new Vendor();
        updated.setId(Long.valueOf("1"));
        updated.setName("Profesional");
        updated.setRatePerHour(500);
        updated.setLocation("Argentina");
        updated.setDni("114420899785");
        updated.setDescription("Software system engineer");
        when(repository.saveAndFlush(any(Vendor.class))).thenReturn(updated);
        when(repository.findById(any(Long.class))).thenReturn(java.util.Optional.of(updated));
        assertEquals(service.findById(Long.valueOf(1)).getLocation(),"Argentina");
    }

    @Test
    void delete() {
        Mockito.doNothing().when(repository).delete(any(Vendor.class));
    }

    @Test
    void findById() {
        when(repository.findById(any(Long.class))).thenReturn(java.util.Optional.of(vendor));
        assertEquals(service.findById(Long.valueOf(1)).getName(),"Juan");
    }

    @Test
    void existById() {
        when(repository.existsById(any(Long.class))).thenReturn(true);
        assertEquals(service.existById(Long.valueOf(1)),true);
    }


}