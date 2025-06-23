package com.examplePerfumeria.Perfumeriav2;

import com.examplePerfumeria.Perfumeriav2.productos.controller.ProductoController;
import com.examplePerfumeria.Perfumeriav2.productos.model.ProductoModel;
import com.examplePerfumeria.Perfumeriav2.productos.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductosControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    private ProductoModel producto;

    @BeforeEach
    void setUp() {
        producto = new ProductoModel();
        producto.setId(1);
        producto.setNombre("Perfume Flor");
        producto.setDescripcion("Fragancia floral");
        producto.setPrecio(59.99);
        producto.setStock(50);
    }


    @Test
    void obtenerTodos_RetornaListaProductos() {

        List<ProductoModel> productos = Arrays.asList(producto);
        when(productoService.obtenerTodos()).thenReturn(productos);

        List<ProductoModel> resultado = productoController.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("Perfume Flor", resultado.get(0).getNombre());
        verify(productoService, times(1)).obtenerTodos();
    }


    @Test
    void obtenerPorId_ConIdExistente_Retorna200() {
        when(productoService.obtenerPorId(1)).thenReturn(Optional.of(producto));

        ResponseEntity<ProductoModel> response = productoController.obtenerPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Perfume Flor", response.getBody().getNombre());
    }

    @Test
    void obtenerPorId_ConIdInexistente_Retorna404() {
        when(productoService.obtenerPorId(999)).thenReturn(Optional.empty());

        ResponseEntity<ProductoModel> response = productoController.obtenerPorId(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void crearProducto_RetornaProductoCreado() {
        when(productoService.guardar(any(ProductoModel.class))).thenReturn(producto);

        ProductoModel resultado = productoController.crearProducto(producto);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(productoService, times(1)).guardar(producto);
    }


    @Test
    void actualizarProducto_ConIdExistente_Retorna200() {
        ProductoModel productoActualizado = new ProductoModel();
        productoActualizado.setNombre("Perfume Flor Actualizado");
        productoActualizado.setPrecio(69.99);

        when(productoService.obtenerPorId(1)).thenReturn(Optional.of(producto));
        when(productoService.guardar(any(ProductoModel.class))).thenReturn(productoActualizado);

        ResponseEntity<ProductoModel> response = productoController.actualizarProducto(1, productoActualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Perfume Flor Actualizado", response.getBody().getNombre());
    }

    @Test
    void actualizarProducto_ConIdInexistente_Retorna404() {
        when(productoService.obtenerPorId(999)).thenReturn(Optional.empty());

        ResponseEntity<ProductoModel> response = productoController.actualizarProducto(999, producto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void eliminarProducto_ConIdExistente_Retorna204() {
        when(productoService.obtenerPorId(1)).thenReturn(Optional.of(producto));
        doNothing().when(productoService).eliminar(1);

        ResponseEntity<Void> response = productoController.eliminarProducto(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productoService, times(1)).eliminar(1);
    }

    @Test
    void eliminarProducto_ConIdInexistente_Retorna404() {
        when(productoService.obtenerPorId(999)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = productoController.eliminarProducto(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productoService, never()).eliminar(anyInt());
    }



}

