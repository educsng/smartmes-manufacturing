package com.smartmes.manufacturing.repository;

import com.smartmes.manufacturing.domain.ManufactureOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufactureOrderRepository extends JpaRepository<ManufactureOrder, Long> {
}
