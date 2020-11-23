package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.CompetitorImpactOnMk;
import com.wildlife.fody.repository.CompetitorImpactOnMkRepository;

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

import com.wildlife.fody.domain.enumeration.ImpactOnMKType;
/**
 * Integration tests for the {@link CompetitorImpactOnMkResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompetitorImpactOnMkResourceIT {

    private static final ImpactOnMKType DEFAULT_IMPACT = ImpactOnMKType.UNKNOWN;
    private static final ImpactOnMKType UPDATED_IMPACT = ImpactOnMKType.BREEDING_MALE_MK_DELAYED_ENTERING_NEST;

    @Autowired
    private CompetitorImpactOnMkRepository competitorImpactOnMkRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetitorImpactOnMkMockMvc;

    private CompetitorImpactOnMk competitorImpactOnMk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitorImpactOnMk createEntity(EntityManager em) {
        CompetitorImpactOnMk competitorImpactOnMk = new CompetitorImpactOnMk()
            .impact(DEFAULT_IMPACT);
        return competitorImpactOnMk;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitorImpactOnMk createUpdatedEntity(EntityManager em) {
        CompetitorImpactOnMk competitorImpactOnMk = new CompetitorImpactOnMk()
            .impact(UPDATED_IMPACT);
        return competitorImpactOnMk;
    }

    @BeforeEach
    public void initTest() {
        competitorImpactOnMk = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitorImpactOnMk() throws Exception {
        int databaseSizeBeforeCreate = competitorImpactOnMkRepository.findAll().size();
        // Create the CompetitorImpactOnMk
        restCompetitorImpactOnMkMockMvc.perform(post("/api/competitor-impact-on-mks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitorImpactOnMk)))
            .andExpect(status().isCreated());

        // Validate the CompetitorImpactOnMk in the database
        List<CompetitorImpactOnMk> competitorImpactOnMkList = competitorImpactOnMkRepository.findAll();
        assertThat(competitorImpactOnMkList).hasSize(databaseSizeBeforeCreate + 1);
        CompetitorImpactOnMk testCompetitorImpactOnMk = competitorImpactOnMkList.get(competitorImpactOnMkList.size() - 1);
        assertThat(testCompetitorImpactOnMk.getImpact()).isEqualTo(DEFAULT_IMPACT);
    }

    @Test
    @Transactional
    public void createCompetitorImpactOnMkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitorImpactOnMkRepository.findAll().size();

        // Create the CompetitorImpactOnMk with an existing ID
        competitorImpactOnMk.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitorImpactOnMkMockMvc.perform(post("/api/competitor-impact-on-mks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitorImpactOnMk)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitorImpactOnMk in the database
        List<CompetitorImpactOnMk> competitorImpactOnMkList = competitorImpactOnMkRepository.findAll();
        assertThat(competitorImpactOnMkList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkImpactIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitorImpactOnMkRepository.findAll().size();
        // set the field null
        competitorImpactOnMk.setImpact(null);

        // Create the CompetitorImpactOnMk, which fails.


        restCompetitorImpactOnMkMockMvc.perform(post("/api/competitor-impact-on-mks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitorImpactOnMk)))
            .andExpect(status().isBadRequest());

        List<CompetitorImpactOnMk> competitorImpactOnMkList = competitorImpactOnMkRepository.findAll();
        assertThat(competitorImpactOnMkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompetitorImpactOnMks() throws Exception {
        // Initialize the database
        competitorImpactOnMkRepository.saveAndFlush(competitorImpactOnMk);

        // Get all the competitorImpactOnMkList
        restCompetitorImpactOnMkMockMvc.perform(get("/api/competitor-impact-on-mks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitorImpactOnMk.getId().intValue())))
            .andExpect(jsonPath("$.[*].impact").value(hasItem(DEFAULT_IMPACT.toString())));
    }
    
    @Test
    @Transactional
    public void getCompetitorImpactOnMk() throws Exception {
        // Initialize the database
        competitorImpactOnMkRepository.saveAndFlush(competitorImpactOnMk);

        // Get the competitorImpactOnMk
        restCompetitorImpactOnMkMockMvc.perform(get("/api/competitor-impact-on-mks/{id}", competitorImpactOnMk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competitorImpactOnMk.getId().intValue()))
            .andExpect(jsonPath("$.impact").value(DEFAULT_IMPACT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCompetitorImpactOnMk() throws Exception {
        // Get the competitorImpactOnMk
        restCompetitorImpactOnMkMockMvc.perform(get("/api/competitor-impact-on-mks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitorImpactOnMk() throws Exception {
        // Initialize the database
        competitorImpactOnMkRepository.saveAndFlush(competitorImpactOnMk);

        int databaseSizeBeforeUpdate = competitorImpactOnMkRepository.findAll().size();

        // Update the competitorImpactOnMk
        CompetitorImpactOnMk updatedCompetitorImpactOnMk = competitorImpactOnMkRepository.findById(competitorImpactOnMk.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitorImpactOnMk are not directly saved in db
        em.detach(updatedCompetitorImpactOnMk);
        updatedCompetitorImpactOnMk
            .impact(UPDATED_IMPACT);

        restCompetitorImpactOnMkMockMvc.perform(put("/api/competitor-impact-on-mks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompetitorImpactOnMk)))
            .andExpect(status().isOk());

        // Validate the CompetitorImpactOnMk in the database
        List<CompetitorImpactOnMk> competitorImpactOnMkList = competitorImpactOnMkRepository.findAll();
        assertThat(competitorImpactOnMkList).hasSize(databaseSizeBeforeUpdate);
        CompetitorImpactOnMk testCompetitorImpactOnMk = competitorImpactOnMkList.get(competitorImpactOnMkList.size() - 1);
        assertThat(testCompetitorImpactOnMk.getImpact()).isEqualTo(UPDATED_IMPACT);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetitorImpactOnMk() throws Exception {
        int databaseSizeBeforeUpdate = competitorImpactOnMkRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitorImpactOnMkMockMvc.perform(put("/api/competitor-impact-on-mks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitorImpactOnMk)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitorImpactOnMk in the database
        List<CompetitorImpactOnMk> competitorImpactOnMkList = competitorImpactOnMkRepository.findAll();
        assertThat(competitorImpactOnMkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompetitorImpactOnMk() throws Exception {
        // Initialize the database
        competitorImpactOnMkRepository.saveAndFlush(competitorImpactOnMk);

        int databaseSizeBeforeDelete = competitorImpactOnMkRepository.findAll().size();

        // Delete the competitorImpactOnMk
        restCompetitorImpactOnMkMockMvc.perform(delete("/api/competitor-impact-on-mks/{id}", competitorImpactOnMk.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompetitorImpactOnMk> competitorImpactOnMkList = competitorImpactOnMkRepository.findAll();
        assertThat(competitorImpactOnMkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
