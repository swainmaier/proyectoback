package com.unrn.redm.web.rest;

import com.unrn.redm.RedmProyectosApp;
import com.unrn.redm.domain.EstadoContenido;
import com.unrn.redm.repository.EstadoContenidoRepository;
import com.unrn.redm.service.EstadoContenidoService;
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
 * Integration tests for the {@link EstadoContenidoResource} REST controller.
 */
@SpringBootTest(classes = RedmProyectosApp.class)
public class EstadoContenidoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    @Autowired
    private EstadoContenidoRepository estadoContenidoRepository;

    @Autowired
    private EstadoContenidoService estadoContenidoService;

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

    private MockMvc restEstadoContenidoMockMvc;

    private EstadoContenido estadoContenido;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstadoContenidoResource estadoContenidoResource = new EstadoContenidoResource(estadoContenidoService);
        this.restEstadoContenidoMockMvc = MockMvcBuilders.standaloneSetup(estadoContenidoResource)
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
    public static EstadoContenido createEntity(EntityManager em) {
        EstadoContenido estadoContenido = new EstadoContenido()
            .titulo(DEFAULT_TITULO);
        return estadoContenido;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoContenido createUpdatedEntity(EntityManager em) {
        EstadoContenido estadoContenido = new EstadoContenido()
            .titulo(UPDATED_TITULO);
        return estadoContenido;
    }

    @BeforeEach
    public void initTest() {
        estadoContenido = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstadoContenido() throws Exception {
        int databaseSizeBeforeCreate = estadoContenidoRepository.findAll().size();

        // Create the EstadoContenido
        restEstadoContenidoMockMvc.perform(post("/api/estado-contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoContenido)))
            .andExpect(status().isCreated());

        // Validate the EstadoContenido in the database
        List<EstadoContenido> estadoContenidoList = estadoContenidoRepository.findAll();
        assertThat(estadoContenidoList).hasSize(databaseSizeBeforeCreate + 1);
        EstadoContenido testEstadoContenido = estadoContenidoList.get(estadoContenidoList.size() - 1);
        assertThat(testEstadoContenido.getTitulo()).isEqualTo(DEFAULT_TITULO);
    }

    @Test
    @Transactional
    public void createEstadoContenidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estadoContenidoRepository.findAll().size();

        // Create the EstadoContenido with an existing ID
        estadoContenido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoContenidoMockMvc.perform(post("/api/estado-contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoContenido)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoContenido in the database
        List<EstadoContenido> estadoContenidoList = estadoContenidoRepository.findAll();
        assertThat(estadoContenidoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEstadoContenidos() throws Exception {
        // Initialize the database
        estadoContenidoRepository.saveAndFlush(estadoContenido);

        // Get all the estadoContenidoList
        restEstadoContenidoMockMvc.perform(get("/api/estado-contenidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoContenido.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)));
    }
    
    @Test
    @Transactional
    public void getEstadoContenido() throws Exception {
        // Initialize the database
        estadoContenidoRepository.saveAndFlush(estadoContenido);

        // Get the estadoContenido
        restEstadoContenidoMockMvc.perform(get("/api/estado-contenidos/{id}", estadoContenido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estadoContenido.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO));
    }

    @Test
    @Transactional
    public void getNonExistingEstadoContenido() throws Exception {
        // Get the estadoContenido
        restEstadoContenidoMockMvc.perform(get("/api/estado-contenidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstadoContenido() throws Exception {
        // Initialize the database
        estadoContenidoService.save(estadoContenido);

        int databaseSizeBeforeUpdate = estadoContenidoRepository.findAll().size();

        // Update the estadoContenido
        EstadoContenido updatedEstadoContenido = estadoContenidoRepository.findById(estadoContenido.getId()).get();
        // Disconnect from session so that the updates on updatedEstadoContenido are not directly saved in db
        em.detach(updatedEstadoContenido);
        updatedEstadoContenido
            .titulo(UPDATED_TITULO);

        restEstadoContenidoMockMvc.perform(put("/api/estado-contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstadoContenido)))
            .andExpect(status().isOk());

        // Validate the EstadoContenido in the database
        List<EstadoContenido> estadoContenidoList = estadoContenidoRepository.findAll();
        assertThat(estadoContenidoList).hasSize(databaseSizeBeforeUpdate);
        EstadoContenido testEstadoContenido = estadoContenidoList.get(estadoContenidoList.size() - 1);
        assertThat(testEstadoContenido.getTitulo()).isEqualTo(UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void updateNonExistingEstadoContenido() throws Exception {
        int databaseSizeBeforeUpdate = estadoContenidoRepository.findAll().size();

        // Create the EstadoContenido

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoContenidoMockMvc.perform(put("/api/estado-contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoContenido)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoContenido in the database
        List<EstadoContenido> estadoContenidoList = estadoContenidoRepository.findAll();
        assertThat(estadoContenidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstadoContenido() throws Exception {
        // Initialize the database
        estadoContenidoService.save(estadoContenido);

        int databaseSizeBeforeDelete = estadoContenidoRepository.findAll().size();

        // Delete the estadoContenido
        restEstadoContenidoMockMvc.perform(delete("/api/estado-contenidos/{id}", estadoContenido.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstadoContenido> estadoContenidoList = estadoContenidoRepository.findAll();
        assertThat(estadoContenidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
