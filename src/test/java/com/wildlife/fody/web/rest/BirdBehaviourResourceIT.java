package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.BirdBehaviour;
import com.wildlife.fody.repository.BirdBehaviourRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wildlife.fody.domain.enumeration.BirdBehaviourType;
/**
 * Integration tests for the {@link BirdBehaviourResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BirdBehaviourResourceIT {

    private static final BirdBehaviourType DEFAULT_BEHAVIOUR = BirdBehaviourType.FLEW_AWAY_FROM_SITE;
    private static final BirdBehaviourType UPDATED_BEHAVIOUR = BirdBehaviourType.IN_NEST_SITE;

    @Autowired
    private BirdBehaviourRepository birdBehaviourRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBirdBehaviourMockMvc;

    private BirdBehaviour birdBehaviour;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BirdBehaviour createEntity(EntityManager em) {
        BirdBehaviour birdBehaviour = new BirdBehaviour()
            .behaviour(DEFAULT_BEHAVIOUR);
        return birdBehaviour;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BirdBehaviour createUpdatedEntity(EntityManager em) {
        BirdBehaviour birdBehaviour = new BirdBehaviour()
            .behaviour(UPDATED_BEHAVIOUR);
        return birdBehaviour;
    }

    @BeforeEach
    public void initTest() {
        birdBehaviour = createEntity(em);
    }

    @Test
    @Transactional
    public void createBirdBehaviour() throws Exception {
        int databaseSizeBeforeCreate = birdBehaviourRepository.findAll().size();
        // Create the BirdBehaviour
        restBirdBehaviourMockMvc.perform(post("/api/bird-behaviours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(birdBehaviour)))
            .andExpect(status().isCreated());

        // Validate the BirdBehaviour in the database
        List<BirdBehaviour> birdBehaviourList = birdBehaviourRepository.findAll();
        assertThat(birdBehaviourList).hasSize(databaseSizeBeforeCreate + 1);
        BirdBehaviour testBirdBehaviour = birdBehaviourList.get(birdBehaviourList.size() - 1);
        assertThat(testBirdBehaviour.getBehaviour()).isEqualTo(DEFAULT_BEHAVIOUR);
    }

    @Test
    @Transactional
    public void createBirdBehaviourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = birdBehaviourRepository.findAll().size();

        // Create the BirdBehaviour with an existing ID
        birdBehaviour.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBirdBehaviourMockMvc.perform(post("/api/bird-behaviours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(birdBehaviour)))
            .andExpect(status().isBadRequest());

        // Validate the BirdBehaviour in the database
        List<BirdBehaviour> birdBehaviourList = birdBehaviourRepository.findAll();
        assertThat(birdBehaviourList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBehaviourIsRequired() throws Exception {
        int databaseSizeBeforeTest = birdBehaviourRepository.findAll().size();
        // set the field null
        birdBehaviour.setBehaviour(null);

        // Create the BirdBehaviour, which fails.


        restBirdBehaviourMockMvc.perform(post("/api/bird-behaviours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(birdBehaviour)))
            .andExpect(status().isBadRequest());

        List<BirdBehaviour> birdBehaviourList = birdBehaviourRepository.findAll();
        assertThat(birdBehaviourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBirdBehaviours() throws Exception {
        // Initialize the database
        birdBehaviourRepository.saveAndFlush(birdBehaviour);

        // Get all the birdBehaviourList
        restBirdBehaviourMockMvc.perform(get("/api/bird-behaviours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(birdBehaviour.getId().intValue())))
            .andExpect(jsonPath("$.[*].behaviour").value(hasItem(DEFAULT_BEHAVIOUR.toString())));
    }
    
    @Test
    @Transactional
    public void getBirdBehaviour() throws Exception {
        // Initialize the database
        birdBehaviourRepository.saveAndFlush(birdBehaviour);

        // Get the birdBehaviour
        restBirdBehaviourMockMvc.perform(get("/api/bird-behaviours/{id}", birdBehaviour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(birdBehaviour.getId().intValue()))
            .andExpect(jsonPath("$.behaviour").value(DEFAULT_BEHAVIOUR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBirdBehaviour() throws Exception {
        // Get the birdBehaviour
        restBirdBehaviourMockMvc.perform(get("/api/bird-behaviours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBirdBehaviour() throws Exception {
        // Initialize the database
        birdBehaviourRepository.saveAndFlush(birdBehaviour);

        int databaseSizeBeforeUpdate = birdBehaviourRepository.findAll().size();

        // Update the birdBehaviour
        BirdBehaviour updatedBirdBehaviour = birdBehaviourRepository.findById(birdBehaviour.getId()).get();
        // Disconnect from session so that the updates on updatedBirdBehaviour are not directly saved in db
        em.detach(updatedBirdBehaviour);
        updatedBirdBehaviour
            .behaviour(UPDATED_BEHAVIOUR);

        restBirdBehaviourMockMvc.perform(put("/api/bird-behaviours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBirdBehaviour)))
            .andExpect(status().isOk());

        // Validate the BirdBehaviour in the database
        List<BirdBehaviour> birdBehaviourList = birdBehaviourRepository.findAll();
        assertThat(birdBehaviourList).hasSize(databaseSizeBeforeUpdate);
        BirdBehaviour testBirdBehaviour = birdBehaviourList.get(birdBehaviourList.size() - 1);
        assertThat(testBirdBehaviour.getBehaviour()).isEqualTo(UPDATED_BEHAVIOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingBirdBehaviour() throws Exception {
        int databaseSizeBeforeUpdate = birdBehaviourRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBirdBehaviourMockMvc.perform(put("/api/bird-behaviours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(birdBehaviour)))
            .andExpect(status().isBadRequest());

        // Validate the BirdBehaviour in the database
        List<BirdBehaviour> birdBehaviourList = birdBehaviourRepository.findAll();
        assertThat(birdBehaviourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBirdBehaviour() throws Exception {
        // Initialize the database
        birdBehaviourRepository.saveAndFlush(birdBehaviour);

        int databaseSizeBeforeDelete = birdBehaviourRepository.findAll().size();

        // Delete the birdBehaviour
        restBirdBehaviourMockMvc.perform(delete("/api/bird-behaviours/{id}", birdBehaviour.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BirdBehaviour> birdBehaviourList = birdBehaviourRepository.findAll();
        assertThat(birdBehaviourList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
