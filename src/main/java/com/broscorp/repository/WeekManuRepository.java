package com.broscorp.repository;

import com.broscorp.domain.WeekManu;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WeekManu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeekManuRepository extends JpaRepository<WeekManu, Long> {

}
