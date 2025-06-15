package com.examplePerfumeria.Perfumeriav2.registroUsuario.controller;

import com.examplePerfumeria.Perfumeriav2.registroUsuario.model.Usuario;
import com.examplePerfumeria.Perfumeriav2.registroUsuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar(){
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevo = usuarioService.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: RUN duplicado");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        try {
            Usuario usuarioExistente = usuarioService.findById(id);

            // Actualizamos los datos
            usuarioExistente.setRun(usuarioActualizado.getRun());
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setApellido(usuarioActualizado.getApellido());
            usuarioExistente.setFechaDeNacimiento(usuarioActualizado.getFechaDeNacimiento());
            usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
            usuarioExistente.setContrasenia(usuarioActualizado.getContrasenia());

            Usuario actualizado = usuarioService.save(usuarioExistente);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Map<String, String> datos) {
        try {
            String correo = datos.get("correo");
            String contrasenia = datos.get("contrasenia");
            Usuario usuario = usuarioService.login(correo, contrasenia);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }








}
