package com.examplePerfumeria.Perfumeriav2.registroUsuario.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "usuario", uniqueConstraints = {
        @UniqueConstraint(columnNames = "run"),
        @UniqueConstraint(columnNames = "correo")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 13)
    private String run;
    @Column(nullable = false) private String nombre;
    @Column(nullable = false) private String apellido;
    private Date fechaDeNacimiento;
    @Column(nullable = false) private String correo;
    @Column(nullable = false) private String contrasenia;
}