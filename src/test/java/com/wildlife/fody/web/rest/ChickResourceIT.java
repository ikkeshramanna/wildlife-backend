package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.Chick;
import com.wildlife.fody.repository.ChickRepository;

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

import com.wildlife.fody.domain.enumeration.ChicKStatusType;
import com.wildlife.fody.domain.enumeration.ChickConditionType;
/**
 * Integration tests for the {@link ChickResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChickResourceIT {

    private static final Integer DEFAULT_CHICK_NUMBER = 1;
    private static final Integer UPDATED_CHICK_NUMBER = 2;

    private static final LocalDate DEFAULT_HATCH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HATCH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ChicKStatusType DEFAULT_CHICK_STATUS = ChicKStatusType.ALIVE;
    private static final ChicKStatusType UPDATED_CHICK_STATUS = ChicKStatusType.FLEDGED;

    private static final Integer DEFAULT_CHICK_AGE = 1;
    private static final Integer UPDATED_CHICK_AGE = 2;

    private static final String DEFAULT_CHICK_ACTIVE = "AAAAAAAAAA";
    private static final String UPDATED_CHICK_ACTIVE = "BBBBBBBBBB";

    private static final ChickConditionType DEFAULT_CHICK_CONDITION = ChickConditionType.GOOD;
    private static final ChickConditionType UPDATED_CHICK_CONDITION = ChickConditionType.MEDIUM;

    private static final String DEFAULT_CHICK_RINGED = "AAAAAAAAAA";
    private static final String UPDATED_CHICK_RINGED = "BBBBBBBBBB";

    private static final String DEFAULT_BLOOD_SAMPLE = "AAAAAAAAAA";
    private static final String UPDATED_BLOOD_SAMPLE = "BBBBBBBBBB";

    @Autowired
    private ChickRepository chickRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChickMockMvc;

    private Chick chick;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chick createEntity(EntityManager em) {
        Chick chick = new Chick()
            .chickNumber(DEFAULT_CHICK_NUMBER)
            .hatchDate(DEFAULT_HATCH_DATE)
            .chickStatus(DEFAULT_CHICK_STATUS)
            .chickAge(DEFAULT_CHICK_AGE)
            .chickActive(DEFAULT_CHICK_ACTIVE)
            .chickCondition(DEFAULT_CHICK_CONDITION)
            .chickRinged(DEFAULT_CHICK_RINGED)
            .bloodSample(DEFAULT_BLOOD_SAMPLE);
        return chick;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chick createUpdatedEntity(EntityManager em) {
        Chick chick = new Chick()
            .chickNumber(UPDATED_CHICK_NUMBER)
            .hatchDate(UPDATED_HATCH_DATE)
            .chickStatus(UPDATED_CHICK_STATUS)
            .chickAge(UPDATED_CHICK_AGE)
            .chickActive(UPDATED_CHICK_ACTIVE)
            .chickCondition(UPDATED_CHICK_CONDITION)
            .chickRinged(UPDATED_CHICK_RINGED)
            .bloodSample(UPDATED_BLOOD_SAMPLE);
        return chick;
    }

    @BeforeEach
    public void initTest() {
        chick = createEntity(em);
    }

    @Test
    @Transactional
    public void createChick() throws Exception {
        int databaseSizeBeforeCreate = chickRepository.findAll().size();
        // Create the Chick
        restChickMockMvc.perform(post("/api/chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chick)))
            .andExpect(status().isCreated());

        // Validate the Chick in the database
        List<Chick> chickList = chickRepository.findAll();
        assertThat(chickList).hasSize(databaseSizeBeforeCreate + 1);
        Chick testChick = chickList.get(chickList.size() - 1);
        assertThat(testChick.getChickNumber()).isEqualTo(DEFAULT_CHICK_NUMBER);
        assertThat(testChick.getHatchDate()).isEqualTo(DEFAULT_HATCH_DATE);
        assertThat(testChick.getChickStatus()).isEqualTo(DEFAULT_CHICK_STATUS);
        assertThat(testChick.getChickAge()).isEqualTo(DEFAULT_CHICK_AGE);
        assertThat(testChick.getChickActive()).isEqualTo(DEFAULT_CHICK_ACTIVE);
        assertThat(testChick.getChickCondition()).isEqualTo(DEFAULT_CHICK_CONDITION);
        assertThat(testChick.getChickRinged()).isEqualTo(DEFAULT_CHICK_RINGED);
        assertThat(testChick.getBloodSample()).isEqualTo(DEFAULT_BLOOD_SAMPLE);
    }

    @Test
    @Transactional
    public void createChickWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chickRepository.findAll().size();

        // Create the Chick with an existing ID
        chick.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChickMockMvc.perform(post("/api/chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chick)))
            .andExpect(status().isBadRequest());

        // Validate the Chick in the database
        List<Chick> chickList = chickRepository.findAll();
        assertThat(chickList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkChickNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = chickRepository.findAll().size();
        // set the field null
        chick.setChickNumber(null);

        // Create the Chick, which fails.


        restChickMockMvc.perform(post("/api/chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chick)))
            .andExpect(status().isBadRequest());

        List<Chick> chickList = chickRepository.findAll();
        assertThat(chickList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHatchDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = chickRepository.findAll().size();
        // set the field null
        chick.setHatchDate(null);

        // Create the Chick, which fails.


        restChickMockMvc.perform(post("/api/chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chick)))
            .andExpect(status().isBadRequest());

        List<Chick> chickList = chickRepository.findAll();
        assertThat(chickList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChickStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = chickRepository.findAll().size();
        // set the field null
        chick.setChickStatus(null);

        // Create the Chick, which fails.


        restChickMockMvc.perform(post("/api/chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chick)))
            .andExpect(status().isBadRequest());

        List<Chick> chickList = chickRepository.findAll();
        assertThat(chickList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChickAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = chickRepository.findAll().size();
        // set the field null
        chick.setChickAge(null);

        // Create the Chick, which fails.


        restChickMockMvc.perform(post("/api/chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chick)))
            .andExpect(status().isBadRequest());

        List<Chick> chickList = chickRepository.findAll();
        assertThat(chickList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChickActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = chickRepository.findAll().size();
        // set the field null
        chick.setChickActive(null);

        // Create the Chick, which fails.


        restChickMockMvc.perform(post("/api/chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chick)))
            .andExpect(status().isBadRequest());

        List<Chick> chickList = chickRepository.findAll();
        assertThat(chickList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChickConditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = chickRepository.findAll().size();
        // set the field null
        chick.setChickCondition(null);

        // Create the Chick, which fails.


        restChickMockMvc.perform(post("/api/chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chick)))
            .andExpect(status().isBadRequest());

        List<Chick> chickList = chickRepository.findAll();
        assertThat(chickList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChickRingedIsRequired() throws Exception {
        int databaseSizeBeforeTest = chickRepository.findAll().size();
        // set the field null
        chick.setChickRinged(null);

        // Create the Chick, which fails.


        restChickMockMvc.perform(post("/api/chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chick)))
            .andExpect(status().isBadRequest());

        List<Chick> chickList = chickRepository.findAll();
        assertThat(chickList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBloodSampleIsRequired() throws Exception {
        int databaseSizeBeforeTest = chickRepository.findAll().size();
        // set the field null
        chick.setBloodSample(null);

        // Create the Chick, which fails.


        restChickMockMvc.perform(post("/api/chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chick)))
            .andExpect(status().isBadRequest());

        List<Chick> chickList = chickRepository.findAll();
        assertThat(chickList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChicks() throws Exception {
        // Initialize the database
        chickRepository.saveAndFlush(chick);

        // Get all the chickList
        restChickMockMvc.perform(get("/api/chicks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chick.getId().intValue())))
            .andExpect(jsonPath("$.[*].chickNumber").value(hasItem(DEFAULT_CHICK_NUMBER)))
            .andExpect(jsonPath("$.[*].hatchDate").value(hasItem(DEFAULT_HATCH_DATE.toString())))
            .andExpect(jsonPath("$.[*].chickStatus").value(hasItem(DEFAULT_CHICK_STATUS.toString())))
            .andExpect(jsonPath("$.[*].chickAge").value(hasItem(DEFAULT_CHICK_AGE)))
            .andExpect(jsonPath("$.[*].chickActive").value(hasItem(DEFAULT_CHICK_ACTIVE)))
            .andExpect(jsonPath("$.[*].chickCondition").value(hasItem(DEFAULT_CHICK_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].chickRinged").value(hasItem(DEFAULT_CHICK_RINGED)))
            .andExpect(jsonPath("$.[*].bloodSample").value(hasItem(DEFAULT_BLOOD_SAMPLE)));
    }
    
    @Test
    @Transactional
    public void getChick() throws Exception {
        // Initialize the database
        chickRepository.saveAndFlush(chick);

        // Get the chick
        restChickMockMvc.perform(get("/api/chicks/{id}", chick.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chick.getId().intValue()))
            .andExpect(jsonPath("$.chickNumber").value(DEFAULT_CHICK_NUMBER))
            .andExpect(jsonPath("$.hatchDate").value(DEFAULT_HATCH_DATE.toString()))
            .andExpect(jsonPath("$.chickStatus").value(DEFAULT_CHICK_STATUS.toString()))
            .andExpect(jsonPath("$.chickAge").value(DEFAULT_CHICK_AGE))
            .andExpect(jsonPath("$.chickActive").value(DEFAULT_CHICK_ACTIVE))
            .andExpect(jsonPath("$.chickCondition").value(DEFAULT_CHICK_CONDITION.toString()))
            .andExpect(jsonPath("$.chickRinged").value(DEFAULT_CHICK_RINGED))
            .andExpect(jsonPath("$.bloodSample").value(DEFAULT_BLOOD_SAMPLE));
    }
    @Test
    @Transactional
    public void getNonExistingChick() throws Exception {
        // Get the chick
        restChickMockMvc.perform(get("/api/chicks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChick() throws Exception {
        // Initialize the database
        chickRepository.saveAndFlush(chick);

        int databaseSizeBeforeUpdate = chickRepository.findAll().size();

        // Update the chick
        Chick updatedChick = chickRepository.findById(chick.getId()).get();
        // Disconnect from session so that the updates on updatedChick are not directly saved in db
        em.detach(updatedChick);
        updatedChick
            .chickNumber(UPDATED_CHICK_NUMBER)
            .hatchDate(UPDATED_HATCH_DATE)
            .chickStatus(UPDATED_CHICK_STATUS)
            .chickAge(UPDATED_CHICK_AGE)
            .chickActive(UPDATED_CHICK_ACTIVE)
            .chickCondition(UPDATED_CHICK_CONDITION)
            .chickRinged(UPDATED_CHICK_RINGED)
            .bloodSample(UPDATED_BLOOD_SAMPLE);

        restChickMockMvc.perform(put("/api/chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedChick)))
            .andExpect(status().isOk());

        // Validate the Chick in the database
        List<Chick> chickList = chickRepository.findAll();
        assertThat(chickList).hasSize(databaseSizeBeforeUpdate);
        Chick testChick = chickList.get(chickList.size() - 1);
        assertThat(testChick.getChickNumber()).isEqualTo(UPDATED_CHICK_NUMBER);
        assertThat(testChick.getHatchDate()).isEqualTo(UPDATED_HATCH_DATE);
        assertThat(testChick.getChickStatus()).isEqualTo(UPDATED_CHICK_STATUS);
        assertThat(testChick.getChickAge()).isEqualTo(UPDATED_CHICK_AGE);
        assertThat(testChick.getChickActive()).isEqualTo(UPDATED_CHICK_ACTIVE);
        assertThat(testChick.getChickCondition()).isEqualTo(UPDATED_CHICK_CONDITION);
        assertThat(testChick.getChickRinged()).isEqualTo(UPDATED_CHICK_RINGED);
        assertThat(testChick.getBloodSample()).isEqualTo(UPDATED_BLOOD_SAMPLE);
    }

    @Test
    @Transactional
    public void updateNonExistingChick() throws Exception {
        int databaseSizeBeforeUpdate = chickRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChickMockMvc.perform(put("/api/chicks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chick)))
            .andExpect(status().isBadRequest());

        // Validate the Chick in the database
        List<Chick> chickList = chickRepository.findAll();
        assertThat(chickList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChick() throws Exception {
        // Initialize the database
        chickRepository.saveAndFlush(chick);

        int databaseSizeBeforeDelete = chickRepository.findAll().size();

        // Delete the chick
        restChickMockMvc.perform(delete("/api/chicks/{id}", chick.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chick> chickList = chickRepository.findAll();
        assertThat(chickList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
