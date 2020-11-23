package com.wildlife.fody.web.rest;

import com.wildlife.fody.domain.SpeciesCategory;
import com.wildlife.fody.repository.SpeciesCategoryRepository;
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
 * REST controller for managing {@link com.wildlife.fody.domain.SpeciesCategory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SpeciesCategoryResource {

    private final Logger log = LoggerFactory.getLogger(SpeciesCategoryResource.class);

    private static final String ENTITY_NAME = "speciesCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpeciesCategoryRepository speciesCategoryRepository;

    public SpeciesCategoryResource(SpeciesCategoryRepository speciesCategoryRepository) {
        this.speciesCategoryRepository = speciesCategoryRepository;
    }

    /**
     * {@code POST  /species-categories} : Create a new speciesCategory.
     *
     * @param speciesCategory the speciesCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new speciesCategory, or with status {@code 400 (Bad Request)} if the speciesCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/species-categories")
    public ResponseEntity<SpeciesCategory> createSpeciesCategory(@Valid @RequestBody SpeciesCategory speciesCategory) throws URISyntaxException {
        log.debug("REST request to save SpeciesCategory : {}", speciesCategory);
        if (speciesCategory.getId() != null) {
            throw new BadRequestAlertException("A new speciesCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpeciesCategory result = speciesCategoryRepository.save(speciesCategory);
        return ResponseEntity.created(new URI("/api/species-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /species-categories} : Updates an existing speciesCategory.
     *
     * @param speciesCategory the speciesCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated speciesCategory,
     * or with status {@code 400 (Bad Request)} if the speciesCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the speciesCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/species-categories")
    public ResponseEntity<SpeciesCategory> updateSpeciesCategory(@Valid @RequestBody SpeciesCategory speciesCategory) throws URISyntaxException {
        log.debug("REST request to update SpeciesCategory : {}", speciesCategory);
        if (speciesCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SpeciesCategory result = speciesCategoryRepository.save(speciesCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, speciesCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /species-categories} : get all the speciesCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of speciesCategories in body.
     */
    @GetMapping("/species-categories")
    public List<SpeciesCategory> getAllSpeciesCategories() {
        log.debug("REST request to get all SpeciesCategories");
        return speciesCategoryRepository.findAll();
    }

    /**
     * {@code GET  /species-categories/:id} : get the "id" speciesCategory.
     *
     * @param id the id of the speciesCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the speciesCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/species-categories/{id}")
    public ResponseEntity<SpeciesCategory> getSpeciesCategory(@PathVariable Long id) {
        log.debug("REST request to get SpeciesCategory : {}", id);
        Optional<SpeciesCategory> speciesCategory = speciesCategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(speciesCategory);
    }

    /**
     * {@code DELETE  /species-categories/:id} : delete the "id" speciesCategory.
     *
     * @param id the id of the speciesCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/species-categories/{id}")
    public ResponseEntity<Void> deleteSpeciesCategory(@PathVariable Long id) {
        log.debug("REST request to delete SpeciesCategory : {}", id);
        speciesCategoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
