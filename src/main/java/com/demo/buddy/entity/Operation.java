package com.demo.buddy.entity;



import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import jakarta.persistence.*;


@Entity
@Table(name = "operation")
@Generated
@Getter
@Setter
@AllArgsConstructor
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numeroTransaction;
    private Date date;
    private double montant;

    public Operation() {

    }

    @ManyToOne
    @JoinColumn(name = "id_user")
    User user;

    @ManyToOne
    @JoinColumn(name = "id_ami")
    User ami;

}
