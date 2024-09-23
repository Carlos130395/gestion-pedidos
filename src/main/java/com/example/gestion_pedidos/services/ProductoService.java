package com.example.gestion_pedidos.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_pedidos.dtos.ProductoDTO;
import com.example.gestion_pedidos.mappers.ProductoMapper;
import com.example.gestion_pedidos.model.Producto;
import com.example.gestion_pedidos.repositories.ProductoRepository;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoDTO> obtenerTodosLosProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(ProductoMapper::toProductoDTO)
                .collect(Collectors.toList());
    }

    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        Producto producto = ProductoMapper.toProducto(productoDTO);
        Producto productoGuardado = productoRepository.save(producto);
        return ProductoMapper.toProductoDTO(productoGuardado);
    }

    public ProductoDTO obtenerProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        return producto != null ? ProductoMapper.toProductoDTO(producto) : null;
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
