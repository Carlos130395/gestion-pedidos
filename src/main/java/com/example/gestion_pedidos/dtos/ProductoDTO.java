package com.example.gestion_pedidos.dtos;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long id; // El identificador único del producto
    private String nombre; // Nombre del producto
    private Double precio; // Precio del producto
    private Integer stock; // Stock disponible del producto
}
