package com.example.gestion_pedidos.model;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(collection = "pedidos")
public class Pedido {
    @Id
    private String id;

    // Cambia los tipos de String a Long
    private Long clienteId;
    private List<Long> productosIds; 
    private Double total;
}
