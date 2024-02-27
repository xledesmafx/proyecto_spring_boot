package com.sistema.playaAutos.playaAutos.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class Auto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String marca;
    @NotEmpty
    private String modelo;
    @NotEmpty
    private String color;
    @NotEmpty
    private String tipo;
    @NotEmpty
    private String kilometraje;
    @NotNull
    private int anho;
    @NotEmpty
    private String descripcion;


}
