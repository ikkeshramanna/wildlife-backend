package com.wildlife.fody.web.rest;

import com.wildlife.fody.domain.CompetitorImpactOnMk;
import com.wildlife.fody.repository.CompetitorImpactOnMkRepository;
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
 * REST controller for managing {@link com.wildlife.fody.domain.CompetitorImpactOnMk}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CompetitorImpactOnMkResource {

    private final Logger log = LoggerFactory.getLogger(CompetitorImpactOnMkResource.class);

    private static final String ENTITY_NAME = "competitorImpactOnMk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetitorImpactOnMkRepository competitorImpactOnMkRepository;

    public CompetitorImpactOnMkResource(CompetitorImpactOnMkRepository competitorImpactOnMkRepository) {
        this.competitorImpactOnMkRepository = competitorImpactOnMkRepository;
    }

    /**
     * {@code POST  /competitor-impact-on-mks} : Create a new competitorImpactOnMk.
     *
     * @param competitorImpactOnMk the competitorImpactOnMk to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competitorImpactOnMk, or with status {@code 400 (Bad Request)} if the competitorImpactOnMk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competitor-impact-on-mks")
    public ResponseEntity<CompetitorImpactOnMk> createCompetitorImpactOnMk(@Valid @RequestBody CompetitorImpactOnMk competitorImpactOnMk) throws URISyntaxException {
        log.debug("REST request to save CompetitorImpactOnMk : {}", competitorImpactOnMk);
        if (competitorImpactOnMk.getId() != null) {
            throw new BadRequestAlertException("A new competitorImpactOnMk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetitorImpactOnMk result = competitorImpactOnMkRepository.save(competitorImpactOnMk);
        return ResponseEntity.created(new URI("/api/competitor-impact-on-mks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competitor-impact-on-mks} : Updates an existing competitorImpactOnMk.
     *
     * @param competitorImpactOnMk the competitorImpactOnMk to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competitorImpactOnMk,
     * or with status {@code 400 (Bad Request)} if the competitorImpactOnMk is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competitorImpactOnMk couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competitor-impact-on-mks")
    public ResponseEntity<CompetitorImpactOnMk> updateCompetitorImpactOnMk(@Valid @RequestBody CompetitorImpactOnMk competitorImpactOnMk) throws URISyntaxException {
        log.debug("REST request to update CompetitorImpactOnMk : {}", competitorImpactOnMk);
        if (competitorImpactOnMk.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompetitorImpactOnMk result = competitorImpactOnMkRepository.save(competitorImpactOnMk);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competitorImpactOnMk.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competitor-impact-on-mks} : get all the competitorImpactOnMks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competitorImpactOnMks in body.
     */
    @GetMapping("/competitor-impact-on-mks")
    public List<CompetitorImpactOnMk> getAllCompetitorImpactOnMks() {
        log.debug("REST request to get all CompetitorImpactOnMks");
        return competitorImpactOnMkRepository.findAll();
    }

    /**
     * {@code GET  /competitor-impact-on-mks/:id} : get the "id" competitorImpactOnMk.
     *
     * @param id the id of the competitorImpactOnMk to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competitorImpactOnMk, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competitor-impact-on-mks/{id}")
    public ResponseEntity<CompetitorImpactOnMk> getCompetitorImpactOnMk(@PathVariable Long id) {
        log.debug("REST request to get CompetitorImpactOnMk : {}", id);
        Optional<CompetitorImpactOnMk> competitorImpactOnMk = competitorImpactOnMkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(competitorImpactOnMk);
    }

    /**
     * {@code DELETE  /competitor-impact-on-mks/:id} : delete the "id" competitorImpactOnMk.
     *
     * @param id the id of the competitorImpactOnMk to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competitor-impact-on-mks/{id}")
    public ResponseEntity<Void> deleteCompetitorImpactOnMk(@PathVariable Long id) {
        log.debug("REST request to delete CompetitorImpactOnMk : {}", id);
        competitorImpactOnMkRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
