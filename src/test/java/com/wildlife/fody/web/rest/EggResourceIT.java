package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.Egg;
import com.wildlife.fody.repository.EggRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wildlife.fody.domain.enumeration.EggStatusType;
/**
 * Integration tests for the {@link EggResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EggResourceIT {

    private static final String DEFAULT_EGG_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EGG_NUMBER = "BBBBBBBBBB";

    private static final EggStatusType DEFAULT_EGG_STATUS = EggStatusType.FERTILE;
    private static final EggStatusType UPDATED_EGG_STATUS = EggStatusType.INFERTILE;

    private static final LocalDate DEFAULT_EGG_LAY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EGG_LAY_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EggRepository eggRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEggMockMvc;

    private Egg egg;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Egg createEntity(EntityManager em) {
        Egg egg = new Egg()
            .eggNumber(DEFAULT_EGG_NUMBER)
            .eggStatus(DEFAULT_EGG_STATUS)
            .eggLayDate(DEFAULT_EGG_LAY_DATE);
        return egg;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Egg createUpdatedEntity(EntityManager em) {
        Egg egg = new Egg()
            .eggNumber(UPDATED_EGG_NUMBER)
            .eggStatus(UPDATED_EGG_STATUS)
            .eggLayDate(UPDATED_EGG_LAY_DATE);
        return egg;
    }

    @BeforeEach
    public void initTest() {
        egg = createEntity(em);
    }

    @Test
    @Transactional
    public void createEgg() throws Exception {
        int databaseSizeBeforeCreate = eggRepository.findAll().size();
        // Create the Egg
        restEggMockMvc.perform(post("/api/eggs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(egg)))
            .andExpect(status().isCreated());

        // Validate the Egg in the database
        List<Egg> eggList = eggRepository.findAll();
        assertThat(eggList).hasSize(databaseSizeBeforeCreate + 1);
        Egg testEgg = eggList.get(eggList.size() - 1);
        assertThat(testEgg.getEggNumber()).isEqualTo(DEFAULT_EGG_NUMBER);
        assertThat(testEgg.getEggStatus()).isEqualTo(DEFAULT_EGG_STATUS);
        assertThat(testEgg.getEggLayDate()).isEqualTo(DEFAULT_EGG_LAY_DATE);
    }

    @Test
    @Transactional
    public void createEggWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eggRepository.findAll().size();

        // Create the Egg with an existing ID
        egg.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEggMockMvc.perform(post("/api/eggs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(egg)))
            .andExpect(status().isBadRequest());

        // Validate the Egg in the database
        List<Egg> eggList = eggRepository.findAll();
        assertThat(eggList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEggs() throws Exception {
        // Initialize the database
        eggRepository.saveAndFlush(egg);

        // Get all the eggList
        restEggMockMvc.perform(get("/api/eggs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(egg.getId().intValue())))
            .andExpect(jsonPath("$.[*].eggNumber").value(hasItem(DEFAULT_EGG_NUMBER)))
            .andExpect(jsonPath("$.[*].eggStatus").value(hasItem(DEFAULT_EGG_STATUS.toString())))
            .andExpect(jsonPath("$.[*].eggLayDate").value(hasItem(DEFAULT_EGG_LAY_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEgg() throws Exception {
        // Initialize the database
        eggRepository.saveAndFlush(egg);

        // Get the egg
        restEggMockMvc.perform(get("/api/eggs/{id}", egg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(egg.getId().intValue()))
            .andExpect(jsonPath("$.eggNumber").value(DEFAULT_EGG_NUMBER))
            .andExpect(jsonPath("$.eggStatus").value(DEFAULT_EGG_STATUS.toString()))
            .andExpect(jsonPath("$.eggLayDate").value(DEFAULT_EGG_LAY_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEgg() throws Exception {
        // Get the egg
        restEggMockMvc.perform(get("/api/eggs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEgg() throws Exception {
        // Initialize the database
        eggRepository.saveAndFlush(egg);

        int databaseSizeBeforeUpdate = eggRepository.findAll().size();

        // Update the egg
        Egg updatedEgg = eggRepository.findById(egg.getId()).get();
        // Disconnect from session so that the updates on updatedEgg are not directly saved in db
        em.detach(updatedEgg);
        updatedEgg
            .eggNumber(UPDATED_EGG_NUMBER)
            .eggStatus(UPDATED_EGG_STATUS)
            .eggLayDate(UPDATED_EGG_LAY_DATE);

        restEggMockMvc.perform(put("/api/eggs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEgg)))
            .andExpect(status().isOk());

        // Validate the Egg in the database
        List<Egg> eggList = eggRepository.findAll();
        assertThat(eggList).hasSize(databaseSizeBeforeUpdate);
        Egg testEgg = eggList.get(eggList.size() - 1);
        assertThat(testEgg.getEggNumber()).isEqualTo(UPDATED_EGG_NUMBER);
        assertThat(testEgg.getEggStatus()).isEqualTo(UPDATED_EGG_STATUS);
        assertThat(testEgg.getEggLayDate()).isEqualTo(UPDATED_EGG_LAY_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEgg() throws Exception {
        int databaseSizeBeforeUpdate = eggRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEggMockMvc.perform(put("/api/eggs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(egg)))
            .andExpect(status().isBadRequest());

        // Validate the Egg in the database
        List<Egg> eggList = eggRepository.findAll();
        assertThat(eggList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEgg() throws Exception {
        // Initialize the database
        eggRepository.saveAndFlush(egg);

        int databaseSizeBeforeDelete = eggRepository.findAll().size();

        // Delete the egg
        restEggMockMvc.perform(delete("/api/eggs/{id}", egg.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Egg> eggList = eggRepository.findAll();
        assertThat(eggList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
