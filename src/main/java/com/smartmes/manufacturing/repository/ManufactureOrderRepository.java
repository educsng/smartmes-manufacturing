package com.smartmes.manufacturing.repository;

import com.smartmes.manufacturing.domain.ManufactureOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManufactureOrderRepository extends JpaRepository<ManufactureOrder, Long> {

    Optional<ManufactureOrder> findByOrderNumber(String orderNumber);
}
