package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
@Entity
@Data
@Table(name="INVOICE")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="INVOICE_ID_GENERATOR", sequenceName="INVOICE_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "INVOICE_ID_GENERATOR")
    @Column
    @JsonIgnore
    private Long id;
    @Column
    @JsonIgnore
    private double totalValue;
    @Column
    private int hoursWorked;
    //Change the way to numbers ddmmyyhms
    @Column
    @NotNull
    private Date createdDate;
    @Column
    private String status;
    @ManyToOne
    @JoinColumn(name ="vendor_id",nullable=false)
    @JsonIgnore
    private Vendor vendor;
    @Column
    @NotBlank
    private String description;
    @Column
    private double materials;
    @Column
    @NotNull
    private String trackSerial;
    @Column
    @NotNull
    private Long contractID;

    public Invoice(){

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
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

    public double getMaterials() {
        return materials;
    }

    public void setMaterials(double materials) {
        this.materials = materials;
    }

    public Long getContractID() {
        return contractID;
    }

    public void setContractID(Long contractID) {
        this.contractID = contractID;
    }
}
