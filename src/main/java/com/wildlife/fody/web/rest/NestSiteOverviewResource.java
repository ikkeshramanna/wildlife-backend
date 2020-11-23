package com.wildlife.fody.web.rest;

import com.wildlife.fody.domain.NestSiteOverview;
import com.wildlife.fody.repository.NestSiteOverviewRepository;
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
 * REST controller for managing {@link com.wildlife.fody.domain.NestSiteOverview}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NestSiteOverviewResource {

    private final Logger log = LoggerFactory.getLogger(NestSiteOverviewResource.class);

    private static final String ENTITY_NAME = "nestSiteOverview";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NestSiteOverviewRepository nestSiteOverviewRepository;

    public NestSiteOverviewResource(NestSiteOverviewRepository nestSiteOverviewRepository) {
        this.nestSiteOverviewRepository = nestSiteOverviewRepository;
    }

    /**
     * {@code POST  /nest-site-overviews} : Create a new nestSiteOverview.
     *
     * @param nestSiteOverview the nestSiteOverview to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nestSiteOverview, or with status {@code 400 (Bad Request)} if the nestSiteOverview has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nest-site-overviews")
    public ResponseEntity<NestSiteOverview> createNestSiteOverview(@Valid @RequestBody NestSiteOverview nestSiteOverview) throws URISyntaxException {
        log.debug("REST request to save NestSiteOverview : {}", nestSiteOverview);
        if (nestSiteOverview.getId() != null) {
            throw new BadRequestAlertException("A new nestSiteOverview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NestSiteOverview result = nestSiteOverviewRepository.save(nestSiteOverview);
        return ResponseEntity.created(new URI("/api/nest-site-overviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nest-site-overviews} : Updates an existing nestSiteOverview.
     *
     * @param nestSiteOverview the nestSiteOverview to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nestSiteOverview,
     * or with status {@code 400 (Bad Request)} if the nestSiteOverview is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nestSiteOverview couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nest-site-overviews")
    public ResponseEntity<NestSiteOverview> updateNestSiteOverview(@Valid @RequestBody NestSiteOverview nestSiteOverview) throws URISyntaxException {
        log.debug("REST request to update NestSiteOverview : {}", nestSiteOverview);
        if (nestSiteOverview.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NestSiteOverview result = nestSiteOverviewRepository.save(nestSiteOverview);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nestSiteOverview.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nest-site-overviews} : get all the nestSiteOverviews.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nestSiteOverviews in body.
     */
    @GetMapping("/nest-site-overviews")
    public List<NestSiteOverview> getAllNestSiteOverviews(@RequestParam(required = false) String filter) {
        if ("sighting-is-null".equals(filter)) {
            log.debug("REST request to get all NestSiteOverviews where sighting is null");
            return StreamSupport
                .stream(nestSiteOverviewRepository.findAll().spliterator(), false)
                .filter(nestSiteOverview -> nestSiteOverview.getSighting() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all NestSiteOverviews");
        return nestSiteOverviewRepository.findAll();
    }

    /**
     * {@code GET  /nest-site-overviews/:id} : get the "id" nestSiteOverview.
     *
     * @param id the id of the nestSiteOverview to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nestSiteOverview, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nest-site-overviews/{id}")
    public ResponseEntity<NestSiteOverview> getNestSiteOverview(@PathVariable Long id) {
        log.debug("REST request to get NestSiteOverview : {}", id);
        Optional<NestSiteOverview> nestSiteOverview = nestSiteOverviewRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nestSiteOverview);
    }

    /**
     * {@code DELETE  /nest-site-overviews/:id} : delete the "id" nestSiteOverview.
     *
     * @param id the id of the nestSiteOverview to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nest-site-overviews/{id}")
    public ResponseEntity<Void> deleteNestSiteOverview(@PathVariable Long id) {
        log.debug("REST request to delete NestSiteOverview : {}", id);
        nestSiteOverviewRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
