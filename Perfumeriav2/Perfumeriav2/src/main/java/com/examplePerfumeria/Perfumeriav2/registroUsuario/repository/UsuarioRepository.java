package com.examplePerfumeria.Perfumeriav2.registroUsuario.repository;

import com.examplePerfumeria.Perfumeriav2.registroUsuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreoAndContrasenia(String correo, String contrasenia);

}
