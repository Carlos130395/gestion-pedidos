package com.example.gestion_pedidos.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.gestion_pedidos.dtos.ProductoDTO;
import com.example.gestion_pedidos.mappers.ProductoMapper;
import com.example.gestion_pedidos.model.Producto;
import com.example.gestion_pedidos.repositories.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTodosLosProductos() {
        Producto producto1 = new Producto(1L, "Producto 1", 100.0, 50);
        Producto producto2 = new Producto(2L, "Producto 2", 200.0, 30);
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto1, producto2));

        List<ProductoDTO> productos = productoService.obtenerTodosLosProductos();
        assertEquals(2, productos.size());
    }

    @Test
    public void testObtenerProductoPorId() {
        Producto producto = new Producto(1L, "Producto 1", 100.0, 50);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        ProductoDTO result = productoService.obtenerProductoPorId(1L);
        assertNotNull(result);
        assertEquals("Producto 1", result.getNombre());
    }

    @Test
    public void testCrearProducto() {
        Producto producto = new Producto(1L, "Nuevo Producto", 150.0, 40);
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        ProductoDTO productoDTO = ProductoMapper.toProductoDTO(producto);
        ProductoDTO result = productoService.crearProducto(productoDTO);
        assertNotNull(result);
        assertEquals("Nuevo Producto", result.getNombre());
    }

    @Test
    public void testEliminarProducto() {
        productoService.eliminarProducto(1L);
        verify(productoRepository, times(1)).deleteById(1L);
    }
}
