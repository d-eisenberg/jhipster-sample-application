package com.broscorp.service.impl;

import com.broscorp.service.WeekManuService;
import com.broscorp.domain.WeekManu;
import com.broscorp.repository.WeekManuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link WeekManu}.
 */
@Service
@Transactional
public class WeekManuServiceImpl implements WeekManuService {

    private final Logger log = LoggerFactory.getLogger(WeekManuServiceImpl.class);

    private final WeekManuRepository weekManuRepository;

    public WeekManuServiceImpl(WeekManuRepository weekManuRepository) {
        this.weekManuRepository = weekManuRepository;
    }

    /**
     * Save a weekManu.
     *
     * @param weekManu the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WeekManu save(WeekManu weekManu) {
        log.debug("Request to save WeekManu : {}", weekManu);
        return weekManuRepository.save(weekManu);
    }

    /**
     * Get all the weekManus.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WeekManu> findAll() {
        log.debug("Request to get all WeekManus");
        return weekManuRepository.findAll();
    }

    /**
     * Get one weekManu by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WeekManu> findOne(Long id) {
        log.debug("Request to get WeekManu : {}", id);
        return weekManuRepository.findById(id);
    }

    /**
     * Delete the weekManu by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WeekManu : {}", id);
        weekManuRepository.deleteById(id);
    }
}
