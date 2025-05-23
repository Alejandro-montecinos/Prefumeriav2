package com.examplePerfumeria.Perfumeriav2.cliente.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Entity
    @Table(name = "cliente")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ClienteModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(nullable = false, length = 100)
        private String nombre;

        @Column(nullable = false, length = 100, unique = true)
        private String correo;

        @Column(length = 20)
        private String telefono;

        @Column(length = 200)
        private String direccion;

        public void setId(Integer id) {
            this.id = id;
        }
}
