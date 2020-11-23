package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.ObservationLocation;
import com.wildlife.fody.repository.ObservationLocationRepository;

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

/**
 * Integration tests for the {@link ObservationLocationResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ObservationLocationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GPS_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_GPS_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_GPS_COORDINATE = "AAAAAAAAAA";
    private static final String UPDATED_GPS_COORDINATE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ADD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADD_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ObservationLocationRepository observationLocationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObservationLocationMockMvc;

    private ObservationLocation observationLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObservationLocation createEntity(EntityManager em) {
        ObservationLocation observationLocation = new ObservationLocation()
            .name(DEFAULT_NAME)
            .gpsLatitude(DEFAULT_GPS_LATITUDE)
            .gpsCoordinate(DEFAULT_GPS_COORDINATE)
            .locationName(DEFAULT_LOCATION_NAME)
            .description(DEFAULT_DESCRIPTION)
            .addDate(DEFAULT_ADD_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return observationLocation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObservationLocation createUpdatedEntity(EntityManager em) {
        ObservationLocation observationLocation = new ObservationLocation()
            .name(UPDATED_NAME)
            .gpsLatitude(UPDATED_GPS_LATITUDE)
            .gpsCoordinate(UPDATED_GPS_COORDINATE)
            .locationName(UPDATED_LOCATION_NAME)
            .description(UPDATED_DESCRIPTION)
            .addDate(UPDATED_ADD_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        return observationLocation;
    }

    @BeforeEach
    public void initTest() {
        observationLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createObservationLocation() throws Exception {
        int databaseSizeBeforeCreate = observationLocationRepository.findAll().size();
        // Create the ObservationLocation
        restObservationLocationMockMvc.perform(post("/api/observation-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observationLocation)))
            .andExpect(status().isCreated());

        // Validate the ObservationLocation in the database
        List<ObservationLocation> observationLocationList = observationLocationRepository.findAll();
        assertThat(observationLocationList).hasSize(databaseSizeBeforeCreate + 1);
        ObservationLocation testObservationLocation = observationLocationList.get(observationLocationList.size() - 1);
        assertThat(testObservationLocation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testObservationLocation.getGpsLatitude()).isEqualTo(DEFAULT_GPS_LATITUDE);
        assertThat(testObservationLocation.getGpsCoordinate()).isEqualTo(DEFAULT_GPS_COORDINATE);
        assertThat(testObservationLocation.getLocationName()).isEqualTo(DEFAULT_LOCATION_NAME);
        assertThat(testObservationLocation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testObservationLocation.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
        assertThat(testObservationLocation.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createObservationLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = observationLocationRepository.findAll().size();

        // Create the ObservationLocation with an existing ID
        observationLocation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObservationLocationMockMvc.perform(post("/api/observation-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observationLocation)))
            .andExpect(status().isBadRequest());

        // Validate the ObservationLocation in the database
        List<ObservationLocation> observationLocationList = observationLocationRepository.findAll();
        assertThat(observationLocationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllObservationLocations() throws Exception {
        // Initialize the database
        observationLocationRepository.saveAndFlush(observationLocation);

        // Get all the observationLocationList
        restObservationLocationMockMvc.perform(get("/api/observation-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observationLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].gpsLatitude").value(hasItem(DEFAULT_GPS_LATITUDE)))
            .andExpect(jsonPath("$.[*].gpsCoordinate").value(hasItem(DEFAULT_GPS_COORDINATE)))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].addDate").value(hasItem(DEFAULT_ADD_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getObservationLocation() throws Exception {
        // Initialize the database
        observationLocationRepository.saveAndFlush(observationLocation);

        // Get the observationLocation
        restObservationLocationMockMvc.perform(get("/api/observation-locations/{id}", observationLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(observationLocation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.gpsLatitude").value(DEFAULT_GPS_LATITUDE))
            .andExpect(jsonPath("$.gpsCoordinate").value(DEFAULT_GPS_COORDINATE))
            .andExpect(jsonPath("$.locationName").value(DEFAULT_LOCATION_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.addDate").value(DEFAULT_ADD_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingObservationLocation() throws Exception {
        // Get the observationLocation
        restObservationLocationMockMvc.perform(get("/api/observation-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObservationLocation() throws Exception {
        // Initialize the database
        observationLocationRepository.saveAndFlush(observationLocation);

        int databaseSizeBeforeUpdate = observationLocationRepository.findAll().size();

        // Update the observationLocation
        ObservationLocation updatedObservationLocation = observationLocationRepository.findById(observationLocation.getId()).get();
        // Disconnect from session so that the updates on updatedObservationLocation are not directly saved in db
        em.detach(updatedObservationLocation);
        updatedObservationLocation
            .name(UPDATED_NAME)
            .gpsLatitude(UPDATED_GPS_LATITUDE)
            .gpsCoordinate(UPDATED_GPS_COORDINATE)
            .locationName(UPDATED_LOCATION_NAME)
            .description(UPDATED_DESCRIPTION)
            .addDate(UPDATED_ADD_DATE)
            .updateDate(UPDATED_UPDATE_DATE);

        restObservationLocationMockMvc.perform(put("/api/observation-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedObservationLocation)))
            .andExpect(status().isOk());

        // Validate the ObservationLocation in the database
        List<ObservationLocation> observationLocationList = observationLocationRepository.findAll();
        assertThat(observationLocationList).hasSize(databaseSizeBeforeUpdate);
        ObservationLocation testObservationLocation = observationLocationList.get(observationLocationList.size() - 1);
        assertThat(testObservationLocation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testObservationLocation.getGpsLatitude()).isEqualTo(UPDATED_GPS_LATITUDE);
        assertThat(testObservationLocation.getGpsCoordinate()).isEqualTo(UPDATED_GPS_COORDINATE);
        assertThat(testObservationLocation.getLocationName()).isEqualTo(UPDATED_LOCATION_NAME);
        assertThat(testObservationLocation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testObservationLocation.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
        assertThat(testObservationLocation.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingObservationLocation() throws Exception {
        int databaseSizeBeforeUpdate = observationLocationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObservationLocationMockMvc.perform(put("/api/observation-locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observationLocation)))
            .andExpect(status().isBadRequest());

        // Validate the ObservationLocation in the database
        List<ObservationLocation> observationLocationList = observationLocationRepository.findAll();
        assertThat(observationLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteObservationLocation() throws Exception {
        // Initialize the database
        observationLocationRepository.saveAndFlush(observationLocation);

        int databaseSizeBeforeDelete = observationLocationRepository.findAll().size();

        // Delete the observationLocation
        restObservationLocationMockMvc.perform(delete("/api/observation-locations/{id}", observationLocation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ObservationLocation> observationLocationList = observationLocationRepository.findAll();
        assertThat(observationLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
