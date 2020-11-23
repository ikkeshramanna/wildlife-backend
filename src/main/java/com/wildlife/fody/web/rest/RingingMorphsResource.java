package com.wildlife.fody.web.rest;

import com.wildlife.fody.domain.RingingMorphs;
import com.wildlife.fody.repository.RingingMorphsRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.wildlife.fody.domain.RingingMorphs}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RingingMorphsResource {

    private final Logger log = LoggerFactory.getLogger(RingingMorphsResource.class);

    private static final String ENTITY_NAME = "ringingMorphs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RingingMorphsRepository ringingMorphsRepository;

    public RingingMorphsResource(RingingMorphsRepository ringingMorphsRepository) {
        this.ringingMorphsRepository = ringingMorphsRepository;
    }

    /**
     * {@code POST  /ringing-morphs} : Create a new ringingMorphs.
     *
     * @param ringingMorphs the ringingMorphs to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ringingMorphs, or with status {@code 400 (Bad Request)} if the ringingMorphs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ringing-morphs")
    public ResponseEntity<RingingMorphs> createRingingMorphs(@Valid @RequestBody RingingMorphs ringingMorphs) throws URISyntaxException {
        log.debug("REST request to save RingingMorphs : {}", ringingMorphs);
        if (ringingMorphs.getId() != null) {
            throw new BadRequestAlertException("A new ringingMorphs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RingingMorphs result = ringingMorphsRepository.save(ringingMorphs);
        return ResponseEntity.created(new URI("/api/ringing-morphs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ringing-morphs} : Updates an existing ringingMorphs.
     *
     * @param ringingMorphs the ringingMorphs to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ringingMorphs,
     * or with status {@code 400 (Bad Request)} if the ringingMorphs is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ringingMorphs couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ringing-morphs")
    public ResponseEntity<RingingMorphs> updateRingingMorphs(@Valid @RequestBody RingingMorphs ringingMorphs) throws URISyntaxException {
        log.debug("REST request to update RingingMorphs : {}", ringingMorphs);
        if (ringingMorphs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RingingMorphs result = ringingMorphsRepository.save(ringingMorphs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ringingMorphs.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ringing-morphs} : get all the ringingMorphs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ringingMorphs in body.
     */
    @GetMapping("/ringing-morphs")
    public List<RingingMorphs> getAllRingingMorphs(@RequestParam(required = false) String filter) {
        if ("sighting-is-null".equals(filter)) {
            log.debug("REST request to get all RingingMorphss where sighting is null");
            return StreamSupport
                .stream(ringingMorphsRepository.findAll().spliterator(), false)
                .filter(ringingMorphs -> ringingMorphs.getSighting() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all RingingMorphs");
        return ringingMorphsRepository.findAll();
    }

    /**
     * {@code GET  /ringing-morphs/:id} : get the "id" ringingMorphs.
     *
     * @param id the id of the ringingMorphs to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ringingMorphs, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ringing-morphs/{id}")
    public ResponseEntity<RingingMorphs> getRingingMorphs(@PathVariable Long id) {
        log.debug("REST request to get RingingMorphs : {}", id);
        Optional<RingingMorphs> ringingMorphs = ringingMorphsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ringingMorphs);
    }

    /**
     * {@code DELETE  /ringing-morphs/:id} : delete the "id" ringingMorphs.
     *
     * @param id the id of the ringingMorphs to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ringing-morphs/{id}")
    public ResponseEntity<Void> deleteRingingMorphs(@PathVariable Long id) {
        log.debug("REST request to delete RingingMorphs : {}", id);
        ringingMorphsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
