package com.wildlife.fody.web.rest;

import com.wildlife.fody.domain.CompetitorAction;
import com.wildlife.fody.repository.CompetitorActionRepository;
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
 * REST controller for managing {@link com.wildlife.fody.domain.CompetitorAction}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CompetitorActionResource {

    private final Logger log = LoggerFactory.getLogger(CompetitorActionResource.class);

    private static final String ENTITY_NAME = "competitorAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitorActionRepository competitorActionRepository;

    public CompetitorActionResource(CompetitorActionRepository competitorActionRepository) {
        this.competitorActionRepository = competitorActionRepository;
    }

    /**
     * {@code POST  /competitor-actions} : Create a new competitorAction.
     *
     * @param competitorAction the competitorAction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitorAction, or with status {@code 400 (Bad Request)} if the competitorAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitor-actions")
    public ResponseEntity<CompetitorAction> createCompetitorAction(@Valid @RequestBody CompetitorAction competitorAction) throws URISyntaxException {
        log.debug("REST request to save CompetitorAction : {}", competitorAction);
        if (competitorAction.getId() != null) {
            throw new BadRequestAlertException("A new competitorAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitorAction result = competitorActionRepository.save(competitorAction);
        return ResponseEntity.created(new URI("/api/competitor-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competitor-actions} : Updates an existing competitorAction.
     *
     * @param competitorAction the competitorAction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitorAction,
     * or with status {@code 400 (Bad Request)} if the competitorAction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitorAction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competitor-actions")
    public ResponseEntity<CompetitorAction> updateCompetitorAction(@Valid @RequestBody CompetitorAction competitorAction) throws URISyntaxException {
        log.debug("REST request to update CompetitorAction : {}", competitorAction);
        if (competitorAction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompetitorAction result = competitorActionRepository.save(competitorAction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitorAction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competitor-actions} : get all the competitorActions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitorActions in body.
     */
    @GetMapping("/competitor-actions")
    public List<CompetitorAction> getAllCompetitorActions() {
        log.debug("REST request to get all CompetitorActions");
        return competitorActionRepository.findAll();
    }

    /**
     * {@code GET  /competitor-actions/:id} : get the "id" competitorAction.
     *
     * @param id the id of the competitorAction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitorAction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitor-actions/{id}")
    public ResponseEntity<CompetitorAction> getCompetitorAction(@PathVariable Long id) {
        log.debug("REST request to get CompetitorAction : {}", id);
        Optional<CompetitorAction> competitorAction = competitorActionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(competitorAction);
    }

    /**
     * {@code DELETE  /competitor-actions/:id} : delete the "id" competitorAction.
     *
     * @param id the id of the competitorAction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competitor-actions/{id}")
    public ResponseEntity<Void> deleteCompetitorAction(@PathVariable Long id) {
        log.debug("REST request to delete CompetitorAction : {}", id);
        competitorActionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
