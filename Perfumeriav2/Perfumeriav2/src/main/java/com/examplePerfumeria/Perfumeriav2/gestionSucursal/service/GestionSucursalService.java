package com.examplePerfumeria.Perfumeriav2.gestionSucursal.service;

import com.examplePerfumeria.Perfumeriav2.gestionSucursal.model.GestionSucursalModel;
import com.examplePerfumeria.Perfumeriav2.gestionSucursal.repository.GestionSucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
@Service

public class GestionSucursalService {

        @Autowired
        private GestionSucursalRepository repository;

        public List<GestionSucursalModel> listarSucursales() {
            return repository.findAll();
        }

        public Optional<GestionSucursalModel> obtenerSucursalPorId(Integer id) {
            return repository.findById(id); // findById devuelve Optional
        }


    public GestionSucursalModel actualizarSucursal(Integer id, GestionSucursalModel nuevaSucursal) {
        return repository.findById(id).map(sucursal -> {
            sucursal.setNombre(nuevaSucursal.getNombre());
            sucursal.setDireccion(nuevaSucursal.getDireccion());
            sucursal.setCiudad(nuevaSucursal.getCiudad());
            sucursal.setHorarioApertura(nuevaSucursal.getHorarioApertura());
            sucursal.setHorarioCierre(nuevaSucursal.getHorarioCierre());
            sucursal.setPersonalAsignado(nuevaSucursal.getPersonalAsignado());
            sucursal.setPoliticasLocales(nuevaSucursal.getPoliticasLocales());
            return repository.save(sucursal);
        }).orElseThrow(() -> new RuntimeException("Sucursal no encontrada con ID: " + id));
    }


    public GestionSucursalModel guardarSucursal(GestionSucursalModel sucursal) {
            return repository.save(sucursal);
        }

        public void eliminarSucursal(Integer id) {
            repository.deleteById(id);
        }
}


