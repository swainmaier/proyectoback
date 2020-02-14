package com.unrn.redm.web.rest;

import com.unrn.redm.RedmProyectosApp;
import com.unrn.redm.domain.Plataforma;
import com.unrn.redm.repository.PlataformaRepository;
import com.unrn.redm.service.PlataformaService;
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
 * Integration tests for the {@link PlataformaResource} REST controller.
 */
@SpringBootTest(classes = RedmProyectosApp.class)
public class PlataformaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    @Autowired
    private PlataformaRepository plataformaRepository;

    @Autowired
    private PlataformaService plataformaService;

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

    private MockMvc restPlataformaMockMvc;

    private Plataforma plataforma;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlataformaResource plataformaResource = new PlataformaResource(plataformaService);
        this.restPlataformaMockMvc = MockMvcBuilders.standaloneSetup(plataformaResource)
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
    public static Plataforma createEntity(EntityManager em) {
        Plataforma plataforma = new Plataforma()
            .titulo(DEFAULT_TITULO);
        return plataforma;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plataforma createUpdatedEntity(EntityManager em) {
        Plataforma plataforma = new Plataforma()
            .titulo(UPDATED_TITULO);
        return plataforma;
    }

    @BeforeEach
    public void initTest() {
        plataforma = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlataforma() throws Exception {
        int databaseSizeBeforeCreate = plataformaRepository.findAll().size();

        // Create the Plataforma
        restPlataformaMockMvc.perform(post("/api/plataformas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plataforma)))
            .andExpect(status().isCreated());

        // Validate the Plataforma in the database
        List<Plataforma> plataformaList = plataformaRepository.findAll();
        assertThat(plataformaList).hasSize(databaseSizeBeforeCreate + 1);
        Plataforma testPlataforma = plataformaList.get(plataformaList.size() - 1);
        assertThat(testPlataforma.getTitulo()).isEqualTo(DEFAULT_TITULO);
    }

    @Test
    @Transactional
    public void createPlataformaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plataformaRepository.findAll().size();

        // Create the Plataforma with an existing ID
        plataforma.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlataformaMockMvc.perform(post("/api/plataformas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plataforma)))
            .andExpect(status().isBadRequest());

        // Validate the Plataforma in the database
        List<Plataforma> plataformaList = plataformaRepository.findAll();
        assertThat(plataformaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlataformas() throws Exception {
        // Initialize the database
        plataformaRepository.saveAndFlush(plataforma);

        // Get all the plataformaList
        restPlataformaMockMvc.perform(get("/api/plataformas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plataforma.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)));
    }
    
    @Test
    @Transactional
    public void getPlataforma() throws Exception {
        // Initialize the database
        plataformaRepository.saveAndFlush(plataforma);

        // Get the plataforma
        restPlataformaMockMvc.perform(get("/api/plataformas/{id}", plataforma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plataforma.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO));
    }

    @Test
    @Transactional
    public void getNonExistingPlataforma() throws Exception {
        // Get the plataforma
        restPlataformaMockMvc.perform(get("/api/plataformas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlataforma() throws Exception {
        // Initialize the database
        plataformaService.save(plataforma);

        int databaseSizeBeforeUpdate = plataformaRepository.findAll().size();

        // Update the plataforma
        Plataforma updatedPlataforma = plataformaRepository.findById(plataforma.getId()).get();
        // Disconnect from session so that the updates on updatedPlataforma are not directly saved in db
        em.detach(updatedPlataforma);
        updatedPlataforma
            .titulo(UPDATED_TITULO);

        restPlataformaMockMvc.perform(put("/api/plataformas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlataforma)))
            .andExpect(status().isOk());

        // Validate the Plataforma in the database
        List<Plataforma> plataformaList = plataformaRepository.findAll();
        assertThat(plataformaList).hasSize(databaseSizeBeforeUpdate);
        Plataforma testPlataforma = plataformaList.get(plataformaList.size() - 1);
        assertThat(testPlataforma.getTitulo()).isEqualTo(UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void updateNonExistingPlataforma() throws Exception {
        int databaseSizeBeforeUpdate = plataformaRepository.findAll().size();

        // Create the Plataforma

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlataformaMockMvc.perform(put("/api/plataformas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plataforma)))
            .andExpect(status().isBadRequest());

        // Validate the Plataforma in the database
        List<Plataforma> plataformaList = plataformaRepository.findAll();
        assertThat(plataformaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlataforma() throws Exception {
        // Initialize the database
        plataformaService.save(plataforma);

        int databaseSizeBeforeDelete = plataformaRepository.findAll().size();

        // Delete the plataforma
        restPlataformaMockMvc.perform(delete("/api/plataformas/{id}", plataforma.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plataforma> plataformaList = plataformaRepository.findAll();
        assertThat(plataformaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
