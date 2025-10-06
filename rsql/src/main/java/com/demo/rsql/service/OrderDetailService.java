package com.demo.rsql.service;

import com.demo.rsql.dto.OrderDetailDto;
import com.demo.rsql.entity.OrderDetail;
import com.demo.rsql.repository.OrderDetailRepository;
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
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    @Transactional(readOnly = true)
    public Page<OrderDetailDto> getAll(
            String filters,
            String sorts,
            Pageable pageable
    ) {
        Specification<OrderDetail> finalSpec = SpecificationBuilder.build(filters,
                FieldMapperUtil.getFieldMap(OrderDetailDto.class, OrderDetail.class));
        Pageable customPageable = PageableUtil.withSortingAndFieldMapping(pageable, sorts, OrderDetailDto.class, OrderDetail.class);

        Page<OrderDetail> page = orderDetailRepository.findAll(finalSpec, customPageable);

        return page.map(OrderDetailDto::of);
    }
}

