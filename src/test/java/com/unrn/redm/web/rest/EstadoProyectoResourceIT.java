package com.unrn.redm.web.rest;

import com.unrn.redm.RedmProyectosApp;
import com.unrn.redm.domain.EstadoProyecto;
import com.unrn.redm.repository.EstadoProyectoRepository;
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
 * Integration tests for the {@link EstadoProyectoResource} REST controller.
 */
@SpringBootTest(classes = RedmProyectosApp.class)
public class EstadoProyectoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    @Autowired
    private EstadoProyectoRepository estadoProyectoRepository;

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

    private MockMvc restEstadoProyectoMockMvc;

    private EstadoProyecto estadoProyecto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstadoProyectoResource estadoProyectoResource = new EstadoProyectoResource(estadoProyectoRepository);
        this.restEstadoProyectoMockMvc = MockMvcBuilders.standaloneSetup(estadoProyectoResource)
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
    public static EstadoProyecto createEntity(EntityManager em) {
        EstadoProyecto estadoProyecto = new EstadoProyecto()
            .titulo(DEFAULT_TITULO);
        return estadoProyecto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoProyecto createUpdatedEntity(EntityManager em) {
        EstadoProyecto estadoProyecto = new EstadoProyecto()
            .titulo(UPDATED_TITULO);
        return estadoProyecto;
    }

    @BeforeEach
    public void initTest() {
        estadoProyecto = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstadoProyecto() throws Exception {
        int databaseSizeBeforeCreate = estadoProyectoRepository.findAll().size();

        // Create the EstadoProyecto
        restEstadoProyectoMockMvc.perform(post("/api/estado-proyectos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoProyecto)))
            .andExpect(status().isCreated());

        // Validate the EstadoProyecto in the database
        List<EstadoProyecto> estadoProyectoList = estadoProyectoRepository.findAll();
        assertThat(estadoProyectoList).hasSize(databaseSizeBeforeCreate + 1);
        EstadoProyecto testEstadoProyecto = estadoProyectoList.get(estadoProyectoList.size() - 1);
        assertThat(testEstadoProyecto.getTitulo()).isEqualTo(DEFAULT_TITULO);
    }

    @Test
    @Transactional
    public void createEstadoProyectoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estadoProyectoRepository.findAll().size();

        // Create the EstadoProyecto with an existing ID
        estadoProyecto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoProyectoMockMvc.perform(post("/api/estado-proyectos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoProyecto)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoProyecto in the database
        List<EstadoProyecto> estadoProyectoList = estadoProyectoRepository.findAll();
        assertThat(estadoProyectoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstadoProyectos() throws Exception {
        // Initialize the database
        estadoProyectoRepository.saveAndFlush(estadoProyecto);

        // Get all the estadoProyectoList
        restEstadoProyectoMockMvc.perform(get("/api/estado-proyectos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoProyecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)));
    }
    
    @Test
    @Transactional
    public void getEstadoProyecto() throws Exception {
        // Initialize the database
        estadoProyectoRepository.saveAndFlush(estadoProyecto);

        // Get the estadoProyecto
        restEstadoProyectoMockMvc.perform(get("/api/estado-proyectos/{id}", estadoProyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estadoProyecto.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO));
    }

    @Test
    @Transactional
    public void getNonExistingEstadoProyecto() throws Exception {
        // Get the estadoProyecto
        restEstadoProyectoMockMvc.perform(get("/api/estado-proyectos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstadoProyecto() throws Exception {
        // Initialize the database
        estadoProyectoRepository.saveAndFlush(estadoProyecto);

        int databaseSizeBeforeUpdate = estadoProyectoRepository.findAll().size();

        // Update the estadoProyecto
        EstadoProyecto updatedEstadoProyecto = estadoProyectoRepository.findById(estadoProyecto.getId()).get();
        // Disconnect from session so that the updates on updatedEstadoProyecto are not directly saved in db
        em.detach(updatedEstadoProyecto);
        updatedEstadoProyecto
            .titulo(UPDATED_TITULO);

        restEstadoProyectoMockMvc.perform(put("/api/estado-proyectos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstadoProyecto)))
            .andExpect(status().isOk());

        // Validate the EstadoProyecto in the database
        List<EstadoProyecto> estadoProyectoList = estadoProyectoRepository.findAll();
        assertThat(estadoProyectoList).hasSize(databaseSizeBeforeUpdate);
        EstadoProyecto testEstadoProyecto = estadoProyectoList.get(estadoProyectoList.size() - 1);
        assertThat(testEstadoProyecto.getTitulo()).isEqualTo(UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void updateNonExistingEstadoProyecto() throws Exception {
        int databaseSizeBeforeUpdate = estadoProyectoRepository.findAll().size();

        // Create the EstadoProyecto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoProyectoMockMvc.perform(put("/api/estado-proyectos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoProyecto)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoProyecto in the database
        List<EstadoProyecto> estadoProyectoList = estadoProyectoRepository.findAll();
        assertThat(estadoProyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstadoProyecto() throws Exception {
        // Initialize the database
        estadoProyectoRepository.saveAndFlush(estadoProyecto);

        int databaseSizeBeforeDelete = estadoProyectoRepository.findAll().size();

        // Delete the estadoProyecto
        restEstadoProyectoMockMvc.perform(delete("/api/estado-proyectos/{id}", estadoProyecto.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstadoProyecto> estadoProyectoList = estadoProyectoRepository.findAll();
        assertThat(estadoProyectoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
