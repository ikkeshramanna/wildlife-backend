package com.wildlife.fody.web.rest;

import com.wildlife.fody.domain.BirdsIdentified;
import com.wildlife.fody.repository.BirdsIdentifiedRepository;
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
 * REST controller for managing {@link com.wildlife.fody.domain.BirdsIdentified}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BirdsIdentifiedResource {

    private final Logger log = LoggerFactory.getLogger(BirdsIdentifiedResource.class);

    private static final String ENTITY_NAME = "birdsIdentified";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BirdsIdentifiedRepository birdsIdentifiedRepository;

    public BirdsIdentifiedResource(BirdsIdentifiedRepository birdsIdentifiedRepository) {
        this.birdsIdentifiedRepository = birdsIdentifiedRepository;
    }

    /**
     * {@code POST  /birds-identifieds} : Create a new birdsIdentified.
     *
     * @param birdsIdentified the birdsIdentified to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new birdsIdentified, or with status {@code 400 (Bad Request)} if the birdsIdentified has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/birds-identifieds")
    public ResponseEntity<BirdsIdentified> createBirdsIdentified(@Valid @RequestBody BirdsIdentified birdsIdentified) throws URISyntaxException {
        log.debug("REST request to save BirdsIdentified : {}", birdsIdentified);
        if (birdsIdentified.getId() != null) {
            throw new BadRequestAlertException("A new birdsIdentified cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BirdsIdentified result = birdsIdentifiedRepository.save(birdsIdentified);
        return ResponseEntity.created(new URI("/api/birds-identifieds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /birds-identifieds} : Updates an existing birdsIdentified.
     *
     * @param birdsIdentified the birdsIdentified to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated birdsIdentified,
     * or with status {@code 400 (Bad Request)} if the birdsIdentified is not valid,
     * or with status {@code 500 (Internal Server Error)} if the birdsIdentified couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/birds-identifieds")
    public ResponseEntity<BirdsIdentified> updateBirdsIdentified(@Valid @RequestBody BirdsIdentified birdsIdentified) throws URISyntaxException {
        log.debug("REST request to update BirdsIdentified : {}", birdsIdentified);
        if (birdsIdentified.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BirdsIdentified result = birdsIdentifiedRepository.save(birdsIdentified);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, birdsIdentified.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /birds-identifieds} : get all the birdsIdentifieds.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of birdsIdentifieds in body.
     */
    @GetMapping("/birds-identifieds")
    public List<BirdsIdentified> getAllBirdsIdentifieds(@RequestParam(required = false) String filter,@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("sighting-is-null".equals(filter)) {
            log.debug("REST request to get all BirdsIdentifieds where sighting is null");
            return StreamSupport
                .stream(birdsIdentifiedRepository.findAll().spliterator(), false)
                .filter(birdsIdentified -> birdsIdentified.getSighting() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all BirdsIdentifieds");
        return birdsIdentifiedRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /birds-identifieds/:id} : get the "id" birdsIdentified.
     *
     * @param id the id of the birdsIdentified to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the birdsIdentified, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/birds-identifieds/{id}")
    public ResponseEntity<BirdsIdentified> getBirdsIdentified(@PathVariable Long id) {
        log.debug("REST request to get BirdsIdentified : {}", id);
        Optional<BirdsIdentified> birdsIdentified = birdsIdentifiedRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(birdsIdentified);
    }

    /**
     * {@code DELETE  /birds-identifieds/:id} : delete the "id" birdsIdentified.
     *
     * @param id the id of the birdsIdentified to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/birds-identifieds/{id}")
    public ResponseEntity<Void> deleteBirdsIdentified(@PathVariable Long id) {
        log.debug("REST request to delete BirdsIdentified : {}", id);
        birdsIdentifiedRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
