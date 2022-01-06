package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="VENDOR")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private double ratePerHour;
    @Column
    private String location;
    @Column
    private String description;
    @OneToMany(targetEntity=Contract.class, mappedBy="vendor")
    private List<Contract>contracts;

    @OneToMany(targetEntity=Invoice.class, mappedBy="vendor")
    private List<Invoice>invoices;
    public Vendor(){

    }

    public Vendor(Long id, String name, double ratePerHour, String location, String description, List<Contract> contracts, List<Invoice> invoices) {
        this.id = id;
        this.name = name;
        this.ratePerHour = ratePerHour;
        this.location = location;
        this.description = description;
        this.contracts = contracts;
        this.invoices = invoices;
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
}


