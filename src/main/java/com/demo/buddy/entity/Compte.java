package com.demo.buddy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "compte")
@Generated
@Getter
@Setter
@AllArgsConstructor
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String coordonneesBancaire;

    private double montant;


    public Compte() {

    }

    @OneToOne(mappedBy = "compteBancaire")
    private User user;

}
