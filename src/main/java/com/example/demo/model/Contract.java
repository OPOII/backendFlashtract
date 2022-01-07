package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="CONTRACT")
@NotNull
public class Contract implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    @NotNull
    private String name;
    @Column
    @NotNull
    private double maxValue;
    @Column
    @NotNull
    private String description;
    @Column
    @NotNull
    private Date createdDate;
    @Column
    @NotNull
    private Date finishedDate;
    @ManyToOne
    @JoinColumn(name ="client_id",nullable = false)
    private Client client;
    @ManyToOne
    @JoinColumn(name ="vendor_id",nullable=false)
    private Vendor vendor;
    @Column
    private List<String>reports;

    public Contract(){

    }

    public Contract(Long id, String name, double maxValue, Date createdDate, Date finishedDate) {
        this.id = id;
        this.name = name;
        this.maxValue = maxValue;
        this.createdDate = createdDate;
        this.finishedDate = finishedDate;
        this.reports = new ArrayList<>();
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
