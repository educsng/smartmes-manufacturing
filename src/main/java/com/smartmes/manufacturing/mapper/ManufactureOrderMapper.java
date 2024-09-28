package com.smartmes.manufacturing.mapper;

import com.smartmes.manufacturing.domain.Employee;
import com.smartmes.manufacturing.domain.Equipment;
import com.smartmes.manufacturing.domain.ManufactureOrder;
import com.smartmes.manufacturing.domain.ManufactureOrderItem;
import com.smartmes.manufacturing.dto.ManufactureOrderItemRequestDto;
import com.smartmes.manufacturing.dto.ManufactureOrderRequestDto;
import com.smartmes.manufacturing.dto.ManufactureOrderResponseDto;
import com.smartmes.manufacturing.enumeration.OrderStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.nonNull;

@Component
public class ManufactureOrderMapper {

    public ManufactureOrder toManufactureOrder(ManufactureOrderRequestDto manufactureOrder, Equipment equipment) {
        return ManufactureOrder.builder()
            .orderNumber(manufactureOrder.getOrderNumber())
            .orderStatus(OrderStatus.CREATED)
            .createdAt(LocalDateTime.now())
            .description(manufactureOrder.getDescription())
            .employee(getLoggedEmployee())
            .equipment(equipment)
            .items(this.toManufactureOrderItems(manufactureOrder.getItems()))
            .build();
    }

    public ManufactureOrderResponseDto toManufactureOrderResponseDto(ManufactureOrder manufactureOrder) {
        return ManufactureOrderResponseDto.builder()
            .message("Ordem de produção registrada com sucesso")
            .manufactureOrder(getManufactureOrder(manufactureOrder))
            .build();
    }

    public List<ManufactureOrderItem> toManufactureOrderItems(List<ManufactureOrderItemRequestDto> items) {
        return items.stream()
            .map(item -> ManufactureOrderItem.builder()
                .description(item.getDescription())
                .quantity(nonNull(item.getQuantity()) ? item.getQuantity() : 0)
                .nonConformingQuantity(nonNull(item.getNonConformingQuantity()) ? item.getNonConformingQuantity() : 0)
                .unit(item.getUnit())
                .shift(item.getShift())
                .build())
            .toList();
    }

    private static Employee getLoggedEmployee() {
        // Not implemented
        // Should return the logged user
        return Employee.builder().id(1L).name("John Doe").email("john@doe.com").phoneNumber("999999999").address("Test address").build();
    }

    private ManufactureOrderResponseDto.ManufactureOrderResponse getManufactureOrder(ManufactureOrder manufactureOrder) {
        return  ManufactureOrderResponseDto.ManufactureOrderResponse.builder()
            .id(manufactureOrder.getId())
            .description(manufactureOrder.getDescription())
            .orderNumber(manufactureOrder.getOrderNumber())
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
                .shift(item.getShift().name())
                .build())
            .toList();
    }


}
