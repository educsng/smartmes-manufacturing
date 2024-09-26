package com.smartmes.manufacturing.service;

import com.smartmes.manufacturing.domain.ManufactureOrder;
import com.smartmes.manufacturing.dto.ManufactureOrderRequestDto;
import com.smartmes.manufacturing.dto.ManufactureOrderResponseDto;
import com.smartmes.manufacturing.mapper.ManufactureOrderMapper;
import com.smartmes.manufacturing.repository.ManufactureOrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManufactureOrderServiceImpl implements ManufactureOrderService {

    private final ManufactureOrderRepository manufactureOrderRepository;
    private final ManufactureOrderMapper manufactureOrderMapper;

    @Override
    public ManufactureOrderResponseDto createManufactureOrder(ManufactureOrderRequestDto requestDto) {
        try {
            ManufactureOrder savedOrder = manufactureOrderRepository.save(manufactureOrderMapper.toManufactureOrder(requestDto));
            return manufactureOrderMapper.toManufactureOrderResponseDto(savedOrder);
        } catch (Exception e) {
            throw new RuntimeException("Erro no registro da ordem de produção");
        }

    }
}
