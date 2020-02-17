package com.broscorp.service;

import com.broscorp.domain.WeekOrder;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link WeekOrder}.
 */
public interface WeekOrderService {

    /**
     * Save a weekOrder.
     *
     * @param weekOrder the entity to save.
     * @return the persisted entity.
     */
    WeekOrder save(WeekOrder weekOrder);

    /**
     * Get all the weekOrders.
     *
     * @return the list of entities.
     */
    List<WeekOrder> findAll();

    /**
     * Get the "id" weekOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WeekOrder> findOne(Long id);

    /**
     * Delete the "id" weekOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
