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
    private Long id;
    private String name;
    private double ratePerHour;
    private String location;
    private String description;
    @OneToMany(targetEntity=Contract.class, mappedBy="vendor")
    private List<Contract>contracts;

    @OneToMany(targetEntity=Invoice.class, mappedBy="vendor")
    private List<Invoice>invoices;
    public Vendor(){

    }
}


