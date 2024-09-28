package com.smartmes.manufacturing.validator;

import com.smartmes.manufacturing.dto.ManufactureOrderRequestDto;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class ManufactureOrderValidator {

    public void validateCreateManufactureOrder(ManufactureOrderRequestDto requestDto) {
        if (isNull(requestDto.getOrderNumber())) {
            throw new IllegalArgumentException("O campo orderNumber é obrigatório");
        }

        if (isNull(requestDto.getEquipmentId())) {
            throw new IllegalArgumentException("O campo equipmentId é obrigatório");
        }

        if (isNull(requestDto.getItems()) || requestDto.getItems().isEmpty()) {
            throw new IllegalArgumentException("O campo items é obrigatório");
        }
    }
}
