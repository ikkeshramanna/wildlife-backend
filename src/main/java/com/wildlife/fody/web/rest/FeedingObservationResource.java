package com.wildlife.fody.web.rest;

import com.wildlife.fody.domain.FeedingObservation;
import com.wildlife.fody.repository.FeedingObservationRepository;
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
 * REST controller for managing {@link com.wildlife.fody.domain.FeedingObservation}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FeedingObservationResource {

    private final Logger log = LoggerFactory.getLogger(FeedingObservationResource.class);

    private static final String ENTITY_NAME = "feedingObservation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedingObservationRepository feedingObservationRepository;

    public FeedingObservationResource(FeedingObservationRepository feedingObservationRepository) {
        this.feedingObservationRepository = feedingObservationRepository;
    }

    /**
     * {@code POST  /feeding-observations} : Create a new feedingObservation.
     *
     * @param feedingObservation the feedingObservation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedingObservation, or with status {@code 400 (Bad Request)} if the feedingObservation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feeding-observations")
    public ResponseEntity<FeedingObservation> createFeedingObservation(@Valid @RequestBody FeedingObservation feedingObservation) throws URISyntaxException {
        log.debug("REST request to save FeedingObservation : {}", feedingObservation);
        if (feedingObservation.getId() != null) {
            throw new BadRequestAlertException("A new feedingObservation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedingObservation result = feedingObservationRepository.save(feedingObservation);
        return ResponseEntity.created(new URI("/api/feeding-observations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feeding-observations} : Updates an existing feedingObservation.
     *
     * @param feedingObservation the feedingObservation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedingObservation,
     * or with status {@code 400 (Bad Request)} if the feedingObservation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedingObservation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feeding-observations")
    public ResponseEntity<FeedingObservation> updateFeedingObservation(@Valid @RequestBody FeedingObservation feedingObservation) throws URISyntaxException {
        log.debug("REST request to update FeedingObservation : {}", feedingObservation);
        if (feedingObservation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FeedingObservation result = feedingObservationRepository.save(feedingObservation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feedingObservation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /feeding-observations} : get all the feedingObservations.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedingObservations in body.
     */
    @GetMapping("/feeding-observations")
    public List<FeedingObservation> getAllFeedingObservations(@RequestParam(required = false) String filter) {
        if ("sighting-is-null".equals(filter)) {
            log.debug("REST request to get all FeedingObservations where sighting is null");
            return StreamSupport
                .stream(feedingObservationRepository.findAll().spliterator(), false)
                .filter(feedingObservation -> feedingObservation.getSighting() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all FeedingObservations");
        return feedingObservationRepository.findAll();
    }

    /**
     * {@code GET  /feeding-observations/:id} : get the "id" feedingObservation.
     *
     * @param id the id of the feedingObservation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedingObservation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feeding-observations/{id}")
    public ResponseEntity<FeedingObservation> getFeedingObservation(@PathVariable Long id) {
        log.debug("REST request to get FeedingObservation : {}", id);
        Optional<FeedingObservation> feedingObservation = feedingObservationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(feedingObservation);
    }

    /**
     * {@code DELETE  /feeding-observations/:id} : delete the "id" feedingObservation.
     *
     * @param id the id of the feedingObservation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feeding-observations/{id}")
    public ResponseEntity<Void> deleteFeedingObservation(@PathVariable Long id) {
        log.debug("REST request to delete FeedingObservation : {}", id);
        feedingObservationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
