package com.broscorp.web.rest;

import com.broscorp.domain.WeekManu;
import com.broscorp.service.WeekManuService;
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
 * REST controller for managing {@link com.broscorp.domain.WeekManu}.
 */
@RestController
@RequestMapping("/api")
public class WeekManuResource {

    private final Logger log = LoggerFactory.getLogger(WeekManuResource.class);

    private static final String ENTITY_NAME = "weekManu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WeekManuService weekManuService;

    public WeekManuResource(WeekManuService weekManuService) {
        this.weekManuService = weekManuService;
    }

    /**
     * {@code POST  /week-manus} : Create a new weekManu.
     *
     * @param weekManu the weekManu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new weekManu, or with status {@code 400 (Bad Request)} if the weekManu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/week-manus")
    public ResponseEntity<WeekManu> createWeekManu(@RequestBody WeekManu weekManu) throws URISyntaxException {
        log.debug("REST request to save WeekManu : {}", weekManu);
        if (weekManu.getId() != null) {
            throw new BadRequestAlertException("A new weekManu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WeekManu result = weekManuService.save(weekManu);
        return ResponseEntity.created(new URI("/api/week-manus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /week-manus} : Updates an existing weekManu.
     *
     * @param weekManu the weekManu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weekManu,
     * or with status {@code 400 (Bad Request)} if the weekManu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the weekManu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/week-manus")
    public ResponseEntity<WeekManu> updateWeekManu(@RequestBody WeekManu weekManu) throws URISyntaxException {
        log.debug("REST request to update WeekManu : {}", weekManu);
        if (weekManu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WeekManu result = weekManuService.save(weekManu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, weekManu.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /week-manus} : get all the weekManus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of weekManus in body.
     */
    @GetMapping("/week-manus")
    public List<WeekManu> getAllWeekManus() {
        log.debug("REST request to get all WeekManus");
        return weekManuService.findAll();
    }

    /**
     * {@code GET  /week-manus/:id} : get the "id" weekManu.
     *
     * @param id the id of the weekManu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the weekManu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/week-manus/{id}")
    public ResponseEntity<WeekManu> getWeekManu(@PathVariable Long id) {
        log.debug("REST request to get WeekManu : {}", id);
        Optional<WeekManu> weekManu = weekManuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(weekManu);
    }

    /**
     * {@code DELETE  /week-manus/:id} : delete the "id" weekManu.
     *
     * @param id the id of the weekManu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/week-manus/{id}")
    public ResponseEntity<Void> deleteWeekManu(@PathVariable Long id) {
        log.debug("REST request to delete WeekManu : {}", id);
        weekManuService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
