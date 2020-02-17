package com.broscorp.service;

import com.broscorp.domain.WeekManu;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link WeekManu}.
 */
public interface WeekManuService {

    /**
     * Save a weekManu.
     *
     * @param weekManu the entity to save.
     * @return the persisted entity.
     */
    WeekManu save(WeekManu weekManu);

    /**
     * Get all the weekManus.
     *
     * @return the list of entities.
     */
    List<WeekManu> findAll();

    /**
     * Get the "id" weekManu.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WeekManu> findOne(Long id);

    /**
     * Delete the "id" weekManu.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
