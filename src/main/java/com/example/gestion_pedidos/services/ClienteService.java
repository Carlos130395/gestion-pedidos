package com.example.gestion_pedidos.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_pedidos.dtos.ClienteDTO;
import com.example.gestion_pedidos.mappers.ClienteMapper;
import com.example.gestion_pedidos.model.Cliente;
import com.example.gestion_pedidos.repositories.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Obtener todos los clientes como DTOs
    public List<ClienteDTO> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(ClienteMapper::toClienteDTO).collect(Collectors.toList());
    }

    // Crear un cliente desde un DTO
    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        Cliente cliente = ClienteMapper.toCliente(clienteDTO);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return ClienteMapper.toClienteDTO(clienteGuardado);
    }

    // Obtener un cliente por su ID como DTO
    public ClienteDTO obtenerClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        return cliente != null ? ClienteMapper.toClienteDTO(cliente) : null;
    }

    // Eliminar un cliente
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
