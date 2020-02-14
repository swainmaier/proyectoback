package com.unrn.redm.web.rest;

import com.unrn.redm.RedmProyectosApp;
import com.unrn.redm.domain.SubGenero;
import com.unrn.redm.repository.SubGeneroRepository;
import com.unrn.redm.service.SubGeneroService;
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
 * Integration tests for the {@link SubGeneroResource} REST controller.
 */
@SpringBootTest(classes = RedmProyectosApp.class)
public class SubGeneroResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    @Autowired
    private SubGeneroRepository subGeneroRepository;

    @Autowired
    private SubGeneroService subGeneroService;

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

    private MockMvc restSubGeneroMockMvc;

    private SubGenero subGenero;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubGeneroResource subGeneroResource = new SubGeneroResource(subGeneroService);
        this.restSubGeneroMockMvc = MockMvcBuilders.standaloneSetup(subGeneroResource)
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
    public static SubGenero createEntity(EntityManager em) {
        SubGenero subGenero = new SubGenero()
            .titulo(DEFAULT_TITULO);
        return subGenero;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubGenero createUpdatedEntity(EntityManager em) {
        SubGenero subGenero = new SubGenero()
            .titulo(UPDATED_TITULO);
        return subGenero;
    }

    @BeforeEach
    public void initTest() {
        subGenero = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubGenero() throws Exception {
        int databaseSizeBeforeCreate = subGeneroRepository.findAll().size();

        // Create the SubGenero
        restSubGeneroMockMvc.perform(post("/api/sub-generos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subGenero)))
            .andExpect(status().isCreated());

        // Validate the SubGenero in the database
        List<SubGenero> subGeneroList = subGeneroRepository.findAll();
        assertThat(subGeneroList).hasSize(databaseSizeBeforeCreate + 1);
        SubGenero testSubGenero = subGeneroList.get(subGeneroList.size() - 1);
        assertThat(testSubGenero.getTitulo()).isEqualTo(DEFAULT_TITULO);
    }

    @Test
    @Transactional
    public void createSubGeneroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subGeneroRepository.findAll().size();

        // Create the SubGenero with an existing ID
        subGenero.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubGeneroMockMvc.perform(post("/api/sub-generos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subGenero)))
            .andExpect(status().isBadRequest());

        // Validate the SubGenero in the database
        List<SubGenero> subGeneroList = subGeneroRepository.findAll();
        assertThat(subGeneroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSubGeneros() throws Exception {
        // Initialize the database
        subGeneroRepository.saveAndFlush(subGenero);

        // Get all the subGeneroList
        restSubGeneroMockMvc.perform(get("/api/sub-generos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subGenero.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)));
    }
    
    @Test
    @Transactional
    public void getSubGenero() throws Exception {
        // Initialize the database
        subGeneroRepository.saveAndFlush(subGenero);

        // Get the subGenero
        restSubGeneroMockMvc.perform(get("/api/sub-generos/{id}", subGenero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subGenero.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO));
    }

    @Test
    @Transactional
    public void getNonExistingSubGenero() throws Exception {
        // Get the subGenero
        restSubGeneroMockMvc.perform(get("/api/sub-generos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubGenero() throws Exception {
        // Initialize the database
        subGeneroService.save(subGenero);

        int databaseSizeBeforeUpdate = subGeneroRepository.findAll().size();

        // Update the subGenero
        SubGenero updatedSubGenero = subGeneroRepository.findById(subGenero.getId()).get();
        // Disconnect from session so that the updates on updatedSubGenero are not directly saved in db
        em.detach(updatedSubGenero);
        updatedSubGenero
            .titulo(UPDATED_TITULO);

        restSubGeneroMockMvc.perform(put("/api/sub-generos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubGenero)))
            .andExpect(status().isOk());

        // Validate the SubGenero in the database
        List<SubGenero> subGeneroList = subGeneroRepository.findAll();
        assertThat(subGeneroList).hasSize(databaseSizeBeforeUpdate);
        SubGenero testSubGenero = subGeneroList.get(subGeneroList.size() - 1);
        assertThat(testSubGenero.getTitulo()).isEqualTo(UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void updateNonExistingSubGenero() throws Exception {
        int databaseSizeBeforeUpdate = subGeneroRepository.findAll().size();

        // Create the SubGenero

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubGeneroMockMvc.perform(put("/api/sub-generos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subGenero)))
            .andExpect(status().isBadRequest());

        // Validate the SubGenero in the database
        List<SubGenero> subGeneroList = subGeneroRepository.findAll();
        assertThat(subGeneroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubGenero() throws Exception {
        // Initialize the database
        subGeneroService.save(subGenero);

        int databaseSizeBeforeDelete = subGeneroRepository.findAll().size();

        // Delete the subGenero
        restSubGeneroMockMvc.perform(delete("/api/sub-generos/{id}", subGenero.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubGenero> subGeneroList = subGeneroRepository.findAll();
        assertThat(subGeneroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
