package com.example.gestion_pedidos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_pedidos.dtos.PedidoDTO;
import com.example.gestion_pedidos.model.Pedido;
import com.example.gestion_pedidos.repositories.PedidoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    // Método para obtener todos los pedidos y convertir a DTO
    public Flux<PedidoDTO> obtenerPedidos() {
        return pedidoRepository.findAll()
                .map(this::convertirEntidadADTO); // Convertir cada entidad Pedido a PedidoDTO
    }

    // Método para crear un pedido a partir del DTO
    public Mono<PedidoDTO> crearPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = convertirDTOAEntidad(pedidoDTO); // Convertir DTO a entidad Pedido
        return pedidoRepository.save(pedido)
                .map(this::convertirEntidadADTO); // Convertir la entidad guardada de nuevo a DTO
    }

    // Conversión de Entidad Pedido a DTO
    private PedidoDTO convertirEntidadADTO(Pedido pedido) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setClienteId(pedido.getClienteId());
        pedidoDTO.setProductosIds(pedido.getProductosIds());
        pedidoDTO.setTotal(pedido.getTotal());
        return pedidoDTO;
    }

    // Conversión de DTO a Entidad Pedido
    private Pedido convertirDTOAEntidad(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setId(pedidoDTO.getId());
        pedido.setClienteId(pedidoDTO.getClienteId());
        pedido.setProductosIds(pedidoDTO.getProductosIds());
        pedido.setTotal(pedidoDTO.getTotal());
        return pedido;
    }
}
