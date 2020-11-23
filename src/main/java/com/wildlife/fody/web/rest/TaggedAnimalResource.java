package com.wildlife.fody.web.rest;

import com.wildlife.fody.domain.TaggedAnimal;
import com.wildlife.fody.repository.TaggedAnimalRepository;
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
 * REST controller for managing {@link com.wildlife.fody.domain.TaggedAnimal}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TaggedAnimalResource {

    private final Logger log = LoggerFactory.getLogger(TaggedAnimalResource.class);

    private static final String ENTITY_NAME = "taggedAnimal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaggedAnimalRepository taggedAnimalRepository;

    public TaggedAnimalResource(TaggedAnimalRepository taggedAnimalRepository) {
        this.taggedAnimalRepository = taggedAnimalRepository;
    }

    /**
     * {@code POST  /tagged-animals} : Create a new taggedAnimal.
     *
     * @param taggedAnimal the taggedAnimal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taggedAnimal, or with status {@code 400 (Bad Request)} if the taggedAnimal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tagged-animals")
    public ResponseEntity<TaggedAnimal> createTaggedAnimal(@RequestBody TaggedAnimal taggedAnimal) throws URISyntaxException {
        log.debug("REST request to save TaggedAnimal : {}", taggedAnimal);
        if (taggedAnimal.getId() != null) {
            throw new BadRequestAlertException("A new taggedAnimal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaggedAnimal result = taggedAnimalRepository.save(taggedAnimal);
        return ResponseEntity.created(new URI("/api/tagged-animals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tagged-animals} : Updates an existing taggedAnimal.
     *
     * @param taggedAnimal the taggedAnimal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taggedAnimal,
     * or with status {@code 400 (Bad Request)} if the taggedAnimal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taggedAnimal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tagged-animals")
    public ResponseEntity<TaggedAnimal> updateTaggedAnimal(@RequestBody TaggedAnimal taggedAnimal) throws URISyntaxException {
        log.debug("REST request to update TaggedAnimal : {}", taggedAnimal);
        if (taggedAnimal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaggedAnimal result = taggedAnimalRepository.save(taggedAnimal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taggedAnimal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tagged-animals} : get all the taggedAnimals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taggedAnimals in body.
     */
    @GetMapping("/tagged-animals")
    public List<TaggedAnimal> getAllTaggedAnimals() {
        log.debug("REST request to get all TaggedAnimals");
        return taggedAnimalRepository.findAll();
    }

    /**
     * {@code GET  /tagged-animals/:id} : get the "id" taggedAnimal.
     *
     * @param id the id of the taggedAnimal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taggedAnimal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tagged-animals/{id}")
    public ResponseEntity<TaggedAnimal> getTaggedAnimal(@PathVariable Long id) {
        log.debug("REST request to get TaggedAnimal : {}", id);
        Optional<TaggedAnimal> taggedAnimal = taggedAnimalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taggedAnimal);
    }

    /**
     * {@code DELETE  /tagged-animals/:id} : delete the "id" taggedAnimal.
     *
     * @param id the id of the taggedAnimal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tagged-animals/{id}")
    public ResponseEntity<Void> deleteTaggedAnimal(@PathVariable Long id) {
        log.debug("REST request to delete TaggedAnimal : {}", id);
        taggedAnimalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
