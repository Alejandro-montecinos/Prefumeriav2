package com.examplePerfumeria.Perfumeriav2.registroUsuario.controller;

import com.examplePerfumeria.Perfumeriav2.registroUsuario.model.Usuario;
import com.examplePerfumeria.Perfumeriav2.registroUsuario.service.UsuarioService;
import com.examplePerfumeria.Perfumeriav2.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> lista = usuarioService.findAll();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario u) {
        try {
            Usuario nuevo = usuarioService.save(u);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: RUN o correo duplicado.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Usuario u) {
        return usuarioService.findOptionalById(id)
                .map(existing -> {
                    existing.setRun(u.getRun());
                    existing.setNombre(u.getNombre());
                    existing.setApellido(u.getApellido());
                    existing.setFechaDeNacimiento(u.getFechaDeNacimiento());
                    existing.setCorreo(u.getCorreo());
                    existing.setContrasenia(u.getContrasenia());
                    Usuario saved = usuarioService.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> datos) {
        try {
            Usuario user = usuarioService.login(datos.get("correo"), datos.get("contrasenia"));
            String token = jwtUtil.generarToken(user.getCorreo());
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "correo", user.getCorreo(),
                    "nombre", user.getNombre(),
                    "apellido", user.getApellido()
            ));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}
