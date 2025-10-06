package com.demo.rsql.controller;

import com.demo.rsql.constants.FrontendApi;
import com.demo.rsql.dto.ApiResponseDto;
import com.demo.rsql.dto.OrderDto;
import com.demo.rsql.dto.ResponseStatus;
import com.demo.rsql.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @GetMapping(FrontendApi.PAYROLL_ORDER)
    public ResponseEntity<ApiResponseDto<?>> getAll(
            @RequestParam(value = "filters", required = false) String filters,
            @RequestParam(value = "sorts", required = false) String sorts,
            @RequestParam(value = "includeDetails", required = false, defaultValue = "false") boolean includeDetails,
            Pageable pageable
    ) {
        ApiResponseDto<?> resp = ApiResponseDto.builder()
                .data(orderService.getAll(filters, sorts, pageable, includeDetails))
                .status(ResponseStatus.SUCCESS.name())
                .message("Get order list successfully!")
                .build();
        return ResponseEntity.ok(resp);
    }
}

