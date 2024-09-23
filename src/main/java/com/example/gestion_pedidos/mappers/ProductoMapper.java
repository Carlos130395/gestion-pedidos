package com.example.gestion_pedidos.mappers;

import com.example.gestion_pedidos.dtos.ProductoDTO;
import com.example.gestion_pedidos.model.Producto;

public class ProductoMapper {

    // Método para convertir ProductoDTO a Producto
    public static Producto toProducto(ProductoDTO productoDTO) {
        if (productoDTO == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(productoDTO.getId());
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        return producto;
    }

    // Método para convertir Producto a ProductoDTO
    public static ProductoDTO toProductoDTO(Producto producto) {
        if (producto == null) {
            return null;
        }
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setStock(producto.getStock());
        return productoDTO;
    }
}
