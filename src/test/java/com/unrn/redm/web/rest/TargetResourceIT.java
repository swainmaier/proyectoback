package com.unrn.redm.web.rest;

import com.unrn.redm.RedmProyectosApp;
import com.unrn.redm.domain.Target;
import com.unrn.redm.repository.TargetRepository;
import com.unrn.redm.service.TargetService;
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
 * Integration tests for the {@link TargetResource} REST controller.
 */
@SpringBootTest(classes = RedmProyectosApp.class)
public class TargetResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    @Autowired
    private TargetRepository targetRepository;

    @Autowired
    private TargetService targetService;

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

    private MockMvc restTargetMockMvc;

    private Target target;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TargetResource targetResource = new TargetResource(targetService);
        this.restTargetMockMvc = MockMvcBuilders.standaloneSetup(targetResource)
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
    public static Target createEntity(EntityManager em) {
        Target target = new Target()
            .titulo(DEFAULT_TITULO);
        return target;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Target createUpdatedEntity(EntityManager em) {
        Target target = new Target()
            .titulo(UPDATED_TITULO);
        return target;
    }

    @BeforeEach
    public void initTest() {
        target = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarget() throws Exception {
        int databaseSizeBeforeCreate = targetRepository.findAll().size();

        // Create the Target
        restTargetMockMvc.perform(post("/api/targets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(target)))
            .andExpect(status().isCreated());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeCreate + 1);
        Target testTarget = targetList.get(targetList.size() - 1);
        assertThat(testTarget.getTitulo()).isEqualTo(DEFAULT_TITULO);
    }

    @Test
    @Transactional
    public void createTargetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = targetRepository.findAll().size();

        // Create the Target with an existing ID
        target.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTargetMockMvc.perform(post("/api/targets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(target)))
            .andExpect(status().isBadRequest());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTargets() throws Exception {
        // Initialize the database
        targetRepository.saveAndFlush(target);

        // Get all the targetList
        restTargetMockMvc.perform(get("/api/targets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(target.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)));
    }
    
    @Test
    @Transactional
    public void getTarget() throws Exception {
        // Initialize the database
        targetRepository.saveAndFlush(target);

        // Get the target
        restTargetMockMvc.perform(get("/api/targets/{id}", target.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(target.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO));
    }

    @Test
    @Transactional
    public void getNonExistingTarget() throws Exception {
        // Get the target
        restTargetMockMvc.perform(get("/api/targets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarget() throws Exception {
        // Initialize the database
        targetService.save(target);

        int databaseSizeBeforeUpdate = targetRepository.findAll().size();

        // Update the target
        Target updatedTarget = targetRepository.findById(target.getId()).get();
        // Disconnect from session so that the updates on updatedTarget are not directly saved in db
        em.detach(updatedTarget);
        updatedTarget
            .titulo(UPDATED_TITULO);

        restTargetMockMvc.perform(put("/api/targets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarget)))
            .andExpect(status().isOk());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeUpdate);
        Target testTarget = targetList.get(targetList.size() - 1);
        assertThat(testTarget.getTitulo()).isEqualTo(UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void updateNonExistingTarget() throws Exception {
        int databaseSizeBeforeUpdate = targetRepository.findAll().size();

        // Create the Target

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTargetMockMvc.perform(put("/api/targets")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(target)))
            .andExpect(status().isBadRequest());

        // Validate the Target in the database
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarget() throws Exception {
        // Initialize the database
        targetService.save(target);

        int databaseSizeBeforeDelete = targetRepository.findAll().size();

        // Delete the target
        restTargetMockMvc.perform(delete("/api/targets/{id}", target.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Target> targetList = targetRepository.findAll();
        assertThat(targetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
