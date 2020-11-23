package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.CompetitorAction;
import com.wildlife.fody.repository.CompetitorActionRepository;

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

import com.wildlife.fody.domain.enumeration.ActionType;
/**
 * Integration tests for the {@link CompetitorActionResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompetitorActionResourceIT {

    private static final ActionType DEFAULT_ACTION = ActionType.CARBARYL_ADDED;
    private static final ActionType UPDATED_ACTION = ActionType.EGGS_DISPATCHED;

    @Autowired
    private CompetitorActionRepository competitorActionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetitorActionMockMvc;

    private CompetitorAction competitorAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitorAction createEntity(EntityManager em) {
        CompetitorAction competitorAction = new CompetitorAction()
            .action(DEFAULT_ACTION);
        return competitorAction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitorAction createUpdatedEntity(EntityManager em) {
        CompetitorAction competitorAction = new CompetitorAction()
            .action(UPDATED_ACTION);
        return competitorAction;
    }

    @BeforeEach
    public void initTest() {
        competitorAction = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitorAction() throws Exception {
        int databaseSizeBeforeCreate = competitorActionRepository.findAll().size();
        // Create the CompetitorAction
        restCompetitorActionMockMvc.perform(post("/api/competitor-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitorAction)))
            .andExpect(status().isCreated());

        // Validate the CompetitorAction in the database
        List<CompetitorAction> competitorActionList = competitorActionRepository.findAll();
        assertThat(competitorActionList).hasSize(databaseSizeBeforeCreate + 1);
        CompetitorAction testCompetitorAction = competitorActionList.get(competitorActionList.size() - 1);
        assertThat(testCompetitorAction.getAction()).isEqualTo(DEFAULT_ACTION);
    }

    @Test
    @Transactional
    public void createCompetitorActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitorActionRepository.findAll().size();

        // Create the CompetitorAction with an existing ID
        competitorAction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitorActionMockMvc.perform(post("/api/competitor-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitorAction)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitorAction in the database
        List<CompetitorAction> competitorActionList = competitorActionRepository.findAll();
        assertThat(competitorActionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitorActionRepository.findAll().size();
        // set the field null
        competitorAction.setAction(null);

        // Create the CompetitorAction, which fails.


        restCompetitorActionMockMvc.perform(post("/api/competitor-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitorAction)))
            .andExpect(status().isBadRequest());

        List<CompetitorAction> competitorActionList = competitorActionRepository.findAll();
        assertThat(competitorActionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompetitorActions() throws Exception {
        // Initialize the database
        competitorActionRepository.saveAndFlush(competitorAction);

        // Get all the competitorActionList
        restCompetitorActionMockMvc.perform(get("/api/competitor-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitorAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())));
    }
    
    @Test
    @Transactional
    public void getCompetitorAction() throws Exception {
        // Initialize the database
        competitorActionRepository.saveAndFlush(competitorAction);

        // Get the competitorAction
        restCompetitorActionMockMvc.perform(get("/api/competitor-actions/{id}", competitorAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competitorAction.getId().intValue()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCompetitorAction() throws Exception {
        // Get the competitorAction
        restCompetitorActionMockMvc.perform(get("/api/competitor-actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitorAction() throws Exception {
        // Initialize the database
        competitorActionRepository.saveAndFlush(competitorAction);

        int databaseSizeBeforeUpdate = competitorActionRepository.findAll().size();

        // Update the competitorAction
        CompetitorAction updatedCompetitorAction = competitorActionRepository.findById(competitorAction.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitorAction are not directly saved in db
        em.detach(updatedCompetitorAction);
        updatedCompetitorAction
            .action(UPDATED_ACTION);

        restCompetitorActionMockMvc.perform(put("/api/competitor-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompetitorAction)))
            .andExpect(status().isOk());

        // Validate the CompetitorAction in the database
        List<CompetitorAction> competitorActionList = competitorActionRepository.findAll();
        assertThat(competitorActionList).hasSize(databaseSizeBeforeUpdate);
        CompetitorAction testCompetitorAction = competitorActionList.get(competitorActionList.size() - 1);
        assertThat(testCompetitorAction.getAction()).isEqualTo(UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetitorAction() throws Exception {
        int databaseSizeBeforeUpdate = competitorActionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitorActionMockMvc.perform(put("/api/competitor-actions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competitorAction)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitorAction in the database
        List<CompetitorAction> competitorActionList = competitorActionRepository.findAll();
        assertThat(competitorActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompetitorAction() throws Exception {
        // Initialize the database
        competitorActionRepository.saveAndFlush(competitorAction);

        int databaseSizeBeforeDelete = competitorActionRepository.findAll().size();

        // Delete the competitorAction
        restCompetitorActionMockMvc.perform(delete("/api/competitor-actions/{id}", competitorAction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompetitorAction> competitorActionList = competitorActionRepository.findAll();
        assertThat(competitorActionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
