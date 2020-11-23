package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.Species;
import com.wildlife.fody.repository.SpeciesRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.wildlife.fody.domain.enumeration.FeedingTraitType;
/**
 * Integration tests for the {@link SpeciesResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SpeciesResourceIT {

    private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

    private static final FeedingTraitType DEFAULT_FEEDING_TRAIT_TYPE = FeedingTraitType.OMNIVORE;
    private static final FeedingTraitType UPDATED_FEEDING_TRAIT_TYPE = FeedingTraitType.CARNIVORE;

    private static final String DEFAULT_SPECIES_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SPECIES_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMMON_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LATIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LATIN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_INDIGENOUS = false;
    private static final Boolean UPDATED_IS_INDIGENOUS = true;

    private static final LocalDate DEFAULT_ADD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADD_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SpeciesRepository speciesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpeciesMockMvc;

    private Species species;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Species createEntity(EntityManager em) {
        Species species = new Species()
            .picture(DEFAULT_PICTURE)
            .pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE)
            .feedingTraitType(DEFAULT_FEEDING_TRAIT_TYPE)
            .speciesType(DEFAULT_SPECIES_TYPE)
            .commonName(DEFAULT_COMMON_NAME)
            .latinName(DEFAULT_LATIN_NAME)
            .description(DEFAULT_DESCRIPTION)
            .isIndigenous(DEFAULT_IS_INDIGENOUS)
            .addDate(DEFAULT_ADD_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return species;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Species createUpdatedEntity(EntityManager em) {
        Species species = new Species()
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .feedingTraitType(UPDATED_FEEDING_TRAIT_TYPE)
            .speciesType(UPDATED_SPECIES_TYPE)
            .commonName(UPDATED_COMMON_NAME)
            .latinName(UPDATED_LATIN_NAME)
            .description(UPDATED_DESCRIPTION)
            .isIndigenous(UPDATED_IS_INDIGENOUS)
            .addDate(UPDATED_ADD_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        return species;
    }

    @BeforeEach
    public void initTest() {
        species = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpecies() throws Exception {
        int databaseSizeBeforeCreate = speciesRepository.findAll().size();
        // Create the Species
        restSpeciesMockMvc.perform(post("/api/species")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(species)))
            .andExpect(status().isCreated());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeCreate + 1);
        Species testSpecies = speciesList.get(speciesList.size() - 1);
        assertThat(testSpecies.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testSpecies.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
        assertThat(testSpecies.getFeedingTraitType()).isEqualTo(DEFAULT_FEEDING_TRAIT_TYPE);
        assertThat(testSpecies.getSpeciesType()).isEqualTo(DEFAULT_SPECIES_TYPE);
        assertThat(testSpecies.getCommonName()).isEqualTo(DEFAULT_COMMON_NAME);
        assertThat(testSpecies.getLatinName()).isEqualTo(DEFAULT_LATIN_NAME);
        assertThat(testSpecies.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSpecies.isIsIndigenous()).isEqualTo(DEFAULT_IS_INDIGENOUS);
        assertThat(testSpecies.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
        assertThat(testSpecies.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createSpeciesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = speciesRepository.findAll().size();

        // Create the Species with an existing ID
        species.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpeciesMockMvc.perform(post("/api/species")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(species)))
            .andExpect(status().isBadRequest());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFeedingTraitTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = speciesRepository.findAll().size();
        // set the field null
        species.setFeedingTraitType(null);

        // Create the Species, which fails.


        restSpeciesMockMvc.perform(post("/api/species")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(species)))
            .andExpect(status().isBadRequest());

        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpeciesTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = speciesRepository.findAll().size();
        // set the field null
        species.setSpeciesType(null);

        // Create the Species, which fails.


        restSpeciesMockMvc.perform(post("/api/species")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(species)))
            .andExpect(status().isBadRequest());

        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommonNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = speciesRepository.findAll().size();
        // set the field null
        species.setCommonName(null);

        // Create the Species, which fails.


        restSpeciesMockMvc.perform(post("/api/species")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(species)))
            .andExpect(status().isBadRequest());

        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatinNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = speciesRepository.findAll().size();
        // set the field null
        species.setLatinName(null);

        // Create the Species, which fails.


        restSpeciesMockMvc.perform(post("/api/species")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(species)))
            .andExpect(status().isBadRequest());

        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = speciesRepository.findAll().size();
        // set the field null
        species.setDescription(null);

        // Create the Species, which fails.


        restSpeciesMockMvc.perform(post("/api/species")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(species)))
            .andExpect(status().isBadRequest());

        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = speciesRepository.findAll().size();
        // set the field null
        species.setAddDate(null);

        // Create the Species, which fails.


        restSpeciesMockMvc.perform(post("/api/species")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(species)))
            .andExpect(status().isBadRequest());

        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = speciesRepository.findAll().size();
        // set the field null
        species.setUpdateDate(null);

        // Create the Species, which fails.


        restSpeciesMockMvc.perform(post("/api/species")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(species)))
            .andExpect(status().isBadRequest());

        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpecies() throws Exception {
        // Initialize the database
        speciesRepository.saveAndFlush(species);

        // Get all the speciesList
        restSpeciesMockMvc.perform(get("/api/species?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(species.getId().intValue())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))))
            .andExpect(jsonPath("$.[*].feedingTraitType").value(hasItem(DEFAULT_FEEDING_TRAIT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].speciesType").value(hasItem(DEFAULT_SPECIES_TYPE)))
            .andExpect(jsonPath("$.[*].commonName").value(hasItem(DEFAULT_COMMON_NAME)))
            .andExpect(jsonPath("$.[*].latinName").value(hasItem(DEFAULT_LATIN_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isIndigenous").value(hasItem(DEFAULT_IS_INDIGENOUS.booleanValue())))
            .andExpect(jsonPath("$.[*].addDate").value(hasItem(DEFAULT_ADD_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getSpecies() throws Exception {
        // Initialize the database
        speciesRepository.saveAndFlush(species);

        // Get the species
        restSpeciesMockMvc.perform(get("/api/species/{id}", species.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(species.getId().intValue()))
            .andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.picture").value(Base64Utils.encodeToString(DEFAULT_PICTURE)))
            .andExpect(jsonPath("$.feedingTraitType").value(DEFAULT_FEEDING_TRAIT_TYPE.toString()))
            .andExpect(jsonPath("$.speciesType").value(DEFAULT_SPECIES_TYPE))
            .andExpect(jsonPath("$.commonName").value(DEFAULT_COMMON_NAME))
            .andExpect(jsonPath("$.latinName").value(DEFAULT_LATIN_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isIndigenous").value(DEFAULT_IS_INDIGENOUS.booleanValue()))
            .andExpect(jsonPath("$.addDate").value(DEFAULT_ADD_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSpecies() throws Exception {
        // Get the species
        restSpeciesMockMvc.perform(get("/api/species/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecies() throws Exception {
        // Initialize the database
        speciesRepository.saveAndFlush(species);

        int databaseSizeBeforeUpdate = speciesRepository.findAll().size();

        // Update the species
        Species updatedSpecies = speciesRepository.findById(species.getId()).get();
        // Disconnect from session so that the updates on updatedSpecies are not directly saved in db
        em.detach(updatedSpecies);
        updatedSpecies
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .feedingTraitType(UPDATED_FEEDING_TRAIT_TYPE)
            .speciesType(UPDATED_SPECIES_TYPE)
            .commonName(UPDATED_COMMON_NAME)
            .latinName(UPDATED_LATIN_NAME)
            .description(UPDATED_DESCRIPTION)
            .isIndigenous(UPDATED_IS_INDIGENOUS)
            .addDate(UPDATED_ADD_DATE)
            .updateDate(UPDATED_UPDATE_DATE);

        restSpeciesMockMvc.perform(put("/api/species")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpecies)))
            .andExpect(status().isOk());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeUpdate);
        Species testSpecies = speciesList.get(speciesList.size() - 1);
        assertThat(testSpecies.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testSpecies.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
        assertThat(testSpecies.getFeedingTraitType()).isEqualTo(UPDATED_FEEDING_TRAIT_TYPE);
        assertThat(testSpecies.getSpeciesType()).isEqualTo(UPDATED_SPECIES_TYPE);
        assertThat(testSpecies.getCommonName()).isEqualTo(UPDATED_COMMON_NAME);
        assertThat(testSpecies.getLatinName()).isEqualTo(UPDATED_LATIN_NAME);
        assertThat(testSpecies.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSpecies.isIsIndigenous()).isEqualTo(UPDATED_IS_INDIGENOUS);
        assertThat(testSpecies.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
        assertThat(testSpecies.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSpecies() throws Exception {
        int databaseSizeBeforeUpdate = speciesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeciesMockMvc.perform(put("/api/species")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(species)))
            .andExpect(status().isBadRequest());

        // Validate the Species in the database
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpecies() throws Exception {
        // Initialize the database
        speciesRepository.saveAndFlush(species);

        int databaseSizeBeforeDelete = speciesRepository.findAll().size();

        // Delete the species
        restSpeciesMockMvc.perform(delete("/api/species/{id}", species.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Species> speciesList = speciesRepository.findAll();
        assertThat(speciesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
