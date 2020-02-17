package com.broscorp.repository;

import com.broscorp.domain.WeekOrder;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WeekOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeekOrderRepository extends JpaRepository<WeekOrder, Long> {

}
