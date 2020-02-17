package com.broscorp.service.impl;

import com.broscorp.service.WeekOrderService;
import com.broscorp.domain.WeekOrder;
import com.broscorp.repository.WeekOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link WeekOrder}.
 */
@Service
@Transactional
public class WeekOrderServiceImpl implements WeekOrderService {

    private final Logger log = LoggerFactory.getLogger(WeekOrderServiceImpl.class);

    private final WeekOrderRepository weekOrderRepository;

    public WeekOrderServiceImpl(WeekOrderRepository weekOrderRepository) {
        this.weekOrderRepository = weekOrderRepository;
    }

    /**
     * Save a weekOrder.
     *
     * @param weekOrder the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WeekOrder save(WeekOrder weekOrder) {
        log.debug("Request to save WeekOrder : {}", weekOrder);
        return weekOrderRepository.save(weekOrder);
    }

    /**
     * Get all the weekOrders.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WeekOrder> findAll() {
        log.debug("Request to get all WeekOrders");
        return weekOrderRepository.findAll();
    }

    /**
     * Get one weekOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WeekOrder> findOne(Long id) {
        log.debug("Request to get WeekOrder : {}", id);
        return weekOrderRepository.findById(id);
    }

    /**
     * Delete the weekOrder by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WeekOrder : {}", id);
        weekOrderRepository.deleteById(id);
    }
}
