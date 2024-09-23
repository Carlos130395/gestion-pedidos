package com.example.gestion_pedidos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestion_pedidos.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
}
