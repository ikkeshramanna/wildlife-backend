package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.EggsAndChick;
import com.wildlife.fody.repository.EggsAndChickRepository;

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

/**
 * Integration tests for the {@link EggsAndChickResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EggsAndChickResourceIT {

    private static final String DEFAULT_CLUTCH_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CLUTCH_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EGGS_PRESENT = false;
    private static final Boolean UPDATED_EGGS_PRESENT = true;

    private static final Boolean DEFAULT_CHICKS_PRESENT = false;
    private static final Boolean UPDATED_CHICKS_PRESENT = true;

    private static final String DEFAULT_PHOTO_TAKEN = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_TAKEN = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private EggsAndChickRepository eggsAndChickRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEggsAndChickMockMvc;

    private EggsAndChick eggsAndChick;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EggsAndChick createEntity(EntityManager em) {
        EggsAndChick eggsAndChick = new EggsAndChick()
            .clutchNumber(DEFAULT_CLUTCH_NUMBER)
            .eggsPresent(DEFAULT_EGGS_PRESENT)
            .chicksPresent(DEFAULT_CHICKS_PRESENT)
            .photoTaken(DEFAULT_PHOTO_TAKEN)
            .comments(DEFAULT_COMMENTS);
        return eggsAndChick;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EggsAndChick createUpdatedEntity(EntityManager em) {
        EggsAndChick eggsAndChick = new EggsAndChick()
            .clutchNumber(UPDATED_CLUTCH_NUMBER)
            .eggsPresent(UPDATED_EGGS_PRESENT)
            .chicksPresent(UPDATED_CHICKS_PRESENT)
            .photoTaken(UPDATED_PHOTO_TAKEN)
            .comments(UPDATED_COMMENTS);
        return eggsAndChick;
    }

    @BeforeEach
    public void initTest() {
        eggsAndChick = createEntity(em);
    }

    @Test
    @Transactional
    public void createEggsAndChick() throws Exception {
        int databaseSizeBeforeCreate = eggsAndChickRepository.findAll().size();
        // Create the EggsAndChick
        restEggsAndChickMockMvc.perform(post("/api/eggs-and-chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eggsAndChick)))
            .andExpect(status().isCreated());

        // Validate the EggsAndChick in the database
        List<EggsAndChick> eggsAndChickList = eggsAndChickRepository.findAll();
        assertThat(eggsAndChickList).hasSize(databaseSizeBeforeCreate + 1);
        EggsAndChick testEggsAndChick = eggsAndChickList.get(eggsAndChickList.size() - 1);
        assertThat(testEggsAndChick.getClutchNumber()).isEqualTo(DEFAULT_CLUTCH_NUMBER);
        assertThat(testEggsAndChick.isEggsPresent()).isEqualTo(DEFAULT_EGGS_PRESENT);
        assertThat(testEggsAndChick.isChicksPresent()).isEqualTo(DEFAULT_CHICKS_PRESENT);
        assertThat(testEggsAndChick.getPhotoTaken()).isEqualTo(DEFAULT_PHOTO_TAKEN);
        assertThat(testEggsAndChick.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createEggsAndChickWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eggsAndChickRepository.findAll().size();

        // Create the EggsAndChick with an existing ID
        eggsAndChick.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEggsAndChickMockMvc.perform(post("/api/eggs-and-chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eggsAndChick)))
            .andExpect(status().isBadRequest());

        // Validate the EggsAndChick in the database
        List<EggsAndChick> eggsAndChickList = eggsAndChickRepository.findAll();
        assertThat(eggsAndChickList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEggsPresentIsRequired() throws Exception {
        int databaseSizeBeforeTest = eggsAndChickRepository.findAll().size();
        // set the field null
        eggsAndChick.setEggsPresent(null);

        // Create the EggsAndChick, which fails.


        restEggsAndChickMockMvc.perform(post("/api/eggs-and-chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eggsAndChick)))
            .andExpect(status().isBadRequest());

        List<EggsAndChick> eggsAndChickList = eggsAndChickRepository.findAll();
        assertThat(eggsAndChickList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChicksPresentIsRequired() throws Exception {
        int databaseSizeBeforeTest = eggsAndChickRepository.findAll().size();
        // set the field null
        eggsAndChick.setChicksPresent(null);

        // Create the EggsAndChick, which fails.


        restEggsAndChickMockMvc.perform(post("/api/eggs-and-chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eggsAndChick)))
            .andExpect(status().isBadRequest());

        List<EggsAndChick> eggsAndChickList = eggsAndChickRepository.findAll();
        assertThat(eggsAndChickList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEggsAndChicks() throws Exception {
        // Initialize the database
        eggsAndChickRepository.saveAndFlush(eggsAndChick);

        // Get all the eggsAndChickList
        restEggsAndChickMockMvc.perform(get("/api/eggs-and-chicks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eggsAndChick.getId().intValue())))
            .andExpect(jsonPath("$.[*].clutchNumber").value(hasItem(DEFAULT_CLUTCH_NUMBER)))
            .andExpect(jsonPath("$.[*].eggsPresent").value(hasItem(DEFAULT_EGGS_PRESENT.booleanValue())))
            .andExpect(jsonPath("$.[*].chicksPresent").value(hasItem(DEFAULT_CHICKS_PRESENT.booleanValue())))
            .andExpect(jsonPath("$.[*].photoTaken").value(hasItem(DEFAULT_PHOTO_TAKEN)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getEggsAndChick() throws Exception {
        // Initialize the database
        eggsAndChickRepository.saveAndFlush(eggsAndChick);

        // Get the eggsAndChick
        restEggsAndChickMockMvc.perform(get("/api/eggs-and-chicks/{id}", eggsAndChick.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eggsAndChick.getId().intValue()))
            .andExpect(jsonPath("$.clutchNumber").value(DEFAULT_CLUTCH_NUMBER))
            .andExpect(jsonPath("$.eggsPresent").value(DEFAULT_EGGS_PRESENT.booleanValue()))
            .andExpect(jsonPath("$.chicksPresent").value(DEFAULT_CHICKS_PRESENT.booleanValue()))
            .andExpect(jsonPath("$.photoTaken").value(DEFAULT_PHOTO_TAKEN))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }
    @Test
    @Transactional
    public void getNonExistingEggsAndChick() throws Exception {
        // Get the eggsAndChick
        restEggsAndChickMockMvc.perform(get("/api/eggs-and-chicks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEggsAndChick() throws Exception {
        // Initialize the database
        eggsAndChickRepository.saveAndFlush(eggsAndChick);

        int databaseSizeBeforeUpdate = eggsAndChickRepository.findAll().size();

        // Update the eggsAndChick
        EggsAndChick updatedEggsAndChick = eggsAndChickRepository.findById(eggsAndChick.getId()).get();
        // Disconnect from session so that the updates on updatedEggsAndChick are not directly saved in db
        em.detach(updatedEggsAndChick);
        updatedEggsAndChick
            .clutchNumber(UPDATED_CLUTCH_NUMBER)
            .eggsPresent(UPDATED_EGGS_PRESENT)
            .chicksPresent(UPDATED_CHICKS_PRESENT)
            .photoTaken(UPDATED_PHOTO_TAKEN)
            .comments(UPDATED_COMMENTS);

        restEggsAndChickMockMvc.perform(put("/api/eggs-and-chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEggsAndChick)))
            .andExpect(status().isOk());

        // Validate the EggsAndChick in the database
        List<EggsAndChick> eggsAndChickList = eggsAndChickRepository.findAll();
        assertThat(eggsAndChickList).hasSize(databaseSizeBeforeUpdate);
        EggsAndChick testEggsAndChick = eggsAndChickList.get(eggsAndChickList.size() - 1);
        assertThat(testEggsAndChick.getClutchNumber()).isEqualTo(UPDATED_CLUTCH_NUMBER);
        assertThat(testEggsAndChick.isEggsPresent()).isEqualTo(UPDATED_EGGS_PRESENT);
        assertThat(testEggsAndChick.isChicksPresent()).isEqualTo(UPDATED_CHICKS_PRESENT);
        assertThat(testEggsAndChick.getPhotoTaken()).isEqualTo(UPDATED_PHOTO_TAKEN);
        assertThat(testEggsAndChick.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingEggsAndChick() throws Exception {
        int databaseSizeBeforeUpdate = eggsAndChickRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEggsAndChickMockMvc.perform(put("/api/eggs-and-chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(eggsAndChick)))
            .andExpect(status().isBadRequest());

        // Validate the EggsAndChick in the database
        List<EggsAndChick> eggsAndChickList = eggsAndChickRepository.findAll();
        assertThat(eggsAndChickList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEggsAndChick() throws Exception {
        // Initialize the database
        eggsAndChickRepository.saveAndFlush(eggsAndChick);

        int databaseSizeBeforeDelete = eggsAndChickRepository.findAll().size();

        // Delete the eggsAndChick
        restEggsAndChickMockMvc.perform(delete("/api/eggs-and-chicks/{id}", eggsAndChick.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EggsAndChick> eggsAndChickList = eggsAndChickRepository.findAll();
        assertThat(eggsAndChickList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
