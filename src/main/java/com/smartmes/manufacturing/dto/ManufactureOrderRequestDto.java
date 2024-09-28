package com.smartmes.manufacturing.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class ManufactureOrderRequestDto {

    private String description;

    @NotBlank(message = "O campo orderNumber é obrigatório")
    private String orderNumber;

    @NotNull(message = "O campo equipmentId é obrigatório")
    private Long equipmentId;

    @NotEmpty(message = "O campo items é obrigatório")
    private List<@Valid ManufactureOrderItemRequestDto> items;
}
