package com.sheikh.oms_api.repository;

import com.sheikh.oms_api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}