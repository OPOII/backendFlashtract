package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@Table(name="CONTRACT")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private double maxValue;
    @Column
    private Date createdDate;
    @Column
    private Date finishedDate;
    @ManyToOne
    @JoinColumn(name ="client_id",nullable = false)
    private Client client;
    @ManyToOne
    @JoinColumn(name ="vendor_id",nullable=false)
    private Vendor vendor;

    public Contract(){

    }

    public Contract(Long id, String name, double maxValue, Date createdDate, Date finishedDate, Client client, Vendor vendor) {
        this.id = id;
        this.name = name;
        this.maxValue = maxValue;
        this.createdDate = createdDate;
        this.finishedDate = finishedDate;
        this.client = client;
        this.vendor = vendor;
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

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
