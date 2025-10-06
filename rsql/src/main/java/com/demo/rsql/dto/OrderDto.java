package com.demo.rsql.dto;

import com.demo.rsql.config.RSQLProperty;
import com.demo.rsql.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    
    @RSQLProperty
    private UUID id;
    
    @RSQLProperty
    private String orderNumber;
    
    @RSQLProperty
    private String customerName;
    
    @RSQLProperty
    private String customerEmail;
    
    @RSQLProperty
    private Double totalAmount;
    
    @RSQLProperty
    private Order.OrderStatus status;
    
    @RSQLProperty
    private LocalDateTime createdAt;
    
    @RSQLProperty
    private LocalDateTime updatedAt;
    
    private List<OrderDetailDto> orderDetails;
    
    public static OrderDto of(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerName(order.getCustomerName())
                .customerEmail(order.getCustomerEmail())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}

