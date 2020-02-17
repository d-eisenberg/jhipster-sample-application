package com.broscorp.web.rest;

import com.broscorp.domain.WeekOrder;
import com.broscorp.service.WeekOrderService;
import com.broscorp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.broscorp.domain.WeekOrder}.
 */
@RestController
@RequestMapping("/api")
public class WeekOrderResource {

    private final Logger log = LoggerFactory.getLogger(WeekOrderResource.class);

    private static final String ENTITY_NAME = "weekOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WeekOrderService weekOrderService;

    public WeekOrderResource(WeekOrderService weekOrderService) {
        this.weekOrderService = weekOrderService;
    }

    /**
     * {@code POST  /week-orders} : Create a new weekOrder.
     *
     * @param weekOrder the weekOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new weekOrder, or with status {@code 400 (Bad Request)} if the weekOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/week-orders")
    public ResponseEntity<WeekOrder> createWeekOrder(@RequestBody WeekOrder weekOrder) throws URISyntaxException {
        log.debug("REST request to save WeekOrder : {}", weekOrder);
        if (weekOrder.getId() != null) {
            throw new BadRequestAlertException("A new weekOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WeekOrder result = weekOrderService.save(weekOrder);
        return ResponseEntity.created(new URI("/api/week-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /week-orders} : Updates an existing weekOrder.
     *
     * @param weekOrder the weekOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weekOrder,
     * or with status {@code 400 (Bad Request)} if the weekOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the weekOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/week-orders")
    public ResponseEntity<WeekOrder> updateWeekOrder(@RequestBody WeekOrder weekOrder) throws URISyntaxException {
        log.debug("REST request to update WeekOrder : {}", weekOrder);
        if (weekOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WeekOrder result = weekOrderService.save(weekOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, weekOrder.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /week-orders} : get all the weekOrders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of weekOrders in body.
     */
    @GetMapping("/week-orders")
    public List<WeekOrder> getAllWeekOrders() {
        log.debug("REST request to get all WeekOrders");
        return weekOrderService.findAll();
    }

    /**
     * {@code GET  /week-orders/:id} : get the "id" weekOrder.
     *
     * @param id the id of the weekOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the weekOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/week-orders/{id}")
    public ResponseEntity<WeekOrder> getWeekOrder(@PathVariable Long id) {
        log.debug("REST request to get WeekOrder : {}", id);
        Optional<WeekOrder> weekOrder = weekOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(weekOrder);
    }

    /**
     * {@code DELETE  /week-orders/:id} : delete the "id" weekOrder.
     *
     * @param id the id of the weekOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/week-orders/{id}")
    public ResponseEntity<Void> deleteWeekOrder(@PathVariable Long id) {
        log.debug("REST request to delete WeekOrder : {}", id);
        weekOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
