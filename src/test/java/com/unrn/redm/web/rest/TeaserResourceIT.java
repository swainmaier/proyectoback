package com.unrn.redm.web.rest;

import com.unrn.redm.RedmProyectosApp;
import com.unrn.redm.domain.Teaser;
import com.unrn.redm.repository.TeaserRepository;
import com.unrn.redm.service.TeaserService;
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
 * Integration tests for the {@link TeaserResource} REST controller.
 */
@SpringBootTest(classes = RedmProyectosApp.class)
public class TeaserResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private TeaserRepository teaserRepository;

    @Autowired
    private TeaserService teaserService;

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

    private MockMvc restTeaserMockMvc;

    private Teaser teaser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeaserResource teaserResource = new TeaserResource(teaserService);
        this.restTeaserMockMvc = MockMvcBuilders.standaloneSetup(teaserResource)
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
    public static Teaser createEntity(EntityManager em) {
        Teaser teaser = new Teaser()
            .titulo(DEFAULT_TITULO)
            .url(DEFAULT_URL);
        return teaser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Teaser createUpdatedEntity(EntityManager em) {
        Teaser teaser = new Teaser()
            .titulo(UPDATED_TITULO)
            .url(UPDATED_URL);
        return teaser;
    }

    @BeforeEach
    public void initTest() {
        teaser = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeaser() throws Exception {
        int databaseSizeBeforeCreate = teaserRepository.findAll().size();

        // Create the Teaser
        restTeaserMockMvc.perform(post("/api/teasers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teaser)))
            .andExpect(status().isCreated());

        // Validate the Teaser in the database
        List<Teaser> teaserList = teaserRepository.findAll();
        assertThat(teaserList).hasSize(databaseSizeBeforeCreate + 1);
        Teaser testTeaser = teaserList.get(teaserList.size() - 1);
        assertThat(testTeaser.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testTeaser.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createTeaserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teaserRepository.findAll().size();

        // Create the Teaser with an existing ID
        teaser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeaserMockMvc.perform(post("/api/teasers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teaser)))
            .andExpect(status().isBadRequest());

        // Validate the Teaser in the database
        List<Teaser> teaserList = teaserRepository.findAll();
        assertThat(teaserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTeasers() throws Exception {
        // Initialize the database
        teaserRepository.saveAndFlush(teaser);

        // Get all the teaserList
        restTeaserMockMvc.perform(get("/api/teasers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teaser.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
    
    @Test
    @Transactional
    public void getTeaser() throws Exception {
        // Initialize the database
        teaserRepository.saveAndFlush(teaser);

        // Get the teaser
        restTeaserMockMvc.perform(get("/api/teasers/{id}", teaser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teaser.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }

    @Test
    @Transactional
    public void getNonExistingTeaser() throws Exception {
        // Get the teaser
        restTeaserMockMvc.perform(get("/api/teasers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeaser() throws Exception {
        // Initialize the database
        teaserService.save(teaser);

        int databaseSizeBeforeUpdate = teaserRepository.findAll().size();

        // Update the teaser
        Teaser updatedTeaser = teaserRepository.findById(teaser.getId()).get();
        // Disconnect from session so that the updates on updatedTeaser are not directly saved in db
        em.detach(updatedTeaser);
        updatedTeaser
            .titulo(UPDATED_TITULO)
            .url(UPDATED_URL);

        restTeaserMockMvc.perform(put("/api/teasers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeaser)))
            .andExpect(status().isOk());

        // Validate the Teaser in the database
        List<Teaser> teaserList = teaserRepository.findAll();
        assertThat(teaserList).hasSize(databaseSizeBeforeUpdate);
        Teaser testTeaser = teaserList.get(teaserList.size() - 1);
        assertThat(testTeaser.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testTeaser.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingTeaser() throws Exception {
        int databaseSizeBeforeUpdate = teaserRepository.findAll().size();

        // Create the Teaser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeaserMockMvc.perform(put("/api/teasers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teaser)))
            .andExpect(status().isBadRequest());

        // Validate the Teaser in the database
        List<Teaser> teaserList = teaserRepository.findAll();
        assertThat(teaserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTeaser() throws Exception {
        // Initialize the database
        teaserService.save(teaser);

        int databaseSizeBeforeDelete = teaserRepository.findAll().size();

        // Delete the teaser
        restTeaserMockMvc.perform(delete("/api/teasers/{id}", teaser.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Teaser> teaserList = teaserRepository.findAll();
        assertThat(teaserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
