package com.demo.buddy.entity;


import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "amis")
@Generated
@Getter
@Setter
@AllArgsConstructor
public class Amis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer idUser;

    private Integer IdAmis;

    public Amis() {

    }

}
