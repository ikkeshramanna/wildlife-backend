package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.SpeciesCategory;
import com.wildlife.fody.repository.SpeciesCategoryRepository;

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

import com.wildlife.fody.domain.enumeration.SpeciesCategoryType;
/**
 * Integration tests for the {@link SpeciesCategoryResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SpeciesCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final SpeciesCategoryType DEFAULT_SPECIES_CATEGORY_TYPE = SpeciesCategoryType.FAUNA;
    private static final SpeciesCategoryType UPDATED_SPECIES_CATEGORY_TYPE = SpeciesCategoryType.FLORA;

    private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ADD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADD_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SpeciesCategoryRepository speciesCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpeciesCategoryMockMvc;

    private SpeciesCategory speciesCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpeciesCategory createEntity(EntityManager em) {
        SpeciesCategory speciesCategory = new SpeciesCategory()
            .name(DEFAULT_NAME)
            .speciesCategoryType(DEFAULT_SPECIES_CATEGORY_TYPE)
            .picture(DEFAULT_PICTURE)
            .pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .addDate(DEFAULT_ADD_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return speciesCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpeciesCategory createUpdatedEntity(EntityManager em) {
        SpeciesCategory speciesCategory = new SpeciesCategory()
            .name(UPDATED_NAME)
            .speciesCategoryType(UPDATED_SPECIES_CATEGORY_TYPE)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .addDate(UPDATED_ADD_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        return speciesCategory;
    }

    @BeforeEach
    public void initTest() {
        speciesCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpeciesCategory() throws Exception {
        int databaseSizeBeforeCreate = speciesCategoryRepository.findAll().size();
        // Create the SpeciesCategory
        restSpeciesCategoryMockMvc.perform(post("/api/species-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(speciesCategory)))
            .andExpect(status().isCreated());

        // Validate the SpeciesCategory in the database
        List<SpeciesCategory> speciesCategoryList = speciesCategoryRepository.findAll();
        assertThat(speciesCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        SpeciesCategory testSpeciesCategory = speciesCategoryList.get(speciesCategoryList.size() - 1);
        assertThat(testSpeciesCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSpeciesCategory.getSpeciesCategoryType()).isEqualTo(DEFAULT_SPECIES_CATEGORY_TYPE);
        assertThat(testSpeciesCategory.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testSpeciesCategory.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
        assertThat(testSpeciesCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSpeciesCategory.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
        assertThat(testSpeciesCategory.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createSpeciesCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = speciesCategoryRepository.findAll().size();

        // Create the SpeciesCategory with an existing ID
        speciesCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpeciesCategoryMockMvc.perform(post("/api/species-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(speciesCategory)))
            .andExpect(status().isBadRequest());

        // Validate the SpeciesCategory in the database
        List<SpeciesCategory> speciesCategoryList = speciesCategoryRepository.findAll();
        assertThat(speciesCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = speciesCategoryRepository.findAll().size();
        // set the field null
        speciesCategory.setName(null);

        // Create the SpeciesCategory, which fails.


        restSpeciesCategoryMockMvc.perform(post("/api/species-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(speciesCategory)))
            .andExpect(status().isBadRequest());

        List<SpeciesCategory> speciesCategoryList = speciesCategoryRepository.findAll();
        assertThat(speciesCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpeciesCategoryTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = speciesCategoryRepository.findAll().size();
        // set the field null
        speciesCategory.setSpeciesCategoryType(null);

        // Create the SpeciesCategory, which fails.


        restSpeciesCategoryMockMvc.perform(post("/api/species-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(speciesCategory)))
            .andExpect(status().isBadRequest());

        List<SpeciesCategory> speciesCategoryList = speciesCategoryRepository.findAll();
        assertThat(speciesCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = speciesCategoryRepository.findAll().size();
        // set the field null
        speciesCategory.setDescription(null);

        // Create the SpeciesCategory, which fails.


        restSpeciesCategoryMockMvc.perform(post("/api/species-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(speciesCategory)))
            .andExpect(status().isBadRequest());

        List<SpeciesCategory> speciesCategoryList = speciesCategoryRepository.findAll();
        assertThat(speciesCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = speciesCategoryRepository.findAll().size();
        // set the field null
        speciesCategory.setAddDate(null);

        // Create the SpeciesCategory, which fails.


        restSpeciesCategoryMockMvc.perform(post("/api/species-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(speciesCategory)))
            .andExpect(status().isBadRequest());

        List<SpeciesCategory> speciesCategoryList = speciesCategoryRepository.findAll();
        assertThat(speciesCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = speciesCategoryRepository.findAll().size();
        // set the field null
        speciesCategory.setUpdateDate(null);

        // Create the SpeciesCategory, which fails.


        restSpeciesCategoryMockMvc.perform(post("/api/species-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(speciesCategory)))
            .andExpect(status().isBadRequest());

        List<SpeciesCategory> speciesCategoryList = speciesCategoryRepository.findAll();
        assertThat(speciesCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpeciesCategories() throws Exception {
        // Initialize the database
        speciesCategoryRepository.saveAndFlush(speciesCategory);

        // Get all the speciesCategoryList
        restSpeciesCategoryMockMvc.perform(get("/api/species-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speciesCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].speciesCategoryType").value(hasItem(DEFAULT_SPECIES_CATEGORY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].addDate").value(hasItem(DEFAULT_ADD_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getSpeciesCategory() throws Exception {
        // Initialize the database
        speciesCategoryRepository.saveAndFlush(speciesCategory);

        // Get the speciesCategory
        restSpeciesCategoryMockMvc.perform(get("/api/species-categories/{id}", speciesCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(speciesCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.speciesCategoryType").value(DEFAULT_SPECIES_CATEGORY_TYPE.toString()))
            .andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.picture").value(Base64Utils.encodeToString(DEFAULT_PICTURE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.addDate").value(DEFAULT_ADD_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSpeciesCategory() throws Exception {
        // Get the speciesCategory
        restSpeciesCategoryMockMvc.perform(get("/api/species-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpeciesCategory() throws Exception {
        // Initialize the database
        speciesCategoryRepository.saveAndFlush(speciesCategory);

        int databaseSizeBeforeUpdate = speciesCategoryRepository.findAll().size();

        // Update the speciesCategory
        SpeciesCategory updatedSpeciesCategory = speciesCategoryRepository.findById(speciesCategory.getId()).get();
        // Disconnect from session so that the updates on updatedSpeciesCategory are not directly saved in db
        em.detach(updatedSpeciesCategory);
        updatedSpeciesCategory
            .name(UPDATED_NAME)
            .speciesCategoryType(UPDATED_SPECIES_CATEGORY_TYPE)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .description(UPDATED_DESCRIPTION)
            .addDate(UPDATED_ADD_DATE)
            .updateDate(UPDATED_UPDATE_DATE);

        restSpeciesCategoryMockMvc.perform(put("/api/species-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpeciesCategory)))
            .andExpect(status().isOk());

        // Validate the SpeciesCategory in the database
        List<SpeciesCategory> speciesCategoryList = speciesCategoryRepository.findAll();
        assertThat(speciesCategoryList).hasSize(databaseSizeBeforeUpdate);
        SpeciesCategory testSpeciesCategory = speciesCategoryList.get(speciesCategoryList.size() - 1);
        assertThat(testSpeciesCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSpeciesCategory.getSpeciesCategoryType()).isEqualTo(UPDATED_SPECIES_CATEGORY_TYPE);
        assertThat(testSpeciesCategory.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testSpeciesCategory.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
        assertThat(testSpeciesCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSpeciesCategory.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
        assertThat(testSpeciesCategory.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSpeciesCategory() throws Exception {
        int databaseSizeBeforeUpdate = speciesCategoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeciesCategoryMockMvc.perform(put("/api/species-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(speciesCategory)))
            .andExpect(status().isBadRequest());

        // Validate the SpeciesCategory in the database
        List<SpeciesCategory> speciesCategoryList = speciesCategoryRepository.findAll();
        assertThat(speciesCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpeciesCategory() throws Exception {
        // Initialize the database
        speciesCategoryRepository.saveAndFlush(speciesCategory);

        int databaseSizeBeforeDelete = speciesCategoryRepository.findAll().size();

        // Delete the speciesCategory
        restSpeciesCategoryMockMvc.perform(delete("/api/species-categories/{id}", speciesCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SpeciesCategory> speciesCategoryList = speciesCategoryRepository.findAll();
        assertThat(speciesCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
