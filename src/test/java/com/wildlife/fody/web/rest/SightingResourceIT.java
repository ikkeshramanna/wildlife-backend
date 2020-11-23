package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.Sighting;
import com.wildlife.fody.repository.SightingRepository;

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

import com.wildlife.fody.domain.enumeration.NestSiteType;
import com.wildlife.fody.domain.enumeration.AreaType;
import com.wildlife.fody.domain.enumeration.NestType;
/**
 * Integration tests for the {@link SightingResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SightingResourceIT {

    private static final NestSiteType DEFAULT_NEST_SITE = NestSiteType.BAMBOUS_VIRIEUX_3;
    private static final NestSiteType UPDATED_NEST_SITE = NestSiteType.CAMIZARD_CHARIZARD_CLIFF;

    private static final AreaType DEFAULT_AREA = AreaType.FERNEY;
    private static final AreaType UPDATED_AREA = AreaType.LE_VALLON;

    private static final NestType DEFAULT_NEST_TYPE = NestType.WOODBOX;
    private static final NestType UPDATED_NEST_TYPE = NestType.WHITE_RP_BOX;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVER = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVER = "BBBBBBBBBB";

    private static final String DEFAULT_GPS_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_GPS_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_GPS_COORDINATE = "AAAAAAAAAA";
    private static final String UPDATED_GPS_COORDINATE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ADD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADD_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SightingRepository sightingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSightingMockMvc;

    private Sighting sighting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sighting createEntity(EntityManager em) {
        Sighting sighting = new Sighting()
            .nestSite(DEFAULT_NEST_SITE)
            .area(DEFAULT_AREA)
            .nestType(DEFAULT_NEST_TYPE)
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH)
            .date(DEFAULT_DATE)
            .observer(DEFAULT_OBSERVER)
            .gpsLatitude(DEFAULT_GPS_LATITUDE)
            .gpsCoordinate(DEFAULT_GPS_COORDINATE)
            .locationName(DEFAULT_LOCATION_NAME)
            .addDate(DEFAULT_ADD_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return sighting;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sighting createUpdatedEntity(EntityManager em) {
        Sighting sighting = new Sighting()
            .nestSite(UPDATED_NEST_SITE)
            .area(UPDATED_AREA)
            .nestType(UPDATED_NEST_TYPE)
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .date(UPDATED_DATE)
            .observer(UPDATED_OBSERVER)
            .gpsLatitude(UPDATED_GPS_LATITUDE)
            .gpsCoordinate(UPDATED_GPS_COORDINATE)
            .locationName(UPDATED_LOCATION_NAME)
            .addDate(UPDATED_ADD_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        return sighting;
    }

    @BeforeEach
    public void initTest() {
        sighting = createEntity(em);
    }

    @Test
    @Transactional
    public void createSighting() throws Exception {
        int databaseSizeBeforeCreate = sightingRepository.findAll().size();
        // Create the Sighting
        restSightingMockMvc.perform(post("/api/sightings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sighting)))
            .andExpect(status().isCreated());

        // Validate the Sighting in the database
        List<Sighting> sightingList = sightingRepository.findAll();
        assertThat(sightingList).hasSize(databaseSizeBeforeCreate + 1);
        Sighting testSighting = sightingList.get(sightingList.size() - 1);
        assertThat(testSighting.getNestSite()).isEqualTo(DEFAULT_NEST_SITE);
        assertThat(testSighting.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testSighting.getNestType()).isEqualTo(DEFAULT_NEST_TYPE);
        assertThat(testSighting.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testSighting.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testSighting.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSighting.getObserver()).isEqualTo(DEFAULT_OBSERVER);
        assertThat(testSighting.getGpsLatitude()).isEqualTo(DEFAULT_GPS_LATITUDE);
        assertThat(testSighting.getGpsCoordinate()).isEqualTo(DEFAULT_GPS_COORDINATE);
        assertThat(testSighting.getLocationName()).isEqualTo(DEFAULT_LOCATION_NAME);
        assertThat(testSighting.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
        assertThat(testSighting.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createSightingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sightingRepository.findAll().size();

        // Create the Sighting with an existing ID
        sighting.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSightingMockMvc.perform(post("/api/sightings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sighting)))
            .andExpect(status().isBadRequest());

        // Validate the Sighting in the database
        List<Sighting> sightingList = sightingRepository.findAll();
        assertThat(sightingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNestSiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = sightingRepository.findAll().size();
        // set the field null
        sighting.setNestSite(null);

        // Create the Sighting, which fails.


        restSightingMockMvc.perform(post("/api/sightings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sighting)))
            .andExpect(status().isBadRequest());

        List<Sighting> sightingList = sightingRepository.findAll();
        assertThat(sightingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = sightingRepository.findAll().size();
        // set the field null
        sighting.setArea(null);

        // Create the Sighting, which fails.


        restSightingMockMvc.perform(post("/api/sightings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sighting)))
            .andExpect(status().isBadRequest());

        List<Sighting> sightingList = sightingRepository.findAll();
        assertThat(sightingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkObserverIsRequired() throws Exception {
        int databaseSizeBeforeTest = sightingRepository.findAll().size();
        // set the field null
        sighting.setObserver(null);

        // Create the Sighting, which fails.


        restSightingMockMvc.perform(post("/api/sightings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sighting)))
            .andExpect(status().isBadRequest());

        List<Sighting> sightingList = sightingRepository.findAll();
        assertThat(sightingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sightingRepository.findAll().size();
        // set the field null
        sighting.setAddDate(null);

        // Create the Sighting, which fails.


        restSightingMockMvc.perform(post("/api/sightings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sighting)))
            .andExpect(status().isBadRequest());

        List<Sighting> sightingList = sightingRepository.findAll();
        assertThat(sightingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = sightingRepository.findAll().size();
        // set the field null
        sighting.setUpdateDate(null);

        // Create the Sighting, which fails.


        restSightingMockMvc.perform(post("/api/sightings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sighting)))
            .andExpect(status().isBadRequest());

        List<Sighting> sightingList = sightingRepository.findAll();
        assertThat(sightingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSightings() throws Exception {
        // Initialize the database
        sightingRepository.saveAndFlush(sighting);

        // Get all the sightingList
        restSightingMockMvc.perform(get("/api/sightings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sighting.getId().intValue())))
            .andExpect(jsonPath("$.[*].nestSite").value(hasItem(DEFAULT_NEST_SITE.toString())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.toString())))
            .andExpect(jsonPath("$.[*].nestType").value(hasItem(DEFAULT_NEST_TYPE.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].observer").value(hasItem(DEFAULT_OBSERVER)))
            .andExpect(jsonPath("$.[*].gpsLatitude").value(hasItem(DEFAULT_GPS_LATITUDE)))
            .andExpect(jsonPath("$.[*].gpsCoordinate").value(hasItem(DEFAULT_GPS_COORDINATE)))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME)))
            .andExpect(jsonPath("$.[*].addDate").value(hasItem(DEFAULT_ADD_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getSighting() throws Exception {
        // Initialize the database
        sightingRepository.saveAndFlush(sighting);

        // Get the sighting
        restSightingMockMvc.perform(get("/api/sightings/{id}", sighting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sighting.getId().intValue()))
            .andExpect(jsonPath("$.nestSite").value(DEFAULT_NEST_SITE.toString()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.toString()))
            .andExpect(jsonPath("$.nestType").value(DEFAULT_NEST_TYPE.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.observer").value(DEFAULT_OBSERVER))
            .andExpect(jsonPath("$.gpsLatitude").value(DEFAULT_GPS_LATITUDE))
            .andExpect(jsonPath("$.gpsCoordinate").value(DEFAULT_GPS_COORDINATE))
            .andExpect(jsonPath("$.locationName").value(DEFAULT_LOCATION_NAME))
            .andExpect(jsonPath("$.addDate").value(DEFAULT_ADD_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSighting() throws Exception {
        // Get the sighting
        restSightingMockMvc.perform(get("/api/sightings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSighting() throws Exception {
        // Initialize the database
        sightingRepository.saveAndFlush(sighting);

        int databaseSizeBeforeUpdate = sightingRepository.findAll().size();

        // Update the sighting
        Sighting updatedSighting = sightingRepository.findById(sighting.getId()).get();
        // Disconnect from session so that the updates on updatedSighting are not directly saved in db
        em.detach(updatedSighting);
        updatedSighting
            .nestSite(UPDATED_NEST_SITE)
            .area(UPDATED_AREA)
            .nestType(UPDATED_NEST_TYPE)
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .date(UPDATED_DATE)
            .observer(UPDATED_OBSERVER)
            .gpsLatitude(UPDATED_GPS_LATITUDE)
            .gpsCoordinate(UPDATED_GPS_COORDINATE)
            .locationName(UPDATED_LOCATION_NAME)
            .addDate(UPDATED_ADD_DATE)
            .updateDate(UPDATED_UPDATE_DATE);

        restSightingMockMvc.perform(put("/api/sightings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSighting)))
            .andExpect(status().isOk());

        // Validate the Sighting in the database
        List<Sighting> sightingList = sightingRepository.findAll();
        assertThat(sightingList).hasSize(databaseSizeBeforeUpdate);
        Sighting testSighting = sightingList.get(sightingList.size() - 1);
        assertThat(testSighting.getNestSite()).isEqualTo(UPDATED_NEST_SITE);
        assertThat(testSighting.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testSighting.getNestType()).isEqualTo(UPDATED_NEST_TYPE);
        assertThat(testSighting.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSighting.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testSighting.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSighting.getObserver()).isEqualTo(UPDATED_OBSERVER);
        assertThat(testSighting.getGpsLatitude()).isEqualTo(UPDATED_GPS_LATITUDE);
        assertThat(testSighting.getGpsCoordinate()).isEqualTo(UPDATED_GPS_COORDINATE);
        assertThat(testSighting.getLocationName()).isEqualTo(UPDATED_LOCATION_NAME);
        assertThat(testSighting.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
        assertThat(testSighting.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSighting() throws Exception {
        int databaseSizeBeforeUpdate = sightingRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSightingMockMvc.perform(put("/api/sightings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sighting)))
            .andExpect(status().isBadRequest());

        // Validate the Sighting in the database
        List<Sighting> sightingList = sightingRepository.findAll();
        assertThat(sightingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSighting() throws Exception {
        // Initialize the database
        sightingRepository.saveAndFlush(sighting);

        int databaseSizeBeforeDelete = sightingRepository.findAll().size();

        // Delete the sighting
        restSightingMockMvc.perform(delete("/api/sightings/{id}", sighting.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sighting> sightingList = sightingRepository.findAll();
        assertThat(sightingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
