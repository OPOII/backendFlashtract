package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@Table(name="CLIENT")
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="CLIENT_ID_GENERATOR", sequenceName="CLIENT_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CLIENT_ID_GENERATOR")
    @Column
    @JsonIgnore
    private Long id;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private String secondName;

    @NotNull
    @Column
    private String company;

    @NotNull
    @Column
    private int professionalCard;
    @OneToMany(targetEntity = Contract.class,mappedBy="client")
    @JsonIgnore
    private List<Contract> contracts;

    public Client(){
        this.contracts = new ArrayList<>();
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

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public Contract addContract(Contract contract){
        getContracts().add(contract);
        contract.setClient(this);
        return contract;
    }


    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getProfessionalCard() {
        return professionalCard;
    }

    public void setProfessionalCard(int professionalCard) {
        this.professionalCard = professionalCard;
    }
}
