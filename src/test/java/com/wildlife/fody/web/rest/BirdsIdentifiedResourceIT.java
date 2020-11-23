package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.BirdsIdentified;
import com.wildlife.fody.repository.BirdsIdentifiedRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wildlife.fody.domain.enumeration.SeenDuringType;
import com.wildlife.fody.domain.enumeration.BirdType;
import com.wildlife.fody.domain.enumeration.SexType;
import com.wildlife.fody.domain.enumeration.StatusType;
import com.wildlife.fody.domain.enumeration.BirdLocationType;
/**
 * Integration tests for the {@link BirdsIdentifiedResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class BirdsIdentifiedResourceIT {

    private static final SeenDuringType DEFAULT_SEEN_DURING = SeenDuringType.OPPORTUNISTIC;
    private static final SeenDuringType UPDATED_SEEN_DURING = SeenDuringType.PRE_SEASON;

    private static final BirdType DEFAULT_TYPE = BirdType.UNKNOWN;
    private static final BirdType UPDATED_TYPE = BirdType.SINGLE_BIRD;

    private static final SexType DEFAULT_SEX = SexType.MALE;
    private static final SexType UPDATED_SEX = SexType.FEMALE;

    private static final StatusType DEFAULT_STATUS = StatusType.MISSING;
    private static final StatusType UPDATED_STATUS = StatusType.ASSUMED_DEAD;

    private static final BirdLocationType DEFAULT_BIRD_LOCATION = BirdLocationType.IN_BOX_CAVITY;
    private static final BirdLocationType UPDATED_BIRD_LOCATION = BirdLocationType.IN_BOX_CAVITY_THEN_LEFT;

    private static final String DEFAULT_SOUTHING = "AAAAAAAAAA";
    private static final String UPDATED_SOUTHING = "BBBBBBBBBB";

    private static final String DEFAULT_EASTING = "AAAAAAAAAA";
    private static final String UPDATED_EASTING = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_COMBO_L = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_COMBO_L = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_COMBO_R = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_COMBO_R = "BBBBBBBBBB";

    private static final String DEFAULT_BIRD_IRN = "AAAAAAAAAA";
    private static final String UPDATED_BIRD_IRN = "BBBBBBBBBB";

    @Autowired
    private BirdsIdentifiedRepository birdsIdentifiedRepository;

    @Mock
    private BirdsIdentifiedRepository birdsIdentifiedRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBirdsIdentifiedMockMvc;

    private BirdsIdentified birdsIdentified;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BirdsIdentified createEntity(EntityManager em) {
        BirdsIdentified birdsIdentified = new BirdsIdentified()
            .seenDuring(DEFAULT_SEEN_DURING)
            .type(DEFAULT_TYPE)
            .sex(DEFAULT_SEX)
            .status(DEFAULT_STATUS)
            .birdLocation(DEFAULT_BIRD_LOCATION)
            .southing(DEFAULT_SOUTHING)
            .easting(DEFAULT_EASTING)
            .comments(DEFAULT_COMMENTS)
            .colorComboL(DEFAULT_COLOR_COMBO_L)
            .colorComboR(DEFAULT_COLOR_COMBO_R)
            .birdIRN(DEFAULT_BIRD_IRN);
        return birdsIdentified;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BirdsIdentified createUpdatedEntity(EntityManager em) {
        BirdsIdentified birdsIdentified = new BirdsIdentified()
            .seenDuring(UPDATED_SEEN_DURING)
            .type(UPDATED_TYPE)
            .sex(UPDATED_SEX)
            .status(UPDATED_STATUS)
            .birdLocation(UPDATED_BIRD_LOCATION)
            .southing(UPDATED_SOUTHING)
            .easting(UPDATED_EASTING)
            .comments(UPDATED_COMMENTS)
            .colorComboL(UPDATED_COLOR_COMBO_L)
            .colorComboR(UPDATED_COLOR_COMBO_R)
            .birdIRN(UPDATED_BIRD_IRN);
        return birdsIdentified;
    }

    @BeforeEach
    public void initTest() {
        birdsIdentified = createEntity(em);
    }

    @Test
    @Transactional
    public void createBirdsIdentified() throws Exception {
        int databaseSizeBeforeCreate = birdsIdentifiedRepository.findAll().size();
        // Create the BirdsIdentified
        restBirdsIdentifiedMockMvc.perform(post("/api/birds-identifieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(birdsIdentified)))
            .andExpect(status().isCreated());

        // Validate the BirdsIdentified in the database
        List<BirdsIdentified> birdsIdentifiedList = birdsIdentifiedRepository.findAll();
        assertThat(birdsIdentifiedList).hasSize(databaseSizeBeforeCreate + 1);
        BirdsIdentified testBirdsIdentified = birdsIdentifiedList.get(birdsIdentifiedList.size() - 1);
        assertThat(testBirdsIdentified.getSeenDuring()).isEqualTo(DEFAULT_SEEN_DURING);
        assertThat(testBirdsIdentified.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBirdsIdentified.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testBirdsIdentified.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBirdsIdentified.getBirdLocation()).isEqualTo(DEFAULT_BIRD_LOCATION);
        assertThat(testBirdsIdentified.getSouthing()).isEqualTo(DEFAULT_SOUTHING);
        assertThat(testBirdsIdentified.getEasting()).isEqualTo(DEFAULT_EASTING);
        assertThat(testBirdsIdentified.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testBirdsIdentified.getColorComboL()).isEqualTo(DEFAULT_COLOR_COMBO_L);
        assertThat(testBirdsIdentified.getColorComboR()).isEqualTo(DEFAULT_COLOR_COMBO_R);
        assertThat(testBirdsIdentified.getBirdIRN()).isEqualTo(DEFAULT_BIRD_IRN);
    }

    @Test
    @Transactional
    public void createBirdsIdentifiedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = birdsIdentifiedRepository.findAll().size();

        // Create the BirdsIdentified with an existing ID
        birdsIdentified.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBirdsIdentifiedMockMvc.perform(post("/api/birds-identifieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(birdsIdentified)))
            .andExpect(status().isBadRequest());

        // Validate the BirdsIdentified in the database
        List<BirdsIdentified> birdsIdentifiedList = birdsIdentifiedRepository.findAll();
        assertThat(birdsIdentifiedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSeenDuringIsRequired() throws Exception {
        int databaseSizeBeforeTest = birdsIdentifiedRepository.findAll().size();
        // set the field null
        birdsIdentified.setSeenDuring(null);

        // Create the BirdsIdentified, which fails.


        restBirdsIdentifiedMockMvc.perform(post("/api/birds-identifieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(birdsIdentified)))
            .andExpect(status().isBadRequest());

        List<BirdsIdentified> birdsIdentifiedList = birdsIdentifiedRepository.findAll();
        assertThat(birdsIdentifiedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = birdsIdentifiedRepository.findAll().size();
        // set the field null
        birdsIdentified.setType(null);

        // Create the BirdsIdentified, which fails.


        restBirdsIdentifiedMockMvc.perform(post("/api/birds-identifieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(birdsIdentified)))
            .andExpect(status().isBadRequest());

        List<BirdsIdentified> birdsIdentifiedList = birdsIdentifiedRepository.findAll();
        assertThat(birdsIdentifiedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexIsRequired() throws Exception {
        int databaseSizeBeforeTest = birdsIdentifiedRepository.findAll().size();
        // set the field null
        birdsIdentified.setSex(null);

        // Create the BirdsIdentified, which fails.


        restBirdsIdentifiedMockMvc.perform(post("/api/birds-identifieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(birdsIdentified)))
            .andExpect(status().isBadRequest());

        List<BirdsIdentified> birdsIdentifiedList = birdsIdentifiedRepository.findAll();
        assertThat(birdsIdentifiedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = birdsIdentifiedRepository.findAll().size();
        // set the field null
        birdsIdentified.setStatus(null);

        // Create the BirdsIdentified, which fails.


        restBirdsIdentifiedMockMvc.perform(post("/api/birds-identifieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(birdsIdentified)))
            .andExpect(status().isBadRequest());

        List<BirdsIdentified> birdsIdentifiedList = birdsIdentifiedRepository.findAll();
        assertThat(birdsIdentifiedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirdLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = birdsIdentifiedRepository.findAll().size();
        // set the field null
        birdsIdentified.setBirdLocation(null);

        // Create the BirdsIdentified, which fails.


        restBirdsIdentifiedMockMvc.perform(post("/api/birds-identifieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(birdsIdentified)))
            .andExpect(status().isBadRequest());

        List<BirdsIdentified> birdsIdentifiedList = birdsIdentifiedRepository.findAll();
        assertThat(birdsIdentifiedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColorComboLIsRequired() throws Exception {
        int databaseSizeBeforeTest = birdsIdentifiedRepository.findAll().size();
        // set the field null
        birdsIdentified.setColorComboL(null);

        // Create the BirdsIdentified, which fails.


        restBirdsIdentifiedMockMvc.perform(post("/api/birds-identifieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(birdsIdentified)))
            .andExpect(status().isBadRequest());

        List<BirdsIdentified> birdsIdentifiedList = birdsIdentifiedRepository.findAll();
        assertThat(birdsIdentifiedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColorComboRIsRequired() throws Exception {
        int databaseSizeBeforeTest = birdsIdentifiedRepository.findAll().size();
        // set the field null
        birdsIdentified.setColorComboR(null);

        // Create the BirdsIdentified, which fails.


        restBirdsIdentifiedMockMvc.perform(post("/api/birds-identifieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(birdsIdentified)))
            .andExpect(status().isBadRequest());

        List<BirdsIdentified> birdsIdentifiedList = birdsIdentifiedRepository.findAll();
        assertThat(birdsIdentifiedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBirdsIdentifieds() throws Exception {
        // Initialize the database
        birdsIdentifiedRepository.saveAndFlush(birdsIdentified);

        // Get all the birdsIdentifiedList
        restBirdsIdentifiedMockMvc.perform(get("/api/birds-identifieds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(birdsIdentified.getId().intValue())))
            .andExpect(jsonPath("$.[*].seenDuring").value(hasItem(DEFAULT_SEEN_DURING.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].birdLocation").value(hasItem(DEFAULT_BIRD_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].southing").value(hasItem(DEFAULT_SOUTHING)))
            .andExpect(jsonPath("$.[*].easting").value(hasItem(DEFAULT_EASTING)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].colorComboL").value(hasItem(DEFAULT_COLOR_COMBO_L)))
            .andExpect(jsonPath("$.[*].colorComboR").value(hasItem(DEFAULT_COLOR_COMBO_R)))
            .andExpect(jsonPath("$.[*].birdIRN").value(hasItem(DEFAULT_BIRD_IRN)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllBirdsIdentifiedsWithEagerRelationshipsIsEnabled() throws Exception {
        when(birdsIdentifiedRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBirdsIdentifiedMockMvc.perform(get("/api/birds-identifieds?eagerload=true"))
            .andExpect(status().isOk());

        verify(birdsIdentifiedRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllBirdsIdentifiedsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(birdsIdentifiedRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBirdsIdentifiedMockMvc.perform(get("/api/birds-identifieds?eagerload=true"))
            .andExpect(status().isOk());

        verify(birdsIdentifiedRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getBirdsIdentified() throws Exception {
        // Initialize the database
        birdsIdentifiedRepository.saveAndFlush(birdsIdentified);

        // Get the birdsIdentified
        restBirdsIdentifiedMockMvc.perform(get("/api/birds-identifieds/{id}", birdsIdentified.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(birdsIdentified.getId().intValue()))
            .andExpect(jsonPath("$.seenDuring").value(DEFAULT_SEEN_DURING.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.birdLocation").value(DEFAULT_BIRD_LOCATION.toString()))
            .andExpect(jsonPath("$.southing").value(DEFAULT_SOUTHING))
            .andExpect(jsonPath("$.easting").value(DEFAULT_EASTING))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.colorComboL").value(DEFAULT_COLOR_COMBO_L))
            .andExpect(jsonPath("$.colorComboR").value(DEFAULT_COLOR_COMBO_R))
            .andExpect(jsonPath("$.birdIRN").value(DEFAULT_BIRD_IRN));
    }
    @Test
    @Transactional
    public void getNonExistingBirdsIdentified() throws Exception {
        // Get the birdsIdentified
        restBirdsIdentifiedMockMvc.perform(get("/api/birds-identifieds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBirdsIdentified() throws Exception {
        // Initialize the database
        birdsIdentifiedRepository.saveAndFlush(birdsIdentified);

        int databaseSizeBeforeUpdate = birdsIdentifiedRepository.findAll().size();

        // Update the birdsIdentified
        BirdsIdentified updatedBirdsIdentified = birdsIdentifiedRepository.findById(birdsIdentified.getId()).get();
        // Disconnect from session so that the updates on updatedBirdsIdentified are not directly saved in db
        em.detach(updatedBirdsIdentified);
        updatedBirdsIdentified
            .seenDuring(UPDATED_SEEN_DURING)
            .type(UPDATED_TYPE)
            .sex(UPDATED_SEX)
            .status(UPDATED_STATUS)
            .birdLocation(UPDATED_BIRD_LOCATION)
            .southing(UPDATED_SOUTHING)
            .easting(UPDATED_EASTING)
            .comments(UPDATED_COMMENTS)
            .colorComboL(UPDATED_COLOR_COMBO_L)
            .colorComboR(UPDATED_COLOR_COMBO_R)
            .birdIRN(UPDATED_BIRD_IRN);

        restBirdsIdentifiedMockMvc.perform(put("/api/birds-identifieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBirdsIdentified)))
            .andExpect(status().isOk());

        // Validate the BirdsIdentified in the database
        List<BirdsIdentified> birdsIdentifiedList = birdsIdentifiedRepository.findAll();
        assertThat(birdsIdentifiedList).hasSize(databaseSizeBeforeUpdate);
        BirdsIdentified testBirdsIdentified = birdsIdentifiedList.get(birdsIdentifiedList.size() - 1);
        assertThat(testBirdsIdentified.getSeenDuring()).isEqualTo(UPDATED_SEEN_DURING);
        assertThat(testBirdsIdentified.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBirdsIdentified.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testBirdsIdentified.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBirdsIdentified.getBirdLocation()).isEqualTo(UPDATED_BIRD_LOCATION);
        assertThat(testBirdsIdentified.getSouthing()).isEqualTo(UPDATED_SOUTHING);
        assertThat(testBirdsIdentified.getEasting()).isEqualTo(UPDATED_EASTING);
        assertThat(testBirdsIdentified.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testBirdsIdentified.getColorComboL()).isEqualTo(UPDATED_COLOR_COMBO_L);
        assertThat(testBirdsIdentified.getColorComboR()).isEqualTo(UPDATED_COLOR_COMBO_R);
        assertThat(testBirdsIdentified.getBirdIRN()).isEqualTo(UPDATED_BIRD_IRN);
    }

    @Test
    @Transactional
    public void updateNonExistingBirdsIdentified() throws Exception {
        int databaseSizeBeforeUpdate = birdsIdentifiedRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBirdsIdentifiedMockMvc.perform(put("/api/birds-identifieds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(birdsIdentified)))
            .andExpect(status().isBadRequest());

        // Validate the BirdsIdentified in the database
        List<BirdsIdentified> birdsIdentifiedList = birdsIdentifiedRepository.findAll();
        assertThat(birdsIdentifiedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBirdsIdentified() throws Exception {
        // Initialize the database
        birdsIdentifiedRepository.saveAndFlush(birdsIdentified);

        int databaseSizeBeforeDelete = birdsIdentifiedRepository.findAll().size();

        // Delete the birdsIdentified
        restBirdsIdentifiedMockMvc.perform(delete("/api/birds-identifieds/{id}", birdsIdentified.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BirdsIdentified> birdsIdentifiedList = birdsIdentifiedRepository.findAll();
        assertThat(birdsIdentifiedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
