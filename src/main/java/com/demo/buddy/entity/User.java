package com.demo.buddy.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "user")
@Generated
@Getter
@Setter
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String email;

    private String mdp;

    //private String compteBancaire;

    private String role;

    public User() {

    }



    //@OneToOne
    //@JoinColumn(name = "compte")
    //private Compte compteLocal;

    @Override
    public String toString() {

        return "[ userId = " + userid + ", " +
                "email = " + email + ", " +" ]";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.getRole().toString());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return this.getMdp();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Getter
    @OneToMany(mappedBy = "user")
    private Collection<Contact> contact;

    @OneToMany(mappedBy = "user")
    private Collection<Operation> operation;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "compte_id")
    private Compte compteBancaire;


}
