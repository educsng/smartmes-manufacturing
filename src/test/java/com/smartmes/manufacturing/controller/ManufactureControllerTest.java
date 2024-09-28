package com.smartmes.manufacturing.controller;

import com.smartmes.manufacturing.dto.ManufactureOrderRequestDto;
import com.smartmes.manufacturing.dto.ManufactureOrderResponseDto;
import com.smartmes.manufacturing.service.ManufactureOrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ManufactureControllerTest {

    @InjectMocks
    ManufactureController controller;

    @Mock
    ManufactureOrderService service;

    @Test
    public void createOrUpdateManufactureOrder_shouldCreateAndReturnNewManufactureOrder_withValidData() {
        // Arrange
        var request = mock(ManufactureOrderRequestDto.class);
        var response = mock(ManufactureOrderResponseDto.class);

        when(service.createOrUpdateManufactureOrder(request)).thenReturn(response);

        // Act
        ManufactureOrderResponseDto order = controller.createOrUpdateManufactureOrder(request);

        // Assert
        assertNotNull(order);
    }

    @Test
    public void createOrUpdateManufactureOrder_shouldNotCreateAndReturnNewManufactureOrder_withValidData() {
        // Arrange
        var request = mock(ManufactureOrderRequestDto.class);

        when(service.createOrUpdateManufactureOrder(request)).thenReturn(null);

        // Act
        ManufactureOrderResponseDto order = controller.createOrUpdateManufactureOrder(request);

        // Assert
        assertNull(order);
    }

}
