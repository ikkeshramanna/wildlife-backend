package com.wildlife.fody.web.rest;

import com.wildlife.fody.domain.ObservationLocation;
import com.wildlife.fody.repository.ObservationLocationRepository;
import com.wildlife.fody.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.wildlife.fody.domain.ObservationLocation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ObservationLocationResource {

    private final Logger log = LoggerFactory.getLogger(ObservationLocationResource.class);

    private static final String ENTITY_NAME = "observationLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObservationLocationRepository observationLocationRepository;

    public ObservationLocationResource(ObservationLocationRepository observationLocationRepository) {
        this.observationLocationRepository = observationLocationRepository;
    }

    /**
     * {@code POST  /observation-locations} : Create a new observationLocation.
     *
     * @param observationLocation the observationLocation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new observationLocation, or with status {@code 400 (Bad Request)} if the observationLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/observation-locations")
    public ResponseEntity<ObservationLocation> createObservationLocation(@RequestBody ObservationLocation observationLocation) throws URISyntaxException {
        log.debug("REST request to save ObservationLocation : {}", observationLocation);
        if (observationLocation.getId() != null) {
            throw new BadRequestAlertException("A new observationLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObservationLocation result = observationLocationRepository.save(observationLocation);
        return ResponseEntity.created(new URI("/api/observation-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /observation-locations} : Updates an existing observationLocation.
     *
     * @param observationLocation the observationLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated observationLocation,
     * or with status {@code 400 (Bad Request)} if the observationLocation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the observationLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/observation-locations")
    public ResponseEntity<ObservationLocation> updateObservationLocation(@RequestBody ObservationLocation observationLocation) throws URISyntaxException {
        log.debug("REST request to update ObservationLocation : {}", observationLocation);
        if (observationLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ObservationLocation result = observationLocationRepository.save(observationLocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, observationLocation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /observation-locations} : get all the observationLocations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of observationLocations in body.
     */
    @GetMapping("/observation-locations")
    public List<ObservationLocation> getAllObservationLocations() {
        log.debug("REST request to get all ObservationLocations");
        return observationLocationRepository.findAll();
    }

    /**
     * {@code GET  /observation-locations/:id} : get the "id" observationLocation.
     *
     * @param id the id of the observationLocation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the observationLocation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/observation-locations/{id}")
    public ResponseEntity<ObservationLocation> getObservationLocation(@PathVariable Long id) {
        log.debug("REST request to get ObservationLocation : {}", id);
        Optional<ObservationLocation> observationLocation = observationLocationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(observationLocation);
    }

    /**
     * {@code DELETE  /observation-locations/:id} : delete the "id" observationLocation.
     *
     * @param id the id of the observationLocation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/observation-locations/{id}")
    public ResponseEntity<Void> deleteObservationLocation(@PathVariable Long id) {
        log.debug("REST request to delete ObservationLocation : {}", id);
        observationLocationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
