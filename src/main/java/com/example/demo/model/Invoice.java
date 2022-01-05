package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@Table(name="Invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double value;
    private Date createdDate;
    private String status;
    /**
    private Vendor vendorAssociated;
     */
    private String description;

}
