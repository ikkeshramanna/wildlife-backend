package com.wildlife.fody.web.rest;

import com.wildlife.fody.domain.Chick;
import com.wildlife.fody.repository.ChickRepository;
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
 * REST controller for managing {@link com.wildlife.fody.domain.Chick}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ChickResource {

    private final Logger log = LoggerFactory.getLogger(ChickResource.class);

    private static final String ENTITY_NAME = "chick";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChickRepository chickRepository;

    public ChickResource(ChickRepository chickRepository) {
        this.chickRepository = chickRepository;
    }

    /**
     * {@code POST  /chicks} : Create a new chick.
     *
     * @param chick the chick to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chick, or with status {@code 400 (Bad Request)} if the chick has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chicks")
    public ResponseEntity<Chick> createChick(@Valid @RequestBody Chick chick) throws URISyntaxException {
        log.debug("REST request to save Chick : {}", chick);
        if (chick.getId() != null) {
            throw new BadRequestAlertException("A new chick cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Chick result = chickRepository.save(chick);
        return ResponseEntity.created(new URI("/api/chicks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chicks} : Updates an existing chick.
     *
     * @param chick the chick to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chick,
     * or with status {@code 400 (Bad Request)} if the chick is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chick couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chicks")
    public ResponseEntity<Chick> updateChick(@Valid @RequestBody Chick chick) throws URISyntaxException {
        log.debug("REST request to update Chick : {}", chick);
        if (chick.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Chick result = chickRepository.save(chick);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chick.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /chicks} : get all the chicks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chicks in body.
     */
    @GetMapping("/chicks")
    public List<Chick> getAllChicks() {
        log.debug("REST request to get all Chicks");
        return chickRepository.findAll();
    }

    /**
     * {@code GET  /chicks/:id} : get the "id" chick.
     *
     * @param id the id of the chick to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chick, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chicks/{id}")
    public ResponseEntity<Chick> getChick(@PathVariable Long id) {
        log.debug("REST request to get Chick : {}", id);
        Optional<Chick> chick = chickRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(chick);
    }

    /**
     * {@code DELETE  /chicks/:id} : delete the "id" chick.
     *
     * @param id the id of the chick to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chicks/{id}")
    public ResponseEntity<Void> deleteChick(@PathVariable Long id) {
        log.debug("REST request to delete Chick : {}", id);
        chickRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
