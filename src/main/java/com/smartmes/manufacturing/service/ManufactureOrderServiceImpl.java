package com.smartmes.manufacturing.service;

import com.smartmes.manufacturing.domain.Equipment;
import com.smartmes.manufacturing.domain.ManufactureOrder;
import com.smartmes.manufacturing.domain.ManufactureOrderItem;
import com.smartmes.manufacturing.dto.ManufactureOrderRequestDto;
import com.smartmes.manufacturing.dto.ManufactureOrderResponseDto;
import com.smartmes.manufacturing.dto.ManufactureOrderUpdateRequestDto;
import com.smartmes.manufacturing.mapper.ManufactureOrderMapper;
import com.smartmes.manufacturing.repository.EquipmentRepository;
import com.smartmes.manufacturing.repository.ManufactureOrderItemRepository;
import com.smartmes.manufacturing.repository.ManufactureOrderRepository;
import com.smartmes.manufacturing.validator.ManufactureOrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManufactureOrderServiceImpl implements ManufactureOrderService {

    private final ManufactureOrderRepository manufactureOrderRepository;
    private final ManufactureOrderMapper manufactureOrderMapper;
    private final ManufactureOrderValidator manufactureOrderValidator;
    private final EquipmentRepository equipmentRepository;
    private final ManufactureOrderItemRepository manufactureOrderItemRepository;

    @Override
    @Transactional
    public ManufactureOrderResponseDto createOrUpdateManufactureOrder(ManufactureOrderRequestDto requestDto) {
        manufactureOrderValidator.validateCreateManufactureOrder(requestDto);

        // Simulates a database or external call to get order (e.g. ERP)
        if (!"123".equals(requestDto.getOrderNumber())) {
            throw new RuntimeException("O número do pedido não foi encontrado, insira um número válido");
        }

        Optional<ManufactureOrder> optionalManufactureOrder = manufactureOrderRepository.findByOrderNumber(requestDto.getOrderNumber());
        if (optionalManufactureOrder.isPresent()) {
            var order = optionalManufactureOrder.get();
            order = this.updateManufactureOrder(order, buildManufactureOrderUpdateRequestDto(order, requestDto));
            return manufactureOrderMapper.toManufactureOrderResponseDto(order);
        }

        final Equipment equipment = equipmentRepository.findById(requestDto.getEquipmentId())
            .orElseThrow(() -> new RuntimeException("O identificador do equipamento não foi encontrado, insira um identificador válido"));

        ManufactureOrder savedOrder = manufactureOrderRepository.save(manufactureOrderMapper.toManufactureOrder(requestDto, equipment));
        savedOrder.getItems().forEach(item -> item.setOrder(savedOrder));
        this.saveManufactureItems(savedOrder);

        return manufactureOrderMapper.toManufactureOrderResponseDto(savedOrder);
    }

    @Transactional
    public void saveManufactureItems(ManufactureOrder savedOrder) {
        manufactureOrderItemRepository.saveAll(savedOrder.getItems());
    }

    @Transactional
    public ManufactureOrder updateManufactureOrder(ManufactureOrder manufactureOrder, ManufactureOrderUpdateRequestDto requestDto) {
        List<ManufactureOrderItem> newBatch = manufactureOrderMapper.toManufactureOrderItems(requestDto.getItems());
        newBatch.forEach(item -> item.setOrder(manufactureOrder));

        List<ManufactureOrderItem> savedItems = manufactureOrderItemRepository.saveAllAndFlush(newBatch);
        manufactureOrder.setItems(savedItems);
        return manufactureOrderRepository.saveAndFlush(manufactureOrder);
    }

    private static ManufactureOrderUpdateRequestDto buildManufactureOrderUpdateRequestDto(ManufactureOrder manufactureOrder, ManufactureOrderRequestDto requestDto) {
        return ManufactureOrderUpdateRequestDto.builder().orderId(manufactureOrder.getId()).items(requestDto.getItems()).build();
    }
}
