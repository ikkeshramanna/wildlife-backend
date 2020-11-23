package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.NestSiteOverview;
import com.wildlife.fody.repository.NestSiteOverviewRepository;

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

import com.wildlife.fody.domain.enumeration.VisitPurposeType;
import com.wildlife.fody.domain.enumeration.UseSignType;
/**
 * Integration tests for the {@link NestSiteOverviewResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NestSiteOverviewResourceIT {

    private static final Integer DEFAULT_NUMBER_PEOPLE = 1;
    private static final Integer UPDATED_NUMBER_PEOPLE = 2;

    private static final VisitPurposeType DEFAULT_PURPOSE_OF_VISIT = VisitPurposeType.OPPORTUNISTIC;
    private static final VisitPurposeType UPDATED_PURPOSE_OF_VISIT = VisitPurposeType.PRE_SEASON;

    private static final UseSignType DEFAULT_SIGNS_OF_USE = UseSignType.MUTES;
    private static final UseSignType UPDATED_SIGNS_OF_USE = UseSignType.SCRAPE;

    private static final String DEFAULT_NESTING_SUBSTRATE = "AAAAAAAAAA";
    private static final String UPDATED_NESTING_SUBSTRATE = "BBBBBBBBBB";

    private static final String DEFAULT_MAINTENANCE_DONE = "AAAAAAAAAA";
    private static final String UPDATED_MAINTENANCE_DONE = "BBBBBBBBBB";

    private static final String DEFAULT_MAINTENANCE_REQUIRED = "AAAAAAAAAA";
    private static final String UPDATED_MAINTENANCE_REQUIRED = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private NestSiteOverviewRepository nestSiteOverviewRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNestSiteOverviewMockMvc;

    private NestSiteOverview nestSiteOverview;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NestSiteOverview createEntity(EntityManager em) {
        NestSiteOverview nestSiteOverview = new NestSiteOverview()
            .numberPeople(DEFAULT_NUMBER_PEOPLE)
            .purposeOfVisit(DEFAULT_PURPOSE_OF_VISIT)
            .signsOfUse(DEFAULT_SIGNS_OF_USE)
            .nestingSubstrate(DEFAULT_NESTING_SUBSTRATE)
            .maintenanceDone(DEFAULT_MAINTENANCE_DONE)
            .maintenanceRequired(DEFAULT_MAINTENANCE_REQUIRED)
            .comments(DEFAULT_COMMENTS);
        return nestSiteOverview;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NestSiteOverview createUpdatedEntity(EntityManager em) {
        NestSiteOverview nestSiteOverview = new NestSiteOverview()
            .numberPeople(UPDATED_NUMBER_PEOPLE)
            .purposeOfVisit(UPDATED_PURPOSE_OF_VISIT)
            .signsOfUse(UPDATED_SIGNS_OF_USE)
            .nestingSubstrate(UPDATED_NESTING_SUBSTRATE)
            .maintenanceDone(UPDATED_MAINTENANCE_DONE)
            .maintenanceRequired(UPDATED_MAINTENANCE_REQUIRED)
            .comments(UPDATED_COMMENTS);
        return nestSiteOverview;
    }

    @BeforeEach
    public void initTest() {
        nestSiteOverview = createEntity(em);
    }

    @Test
    @Transactional
    public void createNestSiteOverview() throws Exception {
        int databaseSizeBeforeCreate = nestSiteOverviewRepository.findAll().size();
        // Create the NestSiteOverview
        restNestSiteOverviewMockMvc.perform(post("/api/nest-site-overviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nestSiteOverview)))
            .andExpect(status().isCreated());

        // Validate the NestSiteOverview in the database
        List<NestSiteOverview> nestSiteOverviewList = nestSiteOverviewRepository.findAll();
        assertThat(nestSiteOverviewList).hasSize(databaseSizeBeforeCreate + 1);
        NestSiteOverview testNestSiteOverview = nestSiteOverviewList.get(nestSiteOverviewList.size() - 1);
        assertThat(testNestSiteOverview.getNumberPeople()).isEqualTo(DEFAULT_NUMBER_PEOPLE);
        assertThat(testNestSiteOverview.getPurposeOfVisit()).isEqualTo(DEFAULT_PURPOSE_OF_VISIT);
        assertThat(testNestSiteOverview.getSignsOfUse()).isEqualTo(DEFAULT_SIGNS_OF_USE);
        assertThat(testNestSiteOverview.getNestingSubstrate()).isEqualTo(DEFAULT_NESTING_SUBSTRATE);
        assertThat(testNestSiteOverview.getMaintenanceDone()).isEqualTo(DEFAULT_MAINTENANCE_DONE);
        assertThat(testNestSiteOverview.getMaintenanceRequired()).isEqualTo(DEFAULT_MAINTENANCE_REQUIRED);
        assertThat(testNestSiteOverview.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createNestSiteOverviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nestSiteOverviewRepository.findAll().size();

        // Create the NestSiteOverview with an existing ID
        nestSiteOverview.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNestSiteOverviewMockMvc.perform(post("/api/nest-site-overviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nestSiteOverview)))
            .andExpect(status().isBadRequest());

        // Validate the NestSiteOverview in the database
        List<NestSiteOverview> nestSiteOverviewList = nestSiteOverviewRepository.findAll();
        assertThat(nestSiteOverviewList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumberPeopleIsRequired() throws Exception {
        int databaseSizeBeforeTest = nestSiteOverviewRepository.findAll().size();
        // set the field null
        nestSiteOverview.setNumberPeople(null);

        // Create the NestSiteOverview, which fails.


        restNestSiteOverviewMockMvc.perform(post("/api/nest-site-overviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nestSiteOverview)))
            .andExpect(status().isBadRequest());

        List<NestSiteOverview> nestSiteOverviewList = nestSiteOverviewRepository.findAll();
        assertThat(nestSiteOverviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPurposeOfVisitIsRequired() throws Exception {
        int databaseSizeBeforeTest = nestSiteOverviewRepository.findAll().size();
        // set the field null
        nestSiteOverview.setPurposeOfVisit(null);

        // Create the NestSiteOverview, which fails.


        restNestSiteOverviewMockMvc.perform(post("/api/nest-site-overviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nestSiteOverview)))
            .andExpect(status().isBadRequest());

        List<NestSiteOverview> nestSiteOverviewList = nestSiteOverviewRepository.findAll();
        assertThat(nestSiteOverviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSignsOfUseIsRequired() throws Exception {
        int databaseSizeBeforeTest = nestSiteOverviewRepository.findAll().size();
        // set the field null
        nestSiteOverview.setSignsOfUse(null);

        // Create the NestSiteOverview, which fails.


        restNestSiteOverviewMockMvc.perform(post("/api/nest-site-overviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nestSiteOverview)))
            .andExpect(status().isBadRequest());

        List<NestSiteOverview> nestSiteOverviewList = nestSiteOverviewRepository.findAll();
        assertThat(nestSiteOverviewList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNestSiteOverviews() throws Exception {
        // Initialize the database
        nestSiteOverviewRepository.saveAndFlush(nestSiteOverview);

        // Get all the nestSiteOverviewList
        restNestSiteOverviewMockMvc.perform(get("/api/nest-site-overviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nestSiteOverview.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberPeople").value(hasItem(DEFAULT_NUMBER_PEOPLE)))
            .andExpect(jsonPath("$.[*].purposeOfVisit").value(hasItem(DEFAULT_PURPOSE_OF_VISIT.toString())))
            .andExpect(jsonPath("$.[*].signsOfUse").value(hasItem(DEFAULT_SIGNS_OF_USE.toString())))
            .andExpect(jsonPath("$.[*].nestingSubstrate").value(hasItem(DEFAULT_NESTING_SUBSTRATE)))
            .andExpect(jsonPath("$.[*].maintenanceDone").value(hasItem(DEFAULT_MAINTENANCE_DONE)))
            .andExpect(jsonPath("$.[*].maintenanceRequired").value(hasItem(DEFAULT_MAINTENANCE_REQUIRED)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getNestSiteOverview() throws Exception {
        // Initialize the database
        nestSiteOverviewRepository.saveAndFlush(nestSiteOverview);

        // Get the nestSiteOverview
        restNestSiteOverviewMockMvc.perform(get("/api/nest-site-overviews/{id}", nestSiteOverview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nestSiteOverview.getId().intValue()))
            .andExpect(jsonPath("$.numberPeople").value(DEFAULT_NUMBER_PEOPLE))
            .andExpect(jsonPath("$.purposeOfVisit").value(DEFAULT_PURPOSE_OF_VISIT.toString()))
            .andExpect(jsonPath("$.signsOfUse").value(DEFAULT_SIGNS_OF_USE.toString()))
            .andExpect(jsonPath("$.nestingSubstrate").value(DEFAULT_NESTING_SUBSTRATE))
            .andExpect(jsonPath("$.maintenanceDone").value(DEFAULT_MAINTENANCE_DONE))
            .andExpect(jsonPath("$.maintenanceRequired").value(DEFAULT_MAINTENANCE_REQUIRED))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }
    @Test
    @Transactional
    public void getNonExistingNestSiteOverview() throws Exception {
        // Get the nestSiteOverview
        restNestSiteOverviewMockMvc.perform(get("/api/nest-site-overviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNestSiteOverview() throws Exception {
        // Initialize the database
        nestSiteOverviewRepository.saveAndFlush(nestSiteOverview);

        int databaseSizeBeforeUpdate = nestSiteOverviewRepository.findAll().size();

        // Update the nestSiteOverview
        NestSiteOverview updatedNestSiteOverview = nestSiteOverviewRepository.findById(nestSiteOverview.getId()).get();
        // Disconnect from session so that the updates on updatedNestSiteOverview are not directly saved in db
        em.detach(updatedNestSiteOverview);
        updatedNestSiteOverview
            .numberPeople(UPDATED_NUMBER_PEOPLE)
            .purposeOfVisit(UPDATED_PURPOSE_OF_VISIT)
            .signsOfUse(UPDATED_SIGNS_OF_USE)
            .nestingSubstrate(UPDATED_NESTING_SUBSTRATE)
            .maintenanceDone(UPDATED_MAINTENANCE_DONE)
            .maintenanceRequired(UPDATED_MAINTENANCE_REQUIRED)
            .comments(UPDATED_COMMENTS);

        restNestSiteOverviewMockMvc.perform(put("/api/nest-site-overviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNestSiteOverview)))
            .andExpect(status().isOk());

        // Validate the NestSiteOverview in the database
        List<NestSiteOverview> nestSiteOverviewList = nestSiteOverviewRepository.findAll();
        assertThat(nestSiteOverviewList).hasSize(databaseSizeBeforeUpdate);
        NestSiteOverview testNestSiteOverview = nestSiteOverviewList.get(nestSiteOverviewList.size() - 1);
        assertThat(testNestSiteOverview.getNumberPeople()).isEqualTo(UPDATED_NUMBER_PEOPLE);
        assertThat(testNestSiteOverview.getPurposeOfVisit()).isEqualTo(UPDATED_PURPOSE_OF_VISIT);
        assertThat(testNestSiteOverview.getSignsOfUse()).isEqualTo(UPDATED_SIGNS_OF_USE);
        assertThat(testNestSiteOverview.getNestingSubstrate()).isEqualTo(UPDATED_NESTING_SUBSTRATE);
        assertThat(testNestSiteOverview.getMaintenanceDone()).isEqualTo(UPDATED_MAINTENANCE_DONE);
        assertThat(testNestSiteOverview.getMaintenanceRequired()).isEqualTo(UPDATED_MAINTENANCE_REQUIRED);
        assertThat(testNestSiteOverview.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingNestSiteOverview() throws Exception {
        int databaseSizeBeforeUpdate = nestSiteOverviewRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNestSiteOverviewMockMvc.perform(put("/api/nest-site-overviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nestSiteOverview)))
            .andExpect(status().isBadRequest());

        // Validate the NestSiteOverview in the database
        List<NestSiteOverview> nestSiteOverviewList = nestSiteOverviewRepository.findAll();
        assertThat(nestSiteOverviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNestSiteOverview() throws Exception {
        // Initialize the database
        nestSiteOverviewRepository.saveAndFlush(nestSiteOverview);

        int databaseSizeBeforeDelete = nestSiteOverviewRepository.findAll().size();

        // Delete the nestSiteOverview
        restNestSiteOverviewMockMvc.perform(delete("/api/nest-site-overviews/{id}", nestSiteOverview.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NestSiteOverview> nestSiteOverviewList = nestSiteOverviewRepository.findAll();
        assertThat(nestSiteOverviewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
