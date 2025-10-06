package com.demo.rsql.dto;

import com.demo.rsql.config.RSQLProperty;
import com.demo.rsql.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    
    @RSQLProperty
    private UUID id;
    
    @RSQLProperty
    private UUID orderId;
    
    @RSQLProperty
    private String productName;
    
    @RSQLProperty
    private String productSku;
    
    @RSQLProperty
    private Integer quantity;
    
    @RSQLProperty
    private Double unitPrice;
    
    @RSQLProperty
    private Double totalPrice;
    
    @RSQLProperty
    private LocalDateTime createdAt;
    
    @RSQLProperty
    private LocalDateTime updatedAt;
    
    public static OrderDetailDto of(OrderDetail orderDetail) {
        return OrderDetailDto.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productName(orderDetail.getProductName())
                .productSku(orderDetail.getProductSku())
                .quantity(orderDetail.getQuantity())
                .unitPrice(orderDetail.getUnitPrice())
                .totalPrice(orderDetail.getTotalPrice())
                .createdAt(orderDetail.getCreatedAt())
                .updatedAt(orderDetail.getUpdatedAt())
                .build();
    }
}

