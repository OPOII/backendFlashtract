package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="CONTRACT")
public class Contract implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="CONTRACT_ID_GENERATOR", sequenceName="CONTRACT_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CONTRACT_ID_GENERATOR")
    @Column
    @JsonIgnore
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
    private String terms;
    @Column
    private Date createdDate;
    @Column
    private Date finishedDate;

    @ManyToOne
    @JoinColumn(name ="client_id")
    @JsonIgnore
    private Client client;
    @ManyToOne
    @JoinColumn(name ="vendor_id")
    @JsonIgnore
    private Vendor vendor;

    @ElementCollection
    @Column(columnDefinition="text")
    @JsonIgnore
    private List<String>reports=new ArrayList<String>();

    public Contract(){
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getReports() {
        return reports;
    }

    public void setReports(List<String> reports) {
        this.reports = reports;
    }

    public double discountInvoidBill(double mount){
        this.maxValue=this.maxValue-mount;
        return maxValue;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }
}
