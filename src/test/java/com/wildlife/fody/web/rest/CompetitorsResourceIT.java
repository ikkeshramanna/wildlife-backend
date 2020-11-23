package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.Competitors;
import com.wildlife.fody.repository.CompetitorsRepository;

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

import com.wildlife.fody.domain.enumeration.CompetitorBehaviourType;
import com.wildlife.fody.domain.enumeration.CompetitorLocationType;
/**
 * Integration tests for the {@link CompetitorsResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompetitorsResourceIT {

    private static final String DEFAULT_MK_AROUND = "AAAAAAAAAA";
    private static final String UPDATED_MK_AROUND = "BBBBBBBBBB";

    private static final Integer DEFAULT_NO_OF_INDIVIDUALS = 1;
    private static final Integer UPDATED_NO_OF_INDIVIDUALS = 2;

    private static final CompetitorBehaviourType DEFAULT_COMPETITOR_BEHAVIOUR = CompetitorBehaviourType.NO_INTERACTION;
    private static final CompetitorBehaviourType UPDATED_COMPETITOR_BEHAVIOUR = CompetitorBehaviourType.CALLING_AT_MK;

    private static final CompetitorLocationType DEFAULT_COMPETITOR_LOCATION = CompetitorLocationType.TRACES_IN_NEST;
    private static final CompetitorLocationType UPDATED_COMPETITOR_LOCATION = CompetitorLocationType.IN_NEST;

    @Autowired
    private CompetitorsRepository competitorsRepository;

    @Mock
    private CompetitorsRepository competitorsRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetitorsMockMvc;

    private Competitors competitors;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competitors createEntity(EntityManager em) {
        Competitors competitors = new Competitors()
            .mkAround(DEFAULT_MK_AROUND)
            .noOfIndividuals(DEFAULT_NO_OF_INDIVIDUALS)
            .competitorBehaviour(DEFAULT_COMPETITOR_BEHAVIOUR)
            .competitorLocation(DEFAULT_COMPETITOR_LOCATION);
        return competitors;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competitors createUpdatedEntity(EntityManager em) {
        Competitors competitors = new Competitors()
            .mkAround(UPDATED_MK_AROUND)
            .noOfIndividuals(UPDATED_NO_OF_INDIVIDUALS)
            .competitorBehaviour(UPDATED_COMPETITOR_BEHAVIOUR)
            .competitorLocation(UPDATED_COMPETITOR_LOCATION);
        return competitors;
    }

    @BeforeEach
    public void initTest() {
        competitors = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitors() throws Exception {
        int databaseSizeBeforeCreate = competitorsRepository.findAll().size();
        // Create the Competitors
        restCompetitorsMockMvc.perform(post("/api/competitors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitors)))
            .andExpect(status().isCreated());

        // Validate the Competitors in the database
        List<Competitors> competitorsList = competitorsRepository.findAll();
        assertThat(competitorsList).hasSize(databaseSizeBeforeCreate + 1);
        Competitors testCompetitors = competitorsList.get(competitorsList.size() - 1);
        assertThat(testCompetitors.getMkAround()).isEqualTo(DEFAULT_MK_AROUND);
        assertThat(testCompetitors.getNoOfIndividuals()).isEqualTo(DEFAULT_NO_OF_INDIVIDUALS);
        assertThat(testCompetitors.getCompetitorBehaviour()).isEqualTo(DEFAULT_COMPETITOR_BEHAVIOUR);
        assertThat(testCompetitors.getCompetitorLocation()).isEqualTo(DEFAULT_COMPETITOR_LOCATION);
    }

    @Test
    @Transactional
    public void createCompetitorsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitorsRepository.findAll().size();

        // Create the Competitors with an existing ID
        competitors.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitorsMockMvc.perform(post("/api/competitors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitors)))
            .andExpect(status().isBadRequest());

        // Validate the Competitors in the database
        List<Competitors> competitorsList = competitorsRepository.findAll();
        assertThat(competitorsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNoOfIndividualsIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitorsRepository.findAll().size();
        // set the field null
        competitors.setNoOfIndividuals(null);

        // Create the Competitors, which fails.


        restCompetitorsMockMvc.perform(post("/api/competitors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitors)))
            .andExpect(status().isBadRequest());

        List<Competitors> competitorsList = competitorsRepository.findAll();
        assertThat(competitorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompetitorBehaviourIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitorsRepository.findAll().size();
        // set the field null
        competitors.setCompetitorBehaviour(null);

        // Create the Competitors, which fails.


        restCompetitorsMockMvc.perform(post("/api/competitors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitors)))
            .andExpect(status().isBadRequest());

        List<Competitors> competitorsList = competitorsRepository.findAll();
        assertThat(competitorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompetitorLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitorsRepository.findAll().size();
        // set the field null
        competitors.setCompetitorLocation(null);

        // Create the Competitors, which fails.


        restCompetitorsMockMvc.perform(post("/api/competitors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitors)))
            .andExpect(status().isBadRequest());

        List<Competitors> competitorsList = competitorsRepository.findAll();
        assertThat(competitorsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompetitors() throws Exception {
        // Initialize the database
        competitorsRepository.saveAndFlush(competitors);

        // Get all the competitorsList
        restCompetitorsMockMvc.perform(get("/api/competitors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitors.getId().intValue())))
            .andExpect(jsonPath("$.[*].mkAround").value(hasItem(DEFAULT_MK_AROUND)))
            .andExpect(jsonPath("$.[*].noOfIndividuals").value(hasItem(DEFAULT_NO_OF_INDIVIDUALS)))
            .andExpect(jsonPath("$.[*].competitorBehaviour").value(hasItem(DEFAULT_COMPETITOR_BEHAVIOUR.toString())))
            .andExpect(jsonPath("$.[*].competitorLocation").value(hasItem(DEFAULT_COMPETITOR_LOCATION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCompetitorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(competitorsRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompetitorsMockMvc.perform(get("/api/competitors?eagerload=true"))
            .andExpect(status().isOk());

        verify(competitorsRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCompetitorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(competitorsRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompetitorsMockMvc.perform(get("/api/competitors?eagerload=true"))
            .andExpect(status().isOk());

        verify(competitorsRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCompetitors() throws Exception {
        // Initialize the database
        competitorsRepository.saveAndFlush(competitors);

        // Get the competitors
        restCompetitorsMockMvc.perform(get("/api/competitors/{id}", competitors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competitors.getId().intValue()))
            .andExpect(jsonPath("$.mkAround").value(DEFAULT_MK_AROUND))
            .andExpect(jsonPath("$.noOfIndividuals").value(DEFAULT_NO_OF_INDIVIDUALS))
            .andExpect(jsonPath("$.competitorBehaviour").value(DEFAULT_COMPETITOR_BEHAVIOUR.toString()))
            .andExpect(jsonPath("$.competitorLocation").value(DEFAULT_COMPETITOR_LOCATION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCompetitors() throws Exception {
        // Get the competitors
        restCompetitorsMockMvc.perform(get("/api/competitors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitors() throws Exception {
        // Initialize the database
        competitorsRepository.saveAndFlush(competitors);

        int databaseSizeBeforeUpdate = competitorsRepository.findAll().size();

        // Update the competitors
        Competitors updatedCompetitors = competitorsRepository.findById(competitors.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitors are not directly saved in db
        em.detach(updatedCompetitors);
        updatedCompetitors
            .mkAround(UPDATED_MK_AROUND)
            .noOfIndividuals(UPDATED_NO_OF_INDIVIDUALS)
            .competitorBehaviour(UPDATED_COMPETITOR_BEHAVIOUR)
            .competitorLocation(UPDATED_COMPETITOR_LOCATION);

        restCompetitorsMockMvc.perform(put("/api/competitors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompetitors)))
            .andExpect(status().isOk());

        // Validate the Competitors in the database
        List<Competitors> competitorsList = competitorsRepository.findAll();
        assertThat(competitorsList).hasSize(databaseSizeBeforeUpdate);
        Competitors testCompetitors = competitorsList.get(competitorsList.size() - 1);
        assertThat(testCompetitors.getMkAround()).isEqualTo(UPDATED_MK_AROUND);
        assertThat(testCompetitors.getNoOfIndividuals()).isEqualTo(UPDATED_NO_OF_INDIVIDUALS);
        assertThat(testCompetitors.getCompetitorBehaviour()).isEqualTo(UPDATED_COMPETITOR_BEHAVIOUR);
        assertThat(testCompetitors.getCompetitorLocation()).isEqualTo(UPDATED_COMPETITOR_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetitors() throws Exception {
        int databaseSizeBeforeUpdate = competitorsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitorsMockMvc.perform(put("/api/competitors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitors)))
            .andExpect(status().isBadRequest());

        // Validate the Competitors in the database
        List<Competitors> competitorsList = competitorsRepository.findAll();
        assertThat(competitorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompetitors() throws Exception {
        // Initialize the database
        competitorsRepository.saveAndFlush(competitors);

        int databaseSizeBeforeDelete = competitorsRepository.findAll().size();

        // Delete the competitors
        restCompetitorsMockMvc.perform(delete("/api/competitors/{id}", competitors.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Competitors> competitorsList = competitorsRepository.findAll();
        assertThat(competitorsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
