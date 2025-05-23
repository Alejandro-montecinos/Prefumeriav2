package com.examplePerfumeria.Perfumeriav2.sucursal.controller;



import com.examplePerfumeria.Perfumeriav2.sucursal.model.SucursalModel;
import com.examplePerfumeria.Perfumeriav2.sucursal.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sucursal")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    // GET: listar todas las sucursales
    @GetMapping
    public ResponseEntity<List<SucursalModel>> listar (){
        List<SucursalModel> sucursal = sucursalService.findAll();
        if (sucursal.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sucursal);
    }

    @PostMapping
    public ResponseEntity<SucursalModel> crearSucursal(@RequestBody SucursalModel sucursal){
        SucursalModel nuevo = sucursalService.save(sucursal);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);

    }

    @PutMapping("/{id}")
    public ResponseEntity<SucursalModel> actualizar(@PathVariable Long id, @RequestBody SucursalModel sucursalActualizado) {
        Optional<SucursalModel> sucursalOpt = sucursalService.findById(id);

        if (sucursalOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SucursalModel sucursalExistente = sucursalOpt.get();

        sucursalExistente.setDireccion(sucursalActualizado.getDireccion());
        sucursalExistente.setHorario(sucursalActualizado.getHorario());
        sucursalExistente.setNombre(sucursalActualizado.getNombre());
        sucursalExistente.setRegion(sucursalActualizado.getRegion());
        sucursalExistente.setComuna(sucursalActualizado.getComuna());

        SucursalModel actualizado = sucursalService.save(sucursalExistente);
        return ResponseEntity.ok(actualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        sucursalService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
