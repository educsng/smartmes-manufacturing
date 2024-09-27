package com.smartmes.manufacturing.repository;

import com.smartmes.manufacturing.domain.ManufactureOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufactureOrderItemRepository extends JpaRepository<ManufactureOrderItem, Long> {
}
