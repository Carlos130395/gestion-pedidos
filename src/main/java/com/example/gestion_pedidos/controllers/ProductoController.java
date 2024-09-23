package com.example.gestion_pedidos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion_pedidos.dtos.ProductoDTO;
import com.example.gestion_pedidos.services.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    // Obtener todos los productos como DTOs
    @GetMapping
    public List<ProductoDTO> obtenerProductos() {
        return productoService.obtenerTodosLosProductos();
    }

    // Crear un nuevo producto desde un DTO
    @PostMapping
    public ProductoDTO crearProducto(@RequestBody ProductoDTO productoDTO) {
        if (productoDTO.getStock() == null) {
            productoDTO.setStock(0);  // Asignar valor por defecto si el stock es nulo
        }
        return productoService.crearProducto(productoDTO);
    }

    // Obtener un producto por su ID y devolverlo como DTO
    @GetMapping("/{id}")
    public ProductoDTO obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id);
    }

    // Eliminar un producto por su ID
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }
}
