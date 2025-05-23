package com.examplePerfumeria.Perfumeriav2.cliente.service;

import com.examplePerfumeria.Perfumeriav2.cliente.model.ClienteModel;
import com.examplePerfumeria.Perfumeriav2.cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<ClienteModel> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<ClienteModel> obtenerClientesPorId(Integer id) {
        return clienteRepository.findById(id);
    }

    public ClienteModel crearCliente(ClienteModel cliente) {
        return clienteRepository.save(cliente);
    }

    public ClienteModel actualizarCliente(ClienteModel cliente) {
        return clienteRepository.save(cliente);
    }

    public void eliminarCliente(Integer id) {
        clienteRepository.deleteById(id);
    }
}
