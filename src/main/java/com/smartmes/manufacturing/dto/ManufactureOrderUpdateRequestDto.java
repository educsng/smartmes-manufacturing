package com.smartmes.manufacturing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManufactureOrderUpdateRequestDto {

    @NotNull(message = "O campo orderId é obrigatório")
    private Long orderId;

    private List<ManufactureOrderItemRequestDto> items;
}
