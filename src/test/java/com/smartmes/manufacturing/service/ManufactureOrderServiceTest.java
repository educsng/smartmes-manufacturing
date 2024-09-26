package com.smartmes.manufacturing.service;

import com.smartmes.manufacturing.domain.Employee;
import com.smartmes.manufacturing.domain.Equipment;
import com.smartmes.manufacturing.domain.ManufactureOrder;
import com.smartmes.manufacturing.dto.ManufactureOrderRequestDto;
import com.smartmes.manufacturing.dto.ManufactureOrderResponseDto;
import com.smartmes.manufacturing.enumeration.OrderStatus;
import com.smartmes.manufacturing.enumeration.ShiftType;
import com.smartmes.manufacturing.mapper.ManufactureOrderMapper;
import com.smartmes.manufacturing.repository.ManufactureOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ManufactureOrderServiceTest {

    @InjectMocks
    ManufactureOrderServiceImpl manufactureOrderService;

    @Mock
    ManufactureOrderRepository manufactureOrderRepository;

    @Mock
    ManufactureOrderMapper manufactureOrderMapper;

    @Test
    public void shouldCreateNewManufactureOrder_withValidData() {
        // Arrange
        var manufactureOrderRequestDto = mock(ManufactureOrderRequestDto.class);
        var manufactureOrderToSave = ManufactureOrder.builder()
            .orderNumber("123")
            .orderStatus(OrderStatus.CREATED)
            .createdAt(LocalDateTime.now())
            .shift(ShiftType.MORNING)
            .description("test")
            .employee(new Employee())
            .equipment(new Equipment())
            .build();
        var expectedResponseDto = ManufactureOrderResponseDto.builder()
            .message("Ordem de produção registrada com sucesso")
            .manufactureOrder(ManufactureOrderResponseDto.ManufactureOrderResponse.builder()
                .id(manufactureOrderToSave.getId())
                .shift(manufactureOrderToSave.getShift().name())
                .description(manufactureOrderToSave.getDescription())
                .equipment(manufactureOrderToSave.getEquipment().getSerialNumber())
                .employee(manufactureOrderToSave.getEmployee().getName())
                .orderStatus(manufactureOrderToSave.getOrderStatus().name())
                .build())
            .build();

        when(manufactureOrderMapper.toManufactureOrder(manufactureOrderRequestDto)).thenReturn(manufactureOrderToSave);
        when(manufactureOrderRepository.save(manufactureOrderToSave)).thenReturn(manufactureOrderToSave);
        when(manufactureOrderMapper.toManufactureOrderResponseDto(manufactureOrderToSave)).thenReturn(expectedResponseDto);

        // Act
        ManufactureOrderResponseDto manufactureOrder = manufactureOrderService.createManufactureOrder(manufactureOrderRequestDto);

        // Assert
        assertNotNull(manufactureOrder);
        assertEquals(expectedResponseDto, manufactureOrder);
    }

    @Test
    public void shouldNotCreateNewManufactureOrder_withInValidData() {
        // Arrange
        var manufactureOrderRequestDto = mock(ManufactureOrderRequestDto.class);

        // Act
        ManufactureOrderResponseDto manufactureOrder = manufactureOrderService.createManufactureOrder(manufactureOrderRequestDto);

        // Assert
        verifyNoInteractions(manufactureOrderMapper, manufactureOrderRepository);
    }

}
