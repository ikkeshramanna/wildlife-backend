package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.TaggedAnimal;
import com.wildlife.fody.repository.TaggedAnimalRepository;

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

import com.wildlife.fody.domain.enumeration.TagType;
import com.wildlife.fody.domain.enumeration.TaggedAnimalSexType;
/**
 * Integration tests for the {@link TaggedAnimalResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaggedAnimalResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_OF_TAGGING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_TAGGING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PHYSICAL_TRAITS = "AAAAAAAAAA";
    private static final String UPDATED_PHYSICAL_TRAITS = "BBBBBBBBBB";

    private static final TagType DEFAULT_TAG_TYPE = TagType.CHIP;
    private static final TagType UPDATED_TAG_TYPE = TagType.BRACELET;

    private static final LocalDate DEFAULT_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final TaggedAnimalSexType DEFAULT_SEX_TYPE = TaggedAnimalSexType.MALE;
    private static final TaggedAnimalSexType UPDATED_SEX_TYPE = TaggedAnimalSexType.FEMALE;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TaggedAnimalRepository taggedAnimalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaggedAnimalMockMvc;

    private TaggedAnimal taggedAnimal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaggedAnimal createEntity(EntityManager em) {
        TaggedAnimal taggedAnimal = new TaggedAnimal()
            .name(DEFAULT_NAME)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .dateOfTagging(DEFAULT_DATE_OF_TAGGING)
            .physicalTraits(DEFAULT_PHYSICAL_TRAITS)
            .tagType(DEFAULT_TAG_TYPE)
            .dateTime(DEFAULT_DATE_TIME)
            .sexType(DEFAULT_SEX_TYPE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return taggedAnimal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaggedAnimal createUpdatedEntity(EntityManager em) {
        TaggedAnimal taggedAnimal = new TaggedAnimal()
            .name(UPDATED_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .dateOfTagging(UPDATED_DATE_OF_TAGGING)
            .physicalTraits(UPDATED_PHYSICAL_TRAITS)
            .tagType(UPDATED_TAG_TYPE)
            .dateTime(UPDATED_DATE_TIME)
            .sexType(UPDATED_SEX_TYPE)
            .updateDate(UPDATED_UPDATE_DATE);
        return taggedAnimal;
    }

    @BeforeEach
    public void initTest() {
        taggedAnimal = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaggedAnimal() throws Exception {
        int databaseSizeBeforeCreate = taggedAnimalRepository.findAll().size();
        // Create the TaggedAnimal
        restTaggedAnimalMockMvc.perform(post("/api/tagged-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taggedAnimal)))
            .andExpect(status().isCreated());

        // Validate the TaggedAnimal in the database
        List<TaggedAnimal> taggedAnimalList = taggedAnimalRepository.findAll();
        assertThat(taggedAnimalList).hasSize(databaseSizeBeforeCreate + 1);
        TaggedAnimal testTaggedAnimal = taggedAnimalList.get(taggedAnimalList.size() - 1);
        assertThat(testTaggedAnimal.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaggedAnimal.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testTaggedAnimal.getDateOfTagging()).isEqualTo(DEFAULT_DATE_OF_TAGGING);
        assertThat(testTaggedAnimal.getPhysicalTraits()).isEqualTo(DEFAULT_PHYSICAL_TRAITS);
        assertThat(testTaggedAnimal.getTagType()).isEqualTo(DEFAULT_TAG_TYPE);
        assertThat(testTaggedAnimal.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testTaggedAnimal.getSexType()).isEqualTo(DEFAULT_SEX_TYPE);
        assertThat(testTaggedAnimal.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createTaggedAnimalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taggedAnimalRepository.findAll().size();

        // Create the TaggedAnimal with an existing ID
        taggedAnimal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaggedAnimalMockMvc.perform(post("/api/tagged-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taggedAnimal)))
            .andExpect(status().isBadRequest());

        // Validate the TaggedAnimal in the database
        List<TaggedAnimal> taggedAnimalList = taggedAnimalRepository.findAll();
        assertThat(taggedAnimalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTaggedAnimals() throws Exception {
        // Initialize the database
        taggedAnimalRepository.saveAndFlush(taggedAnimal);

        // Get all the taggedAnimalList
        restTaggedAnimalMockMvc.perform(get("/api/tagged-animals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taggedAnimal.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].dateOfTagging").value(hasItem(DEFAULT_DATE_OF_TAGGING.toString())))
            .andExpect(jsonPath("$.[*].physicalTraits").value(hasItem(DEFAULT_PHYSICAL_TRAITS)))
            .andExpect(jsonPath("$.[*].tagType").value(hasItem(DEFAULT_TAG_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].sexType").value(hasItem(DEFAULT_SEX_TYPE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTaggedAnimal() throws Exception {
        // Initialize the database
        taggedAnimalRepository.saveAndFlush(taggedAnimal);

        // Get the taggedAnimal
        restTaggedAnimalMockMvc.perform(get("/api/tagged-animals/{id}", taggedAnimal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taggedAnimal.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.dateOfTagging").value(DEFAULT_DATE_OF_TAGGING.toString()))
            .andExpect(jsonPath("$.physicalTraits").value(DEFAULT_PHYSICAL_TRAITS))
            .andExpect(jsonPath("$.tagType").value(DEFAULT_TAG_TYPE.toString()))
            .andExpect(jsonPath("$.dateTime").value(DEFAULT_DATE_TIME.toString()))
            .andExpect(jsonPath("$.sexType").value(DEFAULT_SEX_TYPE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTaggedAnimal() throws Exception {
        // Get the taggedAnimal
        restTaggedAnimalMockMvc.perform(get("/api/tagged-animals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaggedAnimal() throws Exception {
        // Initialize the database
        taggedAnimalRepository.saveAndFlush(taggedAnimal);

        int databaseSizeBeforeUpdate = taggedAnimalRepository.findAll().size();

        // Update the taggedAnimal
        TaggedAnimal updatedTaggedAnimal = taggedAnimalRepository.findById(taggedAnimal.getId()).get();
        // Disconnect from session so that the updates on updatedTaggedAnimal are not directly saved in db
        em.detach(updatedTaggedAnimal);
        updatedTaggedAnimal
            .name(UPDATED_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .dateOfTagging(UPDATED_DATE_OF_TAGGING)
            .physicalTraits(UPDATED_PHYSICAL_TRAITS)
            .tagType(UPDATED_TAG_TYPE)
            .dateTime(UPDATED_DATE_TIME)
            .sexType(UPDATED_SEX_TYPE)
            .updateDate(UPDATED_UPDATE_DATE);

        restTaggedAnimalMockMvc.perform(put("/api/tagged-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaggedAnimal)))
            .andExpect(status().isOk());

        // Validate the TaggedAnimal in the database
        List<TaggedAnimal> taggedAnimalList = taggedAnimalRepository.findAll();
        assertThat(taggedAnimalList).hasSize(databaseSizeBeforeUpdate);
        TaggedAnimal testTaggedAnimal = taggedAnimalList.get(taggedAnimalList.size() - 1);
        assertThat(testTaggedAnimal.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaggedAnimal.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testTaggedAnimal.getDateOfTagging()).isEqualTo(UPDATED_DATE_OF_TAGGING);
        assertThat(testTaggedAnimal.getPhysicalTraits()).isEqualTo(UPDATED_PHYSICAL_TRAITS);
        assertThat(testTaggedAnimal.getTagType()).isEqualTo(UPDATED_TAG_TYPE);
        assertThat(testTaggedAnimal.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testTaggedAnimal.getSexType()).isEqualTo(UPDATED_SEX_TYPE);
        assertThat(testTaggedAnimal.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTaggedAnimal() throws Exception {
        int databaseSizeBeforeUpdate = taggedAnimalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaggedAnimalMockMvc.perform(put("/api/tagged-animals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taggedAnimal)))
            .andExpect(status().isBadRequest());

        // Validate the TaggedAnimal in the database
        List<TaggedAnimal> taggedAnimalList = taggedAnimalRepository.findAll();
        assertThat(taggedAnimalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaggedAnimal() throws Exception {
        // Initialize the database
        taggedAnimalRepository.saveAndFlush(taggedAnimal);

        int databaseSizeBeforeDelete = taggedAnimalRepository.findAll().size();

        // Delete the taggedAnimal
        restTaggedAnimalMockMvc.perform(delete("/api/tagged-animals/{id}", taggedAnimal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaggedAnimal> taggedAnimalList = taggedAnimalRepository.findAll();
        assertThat(taggedAnimalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
