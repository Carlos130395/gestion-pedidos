package com.example.gestion_pedidos.mappers;

import com.example.gestion_pedidos.dtos.ClienteDTO;
import com.example.gestion_pedidos.model.Cliente;

public class ClienteMapper {

    // Convertir Cliente a ClienteDTO
    public static ClienteDTO toClienteDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setEmail(cliente.getEmail());
        return dto;
    }

    // Convertir ClienteDTO a Cliente
    public static Cliente toCliente(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());
        return cliente;
    }
}
