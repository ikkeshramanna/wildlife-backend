package com.wildlife.fody.web.rest;

import com.wildlife.fody.domain.Egg;
import com.wildlife.fody.repository.EggRepository;
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
 * REST controller for managing {@link com.wildlife.fody.domain.Egg}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EggResource {

    private final Logger log = LoggerFactory.getLogger(EggResource.class);

    private static final String ENTITY_NAME = "egg";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EggRepository eggRepository;

    public EggResource(EggRepository eggRepository) {
        this.eggRepository = eggRepository;
    }

    /**
     * {@code POST  /eggs} : Create a new egg.
     *
     * @param egg the egg to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new egg, or with status {@code 400 (Bad Request)} if the egg has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eggs")
    public ResponseEntity<Egg> createEgg(@RequestBody Egg egg) throws URISyntaxException {
        log.debug("REST request to save Egg : {}", egg);
        if (egg.getId() != null) {
            throw new BadRequestAlertException("A new egg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Egg result = eggRepository.save(egg);
        return ResponseEntity.created(new URI("/api/eggs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eggs} : Updates an existing egg.
     *
     * @param egg the egg to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated egg,
     * or with status {@code 400 (Bad Request)} if the egg is not valid,
     * or with status {@code 500 (Internal Server Error)} if the egg couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eggs")
    public ResponseEntity<Egg> updateEgg(@RequestBody Egg egg) throws URISyntaxException {
        log.debug("REST request to update Egg : {}", egg);
        if (egg.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Egg result = eggRepository.save(egg);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, egg.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /eggs} : get all the eggs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eggs in body.
     */
    @GetMapping("/eggs")
    public List<Egg> getAllEggs() {
        log.debug("REST request to get all Eggs");
        return eggRepository.findAll();
    }

    /**
     * {@code GET  /eggs/:id} : get the "id" egg.
     *
     * @param id the id of the egg to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the egg, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eggs/{id}")
    public ResponseEntity<Egg> getEgg(@PathVariable Long id) {
        log.debug("REST request to get Egg : {}", id);
        Optional<Egg> egg = eggRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(egg);
    }

    /**
     * {@code DELETE  /eggs/:id} : delete the "id" egg.
     *
     * @param id the id of the egg to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eggs/{id}")
    public ResponseEntity<Void> deleteEgg(@PathVariable Long id) {
        log.debug("REST request to delete Egg : {}", id);
        eggRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
