package com.unrn.redm.web.rest;

import com.unrn.redm.RedmProyectosApp;
import com.unrn.redm.domain.Genero;
import com.unrn.redm.repository.GeneroRepository;
import com.unrn.redm.service.GeneroService;
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
 * Integration tests for the {@link GeneroResource} REST controller.
 */
@SpringBootTest(classes = RedmProyectosApp.class)
public class GeneroResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private GeneroService generoService;

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

    private MockMvc restGeneroMockMvc;

    private Genero genero;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GeneroResource generoResource = new GeneroResource(generoService);
        this.restGeneroMockMvc = MockMvcBuilders.standaloneSetup(generoResource)
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
    public static Genero createEntity(EntityManager em) {
        Genero genero = new Genero()
            .titulo(DEFAULT_TITULO);
        return genero;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Genero createUpdatedEntity(EntityManager em) {
        Genero genero = new Genero()
            .titulo(UPDATED_TITULO);
        return genero;
    }

    @BeforeEach
    public void initTest() {
        genero = createEntity(em);
    }

    @Test
    @Transactional
    public void createGenero() throws Exception {
        int databaseSizeBeforeCreate = generoRepository.findAll().size();

        // Create the Genero
        restGeneroMockMvc.perform(post("/api/generos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(genero)))
            .andExpect(status().isCreated());

        // Validate the Genero in the database
        List<Genero> generoList = generoRepository.findAll();
        assertThat(generoList).hasSize(databaseSizeBeforeCreate + 1);
        Genero testGenero = generoList.get(generoList.size() - 1);
        assertThat(testGenero.getTitulo()).isEqualTo(DEFAULT_TITULO);
    }

    @Test
    @Transactional
    public void createGeneroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = generoRepository.findAll().size();

        // Create the Genero with an existing ID
        genero.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeneroMockMvc.perform(post("/api/generos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(genero)))
            .andExpect(status().isBadRequest());

        // Validate the Genero in the database
        List<Genero> generoList = generoRepository.findAll();
        assertThat(generoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGeneros() throws Exception {
        // Initialize the database
        generoRepository.saveAndFlush(genero);

        // Get all the generoList
        restGeneroMockMvc.perform(get("/api/generos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(genero.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)));
    }
    
    @Test
    @Transactional
    public void getGenero() throws Exception {
        // Initialize the database
        generoRepository.saveAndFlush(genero);

        // Get the genero
        restGeneroMockMvc.perform(get("/api/generos/{id}", genero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(genero.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO));
    }

    @Test
    @Transactional
    public void getNonExistingGenero() throws Exception {
        // Get the genero
        restGeneroMockMvc.perform(get("/api/generos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGenero() throws Exception {
        // Initialize the database
        generoService.save(genero);

        int databaseSizeBeforeUpdate = generoRepository.findAll().size();

        // Update the genero
        Genero updatedGenero = generoRepository.findById(genero.getId()).get();
        // Disconnect from session so that the updates on updatedGenero are not directly saved in db
        em.detach(updatedGenero);
        updatedGenero
            .titulo(UPDATED_TITULO);

        restGeneroMockMvc.perform(put("/api/generos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGenero)))
            .andExpect(status().isOk());

        // Validate the Genero in the database
        List<Genero> generoList = generoRepository.findAll();
        assertThat(generoList).hasSize(databaseSizeBeforeUpdate);
        Genero testGenero = generoList.get(generoList.size() - 1);
        assertThat(testGenero.getTitulo()).isEqualTo(UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void updateNonExistingGenero() throws Exception {
        int databaseSizeBeforeUpdate = generoRepository.findAll().size();

        // Create the Genero

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeneroMockMvc.perform(put("/api/generos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(genero)))
            .andExpect(status().isBadRequest());

        // Validate the Genero in the database
        List<Genero> generoList = generoRepository.findAll();
        assertThat(generoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGenero() throws Exception {
        // Initialize the database
        generoService.save(genero);

        int databaseSizeBeforeDelete = generoRepository.findAll().size();

        // Delete the genero
        restGeneroMockMvc.perform(delete("/api/generos/{id}", genero.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Genero> generoList = generoRepository.findAll();
        assertThat(generoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
