package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.model.Vendor;
import com.example.demo.repository.IContractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

class ContractServiceImplTest {

    @Mock
    private IContractRepository repository;

    @InjectMocks
    private ContractServiceImpl service;

    private Contract contract;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        contract=new Contract();
        contract.setId(Long.valueOf("1"));
        contract.setName("Java Developer Contract");
        contract.setMaxValue(454848655);
        contract.setDescription("The functions of the programer is to develope a backend for a company in USA");
        contract.setTerms("Not assigned");
        Date actual=new Date();
        Calendar contratationTime= Calendar.getInstance();
        contratationTime.setTime(actual);
        contratationTime.add(Calendar.MONTH,7);
        Date endDate=contratationTime.getTime();
        contract.setCreatedDate(actual);
        contract.setFinishedDate(endDate);

        Client client=new Client();
        client.setId(Long.valueOf("1"));
        client.setName("Jeniffer");
        client.setSecondName("Vastidas Velasquez");
        client.setCompany("Flash Tract");
        client.setProfessionalCard(248258);

        Vendor vendor=new Vendor();
        vendor.setId(Long.valueOf("1"));
        vendor.setName("Juan");
        vendor.setRatePerHour(500);
        vendor.setLocation("Colombia");
        vendor.setDni("114420899785");
        vendor.setDescription("Software system engineer");

        contract.setVendor(vendor);
        contract.setClient(client);
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(contract));
        assertNotNull(service.findAll());
    }

    @Test
    void save() {
        when(repository.save(any(Contract.class))).thenReturn(contract);
        assertNotNull(service.save(contract));
    }

    @Test
    void update() {
        Contract actu=new Contract();
        actu.setId(Long.valueOf("1"));
        actu.setName("MEAN Stack Contract");
        actu.setMaxValue(454848655);
        actu.setDescription("The functions of the programer is to develope a backend for a company in USA");
        actu.setTerms("Not assigned");
        Date actual=new Date();
        Calendar contratationTime= Calendar.getInstance();
        contratationTime.setTime(actual);
        contratationTime.add(Calendar.MONTH,7);
        Date endDate=contratationTime.getTime();
        actu.setCreatedDate(actual);
        actu.setFinishedDate(endDate);
        when(repository.saveAndFlush(any(Contract.class))).thenReturn(actu);
        when(repository.findById(any(Long.class))).thenReturn(java.util.Optional.of(actu));
        assertEquals(service.findById(Long.valueOf(1)).get().getName(),"MEAN Stack Contract");
    }

    @Test
    void delete() {
        Mockito.doNothing().when(repository).delete(any(Contract.class));
    }

    @Test
    void findById() {
        when(repository.findById(any(Long.class))).thenReturn(java.util.Optional.of(contract));
        assertEquals(service.findById(Long.valueOf(1)).get().getName(),"Java Developer Contract");
    }

    @Test
    void existById() {
        when(repository.existsById(any(Long.class))).thenReturn(true);
        assertEquals(service.existById(Long.valueOf(1)),true);
    }
}