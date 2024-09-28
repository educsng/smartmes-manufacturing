package com.smartmes.manufacturing.service;

import com.smartmes.manufacturing.domain.Employee;
import com.smartmes.manufacturing.domain.Equipment;
import com.smartmes.manufacturing.domain.ManufactureOrder;
import com.smartmes.manufacturing.domain.ManufactureOrderItem;
import com.smartmes.manufacturing.dto.ManufactureOrderItemRequestDto;
import com.smartmes.manufacturing.dto.ManufactureOrderRequestDto;
import com.smartmes.manufacturing.dto.ManufactureOrderResponseDto;
import com.smartmes.manufacturing.enumeration.OrderStatus;
import com.smartmes.manufacturing.mapper.ManufactureOrderMapper;
import com.smartmes.manufacturing.repository.EquipmentRepository;
import com.smartmes.manufacturing.repository.ManufactureOrderItemRepository;
import com.smartmes.manufacturing.repository.ManufactureOrderRepository;
import com.smartmes.manufacturing.validator.ManufactureOrderValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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

    @Mock
    ManufactureOrderValidator manufactureOrderValidator;

    @Mock
    EquipmentRepository equipmentRepository;

    @Mock
    ManufactureOrderItemRepository manufactureOrderItemRepository;

    @Test
    public void createOrUpdateManufactureOrder_shouldCreateNewManufactureOrder_withValidData() {
        // Arrange
        var equipment = Equipment.builder().id(123456L).build();
        var manufactureOrderRequestDto = ManufactureOrderRequestDto.builder()
                .orderNumber("123")
                .description("test")
                .equipmentId(123456L)
                .items(Collections.singletonList(ManufactureOrderItemRequestDto.builder().build()))
                .build();
        var manufactureOrderToSave = ManufactureOrder.builder()
            .orderNumber("123")
            .orderStatus(OrderStatus.CREATED)
            .createdAt(LocalDateTime.now())
            .description("test")
            .employee(new Employee())
            .equipment(equipment)
            .items(Collections.singletonList(ManufactureOrderItem.builder().build()))
            .build();
        var expectedResponseDto = ManufactureOrderResponseDto.builder()
            .message("Ordem de produção registrada com sucesso")
            .manufactureOrder(ManufactureOrderResponseDto.ManufactureOrderResponse.builder()
                .id(manufactureOrderToSave.getId())
                .description(manufactureOrderToSave.getDescription())
                .equipment(manufactureOrderToSave.getEquipment().getSerialNumber())
                .employee(manufactureOrderToSave.getEmployee().getName())
                .orderStatus(manufactureOrderToSave.getOrderStatus().name())
                .build())
            .build();

        when(equipmentRepository.findById(123456L)).thenReturn(Optional.of(equipment));
        when(manufactureOrderMapper.toManufactureOrder(manufactureOrderRequestDto, equipment)).thenReturn(manufactureOrderToSave);
        when(manufactureOrderRepository.save(manufactureOrderToSave)).thenReturn(manufactureOrderToSave);
        when(manufactureOrderMapper.toManufactureOrderResponseDto(manufactureOrderToSave)).thenReturn(expectedResponseDto);

        // Act
        ManufactureOrderResponseDto manufactureOrder = manufactureOrderService.createOrUpdateManufactureOrder(manufactureOrderRequestDto);

        // Assert
        assertNotNull(manufactureOrder);
        assertEquals("Ordem de produção registrada com sucesso", manufactureOrder.getMessage());
        assertEquals(expectedResponseDto.getManufactureOrder(), manufactureOrder.getManufactureOrder());
    }

    @Test
    public void createOrUpdateManufactureOrder_shouldNotCreateNewManufactureOrder_withoutOrderNumber() {
        // Arrange
        var manufactureOrderRequestDto = mock(ManufactureOrderRequestDto.class);
        var illegalArgumentException = new IllegalArgumentException("O campo orderNumber é obrigatório");

        doThrow(illegalArgumentException).when(manufactureOrderValidator).validateCreateManufactureOrder(manufactureOrderRequestDto);

        // Act
        RuntimeException exception = assertThrows(IllegalArgumentException.class,
            () -> manufactureOrderService.createOrUpdateManufactureOrder(manufactureOrderRequestDto));

        // Assert
        assertEquals("O campo orderNumber é obrigatório", exception.getMessage());
        verifyNoInteractions(manufactureOrderMapper, manufactureOrderRepository);
    }

    @Test
    public void createOrUpdateManufactureOrder_shouldNotCreateNewManufactureOrder_withoutEquipment() {
        // Arrange
        var manufactureOrderRequestDto = ManufactureOrderRequestDto.builder().orderNumber("123").build();
        var illegalArgumentException = new IllegalArgumentException("O campo equipmentId é obrigatório");

        doThrow(illegalArgumentException).when(manufactureOrderValidator).validateCreateManufactureOrder(manufactureOrderRequestDto);

        // Act
        RuntimeException exception = assertThrows(IllegalArgumentException.class,
            () -> manufactureOrderService.createOrUpdateManufactureOrder(manufactureOrderRequestDto));

        // Assert
        assertEquals("O campo equipmentId é obrigatório", exception.getMessage());
        verifyNoInteractions(manufactureOrderMapper, manufactureOrderRepository);
    }

    @Test
    public void createOrUpdateManufactureOrder_shouldNotCreateNewManufactureOrder_withoutItems() {
        // Arrange
        var manufactureOrderRequestDto = ManufactureOrderRequestDto.builder().orderNumber("123").equipmentId(123456L).build();
        var illegalArgumentException = new IllegalArgumentException("O campo items é obrigatório");

        doThrow(illegalArgumentException).when(manufactureOrderValidator).validateCreateManufactureOrder(manufactureOrderRequestDto);

        // Act
        RuntimeException exception = assertThrows(IllegalArgumentException.class,
            () -> manufactureOrderService.createOrUpdateManufactureOrder(manufactureOrderRequestDto));

        // Assert
        assertEquals("O campo items é obrigatório", exception.getMessage());
        verifyNoInteractions(manufactureOrderMapper, manufactureOrderRepository);
    }

    @Test
    public void createOrUpdateManufactureOrder_shouldNotCreateNewManufactureOrder_whenOrderNumberIsInvalid() {
        // Arrange
        var manufactureOrderRequestDto = ManufactureOrderRequestDto.builder().orderNumber("321").build();

        doNothing().when(manufactureOrderValidator).validateCreateManufactureOrder(manufactureOrderRequestDto);

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> manufactureOrderService.createOrUpdateManufactureOrder(manufactureOrderRequestDto));

        // Assert
        assertEquals("O número do pedido não foi encontrado, insira um número válido", exception.getMessage());
        verifyNoInteractions(manufactureOrderMapper, manufactureOrderRepository);
    }

    @Test
    public void createOrUpdateManufactureOrder_shouldNotCreateNewManufactureOrder_whenEquipmentIdIsInvalid() {
        // Arrange
        var manufactureOrderRequestDto = ManufactureOrderRequestDto.builder().orderNumber("123").equipmentId(654321L).build();

        when(equipmentRepository.findById(manufactureOrderRequestDto.getEquipmentId())).thenReturn(Optional.empty());

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> manufactureOrderService.createOrUpdateManufactureOrder(manufactureOrderRequestDto));

        // Assert
        assertEquals("O identificador do equipamento não foi encontrado, insira um identificador válido", exception.getMessage());
        verifyNoInteractions(manufactureOrderMapper);
        verify(manufactureOrderRepository, never()).saveAndFlush(any(ManufactureOrder.class));
    }

    @Test
    public void createOrUpdateManufactureOrder_shouldUpdateManufactureOrder_whenOrderIsAlreadyCreated() {
        // Arrange
        var alreadyCreatedOrder = ManufactureOrder.builder()
                .id(123456789L)
                .orderNumber("123")
                .build();
        var manufactureOrderRequestDto = ManufactureOrderRequestDto.builder()
                .orderNumber("123")
                .items(List.of())
                .build();
        var expectedResponseDto = ManufactureOrderResponseDto.builder()
                .manufactureOrder(mock(ManufactureOrderResponseDto.ManufactureOrderResponse.class))
                .message("Ordem de produção atualizada com sucesso")
                .build();

        when(manufactureOrderRepository.findByOrderNumber(manufactureOrderRequestDto.getOrderNumber())).thenReturn(Optional.of(alreadyCreatedOrder));
        when(manufactureOrderRepository.saveAndFlush(alreadyCreatedOrder)).thenReturn(alreadyCreatedOrder);
        when(manufactureOrderMapper.toManufactureOrderResponseDto(alreadyCreatedOrder)).thenReturn(expectedResponseDto);

        // Act
        var manufactureOrder = manufactureOrderService.createOrUpdateManufactureOrder(manufactureOrderRequestDto);

        // Assert
        assertEquals("Ordem de produção atualizada com sucesso", manufactureOrder.getMessage());
        assertEquals(expectedResponseDto.getManufactureOrder(), manufactureOrder.getManufactureOrder());
    }

}
