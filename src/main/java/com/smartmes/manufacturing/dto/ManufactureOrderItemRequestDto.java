package com.smartmes.manufacturing.dto;

import com.smartmes.manufacturing.enumeration.ShiftType;
import com.smartmes.manufacturing.enumeration.UnitMeasurementType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManufactureOrderItemRequestDto {

    private String description;

    private Integer quantity;

    private Integer nonConformingQuantity;

    @NotNull(message = "O campo shift é obrigatório")
    @Enumerated(EnumType.STRING)
    private ShiftType shift;

    @Enumerated(EnumType.STRING)
    private UnitMeasurementType unit;
}
