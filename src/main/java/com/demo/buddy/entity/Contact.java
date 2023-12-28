package com.demo.buddy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "contact")
@Generated
@Getter
@Setter
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //private int amiId;
    private int amiID;

    public Contact() {

    }

    @ManyToOne
    @JoinColumn(name = "id_user")
    User user;



}
