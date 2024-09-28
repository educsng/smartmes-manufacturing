package com.smartmes.manufacturing.controller;

import com.smartmes.manufacturing.dto.ManufactureOrderRequestDto;
import com.smartmes.manufacturing.dto.ManufactureOrderResponseDto;
import com.smartmes.manufacturing.service.ManufactureOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manufacture-orders")
public class ManufactureController {

    private final ManufactureOrderService manufactureOrderService;

    @PostMapping
    public ManufactureOrderResponseDto createOrUpdateManufactureOrder(@Valid @RequestBody ManufactureOrderRequestDto requestDto) {
        return manufactureOrderService.createOrUpdateManufactureOrder(requestDto);
    }
}
