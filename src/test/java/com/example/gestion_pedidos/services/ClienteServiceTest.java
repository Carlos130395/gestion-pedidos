package com.example.gestion_pedidos.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.gestion_pedidos.dtos.ClienteDTO;
import com.example.gestion_pedidos.mappers.ClienteMapper;
import com.example.gestion_pedidos.model.Cliente;
import com.example.gestion_pedidos.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTodosLosClientes() {
        // Crear algunos clientes de ejemplo
        Cliente cliente1 = new Cliente(1L, "Cliente 1", "cliente1@example.com");
        Cliente cliente2 = new Cliente(2L, "Cliente 2", "cliente2@example.com");
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        // Llamar al método del servicio
        List<ClienteDTO> clientes = clienteService.obtenerTodosLosClientes();

        // Verificar los resultados
        assertEquals(2, clientes.size());
        assertEquals("Cliente 1", clientes.get(0).getNombre());
        assertEquals("Cliente 2", clientes.get(1).getNombre());
    }

    @Test
    public void testObtenerClientePorId() {
        // Crear un cliente de ejemplo
        Cliente cliente = new Cliente(1L, "Cliente 1", "cliente1@example.com");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // Llamar al método del servicio
        ClienteDTO result = clienteService.obtenerClientePorId(1L);

        // Verificar los resultados
        assertNotNull(result);
        assertEquals("Cliente 1", result.getNombre());
    }

    @Test
    public void testCrearCliente() {
        // Crear un cliente y un DTO de ejemplo
        Cliente cliente = new Cliente(1L, "Nuevo Cliente", "nuevo@example.com");
        ClienteDTO clienteDTO = ClienteMapper.toClienteDTO(cliente);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Llamar al método del servicio
        ClienteDTO result = clienteService.crearCliente(clienteDTO);

        // Verificar los resultados
        assertNotNull(result);
        assertEquals("Nuevo Cliente", result.getNombre());
        assertEquals("nuevo@example.com", result.getEmail());
    }

    @Test
    public void testEliminarCliente() {
        // Llamar al método del servicio
        clienteService.eliminarCliente(1L);

        // Verificar que el repositorio fue invocado con el ID correcto
        verify(clienteRepository, times(1)).deleteById(1L);
    }
}
