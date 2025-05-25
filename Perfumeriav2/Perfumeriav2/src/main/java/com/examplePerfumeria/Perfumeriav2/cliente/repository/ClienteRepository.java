package com.examplePerfumeria.Perfumeriav2.cliente.repository;

import com.examplePerfumeria.Perfumeriav2.cliente.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface ClienteRepository extends JpaRepository<ClienteModel, Integer> {
    }

