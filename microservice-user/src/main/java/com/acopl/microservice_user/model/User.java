package com.acopl.microservice_user.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(unique = true, nullable=false)
    // evita duplicados en mail
    private String email;

    @Column(nullable=false)
    private String rol;

    @ElementCollection
    //Permite guardar List<String> como una tabla secundaria
    //confirmar con nitchen
    private List<String> permisos;

}
