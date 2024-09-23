package com.example.gestion_pedidos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion_pedidos.dtos.PedidoDTO;
import com.example.gestion_pedidos.services.TransaccionService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {
    @Autowired
    private TransaccionService transaccionService;

    @PostMapping("/pedido")
    public Mono<PedidoDTO> crearPedidoConTransaccion(@RequestBody PedidoDTO pedidoDTO) {
        return transaccionService.crearPedidoConTransaccion(pedidoDTO);
    }
    
}
