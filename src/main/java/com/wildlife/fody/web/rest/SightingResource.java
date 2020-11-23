package com.wildlife.fody.web.rest;

import com.wildlife.fody.domain.Sighting;
import com.wildlife.fody.repository.SightingRepository;
import com.wildlife.fody.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.wildlife.fody.domain.Sighting}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SightingResource {

    private final Logger log = LoggerFactory.getLogger(SightingResource.class);

    private static final String ENTITY_NAME = "sighting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SightingRepository sightingRepository;

    public SightingResource(SightingRepository sightingRepository) {
        this.sightingRepository = sightingRepository;
    }

    /**
     * {@code POST  /sightings} : Create a new sighting.
     *
     * @param sighting the sighting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sighting, or with status {@code 400 (Bad Request)} if the sighting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sightings")
    public ResponseEntity<Sighting> createSighting(@Valid @RequestBody Sighting sighting) throws URISyntaxException {
        log.debug("REST request to save Sighting : {}", sighting);
        if (sighting.getId() != null) {
            throw new BadRequestAlertException("A new sighting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sighting result = sightingRepository.save(sighting);
        return ResponseEntity.created(new URI("/api/sightings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sightings} : Updates an existing sighting.
     *
     * @param sighting the sighting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sighting,
     * or with status {@code 400 (Bad Request)} if the sighting is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sighting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sightings")
    public ResponseEntity<Sighting> updateSighting(@Valid @RequestBody Sighting sighting) throws URISyntaxException {
        log.debug("REST request to update Sighting : {}", sighting);
        if (sighting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sighting result = sightingRepository.save(sighting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sighting.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sightings} : get all the sightings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sightings in body.
     */
    @GetMapping("/sightings")
    public List<Sighting> getAllSightings() {
        log.debug("REST request to get all Sightings");
        return sightingRepository.findAll();
    }

    /**
     * {@code GET  /sightings/:id} : get the "id" sighting.
     *
     * @param id the id of the sighting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sighting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sightings/{id}")
    public ResponseEntity<Sighting> getSighting(@PathVariable Long id) {
        log.debug("REST request to get Sighting : {}", id);
        Optional<Sighting> sighting = sightingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sighting);
    }

    /**
     * {@code DELETE  /sightings/:id} : delete the "id" sighting.
     *
     * @param id the id of the sighting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sightings/{id}")
    public ResponseEntity<Void> deleteSighting(@PathVariable Long id) {
        log.debug("REST request to delete Sighting : {}", id);
        sightingRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
