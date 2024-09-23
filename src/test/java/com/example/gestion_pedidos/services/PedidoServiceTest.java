package com.example.gestion_pedidos.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.gestion_pedidos.dtos.PedidoDTO;
import com.example.gestion_pedidos.model.Pedido;
import com.example.gestion_pedidos.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerPedidos() {
        // Crear algunos pedidos de ejemplo
        Pedido pedido1 = new Pedido();
        pedido1.setId("1");
        pedido1.setClienteId(123L);
        pedido1.setTotal(150.0);

        Pedido pedido2 = new Pedido();
        pedido2.setId("2");
        pedido2.setClienteId(456L);
        pedido2.setTotal(200.0);

        when(pedidoRepository.findAll()).thenReturn(Flux.just(pedido1, pedido2));

        // Llamar al método del servicio
        Flux<PedidoDTO> pedidos = pedidoService.obtenerPedidos();

        // Verificar los resultados
        assertEquals(2, pedidos.collectList().block().size());
    }

    @Test
    public void testCrearPedido() {
        // Crear un pedido y un DTO de ejemplo
        Pedido pedido = new Pedido();
        pedido.setId("1");
        pedido.setClienteId(123L);
        pedido.setTotal(150.0);

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId("1");
        pedidoDTO.setClienteId(123L);
        pedidoDTO.setTotal(150.0);

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(Mono.just(pedido));

        // Llamar al método del servicio
        Mono<PedidoDTO> result = pedidoService.crearPedido(pedidoDTO);

        // Verificar los resultados
        assertNotNull(result.block());
        assertEquals("123", result.block().getClienteId());
        assertEquals(150.0, result.block().getTotal());
    }
}
