package com.example.demo.service;

import com.example.demo.model.Client;
import com.example.demo.model.Contract;
import com.example.demo.model.Vendor;
import com.example.demo.repository.IClientRepository;
import com.example.demo.repository.IContractRepository;
import com.example.demo.repository.IVendorRepository;
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

class ClientServiceImplTest {

    @Mock
    private IClientRepository repository;
    @Mock
    private IVendorRepository repoVendor;
    @Mock
    private IContractRepository repoContract;

    @InjectMocks
    private ClientServiceImpl service;
    @InjectMocks
    private ContractServiceImpl contractService;
    @InjectMocks
    private VendorServiceImpl vendorService;

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        client=new Client();
        client.setId(Long.valueOf("1"));
        client.setName("Jeniffer");
        client.setSecondName("Vastidas Velasquez");
        client.setCompany("Flash Tract");
        client.setProfessionalCard(248258);
    }
    @Test
    void findAll() {
       when(repository.findAll()).thenReturn(Arrays.asList(client));
        assertNotNull(service.findAll());
    }

    @Test
    void save() {
        when(repository.save(any(Client.class))).thenReturn(client);
        assertNotNull(service.save(client));
        when(repository.existsById(any(Long.class))).thenReturn(true);
        assertEquals(service.existById(Long.valueOf("1")),true);
    }

    @Test
    void update() {
        Client update=new Client();
        update.setId(Long.valueOf("1"));
        update.setName("Juliana");
        update.setSecondName("Vastidas Velasquez");
        update.setCompany("Flash Tract");
        update.setProfessionalCard(248258);
        when(repository.saveAndFlush(any(Client.class))).thenReturn(update);
        when(repository.findById(any(Long.class))).thenReturn(java.util.Optional.of(update));
        assertEquals(service.findById(Long.valueOf(1)).get().getName(),"Juliana");
    }

    @Test
    void delete() {
        Mockito.doNothing().when(repository).delete(any(Client.class));
        when(repository.existsById(any(Long.class))).thenReturn(false);
        assertEquals(service.existById(Long.valueOf("1")),false);
    }

    @Test
    void findById() {
        when(repository.findById(any(Long.class))).thenReturn(java.util.Optional.of(client));
        assertEquals(service.findById(Long.valueOf(1)).get().getName(),"Jeniffer");
    }

    @Test
    void existById() {
        when(repository.existsById(any(Long.class))).thenReturn(true);
        assertEquals(service.existById(Long.valueOf("1")),true);
    }

    @Test
    void createContract() {
        Contract contract=new Contract();
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

        Vendor vendor=new Vendor();
        vendor.setId(Long.valueOf("1"));
        vendor.setName("Juan");
        vendor.setRatePerHour(500);
        vendor.setLocation("Colombia");
        vendor.setDni("114420899785");
        vendor.setDescription("Software system engineer");

        contract.setVendor(vendor);
        contract.setClient(client);
        when(repoVendor.save(any(Vendor.class))).thenReturn(vendor);
        when(repository.save(any(Client.class))).thenReturn(client);
        when(repoContract.save(any(Contract.class))).thenReturn(contract);

        when(repoVendor.existsById(any(Long.class))).thenReturn(true);
        when(repository.existsById(any(Long.class))).thenReturn(true);
        when(repoContract.existsById(any(Long.class))).thenReturn(true);

        when(repoVendor.findById(any(Long.class))).thenReturn(java.util.Optional.of(vendor));
        when(repository.findById(any(Long.class))).thenReturn(java.util.Optional.of(client));
        when(repoContract.findById(any(Long.class))).thenReturn(java.util.Optional.of(contract));

        System.out.println( vendorService.save(vendor).getId());
        System.out.println( service.save(client).getId());
        service.createContract(contract,vendor.getId(),client.getId());
        assertNotNull(service.existById(contract.getId()));

    }
}