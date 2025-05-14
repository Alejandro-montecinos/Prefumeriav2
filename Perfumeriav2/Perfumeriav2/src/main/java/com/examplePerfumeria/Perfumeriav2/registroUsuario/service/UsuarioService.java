package com.examplePerfumeria.Perfumeriav2.registroUsuario.service;

import com.examplePerfumeria.Perfumeriav2.registroUsuario.model.Usuario;
import com.examplePerfumeria.Perfumeriav2.registroUsuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;


    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    };

    public Usuario findById(Long id){
        return usuarioRepository.findById(id).get();
    }

    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public void  delete(Long id){
        usuarioRepository.deleteById(id);
    }

    public Usuario login(String correo, String contrasenia) {
        return usuarioRepository.findByCorreoAndContrasenia(correo, contrasenia)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
    }




}
