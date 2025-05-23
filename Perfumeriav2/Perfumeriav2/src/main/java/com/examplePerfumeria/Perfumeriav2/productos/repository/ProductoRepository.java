package com.examplePerfumeria.Perfumeriav2.productos.repository;


import com.examplePerfumeria.Perfumeriav2.productos.model.ProductoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModel, Integer> {
}
