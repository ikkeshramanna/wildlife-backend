package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.FeedingObservation;
import com.wildlife.fody.repository.FeedingObservationRepository;

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

import com.wildlife.fody.domain.enumeration.FeedingObservationType;
import com.wildlife.fody.domain.enumeration.BreedAttemptType;
import com.wildlife.fody.domain.enumeration.BreedingStageType;
import com.wildlife.fody.domain.enumeration.BreedingOutcomeType;
import com.wildlife.fody.domain.enumeration.PreyItemType;
import com.wildlife.fody.domain.enumeration.PreySpeciesType;
import com.wildlife.fody.domain.enumeration.CloudType;
import com.wildlife.fody.domain.enumeration.WindType;
import com.wildlife.fody.domain.enumeration.RainType;
import com.wildlife.fody.domain.enumeration.FeedingObservationOutcomeType;
/**
 * Integration tests for the {@link FeedingObservationResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FeedingObservationResourceIT {

    private static final Integer DEFAULT_NO_FIELD_WORKERS = 1;
    private static final Integer UPDATED_NO_FIELD_WORKERS = 2;

    private static final FeedingObservationType DEFAULT_TYPE = FeedingObservationType.FOOD_ITEM_PASS;
    private static final FeedingObservationType UPDATED_TYPE = FeedingObservationType.NEST_SITE_REMAINS;

    private static final BreedAttemptType DEFAULT_BREEDING_ATTEMPT = BreedAttemptType.YES;
    private static final BreedAttemptType UPDATED_BREEDING_ATTEMPT = BreedAttemptType.NO;

    private static final BreedingStageType DEFAULT_BREEDING_STAGE = BreedingStageType.EGG;
    private static final BreedingStageType UPDATED_BREEDING_STAGE = BreedingStageType.CHICK;

    private static final BreedingOutcomeType DEFAULT_BREEDING_OUTCOME = BreedingOutcomeType.ONGOING;
    private static final BreedingOutcomeType UPDATED_BREEDING_OUTCOME = BreedingOutcomeType.FLEDGED;

    private static final PreyItemType DEFAULT_PREY_ITEM = PreyItemType.UNKNOWN;
    private static final PreyItemType UPDATED_PREY_ITEM = PreyItemType.GECKO;

    private static final PreySpeciesType DEFAULT_PREY_SPECIES = PreySpeciesType.UNKNOWN;
    private static final PreySpeciesType UPDATED_PREY_SPECIES = PreySpeciesType.PHELSUMA_SPP;

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    private static final CloudType DEFAULT_CLOUD = CloudType.OVERCAST;
    private static final CloudType UPDATED_CLOUD = CloudType.CLOUDY;

    private static final WindType DEFAULT_WIND = WindType.CALM;
    private static final WindType UPDATED_WIND = WindType.BREEZE;

    private static final RainType DEFAULT_RAIN = RainType.HEAVY;
    private static final RainType UPDATED_RAIN = RainType.LIGHT;

    private static final FeedingObservationOutcomeType DEFAULT_OUTCOME = FeedingObservationOutcomeType.UNKNOWN;
    private static final FeedingObservationOutcomeType UPDATED_OUTCOME = FeedingObservationOutcomeType.REMAINS_IN_NEST_SITE;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private FeedingObservationRepository feedingObservationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeedingObservationMockMvc;

    private FeedingObservation feedingObservation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedingObservation createEntity(EntityManager em) {
        FeedingObservation feedingObservation = new FeedingObservation()
            .noFieldWorkers(DEFAULT_NO_FIELD_WORKERS)
            .type(DEFAULT_TYPE)
            .breedingAttempt(DEFAULT_BREEDING_ATTEMPT)
            .breedingStage(DEFAULT_BREEDING_STAGE)
            .breedingOutcome(DEFAULT_BREEDING_OUTCOME)
            .preyItem(DEFAULT_PREY_ITEM)
            .preySpecies(DEFAULT_PREY_SPECIES)
            .time(DEFAULT_TIME)
            .cloud(DEFAULT_CLOUD)
            .wind(DEFAULT_WIND)
            .rain(DEFAULT_RAIN)
            .outcome(DEFAULT_OUTCOME)
            .comment(DEFAULT_COMMENT);
        return feedingObservation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FeedingObservation createUpdatedEntity(EntityManager em) {
        FeedingObservation feedingObservation = new FeedingObservation()
            .noFieldWorkers(UPDATED_NO_FIELD_WORKERS)
            .type(UPDATED_TYPE)
            .breedingAttempt(UPDATED_BREEDING_ATTEMPT)
            .breedingStage(UPDATED_BREEDING_STAGE)
            .breedingOutcome(UPDATED_BREEDING_OUTCOME)
            .preyItem(UPDATED_PREY_ITEM)
            .preySpecies(UPDATED_PREY_SPECIES)
            .time(UPDATED_TIME)
            .cloud(UPDATED_CLOUD)
            .wind(UPDATED_WIND)
            .rain(UPDATED_RAIN)
            .outcome(UPDATED_OUTCOME)
            .comment(UPDATED_COMMENT);
        return feedingObservation;
    }

    @BeforeEach
    public void initTest() {
        feedingObservation = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeedingObservation() throws Exception {
        int databaseSizeBeforeCreate = feedingObservationRepository.findAll().size();
        // Create the FeedingObservation
        restFeedingObservationMockMvc.perform(post("/api/feeding-observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedingObservation)))
            .andExpect(status().isCreated());

        // Validate the FeedingObservation in the database
        List<FeedingObservation> feedingObservationList = feedingObservationRepository.findAll();
        assertThat(feedingObservationList).hasSize(databaseSizeBeforeCreate + 1);
        FeedingObservation testFeedingObservation = feedingObservationList.get(feedingObservationList.size() - 1);
        assertThat(testFeedingObservation.getNoFieldWorkers()).isEqualTo(DEFAULT_NO_FIELD_WORKERS);
        assertThat(testFeedingObservation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFeedingObservation.getBreedingAttempt()).isEqualTo(DEFAULT_BREEDING_ATTEMPT);
        assertThat(testFeedingObservation.getBreedingStage()).isEqualTo(DEFAULT_BREEDING_STAGE);
        assertThat(testFeedingObservation.getBreedingOutcome()).isEqualTo(DEFAULT_BREEDING_OUTCOME);
        assertThat(testFeedingObservation.getPreyItem()).isEqualTo(DEFAULT_PREY_ITEM);
        assertThat(testFeedingObservation.getPreySpecies()).isEqualTo(DEFAULT_PREY_SPECIES);
        assertThat(testFeedingObservation.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testFeedingObservation.getCloud()).isEqualTo(DEFAULT_CLOUD);
        assertThat(testFeedingObservation.getWind()).isEqualTo(DEFAULT_WIND);
        assertThat(testFeedingObservation.getRain()).isEqualTo(DEFAULT_RAIN);
        assertThat(testFeedingObservation.getOutcome()).isEqualTo(DEFAULT_OUTCOME);
        assertThat(testFeedingObservation.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createFeedingObservationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feedingObservationRepository.findAll().size();

        // Create the FeedingObservation with an existing ID
        feedingObservation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedingObservationMockMvc.perform(post("/api/feeding-observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedingObservation)))
            .andExpect(status().isBadRequest());

        // Validate the FeedingObservation in the database
        List<FeedingObservation> feedingObservationList = feedingObservationRepository.findAll();
        assertThat(feedingObservationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNoFieldWorkersIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedingObservationRepository.findAll().size();
        // set the field null
        feedingObservation.setNoFieldWorkers(null);

        // Create the FeedingObservation, which fails.


        restFeedingObservationMockMvc.perform(post("/api/feeding-observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedingObservation)))
            .andExpect(status().isBadRequest());

        List<FeedingObservation> feedingObservationList = feedingObservationRepository.findAll();
        assertThat(feedingObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedingObservationRepository.findAll().size();
        // set the field null
        feedingObservation.setType(null);

        // Create the FeedingObservation, which fails.


        restFeedingObservationMockMvc.perform(post("/api/feeding-observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedingObservation)))
            .andExpect(status().isBadRequest());

        List<FeedingObservation> feedingObservationList = feedingObservationRepository.findAll();
        assertThat(feedingObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBreedingStageIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedingObservationRepository.findAll().size();
        // set the field null
        feedingObservation.setBreedingStage(null);

        // Create the FeedingObservation, which fails.


        restFeedingObservationMockMvc.perform(post("/api/feeding-observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedingObservation)))
            .andExpect(status().isBadRequest());

        List<FeedingObservation> feedingObservationList = feedingObservationRepository.findAll();
        assertThat(feedingObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPreyItemIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedingObservationRepository.findAll().size();
        // set the field null
        feedingObservation.setPreyItem(null);

        // Create the FeedingObservation, which fails.


        restFeedingObservationMockMvc.perform(post("/api/feeding-observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedingObservation)))
            .andExpect(status().isBadRequest());

        List<FeedingObservation> feedingObservationList = feedingObservationRepository.findAll();
        assertThat(feedingObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPreySpeciesIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedingObservationRepository.findAll().size();
        // set the field null
        feedingObservation.setPreySpecies(null);

        // Create the FeedingObservation, which fails.


        restFeedingObservationMockMvc.perform(post("/api/feeding-observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedingObservation)))
            .andExpect(status().isBadRequest());

        List<FeedingObservation> feedingObservationList = feedingObservationRepository.findAll();
        assertThat(feedingObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCloudIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedingObservationRepository.findAll().size();
        // set the field null
        feedingObservation.setCloud(null);

        // Create the FeedingObservation, which fails.


        restFeedingObservationMockMvc.perform(post("/api/feeding-observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedingObservation)))
            .andExpect(status().isBadRequest());

        List<FeedingObservation> feedingObservationList = feedingObservationRepository.findAll();
        assertThat(feedingObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWindIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedingObservationRepository.findAll().size();
        // set the field null
        feedingObservation.setWind(null);

        // Create the FeedingObservation, which fails.


        restFeedingObservationMockMvc.perform(post("/api/feeding-observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedingObservation)))
            .andExpect(status().isBadRequest());

        List<FeedingObservation> feedingObservationList = feedingObservationRepository.findAll();
        assertThat(feedingObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRainIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedingObservationRepository.findAll().size();
        // set the field null
        feedingObservation.setRain(null);

        // Create the FeedingObservation, which fails.


        restFeedingObservationMockMvc.perform(post("/api/feeding-observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedingObservation)))
            .andExpect(status().isBadRequest());

        List<FeedingObservation> feedingObservationList = feedingObservationRepository.findAll();
        assertThat(feedingObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOutcomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedingObservationRepository.findAll().size();
        // set the field null
        feedingObservation.setOutcome(null);

        // Create the FeedingObservation, which fails.


        restFeedingObservationMockMvc.perform(post("/api/feeding-observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedingObservation)))
            .andExpect(status().isBadRequest());

        List<FeedingObservation> feedingObservationList = feedingObservationRepository.findAll();
        assertThat(feedingObservationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeedingObservations() throws Exception {
        // Initialize the database
        feedingObservationRepository.saveAndFlush(feedingObservation);

        // Get all the feedingObservationList
        restFeedingObservationMockMvc.perform(get("/api/feeding-observations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedingObservation.getId().intValue())))
            .andExpect(jsonPath("$.[*].noFieldWorkers").value(hasItem(DEFAULT_NO_FIELD_WORKERS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].breedingAttempt").value(hasItem(DEFAULT_BREEDING_ATTEMPT.toString())))
            .andExpect(jsonPath("$.[*].breedingStage").value(hasItem(DEFAULT_BREEDING_STAGE.toString())))
            .andExpect(jsonPath("$.[*].breedingOutcome").value(hasItem(DEFAULT_BREEDING_OUTCOME.toString())))
            .andExpect(jsonPath("$.[*].preyItem").value(hasItem(DEFAULT_PREY_ITEM.toString())))
            .andExpect(jsonPath("$.[*].preySpecies").value(hasItem(DEFAULT_PREY_SPECIES.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].cloud").value(hasItem(DEFAULT_CLOUD.toString())))
            .andExpect(jsonPath("$.[*].wind").value(hasItem(DEFAULT_WIND.toString())))
            .andExpect(jsonPath("$.[*].rain").value(hasItem(DEFAULT_RAIN.toString())))
            .andExpect(jsonPath("$.[*].outcome").value(hasItem(DEFAULT_OUTCOME.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
    }
    
    @Test
    @Transactional
    public void getFeedingObservation() throws Exception {
        // Initialize the database
        feedingObservationRepository.saveAndFlush(feedingObservation);

        // Get the feedingObservation
        restFeedingObservationMockMvc.perform(get("/api/feeding-observations/{id}", feedingObservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feedingObservation.getId().intValue()))
            .andExpect(jsonPath("$.noFieldWorkers").value(DEFAULT_NO_FIELD_WORKERS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.breedingAttempt").value(DEFAULT_BREEDING_ATTEMPT.toString()))
            .andExpect(jsonPath("$.breedingStage").value(DEFAULT_BREEDING_STAGE.toString()))
            .andExpect(jsonPath("$.breedingOutcome").value(DEFAULT_BREEDING_OUTCOME.toString()))
            .andExpect(jsonPath("$.preyItem").value(DEFAULT_PREY_ITEM.toString()))
            .andExpect(jsonPath("$.preySpecies").value(DEFAULT_PREY_SPECIES.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME))
            .andExpect(jsonPath("$.cloud").value(DEFAULT_CLOUD.toString()))
            .andExpect(jsonPath("$.wind").value(DEFAULT_WIND.toString()))
            .andExpect(jsonPath("$.rain").value(DEFAULT_RAIN.toString()))
            .andExpect(jsonPath("$.outcome").value(DEFAULT_OUTCOME.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
    }
    @Test
    @Transactional
    public void getNonExistingFeedingObservation() throws Exception {
        // Get the feedingObservation
        restFeedingObservationMockMvc.perform(get("/api/feeding-observations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeedingObservation() throws Exception {
        // Initialize the database
        feedingObservationRepository.saveAndFlush(feedingObservation);

        int databaseSizeBeforeUpdate = feedingObservationRepository.findAll().size();

        // Update the feedingObservation
        FeedingObservation updatedFeedingObservation = feedingObservationRepository.findById(feedingObservation.getId()).get();
        // Disconnect from session so that the updates on updatedFeedingObservation are not directly saved in db
        em.detach(updatedFeedingObservation);
        updatedFeedingObservation
            .noFieldWorkers(UPDATED_NO_FIELD_WORKERS)
            .type(UPDATED_TYPE)
            .breedingAttempt(UPDATED_BREEDING_ATTEMPT)
            .breedingStage(UPDATED_BREEDING_STAGE)
            .breedingOutcome(UPDATED_BREEDING_OUTCOME)
            .preyItem(UPDATED_PREY_ITEM)
            .preySpecies(UPDATED_PREY_SPECIES)
            .time(UPDATED_TIME)
            .cloud(UPDATED_CLOUD)
            .wind(UPDATED_WIND)
            .rain(UPDATED_RAIN)
            .outcome(UPDATED_OUTCOME)
            .comment(UPDATED_COMMENT);

        restFeedingObservationMockMvc.perform(put("/api/feeding-observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFeedingObservation)))
            .andExpect(status().isOk());

        // Validate the FeedingObservation in the database
        List<FeedingObservation> feedingObservationList = feedingObservationRepository.findAll();
        assertThat(feedingObservationList).hasSize(databaseSizeBeforeUpdate);
        FeedingObservation testFeedingObservation = feedingObservationList.get(feedingObservationList.size() - 1);
        assertThat(testFeedingObservation.getNoFieldWorkers()).isEqualTo(UPDATED_NO_FIELD_WORKERS);
        assertThat(testFeedingObservation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFeedingObservation.getBreedingAttempt()).isEqualTo(UPDATED_BREEDING_ATTEMPT);
        assertThat(testFeedingObservation.getBreedingStage()).isEqualTo(UPDATED_BREEDING_STAGE);
        assertThat(testFeedingObservation.getBreedingOutcome()).isEqualTo(UPDATED_BREEDING_OUTCOME);
        assertThat(testFeedingObservation.getPreyItem()).isEqualTo(UPDATED_PREY_ITEM);
        assertThat(testFeedingObservation.getPreySpecies()).isEqualTo(UPDATED_PREY_SPECIES);
        assertThat(testFeedingObservation.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testFeedingObservation.getCloud()).isEqualTo(UPDATED_CLOUD);
        assertThat(testFeedingObservation.getWind()).isEqualTo(UPDATED_WIND);
        assertThat(testFeedingObservation.getRain()).isEqualTo(UPDATED_RAIN);
        assertThat(testFeedingObservation.getOutcome()).isEqualTo(UPDATED_OUTCOME);
        assertThat(testFeedingObservation.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingFeedingObservation() throws Exception {
        int databaseSizeBeforeUpdate = feedingObservationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeedingObservationMockMvc.perform(put("/api/feeding-observations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feedingObservation)))
            .andExpect(status().isBadRequest());

        // Validate the FeedingObservation in the database
        List<FeedingObservation> feedingObservationList = feedingObservationRepository.findAll();
        assertThat(feedingObservationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFeedingObservation() throws Exception {
        // Initialize the database
        feedingObservationRepository.saveAndFlush(feedingObservation);

        int databaseSizeBeforeDelete = feedingObservationRepository.findAll().size();

        // Delete the feedingObservation
        restFeedingObservationMockMvc.perform(delete("/api/feeding-observations/{id}", feedingObservation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FeedingObservation> feedingObservationList = feedingObservationRepository.findAll();
        assertThat(feedingObservationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
