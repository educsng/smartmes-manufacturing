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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ManufactureControllerTest {

    @InjectMocks
    ManufactureController controller;

    @Mock
    ManufactureOrderService service;

    @Test
    public void shouldCreateAndReturnNewManufactureOrder_withValidData() {
        // Arrange
        var request = mock(ManufactureOrderRequestDto.class);
        var response = mock(ManufactureOrderResponseDto.class);

        when(service.createManufactureOrder(request)).thenReturn(response);

        // Act
        ManufactureOrderResponseDto order = controller.createManufactureOrder(request);

        // Assert
        assertNotNull(order);
    }

}
