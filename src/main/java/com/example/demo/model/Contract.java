package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@Table(name="Contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private double maxValue;
    private Date createdDate;
    private Date finishedDate;

}
