package com.wildlife.fody.web.rest;

import com.wildlife.fody.WildlifeApp;
import com.wildlife.fody.domain.Country;
import com.wildlife.fody.repository.CountryRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CountryResource} REST controller.
 */
@SpringBootTest(classes = WildlifeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CountryResourceIT {

    private static final String DEFAULT_COUNTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ADD_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADD_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CountryRepository countryRepository;

    @Mock
    private CountryRepository countryRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCountryMockMvc;

    private Country country;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createEntity(EntityManager em) {
        Country country = new Country()
            .countryName(DEFAULT_COUNTRY_NAME)
            .addDate(DEFAULT_ADD_DATE);
        return country;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Country createUpdatedEntity(EntityManager em) {
        Country country = new Country()
            .countryName(UPDATED_COUNTRY_NAME)
            .addDate(UPDATED_ADD_DATE);
        return country;
    }

    @BeforeEach
    public void initTest() {
        country = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountry() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();
        // Create the Country
        restCountryMockMvc.perform(post("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isCreated());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate + 1);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCountryName()).isEqualTo(DEFAULT_COUNTRY_NAME);
        assertThat(testCountry.getAddDate()).isEqualTo(DEFAULT_ADD_DATE);
    }

    @Test
    @Transactional
    public void createCountryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countryRepository.findAll().size();

        // Create the Country with an existing ID
        country.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryMockMvc.perform(post("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCountryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setCountryName(null);

        // Create the Country, which fails.


        restCountryMockMvc.perform(post("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryRepository.findAll().size();
        // set the field null
        country.setAddDate(null);

        // Create the Country, which fails.


        restCountryMockMvc.perform(post("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isBadRequest());

        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCountries() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get all the countryList
        restCountryMockMvc.perform(get("/api/countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(country.getId().intValue())))
            .andExpect(jsonPath("$.[*].countryName").value(hasItem(DEFAULT_COUNTRY_NAME)))
            .andExpect(jsonPath("$.[*].addDate").value(hasItem(DEFAULT_ADD_DATE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCountriesWithEagerRelationshipsIsEnabled() throws Exception {
        when(countryRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCountryMockMvc.perform(get("/api/countries?eagerload=true"))
            .andExpect(status().isOk());

        verify(countryRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCountriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(countryRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCountryMockMvc.perform(get("/api/countries?eagerload=true"))
            .andExpect(status().isOk());

        verify(countryRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", country.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(country.getId().intValue()))
            .andExpect(jsonPath("$.countryName").value(DEFAULT_COUNTRY_NAME))
            .andExpect(jsonPath("$.addDate").value(DEFAULT_ADD_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCountry() throws Exception {
        // Get the country
        restCountryMockMvc.perform(get("/api/countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // Update the country
        Country updatedCountry = countryRepository.findById(country.getId()).get();
        // Disconnect from session so that the updates on updatedCountry are not directly saved in db
        em.detach(updatedCountry);
        updatedCountry
            .countryName(UPDATED_COUNTRY_NAME)
            .addDate(UPDATED_ADD_DATE);

        restCountryMockMvc.perform(put("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCountry)))
            .andExpect(status().isOk());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
        Country testCountry = countryList.get(countryList.size() - 1);
        assertThat(testCountry.getCountryName()).isEqualTo(UPDATED_COUNTRY_NAME);
        assertThat(testCountry.getAddDate()).isEqualTo(UPDATED_ADD_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCountry() throws Exception {
        int databaseSizeBeforeUpdate = countryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountryMockMvc.perform(put("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(country)))
            .andExpect(status().isBadRequest());

        // Validate the Country in the database
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCountry() throws Exception {
        // Initialize the database
        countryRepository.saveAndFlush(country);

        int databaseSizeBeforeDelete = countryRepository.findAll().size();

        // Delete the country
        restCountryMockMvc.perform(delete("/api/countries/{id}", country.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Country> countryList = countryRepository.findAll();
        assertThat(countryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
