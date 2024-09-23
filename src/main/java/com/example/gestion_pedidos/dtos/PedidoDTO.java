package com.example.gestion_pedidos.dtos;

import lombok.Data;
import java.util.List;

@Data
public class PedidoDTO {
    private String id;
    private Long clienteId;
    private List<Long> productosIds;
    private Double total;
}
