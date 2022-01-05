package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Entity
@Data
@Table(name="Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private List<Contract> listContracts;


}
