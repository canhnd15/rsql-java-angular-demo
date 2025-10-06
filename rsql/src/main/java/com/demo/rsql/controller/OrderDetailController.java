package com.demo.rsql.controller;

import com.demo.rsql.constants.FrontendApi;
import com.demo.rsql.dto.ApiResponseDto;
import com.demo.rsql.dto.OrderDetailDto;
import com.demo.rsql.dto.ResponseStatus;
import com.demo.rsql.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @GetMapping(FrontendApi.PAYROLL_ORDER_DETAIL)
    public ResponseEntity<ApiResponseDto<?>> getAll(
            @RequestParam(value = "filters", required = false) String filters,
            @RequestParam(value = "sorts", required = false) String sorts,
            Pageable pageable
    ) {
        ApiResponseDto<?> resp = ApiResponseDto.builder()
                .data(orderDetailService.getAll(filters, sorts, pageable))
                .status(ResponseStatus.SUCCESS.name())
                .message("Get order detail list successfully!")
                .build();
        return ResponseEntity.ok(resp);
    }
}

