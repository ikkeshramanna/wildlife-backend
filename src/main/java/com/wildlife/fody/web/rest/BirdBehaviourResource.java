package com.wildlife.fody.web.rest;

import com.wildlife.fody.domain.BirdBehaviour;
import com.wildlife.fody.repository.BirdBehaviourRepository;
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
 * REST controller for managing {@link com.wildlife.fody.domain.BirdBehaviour}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BirdBehaviourResource {

    private final Logger log = LoggerFactory.getLogger(BirdBehaviourResource.class);

    private static final String ENTITY_NAME = "birdBehaviour";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BirdBehaviourRepository birdBehaviourRepository;

    public BirdBehaviourResource(BirdBehaviourRepository birdBehaviourRepository) {
        this.birdBehaviourRepository = birdBehaviourRepository;
    }

    /**
     * {@code POST  /bird-behaviours} : Create a new birdBehaviour.
     *
     * @param birdBehaviour the birdBehaviour to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new birdBehaviour, or with status {@code 400 (Bad Request)} if the birdBehaviour has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bird-behaviours")
    public ResponseEntity<BirdBehaviour> createBirdBehaviour(@Valid @RequestBody BirdBehaviour birdBehaviour) throws URISyntaxException {
        log.debug("REST request to save BirdBehaviour : {}", birdBehaviour);
        if (birdBehaviour.getId() != null) {
            throw new BadRequestAlertException("A new birdBehaviour cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BirdBehaviour result = birdBehaviourRepository.save(birdBehaviour);
        return ResponseEntity.created(new URI("/api/bird-behaviours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bird-behaviours} : Updates an existing birdBehaviour.
     *
     * @param birdBehaviour the birdBehaviour to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated birdBehaviour,
     * or with status {@code 400 (Bad Request)} if the birdBehaviour is not valid,
     * or with status {@code 500 (Internal Server Error)} if the birdBehaviour couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bird-behaviours")
    public ResponseEntity<BirdBehaviour> updateBirdBehaviour(@Valid @RequestBody BirdBehaviour birdBehaviour) throws URISyntaxException {
        log.debug("REST request to update BirdBehaviour : {}", birdBehaviour);
        if (birdBehaviour.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BirdBehaviour result = birdBehaviourRepository.save(birdBehaviour);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, birdBehaviour.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bird-behaviours} : get all the birdBehaviours.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of birdBehaviours in body.
     */
    @GetMapping("/bird-behaviours")
    public List<BirdBehaviour> getAllBirdBehaviours() {
        log.debug("REST request to get all BirdBehaviours");
        return birdBehaviourRepository.findAll();
    }

    /**
     * {@code GET  /bird-behaviours/:id} : get the "id" birdBehaviour.
     *
     * @param id the id of the birdBehaviour to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the birdBehaviour, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bird-behaviours/{id}")
    public ResponseEntity<BirdBehaviour> getBirdBehaviour(@PathVariable Long id) {
        log.debug("REST request to get BirdBehaviour : {}", id);
        Optional<BirdBehaviour> birdBehaviour = birdBehaviourRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(birdBehaviour);
    }

    /**
     * {@code DELETE  /bird-behaviours/:id} : delete the "id" birdBehaviour.
     *
     * @param id the id of the birdBehaviour to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bird-behaviours/{id}")
    public ResponseEntity<Void> deleteBirdBehaviour(@PathVariable Long id) {
        log.debug("REST request to delete BirdBehaviour : {}", id);
        birdBehaviourRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
