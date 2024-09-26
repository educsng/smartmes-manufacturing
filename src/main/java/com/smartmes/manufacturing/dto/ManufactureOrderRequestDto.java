package com.smartmes.manufacturing.dto;

import com.smartmes.manufacturing.enumeration.ShiftType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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

    @NotNull(message = "O campo shift é obrigatório")
    @Enumerated(EnumType.STRING)
    private ShiftType shift;

    @NotNull(message = "O campo equipmentId é obrigatório")
    private String equipmentId;

    private List<@Valid ManufactureOrderItemRequestDto> items;
}
