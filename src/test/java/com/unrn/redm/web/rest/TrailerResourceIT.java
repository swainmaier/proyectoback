package com.unrn.redm.web.rest;

import com.unrn.redm.RedmProyectosApp;
import com.unrn.redm.domain.Trailer;
import com.unrn.redm.repository.TrailerRepository;
import com.unrn.redm.service.TrailerService;
import com.unrn.redm.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.unrn.redm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TrailerResource} REST controller.
 */
@SpringBootTest(classes = RedmProyectosApp.class)
public class TrailerResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private TrailerRepository trailerRepository;

    @Autowired
    private TrailerService trailerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTrailerMockMvc;

    private Trailer trailer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrailerResource trailerResource = new TrailerResource(trailerService);
        this.restTrailerMockMvc = MockMvcBuilders.standaloneSetup(trailerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trailer createEntity(EntityManager em) {
        Trailer trailer = new Trailer()
            .titulo(DEFAULT_TITULO)
            .url(DEFAULT_URL);
        return trailer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trailer createUpdatedEntity(EntityManager em) {
        Trailer trailer = new Trailer()
            .titulo(UPDATED_TITULO)
            .url(UPDATED_URL);
        return trailer;
    }

    @BeforeEach
    public void initTest() {
        trailer = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrailer() throws Exception {
        int databaseSizeBeforeCreate = trailerRepository.findAll().size();

        // Create the Trailer
        restTrailerMockMvc.perform(post("/api/trailers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trailer)))
            .andExpect(status().isCreated());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeCreate + 1);
        Trailer testTrailer = trailerList.get(trailerList.size() - 1);
        assertThat(testTrailer.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testTrailer.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createTrailerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trailerRepository.findAll().size();

        // Create the Trailer with an existing ID
        trailer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrailerMockMvc.perform(post("/api/trailers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trailer)))
            .andExpect(status().isBadRequest());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTrailers() throws Exception {
        // Initialize the database
        trailerRepository.saveAndFlush(trailer);

        // Get all the trailerList
        restTrailerMockMvc.perform(get("/api/trailers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trailer.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
    
    @Test
    @Transactional
    public void getTrailer() throws Exception {
        // Initialize the database
        trailerRepository.saveAndFlush(trailer);

        // Get the trailer
        restTrailerMockMvc.perform(get("/api/trailers/{id}", trailer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trailer.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }

    @Test
    @Transactional
    public void getNonExistingTrailer() throws Exception {
        // Get the trailer
        restTrailerMockMvc.perform(get("/api/trailers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrailer() throws Exception {
        // Initialize the database
        trailerService.save(trailer);

        int databaseSizeBeforeUpdate = trailerRepository.findAll().size();

        // Update the trailer
        Trailer updatedTrailer = trailerRepository.findById(trailer.getId()).get();
        // Disconnect from session so that the updates on updatedTrailer are not directly saved in db
        em.detach(updatedTrailer);
        updatedTrailer
            .titulo(UPDATED_TITULO)
            .url(UPDATED_URL);

        restTrailerMockMvc.perform(put("/api/trailers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrailer)))
            .andExpect(status().isOk());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeUpdate);
        Trailer testTrailer = trailerList.get(trailerList.size() - 1);
        assertThat(testTrailer.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testTrailer.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingTrailer() throws Exception {
        int databaseSizeBeforeUpdate = trailerRepository.findAll().size();

        // Create the Trailer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrailerMockMvc.perform(put("/api/trailers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trailer)))
            .andExpect(status().isBadRequest());

        // Validate the Trailer in the database
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrailer() throws Exception {
        // Initialize the database
        trailerService.save(trailer);

        int databaseSizeBeforeDelete = trailerRepository.findAll().size();

        // Delete the trailer
        restTrailerMockMvc.perform(delete("/api/trailers/{id}", trailer.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trailer> trailerList = trailerRepository.findAll();
        assertThat(trailerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
