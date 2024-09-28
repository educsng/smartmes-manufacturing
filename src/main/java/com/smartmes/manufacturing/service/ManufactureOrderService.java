package com.smartmes.manufacturing.service;

import com.smartmes.manufacturing.dto.ManufactureOrderRequestDto;
import com.smartmes.manufacturing.dto.ManufactureOrderResponseDto;

public interface ManufactureOrderService {

    ManufactureOrderResponseDto createOrUpdateManufactureOrder(ManufactureOrderRequestDto manufactureOrder);
}
