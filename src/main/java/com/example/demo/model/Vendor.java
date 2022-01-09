package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="VENDOR")
public class Vendor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="VENDOR_ID_GENERATOR", sequenceName="VENDOR_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "VENDOR_ID_GENERATOR")
    @Column
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column
    private String name;

    @Column
    private double ratePerHour;

    @Column
    private String location;

    @Column
    private String dni;

    @Column
    private String description;

    @OneToMany(targetEntity=Contract.class, mappedBy="vendor")
    @JsonIgnore
    private List<Contract>contracts;

    @OneToMany(targetEntity=Invoice.class, mappedBy="vendor")
    @JsonIgnore
    private List<Invoice>invoices;
    public Vendor(){
        this.contracts = new ArrayList<>();
        this.invoices = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(double ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Contract addContract(Contract contract) {
        getContracts().add(contract);
        contract.setVendor(this);
        return contract;
    }

    public Invoice addInvoice(Invoice invoice) {
        getInvoices().add(invoice);
        invoice.setVendor(this);
        return invoice;
    }

}


