package com.example.gestion_pedidos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion_pedidos.dtos.PedidoDTO;
import com.example.gestion_pedidos.services.PedidoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public Flux<PedidoDTO> obtenerPedidos() {
        return pedidoService.obtenerPedidos(); // Retorna un Flux para múltiples pedidos convertidos a DTO
    }

    @PostMapping
    public Mono<PedidoDTO> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.crearPedido(pedidoDTO); // Retorna un Mono para un pedido convertido a DTO
    }
}
