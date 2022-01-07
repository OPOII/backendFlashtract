package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Data
@Table(name="INVOICE")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column
    private double value;

    //Change the way to numbers ddmmyyhms
    @Column
    private Date createdDate;
    @Column
    private String status;
    @ManyToOne
    @JoinColumn(name ="vendor_id",nullable=false)
    private Vendor vendor;
    @Column
    private String description;
    @Column
    private String trackSerial;

    public Invoice(){

    }

    public Invoice(Long id, double value, String status, String description) {
        this.id = id;
        this.value = value;
        this.createdDate = new Date();
        this.status = status;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrackSerial() {
        return trackSerial;
    }

    public void setTrackSerial(String trackSerial) {
        this.trackSerial = trackSerial;
    }
}
