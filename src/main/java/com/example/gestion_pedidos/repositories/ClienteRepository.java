package com.example.gestion_pedidos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gestion_pedidos.model.Cliente;

import reactor.core.publisher.Mono;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
