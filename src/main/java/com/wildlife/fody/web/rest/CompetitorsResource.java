package com.wildlife.fody.web.rest;

import com.wildlife.fody.domain.Competitors;
import com.wildlife.fody.repository.CompetitorsRepository;
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
 * REST controller for managing {@link com.wildlife.fody.domain.Competitors}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CompetitorsResource {

    private final Logger log = LoggerFactory.getLogger(CompetitorsResource.class);

    private static final String ENTITY_NAME = "competitors";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitorsRepository competitorsRepository;

    public CompetitorsResource(CompetitorsRepository competitorsRepository) {
        this.competitorsRepository = competitorsRepository;
    }

    /**
     * {@code POST  /competitors} : Create a new competitors.
     *
     * @param competitors the competitors to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitors, or with status {@code 400 (Bad Request)} if the competitors has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitors")
    public ResponseEntity<Competitors> createCompetitors(@Valid @RequestBody Competitors competitors) throws URISyntaxException {
        log.debug("REST request to save Competitors : {}", competitors);
        if (competitors.getId() != null) {
            throw new BadRequestAlertException("A new competitors cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Competitors result = competitorsRepository.save(competitors);
        return ResponseEntity.created(new URI("/api/competitors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competitors} : Updates an existing competitors.
     *
     * @param competitors the competitors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitors,
     * or with status {@code 400 (Bad Request)} if the competitors is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competitors")
    public ResponseEntity<Competitors> updateCompetitors(@Valid @RequestBody Competitors competitors) throws URISyntaxException {
        log.debug("REST request to update Competitors : {}", competitors);
        if (competitors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Competitors result = competitorsRepository.save(competitors);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitors.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competitors} : get all the competitors.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitors in body.
     */
    @GetMapping("/competitors")
    public List<Competitors> getAllCompetitors(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Competitors");
        return competitorsRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /competitors/:id} : get the "id" competitors.
     *
     * @param id the id of the competitors to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitors, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitors/{id}")
    public ResponseEntity<Competitors> getCompetitors(@PathVariable Long id) {
        log.debug("REST request to get Competitors : {}", id);
        Optional<Competitors> competitors = competitorsRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(competitors);
    }

    /**
     * {@code DELETE  /competitors/:id} : delete the "id" competitors.
     *
     * @param id the id of the competitors to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competitors/{id}")
    public ResponseEntity<Void> deleteCompetitors(@PathVariable Long id) {
        log.debug("REST request to delete Competitors : {}", id);
        competitorsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
