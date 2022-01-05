package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="Vendor")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private double ratePerHour;
    private String location;
    private String description;
    /**@OneToMany(targetEntity=Vendor.class, mappedBy="Invoice", fetch=FetchType.EAGER)
    private List<Invoice> contracts;
*/
}


