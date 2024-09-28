package com.smartmes.manufacturing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManufactureOrderResponseDto {

    private String message;
    private ManufactureOrderResponse manufactureOrder;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ManufactureOrderResponse {
        private Long id;
        private String orderNumber;
        private String description;
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;
        private String equipment;
        private String employee;
        private String orderStatus;
        private List<ManufactureOrderItemResponseDto> items;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ManufactureOrderItemResponseDto {
        private Long id;
        private String description;
        private Integer quantity;
        private Integer nonConformingQuantity;
        private String unit;
        private String shift;
    }
}
