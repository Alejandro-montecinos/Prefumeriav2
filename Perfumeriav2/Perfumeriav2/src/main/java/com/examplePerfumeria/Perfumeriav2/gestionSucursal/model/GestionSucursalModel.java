package com.examplePerfumeria.Perfumeriav2.gestionSucursal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sucursal")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class GestionSucursalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String ciudad;

    @Column(name = "horario_apertura", length = 20)
    private String horarioApertura;

    @Column(name = "horario_cierre", length = 20)
    private String horarioCierre;

    @Column(name = "personal_asignado", length = 200)
    private String personalAsignado;

    @Column(name = "politicas_locales", length = 500)
    private String politicasLocales;

    public void setId(Integer id) {
        this.id = id;

    }
}


