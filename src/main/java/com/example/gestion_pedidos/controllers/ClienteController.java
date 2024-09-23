package com.example.gestion_pedidos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion_pedidos.dtos.ClienteDTO;
import com.example.gestion_pedidos.services.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    // Endpoint para obtener todos los clientes como DTOs
    @GetMapping
    public List<ClienteDTO> obtenerClientes() {
        return clienteService.obtenerTodosLosClientes();
    }

    // Endpoint para crear un cliente desde un DTO
    @PostMapping
    public ClienteDTO crearCliente(@RequestBody ClienteDTO clienteDTO) {
        return clienteService.crearCliente(clienteDTO);
    }

    // Endpoint para obtener un cliente por su ID y devolverlo como DTO
    @GetMapping("/{id}")
    public ClienteDTO obtenerClientePorId(@PathVariable Long id) {
        return clienteService.obtenerClientePorId(id);
    }

    // Endpoint para eliminar un cliente por su ID
    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
    }
}
