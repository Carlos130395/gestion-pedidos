package com.example.gestion_pedidos.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gestion_pedidos.dtos.PedidoDTO;
import com.example.gestion_pedidos.model.Pedido;
import com.example.gestion_pedidos.model.Producto;
import com.example.gestion_pedidos.repositories.ClienteRepository;
import com.example.gestion_pedidos.repositories.PedidoRepository;
import com.example.gestion_pedidos.repositories.ProductoRepository;

import reactor.core.publisher.Mono;

@Service
public class TransaccionService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional  // Habilitamos transacciones en PostgreSQL
    public Mono<PedidoDTO> crearPedidoConTransaccion(PedidoDTO pedidoDTO) {
        try {
            // Verificar si el cliente existe
            return Mono.justOrEmpty(clienteRepository.findById(pedidoDTO.getClienteId()))
                .switchIfEmpty(Mono.error(new RuntimeException("Cliente no encontrado")))
                .flatMap(cliente -> {
                    // Obtener los productos según los IDs
                    return Mono.justOrEmpty(productoRepository.findAllById(pedidoDTO.getProductosIds()))
                        .flatMap(productos -> {
                            if (productos.size() != pedidoDTO.getProductosIds().size()) {
                                return Mono.error(new RuntimeException("Algunos productos no fueron encontrados"));
                            }

                            // Validar y ajustar el stock de los productos
                            List<Producto> productosConStockAjustado = productos.stream().map(producto -> {
                                if (producto.getStock() <= 0) {
                                    throw new RuntimeException("El producto " + producto.getNombre() + " no tiene stock disponible o tiene stock negativo");
                                }
                                producto.setStock(producto.getStock() - 1);
                                return producto;
                            }).collect(Collectors.toList());

                            // Guardar los productos con el stock actualizado en una sola operación
                            productoRepository.saveAll(productosConStockAjustado);

                            // Convertir PedidoDTO a entidad Pedido
                            Pedido pedido = convertirDTOPedidoAEntidad(pedidoDTO);

                            // Guardar el pedido en MongoDB
                            return pedidoRepository.save(pedido)
                                    .map(this::convertirEntidadAPedidoDTO)
                                    .onErrorResume(error -> {
                                        // Si algo falla en MongoDB, revertir los cambios en PostgreSQL
                                        revertirCambiosPostgreSQL(productosConStockAjustado);
                                        return Mono.error(new RuntimeException("Error creando pedido en MongoDB: " + error.getMessage()));
                                    });
                        });
                });
        } catch (Exception e) {
            return Mono.error(new RuntimeException("Error en transacción: " + e.getMessage()));
        }
    }

    /**
     * Revertir cambios en PostgreSQL (restaurar stock en los productos).
     */
    private void revertirCambiosPostgreSQL(List<Producto> productos) {
        List<Producto> productosConStockRestaurado = productos.stream().map(producto -> {
            producto.setStock(producto.getStock() + 1); // Restaurar el stock original
            return producto;
        }).collect(Collectors.toList());

        // Guardar todos los productos con el stock restaurado
        productoRepository.saveAll(productosConStockRestaurado);
    }

    // Métodos de conversión entre Pedido y PedidoDTO
    private Pedido convertirDTOPedidoAEntidad(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setClienteId(pedidoDTO.getClienteId());
        pedido.setProductosIds(pedidoDTO.getProductosIds());
        pedido.setTotal(pedidoDTO.getTotal());
        return pedido;
    }

    private PedidoDTO convertirEntidadAPedidoDTO(Pedido pedido) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setClienteId(pedido.getClienteId());
        pedidoDTO.setProductosIds(pedido.getProductosIds());
        pedidoDTO.setTotal(pedido.getTotal());
        return pedidoDTO;
    }
}
