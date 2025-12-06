package com.example.heecoffee.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "age")
    Integer age;

    @Column(name = "email")
    String email;

    @Column(name = "password")
    @JsonIgnore
    String password;

    @Column(name = "name")
    String name;

    @Column(name = "address")
    String address;

    @Column(name = "role")
    String role;
}
