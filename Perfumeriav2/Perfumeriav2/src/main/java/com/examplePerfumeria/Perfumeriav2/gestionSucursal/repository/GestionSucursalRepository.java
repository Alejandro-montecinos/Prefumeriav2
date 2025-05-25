package com.examplePerfumeria.Perfumeriav2.gestionSucursal.repository;

import com.examplePerfumeria.Perfumeriav2.gestionSucursal.model.GestionSucursalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


    @Repository
    public interface GestionSucursalRepository extends JpaRepository<GestionSucursalModel, Integer> {
    }


