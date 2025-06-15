package com.examplePerfumeria.Perfumeriav2;

import com.examplePerfumeria.Perfumeriav2.registroUsuario.controller.UsuarioController;
import com.examplePerfumeria.Perfumeriav2.registroUsuario.model.Usuario;
import com.examplePerfumeria.Perfumeriav2.registroUsuario.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setRun("12345678-9");
        usuario.setNombre("juan");
        usuario.setApellido("perez");
        usuario.setCorreo("juanPerez@example.com"); // Corregido el correo
        usuario.setContrasenia("contraseña12345");
        usuario.setFechaDeNacimiento(new Date());
    }

    @Test
    void listarUsuarios_ConUsuarios_RetornaLista() {
        // Configuración del mock
        List<Usuario> usuarios = Collections.singletonList(usuario); // Pasar el usuario como parámetro
        when(usuarioService.findAll()).thenReturn(usuarios); // Mock correcto del servicio

        ResponseEntity<List<Usuario>> response = usuarioController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("12345678-9", response.getBody().get(0).getRun());
    }

    @Test
    void listarUsuarios_SinUsuarios_RetornaNoContent() {
        when(usuarioService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Usuario>> response = usuarioController.listar();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void crearUsuario_ConDatosValidos_Retorna201() {
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);

        // Cambia ResponseEntity<Usuario> por ResponseEntity<?>
        ResponseEntity<?> response = usuarioController.crearUsuario(usuario);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Haz un cast seguro del body
        Usuario usuarioResponse = (Usuario) response.getBody();
        assertEquals("12345678-9", usuarioResponse.getRun());
        verify(usuarioService, times(1)).save(usuario);
    }

    @Test
    void crearUsuario_ConRunDuplicado_Retorna409() {
        // Simula un error de RUN duplicado
        when(usuarioService.save(any(Usuario.class)))
                .thenThrow(new DataIntegrityViolationException("RUN ya existe"));

        // Ejecuta y verifica
        ResponseEntity<?> response = usuarioController.crearUsuario(usuario);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Error: RUN duplicado", response.getBody()); // Verifica el mensaje
    }

    @Test
    void actualizarUsuario_ConIdValido_Retorna200() {
        // Datos de actualización
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("Juan Modificado");
        usuarioActualizado.setRun("12345678-9"); // Campo obligatorio

        // Configura mocks
        when(usuarioService.findById(1L)).thenReturn(usuario); // Usuario existe
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuarioActualizado);

        // Ejecuta
        ResponseEntity<Usuario> response = usuarioController.actualizar(1L, usuarioActualizado);

        // Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Juan Modificado", response.getBody().getNombre());
    }

    @Test
    void actualizarUsuario_ConIdInvalido_Retorna404() {
        when(usuarioService.findById(999L)).thenThrow(new RuntimeException("Usuario no encontrado"));

        ResponseEntity<Usuario> response = usuarioController.actualizar(999L, usuario);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void eliminarUsuario_ConIdValido_Retorna204() {
        // Simula eliminación exitosa (no retorna nada)
        doNothing().when(usuarioService).delete(1L);

        ResponseEntity<Void> response = usuarioController.eliminar(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).delete(1L); // Verifica que se llamó al servicio
    }

    @Test
    void login_ConCredencialesCorrectas_Retorna200() {
        // Datos de login
        Map<String, String> credenciales = Map.of(
                "correo", "juan@example.com",
                "contrasenia", "password123"
        );

        // Configura mock
        when(usuarioService.login("juan@example.com", "password123")).thenReturn(usuario);

        // Ejecuta
        ResponseEntity<Usuario> response = usuarioController.login(credenciales);

        // Verificaciones
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("juan", response.getBody().getNombre());
    }

    @Test
    void login_ConCredencialesInvalidas_Retorna401() {
        Map<String, String> credenciales = Map.of(
                "correo", "juan@example.com",
                "contrasenia", "incorrecta"
        );

        when(usuarioService.login(anyString(), anyString()))
                .thenThrow(new RuntimeException("Credenciales inválidas"));

        ResponseEntity<Usuario> response = usuarioController.login(credenciales);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }


}