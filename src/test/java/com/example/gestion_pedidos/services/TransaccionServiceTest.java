package com.example.gestion_pedidos.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.gestion_pedidos.dtos.PedidoDTO;
import com.example.gestion_pedidos.model.Cliente;
import com.example.gestion_pedidos.model.Pedido;
import com.example.gestion_pedidos.model.Producto;
import com.example.gestion_pedidos.repositories.ClienteRepository;
import com.example.gestion_pedidos.repositories.PedidoRepository;
import com.example.gestion_pedidos.repositories.ProductoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

public class TransaccionServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private TransaccionService transaccionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicia los mocks
    }

    @Test
    public void testCrearPedidoConTransaccion_Exito() {
        // Crear un Cliente y un Producto de prueba
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("John Doe");

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto de prueba");
        producto.setStock(10);
        producto.setPrecio(100.0);

        Pedido pedido = new Pedido();
        pedido.setClienteId(cliente.getId());
        pedido.setProductosIds(Arrays.asList(producto.getId()));

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setClienteId(cliente.getId());
        pedidoDTO.setProductosIds(Arrays.asList(producto.getId()));

        // Mock de los repositorios
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findAllById(pedidoDTO.getProductosIds())).thenReturn(Arrays.asList(producto));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(Mono.just(pedido));

        // Llamar al método de servicio
        Mono<PedidoDTO> resultado = transaccionService.crearPedidoConTransaccion(pedidoDTO);

        // Verificar que no sea nulo y se guarde correctamente
        assertNotNull(resultado);
        PedidoDTO pedidoGuardado = resultado.block();
        assertEquals(1L, pedidoGuardado.getClienteId());
        assertEquals(Arrays.asList(producto.getId()), pedidoGuardado.getProductosIds());
    }

    @Test
    public void testCrearPedidoConTransaccion_ClienteNoEncontrado() {
        // Crear un PedidoDTO de prueba
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setClienteId(1L);
        pedidoDTO.setProductosIds(Arrays.asList(1L));

        // Mock del repositorio de cliente para devolver un cliente no encontrado
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamar al método de servicio
        Mono<PedidoDTO> resultado = transaccionService.crearPedidoConTransaccion(pedidoDTO);

        // Verificar que se lanzó una excepción de "Cliente no encontrado"
        assertThrows(RuntimeException.class, () -> resultado.block());
    }

    @Test
    public void testCrearPedidoConTransaccion_ProductoNoEncontrado() {
        // Crear un Cliente de prueba
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("John Doe");

        // Crear un PedidoDTO de prueba
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setClienteId(1L);
        pedidoDTO.setProductosIds(Arrays.asList(1L, 2L));

        // Mock de los repositorios
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findAllById(pedidoDTO.getProductosIds())).thenReturn(Arrays.asList(new Producto()));

        // Llamar al método de servicio
        Mono<PedidoDTO> resultado = transaccionService.crearPedidoConTransaccion(pedidoDTO);

        // Verificar que se lanzó una excepción de "Productos no encontrados"
        assertThrows(RuntimeException.class, () -> resultado.block());
    }

    @Test
    public void testCrearPedidoConTransaccion_SinStockDisponible() {
        // Crear un Cliente y un Producto de prueba
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("John Doe");

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto sin stock");
        producto.setStock(0);  // Sin stock

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setClienteId(1L);
        pedidoDTO.setProductosIds(Arrays.asList(producto.getId()));

        // Mock de los repositorios
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findAllById(pedidoDTO.getProductosIds())).thenReturn(Arrays.asList(producto));

        // Llamar al método de servicio
        Mono<PedidoDTO> resultado = transaccionService.crearPedidoConTransaccion(pedidoDTO);

        // Verificar que se lanzó una excepción de "Producto sin stock disponible"
        assertThrows(RuntimeException.class, () -> resultado.block());
    }
}
