package com.examplePerfumeria.Perfumeriav2;

import com.examplePerfumeria.Perfumeriav2.sucursal.controller.SucursalController;
import com.examplePerfumeria.Perfumeriav2.sucursal.model.SucursalModel;
import com.examplePerfumeria.Perfumeriav2.sucursal.service.SucursalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SucursalService sucursalService;

    @InjectMocks
    private SucursalController sucursalController;

    private SucursalModel sucursal;

    @BeforeEach
    void setUp() {
        sucursal = new SucursalModel();
        sucursal.setId(1L);
        sucursal.setNombre("Sucursal Centro");
        sucursal.setDireccion("Calle Principal 123");
        sucursal.setRegion("Metropolitana");
        sucursal.setComuna("Santiago");
        sucursal.setHorario("09:00-18:00");
    }

    // --- GET /api/v1/sucursal ---
    @Test
    void listar_ConSucursales_Retorna200() {
        List<SucursalModel> sucursales = Arrays.asList(sucursal);
        when(sucursalService.findAll()).thenReturn(sucursales);

        ResponseEntity<List<SucursalModel>> response = sucursalController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Sucursal Centro", response.getBody().get(0).getNombre());
    }

    @Test
    void listar_SinSucursales_Retorna204() {
        when(sucursalService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<SucursalModel>> response = sucursalController.listar();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    // --- POST /api/v1/sucursal ---
    @Test
    void crearSucursal_ConDatosValidos_Retorna201() {
        when(sucursalService.save(any(SucursalModel.class))).thenReturn(sucursal);

        ResponseEntity<SucursalModel> response = sucursalController.crearSucursal(sucursal);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Sucursal Centro", response.getBody().getNombre());
        verify(sucursalService, times(1)).save(sucursal);
    }

    // --- PUT /api/v1/sucursal/{id} ---
    @Test
    void actualizar_ConIdExistente_Retorna200() {
        SucursalModel sucursalActualizado = new SucursalModel();
        sucursalActualizado.setNombre("Sucursal Centro Actualizada");
        sucursalActualizado.setHorario("10:00-19:00");

        when(sucursalService.findById(1L)).thenReturn(Optional.of(sucursal));
        when(sucursalService.save(any(SucursalModel.class))).thenReturn(sucursalActualizado);

        ResponseEntity<SucursalModel> response =
                sucursalController.actualizar(1L, sucursalActualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sucursal Centro Actualizada", response.getBody().getNombre());
    }

    @Test
    void actualizar_ConIdInexistente_Retorna404() {
        when(sucursalService.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<SucursalModel> response =
                sucursalController.actualizar(999L, sucursal);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(sucursalService, never()).save(any());
    }

    // --- DELETE /api/v1/sucursal/{id} ---
    @Test
    void eliminar_Retorna204() {
        // Configuración más simple - solo mockear delete()
        doNothing().when(sucursalService).delete(1L);

        ResponseEntity<Void> response = sucursalController.eliminar(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(sucursalService, times(1)).delete(1L);

        // Verifica que NO se llamó a existsById() si no lo usas
        verify(sucursalService, never()).existsById(anyLong());
    }



}
