package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@Table(name="INVOICE")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double value;
    private Date createdDate;
    private String status;
    @ManyToOne
    @JoinColumn(name ="vendor_id",nullable=false)
    private Vendor vendor;
    private String description;
    public Invoice(){

    }

}
