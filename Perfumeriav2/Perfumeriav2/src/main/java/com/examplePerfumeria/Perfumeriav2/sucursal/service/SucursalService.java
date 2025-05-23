package com.examplePerfumeria.Perfumeriav2.sucursal.service;

import com.examplePerfumeria.Perfumeriav2.sucursal.model.SucursalModel;
import com.examplePerfumeria.Perfumeriav2.sucursal.repository.SucursalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;


    public List<SucursalModel> findAll() {
        return sucursalRepository.findAll();
    }


    public Optional<SucursalModel> findById(Long id) {
        return sucursalRepository.findById(id);
    }



    public SucursalModel save(SucursalModel sucursal) {
        return sucursalRepository.save(sucursal);
    }

    public void delete(Long id) {
        sucursalRepository.deleteById(id);
    }


    public boolean existsById(Long id) {
        return sucursalRepository.existsById(id);
    }

}
