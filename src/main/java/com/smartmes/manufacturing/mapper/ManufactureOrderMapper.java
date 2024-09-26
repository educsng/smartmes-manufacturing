package com.smartmes.manufacturing.mapper;

import com.smartmes.manufacturing.domain.Employee;
import com.smartmes.manufacturing.domain.Equipment;
import com.smartmes.manufacturing.domain.ManufactureOrder;
import com.smartmes.manufacturing.dto.ManufactureOrderRequestDto;
import com.smartmes.manufacturing.dto.ManufactureOrderResponseDto;
import com.smartmes.manufacturing.enumeration.OrderStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ManufactureOrderMapper {

    public ManufactureOrder toManufactureOrder(ManufactureOrderRequestDto manufactureOrder) {
        return ManufactureOrder.builder()
            .orderNumber(manufactureOrder.getOrderNumber())
            .orderStatus(OrderStatus.CREATED)
            .createdAt(LocalDateTime.now())
            .shift(manufactureOrder.getShift())
            .description(manufactureOrder.getDescription())
            .employee(getLoggedEmployee())
            .equipment(getEquipment(manufactureOrder.getEquipmentId()))
            .build();
    }

    public ManufactureOrderResponseDto toManufactureOrderResponseDto(ManufactureOrder manufactureOrder) {
        return ManufactureOrderResponseDto.builder()
            .message("Ordem de produção registrada com sucesso")
            .manufactureOrder(getManufactureOrder(manufactureOrder))
            .build();
    }

    private static Employee getLoggedEmployee() {
        // TODO - implement
        return null;
    }

    private Equipment getEquipment(String equipmentId) {
        // TODO - implement
        return null;
    }

    private ManufactureOrderResponseDto.ManufactureOrderResponse getManufactureOrder(ManufactureOrder manufactureOrder) {
        return  ManufactureOrderResponseDto.ManufactureOrderResponse.builder()
            .id(manufactureOrder.getId())
            .shift(manufactureOrder.getShift().name())
            .description(manufactureOrder.getDescription())
            .equipment(manufactureOrder.getEquipment().getSerialNumber())
            .employee(manufactureOrder.getEmployee().getName())
            .orderStatus(manufactureOrder.getOrderStatus().name())
            .items(getManufactureOrderItems(manufactureOrder))
            .build();
    }

    private List<ManufactureOrderResponseDto.ManufactureOrderItemResponseDto> getManufactureOrderItems(ManufactureOrder manufactureOrder) {
        return manufactureOrder.getItems().stream()
            .map(item -> ManufactureOrderResponseDto.ManufactureOrderItemResponseDto.builder()
                .id(item.getId())
                .description(item.getDescription())
                .quantity(item.getQuantity())
                .nonConformingQuantity(item.getNonConformingQuantity())
                .unit(item.getUnit().name())
                .build())
            .toList();
    }
}
