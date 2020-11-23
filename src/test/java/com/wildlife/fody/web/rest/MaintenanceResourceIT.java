package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.Maintenance;
import com.wildlife.fody.repository.MaintenanceRepository;

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

import com.wildlife.fody.domain.enumeration.StrutType;
import com.wildlife.fody.domain.enumeration.BoxConditionType;
import com.wildlife.fody.domain.enumeration.BeePlasticType;
import com.wildlife.fody.domain.enumeration.HatchType;
import com.wildlife.fody.domain.enumeration.StepsType;
import com.wildlife.fody.domain.enumeration.HandHoldsType;
import com.wildlife.fody.domain.enumeration.ClearingType;
import com.wildlife.fody.domain.enumeration.PathType;
import com.wildlife.fody.domain.enumeration.SiteDescriptionType;
import com.wildlife.fody.domain.enumeration.SideType;
import com.wildlife.fody.domain.enumeration.TreeSpeciesType;
/**
 * Integration tests for the {@link MaintenanceResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MaintenanceResourceIT {

    private static final StrutType DEFAULT_STRUTS = StrutType.GOOD;
    private static final StrutType UPDATED_STRUTS = StrutType.NEEDS_REPLACING_IMMEDIATELY;

    private static final BoxConditionType DEFAULT_BOX_CONDITION = BoxConditionType.GOOD;
    private static final BoxConditionType UPDATED_BOX_CONDITION = BoxConditionType.NEEDS_FIXING;

    private static final BeePlasticType DEFAULT_BEE_PLASTIC = BeePlasticType.PRESENT;
    private static final BeePlasticType UPDATED_BEE_PLASTIC = BeePlasticType.NEEDS_ADDING;

    private static final HatchType DEFAULT_HATCH = HatchType.GOOD;
    private static final HatchType UPDATED_HATCH = HatchType.NEEDS_EYELETS;

    private static final StepsType DEFAULT_STEPS = StepsType.ADDED_1;
    private static final StepsType UPDATED_STEPS = StepsType.ADDED_2;

    private static final HandHoldsType DEFAULT_HANDHOLDS = HandHoldsType.ADDED_1;
    private static final HandHoldsType UPDATED_HANDHOLDS = HandHoldsType.ADDED_2;

    private static final String DEFAULT_TREE_NEEDS_REPLACING = "AAAAAAAAAA";
    private static final String UPDATED_TREE_NEEDS_REPLACING = "BBBBBBBBBB";

    private static final String DEFAULT_BOX_NEEDS_REPLACING = "AAAAAAAAAA";
    private static final String UPDATED_BOX_NEEDS_REPLACING = "BBBBBBBBBB";

    private static final ClearingType DEFAULT_CLEARING = ClearingType.GOOD;
    private static final ClearingType UPDATED_CLEARING = ClearingType.DONE;

    private static final PathType DEFAULT_PATH = PathType.GOOD;
    private static final PathType UPDATED_PATH = PathType.DONE;

    private static final String DEFAULT_ADDITIONAL_VISIT = "AAAAAAAAAA";
    private static final String UPDATED_ADDITIONAL_VISIT = "BBBBBBBBBB";

    private static final String DEFAULT_DRILL_REQUIRED = "AAAAAAAAAA";
    private static final String UPDATED_DRILL_REQUIRED = "BBBBBBBBBB";

    private static final SiteDescriptionType DEFAULT_SITE_DESCRIPTION = SiteDescriptionType.ACCURATE;
    private static final SiteDescriptionType UPDATED_SITE_DESCRIPTION = SiteDescriptionType.NOT_GREAT;

    private static final String DEFAULT_BEARING = "AAAAAAAAAA";
    private static final String UPDATED_BEARING = "BBBBBBBBBB";

    private static final SideType DEFAULT_SIDE = SideType.RIGHT;
    private static final SideType UPDATED_SIDE = SideType.LEFT;

    private static final TreeSpeciesType DEFAULT_TREE_SPECIES = TreeSpeciesType.CAN_PAN;
    private static final TreeSpeciesType UPDATED_TREE_SPECIES = TreeSpeciesType.CAS_ORI;

    private static final Float DEFAULT_HEIGHT = 1F;
    private static final Float UPDATED_HEIGHT = 2F;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaintenanceMockMvc;

    private Maintenance maintenance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Maintenance createEntity(EntityManager em) {
        Maintenance maintenance = new Maintenance()
            .struts(DEFAULT_STRUTS)
            .boxCondition(DEFAULT_BOX_CONDITION)
            .beePlastic(DEFAULT_BEE_PLASTIC)
            .hatch(DEFAULT_HATCH)
            .steps(DEFAULT_STEPS)
            .handholds(DEFAULT_HANDHOLDS)
            .treeNeedsReplacing(DEFAULT_TREE_NEEDS_REPLACING)
            .boxNeedsReplacing(DEFAULT_BOX_NEEDS_REPLACING)
            .clearing(DEFAULT_CLEARING)
            .path(DEFAULT_PATH)
            .additionalVisit(DEFAULT_ADDITIONAL_VISIT)
            .drillRequired(DEFAULT_DRILL_REQUIRED)
            .siteDescription(DEFAULT_SITE_DESCRIPTION)
            .bearing(DEFAULT_BEARING)
            .side(DEFAULT_SIDE)
            .treeSpecies(DEFAULT_TREE_SPECIES)
            .height(DEFAULT_HEIGHT)
            .comments(DEFAULT_COMMENTS);
        return maintenance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Maintenance createUpdatedEntity(EntityManager em) {
        Maintenance maintenance = new Maintenance()
            .struts(UPDATED_STRUTS)
            .boxCondition(UPDATED_BOX_CONDITION)
            .beePlastic(UPDATED_BEE_PLASTIC)
            .hatch(UPDATED_HATCH)
            .steps(UPDATED_STEPS)
            .handholds(UPDATED_HANDHOLDS)
            .treeNeedsReplacing(UPDATED_TREE_NEEDS_REPLACING)
            .boxNeedsReplacing(UPDATED_BOX_NEEDS_REPLACING)
            .clearing(UPDATED_CLEARING)
            .path(UPDATED_PATH)
            .additionalVisit(UPDATED_ADDITIONAL_VISIT)
            .drillRequired(UPDATED_DRILL_REQUIRED)
            .siteDescription(UPDATED_SITE_DESCRIPTION)
            .bearing(UPDATED_BEARING)
            .side(UPDATED_SIDE)
            .treeSpecies(UPDATED_TREE_SPECIES)
            .height(UPDATED_HEIGHT)
            .comments(UPDATED_COMMENTS);
        return maintenance;
    }

    @BeforeEach
    public void initTest() {
        maintenance = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaintenance() throws Exception {
        int databaseSizeBeforeCreate = maintenanceRepository.findAll().size();
        // Create the Maintenance
        restMaintenanceMockMvc.perform(post("/api/maintenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(maintenance)))
            .andExpect(status().isCreated());

        // Validate the Maintenance in the database
        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        assertThat(maintenanceList).hasSize(databaseSizeBeforeCreate + 1);
        Maintenance testMaintenance = maintenanceList.get(maintenanceList.size() - 1);
        assertThat(testMaintenance.getStruts()).isEqualTo(DEFAULT_STRUTS);
        assertThat(testMaintenance.getBoxCondition()).isEqualTo(DEFAULT_BOX_CONDITION);
        assertThat(testMaintenance.getBeePlastic()).isEqualTo(DEFAULT_BEE_PLASTIC);
        assertThat(testMaintenance.getHatch()).isEqualTo(DEFAULT_HATCH);
        assertThat(testMaintenance.getSteps()).isEqualTo(DEFAULT_STEPS);
        assertThat(testMaintenance.getHandholds()).isEqualTo(DEFAULT_HANDHOLDS);
        assertThat(testMaintenance.getTreeNeedsReplacing()).isEqualTo(DEFAULT_TREE_NEEDS_REPLACING);
        assertThat(testMaintenance.getBoxNeedsReplacing()).isEqualTo(DEFAULT_BOX_NEEDS_REPLACING);
        assertThat(testMaintenance.getClearing()).isEqualTo(DEFAULT_CLEARING);
        assertThat(testMaintenance.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testMaintenance.getAdditionalVisit()).isEqualTo(DEFAULT_ADDITIONAL_VISIT);
        assertThat(testMaintenance.getDrillRequired()).isEqualTo(DEFAULT_DRILL_REQUIRED);
        assertThat(testMaintenance.getSiteDescription()).isEqualTo(DEFAULT_SITE_DESCRIPTION);
        assertThat(testMaintenance.getBearing()).isEqualTo(DEFAULT_BEARING);
        assertThat(testMaintenance.getSide()).isEqualTo(DEFAULT_SIDE);
        assertThat(testMaintenance.getTreeSpecies()).isEqualTo(DEFAULT_TREE_SPECIES);
        assertThat(testMaintenance.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testMaintenance.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createMaintenanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = maintenanceRepository.findAll().size();

        // Create the Maintenance with an existing ID
        maintenance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaintenanceMockMvc.perform(post("/api/maintenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(maintenance)))
            .andExpect(status().isBadRequest());

        // Validate the Maintenance in the database
        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        assertThat(maintenanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStrutsIsRequired() throws Exception {
        int databaseSizeBeforeTest = maintenanceRepository.findAll().size();
        // set the field null
        maintenance.setStruts(null);

        // Create the Maintenance, which fails.


        restMaintenanceMockMvc.perform(post("/api/maintenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(maintenance)))
            .andExpect(status().isBadRequest());

        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        assertThat(maintenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBoxConditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = maintenanceRepository.findAll().size();
        // set the field null
        maintenance.setBoxCondition(null);

        // Create the Maintenance, which fails.


        restMaintenanceMockMvc.perform(post("/api/maintenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(maintenance)))
            .andExpect(status().isBadRequest());

        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        assertThat(maintenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBeePlasticIsRequired() throws Exception {
        int databaseSizeBeforeTest = maintenanceRepository.findAll().size();
        // set the field null
        maintenance.setBeePlastic(null);

        // Create the Maintenance, which fails.


        restMaintenanceMockMvc.perform(post("/api/maintenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(maintenance)))
            .andExpect(status().isBadRequest());

        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        assertThat(maintenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHatchIsRequired() throws Exception {
        int databaseSizeBeforeTest = maintenanceRepository.findAll().size();
        // set the field null
        maintenance.setHatch(null);

        // Create the Maintenance, which fails.


        restMaintenanceMockMvc.perform(post("/api/maintenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(maintenance)))
            .andExpect(status().isBadRequest());

        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        assertThat(maintenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClearingIsRequired() throws Exception {
        int databaseSizeBeforeTest = maintenanceRepository.findAll().size();
        // set the field null
        maintenance.setClearing(null);

        // Create the Maintenance, which fails.


        restMaintenanceMockMvc.perform(post("/api/maintenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(maintenance)))
            .andExpect(status().isBadRequest());

        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        assertThat(maintenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = maintenanceRepository.findAll().size();
        // set the field null
        maintenance.setPath(null);

        // Create the Maintenance, which fails.


        restMaintenanceMockMvc.perform(post("/api/maintenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(maintenance)))
            .andExpect(status().isBadRequest());

        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        assertThat(maintenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBearingIsRequired() throws Exception {
        int databaseSizeBeforeTest = maintenanceRepository.findAll().size();
        // set the field null
        maintenance.setBearing(null);

        // Create the Maintenance, which fails.


        restMaintenanceMockMvc.perform(post("/api/maintenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(maintenance)))
            .andExpect(status().isBadRequest());

        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        assertThat(maintenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTreeSpeciesIsRequired() throws Exception {
        int databaseSizeBeforeTest = maintenanceRepository.findAll().size();
        // set the field null
        maintenance.setTreeSpecies(null);

        // Create the Maintenance, which fails.


        restMaintenanceMockMvc.perform(post("/api/maintenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(maintenance)))
            .andExpect(status().isBadRequest());

        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        assertThat(maintenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = maintenanceRepository.findAll().size();
        // set the field null
        maintenance.setHeight(null);

        // Create the Maintenance, which fails.


        restMaintenanceMockMvc.perform(post("/api/maintenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(maintenance)))
            .andExpect(status().isBadRequest());

        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        assertThat(maintenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaintenances() throws Exception {
        // Initialize the database
        maintenanceRepository.saveAndFlush(maintenance);

        // Get all the maintenanceList
        restMaintenanceMockMvc.perform(get("/api/maintenances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maintenance.getId().intValue())))
            .andExpect(jsonPath("$.[*].struts").value(hasItem(DEFAULT_STRUTS.toString())))
            .andExpect(jsonPath("$.[*].boxCondition").value(hasItem(DEFAULT_BOX_CONDITION.toString())))
            .andExpect(jsonPath("$.[*].beePlastic").value(hasItem(DEFAULT_BEE_PLASTIC.toString())))
            .andExpect(jsonPath("$.[*].hatch").value(hasItem(DEFAULT_HATCH.toString())))
            .andExpect(jsonPath("$.[*].steps").value(hasItem(DEFAULT_STEPS.toString())))
            .andExpect(jsonPath("$.[*].handholds").value(hasItem(DEFAULT_HANDHOLDS.toString())))
            .andExpect(jsonPath("$.[*].treeNeedsReplacing").value(hasItem(DEFAULT_TREE_NEEDS_REPLACING)))
            .andExpect(jsonPath("$.[*].boxNeedsReplacing").value(hasItem(DEFAULT_BOX_NEEDS_REPLACING)))
            .andExpect(jsonPath("$.[*].clearing").value(hasItem(DEFAULT_CLEARING.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].additionalVisit").value(hasItem(DEFAULT_ADDITIONAL_VISIT)))
            .andExpect(jsonPath("$.[*].drillRequired").value(hasItem(DEFAULT_DRILL_REQUIRED)))
            .andExpect(jsonPath("$.[*].siteDescription").value(hasItem(DEFAULT_SITE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].bearing").value(hasItem(DEFAULT_BEARING)))
            .andExpect(jsonPath("$.[*].side").value(hasItem(DEFAULT_SIDE.toString())))
            .andExpect(jsonPath("$.[*].treeSpecies").value(hasItem(DEFAULT_TREE_SPECIES.toString())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getMaintenance() throws Exception {
        // Initialize the database
        maintenanceRepository.saveAndFlush(maintenance);

        // Get the maintenance
        restMaintenanceMockMvc.perform(get("/api/maintenances/{id}", maintenance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(maintenance.getId().intValue()))
            .andExpect(jsonPath("$.struts").value(DEFAULT_STRUTS.toString()))
            .andExpect(jsonPath("$.boxCondition").value(DEFAULT_BOX_CONDITION.toString()))
            .andExpect(jsonPath("$.beePlastic").value(DEFAULT_BEE_PLASTIC.toString()))
            .andExpect(jsonPath("$.hatch").value(DEFAULT_HATCH.toString()))
            .andExpect(jsonPath("$.steps").value(DEFAULT_STEPS.toString()))
            .andExpect(jsonPath("$.handholds").value(DEFAULT_HANDHOLDS.toString()))
            .andExpect(jsonPath("$.treeNeedsReplacing").value(DEFAULT_TREE_NEEDS_REPLACING))
            .andExpect(jsonPath("$.boxNeedsReplacing").value(DEFAULT_BOX_NEEDS_REPLACING))
            .andExpect(jsonPath("$.clearing").value(DEFAULT_CLEARING.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.additionalVisit").value(DEFAULT_ADDITIONAL_VISIT))
            .andExpect(jsonPath("$.drillRequired").value(DEFAULT_DRILL_REQUIRED))
            .andExpect(jsonPath("$.siteDescription").value(DEFAULT_SITE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.bearing").value(DEFAULT_BEARING))
            .andExpect(jsonPath("$.side").value(DEFAULT_SIDE.toString()))
            .andExpect(jsonPath("$.treeSpecies").value(DEFAULT_TREE_SPECIES.toString()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }
    @Test
    @Transactional
    public void getNonExistingMaintenance() throws Exception {
        // Get the maintenance
        restMaintenanceMockMvc.perform(get("/api/maintenances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaintenance() throws Exception {
        // Initialize the database
        maintenanceRepository.saveAndFlush(maintenance);

        int databaseSizeBeforeUpdate = maintenanceRepository.findAll().size();

        // Update the maintenance
        Maintenance updatedMaintenance = maintenanceRepository.findById(maintenance.getId()).get();
        // Disconnect from session so that the updates on updatedMaintenance are not directly saved in db
        em.detach(updatedMaintenance);
        updatedMaintenance
            .struts(UPDATED_STRUTS)
            .boxCondition(UPDATED_BOX_CONDITION)
            .beePlastic(UPDATED_BEE_PLASTIC)
            .hatch(UPDATED_HATCH)
            .steps(UPDATED_STEPS)
            .handholds(UPDATED_HANDHOLDS)
            .treeNeedsReplacing(UPDATED_TREE_NEEDS_REPLACING)
            .boxNeedsReplacing(UPDATED_BOX_NEEDS_REPLACING)
            .clearing(UPDATED_CLEARING)
            .path(UPDATED_PATH)
            .additionalVisit(UPDATED_ADDITIONAL_VISIT)
            .drillRequired(UPDATED_DRILL_REQUIRED)
            .siteDescription(UPDATED_SITE_DESCRIPTION)
            .bearing(UPDATED_BEARING)
            .side(UPDATED_SIDE)
            .treeSpecies(UPDATED_TREE_SPECIES)
            .height(UPDATED_HEIGHT)
            .comments(UPDATED_COMMENTS);

        restMaintenanceMockMvc.perform(put("/api/maintenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMaintenance)))
            .andExpect(status().isOk());

        // Validate the Maintenance in the database
        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        assertThat(maintenanceList).hasSize(databaseSizeBeforeUpdate);
        Maintenance testMaintenance = maintenanceList.get(maintenanceList.size() - 1);
        assertThat(testMaintenance.getStruts()).isEqualTo(UPDATED_STRUTS);
        assertThat(testMaintenance.getBoxCondition()).isEqualTo(UPDATED_BOX_CONDITION);
        assertThat(testMaintenance.getBeePlastic()).isEqualTo(UPDATED_BEE_PLASTIC);
        assertThat(testMaintenance.getHatch()).isEqualTo(UPDATED_HATCH);
        assertThat(testMaintenance.getSteps()).isEqualTo(UPDATED_STEPS);
        assertThat(testMaintenance.getHandholds()).isEqualTo(UPDATED_HANDHOLDS);
        assertThat(testMaintenance.getTreeNeedsReplacing()).isEqualTo(UPDATED_TREE_NEEDS_REPLACING);
        assertThat(testMaintenance.getBoxNeedsReplacing()).isEqualTo(UPDATED_BOX_NEEDS_REPLACING);
        assertThat(testMaintenance.getClearing()).isEqualTo(UPDATED_CLEARING);
        assertThat(testMaintenance.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testMaintenance.getAdditionalVisit()).isEqualTo(UPDATED_ADDITIONAL_VISIT);
        assertThat(testMaintenance.getDrillRequired()).isEqualTo(UPDATED_DRILL_REQUIRED);
        assertThat(testMaintenance.getSiteDescription()).isEqualTo(UPDATED_SITE_DESCRIPTION);
        assertThat(testMaintenance.getBearing()).isEqualTo(UPDATED_BEARING);
        assertThat(testMaintenance.getSide()).isEqualTo(UPDATED_SIDE);
        assertThat(testMaintenance.getTreeSpecies()).isEqualTo(UPDATED_TREE_SPECIES);
        assertThat(testMaintenance.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testMaintenance.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingMaintenance() throws Exception {
        int databaseSizeBeforeUpdate = maintenanceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaintenanceMockMvc.perform(put("/api/maintenances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(maintenance)))
            .andExpect(status().isBadRequest());

        // Validate the Maintenance in the database
        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        assertThat(maintenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMaintenance() throws Exception {
        // Initialize the database
        maintenanceRepository.saveAndFlush(maintenance);

        int databaseSizeBeforeDelete = maintenanceRepository.findAll().size();

        // Delete the maintenance
        restMaintenanceMockMvc.perform(delete("/api/maintenances/{id}", maintenance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        assertThat(maintenanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
