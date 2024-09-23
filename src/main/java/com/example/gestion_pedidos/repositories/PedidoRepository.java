package com.example.gestion_pedidos.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.gestion_pedidos.model.Pedido;

public interface PedidoRepository extends ReactiveMongoRepository<Pedido, String> {
    
}
