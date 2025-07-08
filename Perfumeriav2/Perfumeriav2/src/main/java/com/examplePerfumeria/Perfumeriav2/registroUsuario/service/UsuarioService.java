package com.examplePerfumeria.Perfumeriav2.registroUsuario.service;

import com.examplePerfumeria.Perfumeriav2.registroUsuario.model.Usuario;
import com.examplePerfumeria.Perfumeriav2.registroUsuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    public List<Usuario> findAll() { return repo.findAll(); }
    public Optional<Usuario> findOptionalById(Long id) { return repo.findById(id); }
    public Usuario save(Usuario u) { return repo.save(u); }
    public void delete(Long id) { repo.deleteById(id); }

    public Usuario login(String correo, String pass) {
        return repo.findByCorreoAndContrasenia(correo, pass)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
    }
}