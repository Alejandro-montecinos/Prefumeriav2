package com.examplePerfumeria.Perfumeriav2;

import com.examplePerfumeria.Perfumeriav2.cliente.controller.ClienteController;
import com.examplePerfumeria.Perfumeriav2.cliente.model.ClienteModel;
import com.examplePerfumeria.Perfumeriav2.cliente.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private ClienteModel cliente;

    @BeforeEach
    void setUp() {
        cliente = new ClienteModel();
        cliente.setId(1);
        cliente.setNombre("Juan Pérez");
        cliente.setCorreo("juan@example.com");
        cliente.setTelefono("+56912345678");
        cliente.setDireccion("Calle Principal 123");
    }

    @Test
    void listarTodos_RetornaListaClientes() {
        // Configurar mock
        when(clienteService.listarClientes()).thenReturn(List.of(cliente));

        // Ejecutar
        List<ClienteModel> resultado = clienteController.listarTodos();

        // Verificar
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());
        verify(clienteService, times(1)).listarClientes();
    }

    @Test
    void obtenerPorId_ConIdExistente_Retorna200() {
        when(clienteService.obtenerClientesPorId(1)).thenReturn(Optional.of(cliente));

        ResponseEntity<ClienteModel> response = clienteController.obtenerPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("juan@example.com", response.getBody().getCorreo());
    }

    @Test
    void obtenerPorId_ConIdInexistente_Retorna404() {
        when(clienteService.obtenerClientesPorId(999)).thenReturn(Optional.empty());

        ResponseEntity<ClienteModel> response = clienteController.obtenerPorId(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void crear_ConDatosValidos_Retorna201() {
        when(clienteService.crearCliente(any(ClienteModel.class))).thenReturn(cliente);

        ResponseEntity<ClienteModel> response = clienteController.crear(cliente);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        verify(clienteService, times(1)).crearCliente(cliente);
    }

    @Test
    void actualizar_ConIdExistente_Retorna200() {
        ClienteModel clienteActualizado = new ClienteModel();
        clienteActualizado.setNombre("Juan Pérez Actualizado");
        clienteActualizado.setCorreo("juan.nuevo@example.com");

        when(clienteService.obtenerClientesPorId(1)).thenReturn(Optional.of(cliente));
        when(clienteService.actualizarCliente(any(ClienteModel.class))).thenReturn(clienteActualizado);

        ResponseEntity<ClienteModel> response = clienteController.actualizar(1, clienteActualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Juan Pérez Actualizado", response.getBody().getNombre());
    }

    @Test
    void actualizar_ConIdInexistente_Retorna404() {
        when(clienteService.obtenerClientesPorId(999)).thenReturn(Optional.empty());

        ResponseEntity<ClienteModel> response = clienteController.actualizar(999, cliente);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(clienteService, never()).actualizarCliente(any());
    }

    @Test
    void eliminar_ConIdExistente_Retorna204() {
        when(clienteService.obtenerClientesPorId(1)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteService).eliminarCliente(1);

        ResponseEntity<Void> response = clienteController.eliminar(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clienteService, times(1)).eliminarCliente(1);
    }

    @Test
    void eliminar_ConIdInexistente_Retorna404() {
        when(clienteService.obtenerClientesPorId(999)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = clienteController.eliminar(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(clienteService, never()).eliminarCliente(anyInt());
    }

}
