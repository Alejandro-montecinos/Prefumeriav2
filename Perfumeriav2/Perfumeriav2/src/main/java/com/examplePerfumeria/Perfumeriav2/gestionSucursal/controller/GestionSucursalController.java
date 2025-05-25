package com.examplePerfumeria.Perfumeriav2.gestionSucursal.controller;

import com.examplePerfumeria.Perfumeriav2.gestionSucursal.model.GestionSucursalModel;
import com.examplePerfumeria.Perfumeriav2.gestionSucursal.service.GestionSucursalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sucursales") // ruta clara y RESTful
public class GestionSucursalController {

    private final GestionSucursalService gestionSucursalService;

    public GestionSucursalController(GestionSucursalService gestionSucursalService) {
        this.gestionSucursalService = gestionSucursalService;
    }

    @GetMapping
    public List<GestionSucursalModel> obtenerTodas() {
        return gestionSucursalService.listarSucursales();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GestionSucursalModel> obtenerPorId(@PathVariable Integer id) {
        return gestionSucursalService.obtenerSucursalPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public GestionSucursalModel crear(@RequestBody GestionSucursalModel sucursal) {
        return gestionSucursalService.guardarSucursal(sucursal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GestionSucursalModel> actualizar(@PathVariable Integer id, @RequestBody GestionSucursalModel sucursal) {
        return gestionSucursalService.obtenerSucursalPorId(id)
                .map(s -> {
                    sucursal.setId(id);
                    GestionSucursalModel actualizada = gestionSucursalService.guardarSucursal(sucursal);
                    return ResponseEntity.ok(actualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return gestionSucursalService.obtenerSucursalPorId(id)
                .map(s -> {
                    gestionSucursalService.eliminarSucursal(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
