package com.demo.rsql.service;

import com.demo.rsql.dto.OrderDto;
import com.demo.rsql.entity.Order;
import com.demo.rsql.repository.OrderRepository;
import com.demo.rsql.util.FieldMapperUtil;
import com.demo.rsql.util.PageableUtil;
import com.demo.rsql.config.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Page<OrderDto> getAll(
            String filters,
            String sorts,
            Pageable pageable
    ) {
        Specification<Order> finalSpec = SpecificationBuilder.build(filters,
                FieldMapperUtil.getFieldMap(OrderDto.class, Order.class));
        Pageable customPageable = PageableUtil.withSortingAndFieldMapping(pageable, sorts, OrderDto.class, Order.class);

        Page<Order> page = orderRepository.findAll(finalSpec, customPageable);

        return page.map(OrderDto::of);
    }
}

