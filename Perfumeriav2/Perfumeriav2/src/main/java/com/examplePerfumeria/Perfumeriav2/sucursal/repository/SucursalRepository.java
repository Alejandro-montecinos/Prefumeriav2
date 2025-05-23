package com.examplePerfumeria.Perfumeriav2.sucursal.repository;

import com.examplePerfumeria.Perfumeriav2.sucursal.model.SucursalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository  extends JpaRepository<SucursalModel, Long> {


}
