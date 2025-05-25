package com.examplePerfumeria.Perfumeriav2.cliente.controller;

import com.examplePerfumeria.Perfumeriav2.cliente.model.ClienteModel;
import com.examplePerfumeria.Perfumeriav2.cliente.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public List<ClienteModel> listarTodos() {
        return clienteService.listarClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> obtenerPorId(@PathVariable Integer id) {
        return clienteService.obtenerClientesPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteModel> crear(@RequestBody ClienteModel cliente) {
        ClienteModel nuevo = clienteService.crearCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> actualizar(@PathVariable Integer id, @RequestBody ClienteModel cliente) {
        return clienteService.obtenerClientesPorId(id)
                .map(c -> {
                    cliente.setId(id);
                    ClienteModel actualizado = clienteService.actualizarCliente(cliente);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return clienteService.obtenerClientesPorId(id)
                .map(c -> {
                    clienteService.eliminarCliente(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
