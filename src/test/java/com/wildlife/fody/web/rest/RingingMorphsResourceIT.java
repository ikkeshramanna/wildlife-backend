package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.RingingMorphs;
import com.wildlife.fody.repository.RingingMorphsRepository;

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

import com.wildlife.fody.domain.enumeration.MkType;
import com.wildlife.fody.domain.enumeration.ReasonForCaptureType;
import com.wildlife.fody.domain.enumeration.CaptureMethodType;
/**
 * Integration tests for the {@link RingingMorphsResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RingingMorphsResourceIT {

    private static final MkType DEFAULT_MK_TYPE = MkType.CHICK;
    private static final MkType UPDATED_MK_TYPE = MkType.JUVENILLE;

    private static final ReasonForCaptureType DEFAULT_REASON_FOR_CAPTURE = ReasonForCaptureType.CHICK_RINGING;
    private static final ReasonForCaptureType UPDATED_REASON_FOR_CAPTURE = ReasonForCaptureType.UNRINGED;

    private static final CaptureMethodType DEFAULT_CAPTURE_METHOD = CaptureMethodType.NESTLING;
    private static final CaptureMethodType UPDATED_CAPTURE_METHOD = CaptureMethodType.INREST;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final Double DEFAULT_HEAD = 1D;
    private static final Double UPDATED_HEAD = 2D;

    private static final Double DEFAULT_EXPOSED_CULMEN = 1D;
    private static final Double UPDATED_EXPOSED_CULMEN = 2D;

    private static final Double DEFAULT_CULMEN_TO_SKULL = 1D;
    private static final Double UPDATED_CULMEN_TO_SKULL = 2D;

    private static final Double DEFAULT_WING = 1D;
    private static final Double UPDATED_WING = 2D;

    private static final Double DEFAULT_P_8 = 1D;
    private static final Double UPDATED_P_8 = 2D;

    private static final Double DEFAULT_P_8_BRUSH = 1D;
    private static final Double UPDATED_P_8_BRUSH = 2D;

    private static final Double DEFAULT_TAIL = 1D;
    private static final Double UPDATED_TAIL = 2D;

    private static final Double DEFAULT_TAIL_BRUSH = 1D;
    private static final Double UPDATED_TAIL_BRUSH = 2D;

    private static final Double DEFAULT_TARSUS_LENGTH = 1D;
    private static final Double UPDATED_TARSUS_LENGTH = 2D;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private RingingMorphsRepository ringingMorphsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRingingMorphsMockMvc;

    private RingingMorphs ringingMorphs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RingingMorphs createEntity(EntityManager em) {
        RingingMorphs ringingMorphs = new RingingMorphs()
            .mkType(DEFAULT_MK_TYPE)
            .reasonForCapture(DEFAULT_REASON_FOR_CAPTURE)
            .captureMethod(DEFAULT_CAPTURE_METHOD)
            .name(DEFAULT_NAME)
            .age(DEFAULT_AGE)
            .weight(DEFAULT_WEIGHT)
            .head(DEFAULT_HEAD)
            .exposedCulmen(DEFAULT_EXPOSED_CULMEN)
            .culmenToSkull(DEFAULT_CULMEN_TO_SKULL)
            .wing(DEFAULT_WING)
            .p8(DEFAULT_P_8)
            .p8Brush(DEFAULT_P_8_BRUSH)
            .tail(DEFAULT_TAIL)
            .tailBrush(DEFAULT_TAIL_BRUSH)
            .tarsusLength(DEFAULT_TARSUS_LENGTH)
            .comments(DEFAULT_COMMENTS);
        return ringingMorphs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RingingMorphs createUpdatedEntity(EntityManager em) {
        RingingMorphs ringingMorphs = new RingingMorphs()
            .mkType(UPDATED_MK_TYPE)
            .reasonForCapture(UPDATED_REASON_FOR_CAPTURE)
            .captureMethod(UPDATED_CAPTURE_METHOD)
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .weight(UPDATED_WEIGHT)
            .head(UPDATED_HEAD)
            .exposedCulmen(UPDATED_EXPOSED_CULMEN)
            .culmenToSkull(UPDATED_CULMEN_TO_SKULL)
            .wing(UPDATED_WING)
            .p8(UPDATED_P_8)
            .p8Brush(UPDATED_P_8_BRUSH)
            .tail(UPDATED_TAIL)
            .tailBrush(UPDATED_TAIL_BRUSH)
            .tarsusLength(UPDATED_TARSUS_LENGTH)
            .comments(UPDATED_COMMENTS);
        return ringingMorphs;
    }

    @BeforeEach
    public void initTest() {
        ringingMorphs = createEntity(em);
    }

    @Test
    @Transactional
    public void createRingingMorphs() throws Exception {
        int databaseSizeBeforeCreate = ringingMorphsRepository.findAll().size();
        // Create the RingingMorphs
        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isCreated());

        // Validate the RingingMorphs in the database
        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeCreate + 1);
        RingingMorphs testRingingMorphs = ringingMorphsList.get(ringingMorphsList.size() - 1);
        assertThat(testRingingMorphs.getMkType()).isEqualTo(DEFAULT_MK_TYPE);
        assertThat(testRingingMorphs.getReasonForCapture()).isEqualTo(DEFAULT_REASON_FOR_CAPTURE);
        assertThat(testRingingMorphs.getCaptureMethod()).isEqualTo(DEFAULT_CAPTURE_METHOD);
        assertThat(testRingingMorphs.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRingingMorphs.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testRingingMorphs.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testRingingMorphs.getHead()).isEqualTo(DEFAULT_HEAD);
        assertThat(testRingingMorphs.getExposedCulmen()).isEqualTo(DEFAULT_EXPOSED_CULMEN);
        assertThat(testRingingMorphs.getCulmenToSkull()).isEqualTo(DEFAULT_CULMEN_TO_SKULL);
        assertThat(testRingingMorphs.getWing()).isEqualTo(DEFAULT_WING);
        assertThat(testRingingMorphs.getp8()).isEqualTo(DEFAULT_P_8);
        assertThat(testRingingMorphs.getp8Brush()).isEqualTo(DEFAULT_P_8_BRUSH);
        assertThat(testRingingMorphs.getTail()).isEqualTo(DEFAULT_TAIL);
        assertThat(testRingingMorphs.getTailBrush()).isEqualTo(DEFAULT_TAIL_BRUSH);
        assertThat(testRingingMorphs.getTarsusLength()).isEqualTo(DEFAULT_TARSUS_LENGTH);
        assertThat(testRingingMorphs.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createRingingMorphsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ringingMorphsRepository.findAll().size();

        // Create the RingingMorphs with an existing ID
        ringingMorphs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        // Validate the RingingMorphs in the database
        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ringingMorphsRepository.findAll().size();
        // set the field null
        ringingMorphs.setMkType(null);

        // Create the RingingMorphs, which fails.


        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReasonForCaptureIsRequired() throws Exception {
        int databaseSizeBeforeTest = ringingMorphsRepository.findAll().size();
        // set the field null
        ringingMorphs.setReasonForCapture(null);

        // Create the RingingMorphs, which fails.


        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCaptureMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = ringingMorphsRepository.findAll().size();
        // set the field null
        ringingMorphs.setCaptureMethod(null);

        // Create the RingingMorphs, which fails.


        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ringingMorphsRepository.findAll().size();
        // set the field null
        ringingMorphs.setName(null);

        // Create the RingingMorphs, which fails.


        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ringingMorphsRepository.findAll().size();
        // set the field null
        ringingMorphs.setAge(null);

        // Create the RingingMorphs, which fails.


        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = ringingMorphsRepository.findAll().size();
        // set the field null
        ringingMorphs.setWeight(null);

        // Create the RingingMorphs, which fails.


        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeadIsRequired() throws Exception {
        int databaseSizeBeforeTest = ringingMorphsRepository.findAll().size();
        // set the field null
        ringingMorphs.setHead(null);

        // Create the RingingMorphs, which fails.


        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExposedCulmenIsRequired() throws Exception {
        int databaseSizeBeforeTest = ringingMorphsRepository.findAll().size();
        // set the field null
        ringingMorphs.setExposedCulmen(null);

        // Create the RingingMorphs, which fails.


        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCulmenToSkullIsRequired() throws Exception {
        int databaseSizeBeforeTest = ringingMorphsRepository.findAll().size();
        // set the field null
        ringingMorphs.setCulmenToSkull(null);

        // Create the RingingMorphs, which fails.


        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWingIsRequired() throws Exception {
        int databaseSizeBeforeTest = ringingMorphsRepository.findAll().size();
        // set the field null
        ringingMorphs.setWing(null);

        // Create the RingingMorphs, which fails.


        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkp8IsRequired() throws Exception {
        int databaseSizeBeforeTest = ringingMorphsRepository.findAll().size();
        // set the field null
        ringingMorphs.setp8(null);

        // Create the RingingMorphs, which fails.


        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkp8BrushIsRequired() throws Exception {
        int databaseSizeBeforeTest = ringingMorphsRepository.findAll().size();
        // set the field null
        ringingMorphs.setp8Brush(null);

        // Create the RingingMorphs, which fails.


        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTailIsRequired() throws Exception {
        int databaseSizeBeforeTest = ringingMorphsRepository.findAll().size();
        // set the field null
        ringingMorphs.setTail(null);

        // Create the RingingMorphs, which fails.


        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTailBrushIsRequired() throws Exception {
        int databaseSizeBeforeTest = ringingMorphsRepository.findAll().size();
        // set the field null
        ringingMorphs.setTailBrush(null);

        // Create the RingingMorphs, which fails.


        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTarsusLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = ringingMorphsRepository.findAll().size();
        // set the field null
        ringingMorphs.setTarsusLength(null);

        // Create the RingingMorphs, which fails.


        restRingingMorphsMockMvc.perform(post("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRingingMorphs() throws Exception {
        // Initialize the database
        ringingMorphsRepository.saveAndFlush(ringingMorphs);

        // Get all the ringingMorphsList
        restRingingMorphsMockMvc.perform(get("/api/ringing-morphs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ringingMorphs.getId().intValue())))
            .andExpect(jsonPath("$.[*].mkType").value(hasItem(DEFAULT_MK_TYPE.toString())))
            .andExpect(jsonPath("$.[*].reasonForCapture").value(hasItem(DEFAULT_REASON_FOR_CAPTURE.toString())))
            .andExpect(jsonPath("$.[*].captureMethod").value(hasItem(DEFAULT_CAPTURE_METHOD.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].head").value(hasItem(DEFAULT_HEAD.doubleValue())))
            .andExpect(jsonPath("$.[*].exposedCulmen").value(hasItem(DEFAULT_EXPOSED_CULMEN.doubleValue())))
            .andExpect(jsonPath("$.[*].culmenToSkull").value(hasItem(DEFAULT_CULMEN_TO_SKULL.doubleValue())))
            .andExpect(jsonPath("$.[*].wing").value(hasItem(DEFAULT_WING.doubleValue())))
            .andExpect(jsonPath("$.[*].p8").value(hasItem(DEFAULT_P_8.doubleValue())))
            .andExpect(jsonPath("$.[*].p8Brush").value(hasItem(DEFAULT_P_8_BRUSH.doubleValue())))
            .andExpect(jsonPath("$.[*].tail").value(hasItem(DEFAULT_TAIL.doubleValue())))
            .andExpect(jsonPath("$.[*].tailBrush").value(hasItem(DEFAULT_TAIL_BRUSH.doubleValue())))
            .andExpect(jsonPath("$.[*].tarsusLength").value(hasItem(DEFAULT_TARSUS_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getRingingMorphs() throws Exception {
        // Initialize the database
        ringingMorphsRepository.saveAndFlush(ringingMorphs);

        // Get the ringingMorphs
        restRingingMorphsMockMvc.perform(get("/api/ringing-morphs/{id}", ringingMorphs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ringingMorphs.getId().intValue()))
            .andExpect(jsonPath("$.mkType").value(DEFAULT_MK_TYPE.toString()))
            .andExpect(jsonPath("$.reasonForCapture").value(DEFAULT_REASON_FOR_CAPTURE.toString()))
            .andExpect(jsonPath("$.captureMethod").value(DEFAULT_CAPTURE_METHOD.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.head").value(DEFAULT_HEAD.doubleValue()))
            .andExpect(jsonPath("$.exposedCulmen").value(DEFAULT_EXPOSED_CULMEN.doubleValue()))
            .andExpect(jsonPath("$.culmenToSkull").value(DEFAULT_CULMEN_TO_SKULL.doubleValue()))
            .andExpect(jsonPath("$.wing").value(DEFAULT_WING.doubleValue()))
            .andExpect(jsonPath("$.p8").value(DEFAULT_P_8.doubleValue()))
            .andExpect(jsonPath("$.p8Brush").value(DEFAULT_P_8_BRUSH.doubleValue()))
            .andExpect(jsonPath("$.tail").value(DEFAULT_TAIL.doubleValue()))
            .andExpect(jsonPath("$.tailBrush").value(DEFAULT_TAIL_BRUSH.doubleValue()))
            .andExpect(jsonPath("$.tarsusLength").value(DEFAULT_TARSUS_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }
    @Test
    @Transactional
    public void getNonExistingRingingMorphs() throws Exception {
        // Get the ringingMorphs
        restRingingMorphsMockMvc.perform(get("/api/ringing-morphs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRingingMorphs() throws Exception {
        // Initialize the database
        ringingMorphsRepository.saveAndFlush(ringingMorphs);

        int databaseSizeBeforeUpdate = ringingMorphsRepository.findAll().size();

        // Update the ringingMorphs
        RingingMorphs updatedRingingMorphs = ringingMorphsRepository.findById(ringingMorphs.getId()).get();
        // Disconnect from session so that the updates on updatedRingingMorphs are not directly saved in db
        em.detach(updatedRingingMorphs);
        updatedRingingMorphs
            .mkType(UPDATED_MK_TYPE)
            .reasonForCapture(UPDATED_REASON_FOR_CAPTURE)
            .captureMethod(UPDATED_CAPTURE_METHOD)
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .weight(UPDATED_WEIGHT)
            .head(UPDATED_HEAD)
            .exposedCulmen(UPDATED_EXPOSED_CULMEN)
            .culmenToSkull(UPDATED_CULMEN_TO_SKULL)
            .wing(UPDATED_WING)
            .p8(UPDATED_P_8)
            .p8Brush(UPDATED_P_8_BRUSH)
            .tail(UPDATED_TAIL)
            .tailBrush(UPDATED_TAIL_BRUSH)
            .tarsusLength(UPDATED_TARSUS_LENGTH)
            .comments(UPDATED_COMMENTS);

        restRingingMorphsMockMvc.perform(put("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRingingMorphs)))
            .andExpect(status().isOk());

        // Validate the RingingMorphs in the database
        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeUpdate);
        RingingMorphs testRingingMorphs = ringingMorphsList.get(ringingMorphsList.size() - 1);
        assertThat(testRingingMorphs.getMkType()).isEqualTo(UPDATED_MK_TYPE);
        assertThat(testRingingMorphs.getReasonForCapture()).isEqualTo(UPDATED_REASON_FOR_CAPTURE);
        assertThat(testRingingMorphs.getCaptureMethod()).isEqualTo(UPDATED_CAPTURE_METHOD);
        assertThat(testRingingMorphs.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRingingMorphs.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testRingingMorphs.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testRingingMorphs.getHead()).isEqualTo(UPDATED_HEAD);
        assertThat(testRingingMorphs.getExposedCulmen()).isEqualTo(UPDATED_EXPOSED_CULMEN);
        assertThat(testRingingMorphs.getCulmenToSkull()).isEqualTo(UPDATED_CULMEN_TO_SKULL);
        assertThat(testRingingMorphs.getWing()).isEqualTo(UPDATED_WING);
        assertThat(testRingingMorphs.getp8()).isEqualTo(UPDATED_P_8);
        assertThat(testRingingMorphs.getp8Brush()).isEqualTo(UPDATED_P_8_BRUSH);
        assertThat(testRingingMorphs.getTail()).isEqualTo(UPDATED_TAIL);
        assertThat(testRingingMorphs.getTailBrush()).isEqualTo(UPDATED_TAIL_BRUSH);
        assertThat(testRingingMorphs.getTarsusLength()).isEqualTo(UPDATED_TARSUS_LENGTH);
        assertThat(testRingingMorphs.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingRingingMorphs() throws Exception {
        int databaseSizeBeforeUpdate = ringingMorphsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRingingMorphsMockMvc.perform(put("/api/ringing-morphs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ringingMorphs)))
            .andExpect(status().isBadRequest());

        // Validate the RingingMorphs in the database
        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRingingMorphs() throws Exception {
        // Initialize the database
        ringingMorphsRepository.saveAndFlush(ringingMorphs);

        int databaseSizeBeforeDelete = ringingMorphsRepository.findAll().size();

        // Delete the ringingMorphs
        restRingingMorphsMockMvc.perform(delete("/api/ringing-morphs/{id}", ringingMorphs.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RingingMorphs> ringingMorphsList = ringingMorphsRepository.findAll();
        assertThat(ringingMorphsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
