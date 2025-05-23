package com.examplePerfumeria.Perfumeriav2.productos.service;


import com.examplePerfumeria.Perfumeriav2.productos.model.ProductoModel;
import com.examplePerfumeria.Perfumeriav2.productos.repository.ProductoRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService (ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    public List<ProductoModel> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<ProductoModel> obtenerPorId (Integer id) {
        return productoRepository.findById(id);
    }

    public ProductoModel guardar(ProductoModel producto) {
        return productoRepository.save(producto);
    }
    public void eliminar (Integer id) {
        productoRepository.deleteById(id);
    }

}
