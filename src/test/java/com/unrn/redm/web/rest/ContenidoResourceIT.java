package com.unrn.redm.web.rest;

import com.unrn.redm.RedmProyectosApp;
import com.unrn.redm.domain.Contenido;
import com.unrn.redm.repository.ContenidoRepository;
import com.unrn.redm.service.ContenidoService;
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
 * Integration tests for the {@link ContenidoResource} REST controller.
 */
@SpringBootTest(classes = RedmProyectosApp.class)
public class ContenidoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANT_TEMPORADAS = 1;
    private static final Integer UPDATED_CANT_TEMPORADAS = 2;

    private static final String DEFAULT_FECHA_PUBLICACION = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_PUBLICACION = "BBBBBBBBBB";

    private static final String DEFAULT_CARATULA = "AAAAAAAAAA";
    private static final String UPDATED_CARATULA = "BBBBBBBBBB";

    @Autowired
    private ContenidoRepository contenidoRepository;

    @Autowired
    private ContenidoService contenidoService;

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

    private MockMvc restContenidoMockMvc;

    private Contenido contenido;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContenidoResource contenidoResource = new ContenidoResource(contenidoService);
        this.restContenidoMockMvc = MockMvcBuilders.standaloneSetup(contenidoResource)
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
    public static Contenido createEntity(EntityManager em) {
        Contenido contenido = new Contenido()
            .titulo(DEFAULT_TITULO)
            .cantTemporadas(DEFAULT_CANT_TEMPORADAS)
            .fechaPublicacion(DEFAULT_FECHA_PUBLICACION)
            .caratula(DEFAULT_CARATULA);
        return contenido;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contenido createUpdatedEntity(EntityManager em) {
        Contenido contenido = new Contenido()
            .titulo(UPDATED_TITULO)
            .cantTemporadas(UPDATED_CANT_TEMPORADAS)
            .fechaPublicacion(UPDATED_FECHA_PUBLICACION)
            .caratula(UPDATED_CARATULA);
        return contenido;
    }

    @BeforeEach
    public void initTest() {
        contenido = createEntity(em);
    }

    @Test
    @Transactional
    public void createContenido() throws Exception {
        int databaseSizeBeforeCreate = contenidoRepository.findAll().size();

        // Create the Contenido
        restContenidoMockMvc.perform(post("/api/contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenido)))
            .andExpect(status().isCreated());

        // Validate the Contenido in the database
        List<Contenido> contenidoList = contenidoRepository.findAll();
        assertThat(contenidoList).hasSize(databaseSizeBeforeCreate + 1);
        Contenido testContenido = contenidoList.get(contenidoList.size() - 1);
        assertThat(testContenido.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testContenido.getCantTemporadas()).isEqualTo(DEFAULT_CANT_TEMPORADAS);
        assertThat(testContenido.getFechaPublicacion()).isEqualTo(DEFAULT_FECHA_PUBLICACION);
        assertThat(testContenido.getCaratula()).isEqualTo(DEFAULT_CARATULA);
    }

    @Test
    @Transactional
    public void createContenidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contenidoRepository.findAll().size();

        // Create the Contenido with an existing ID
        contenido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContenidoMockMvc.perform(post("/api/contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenido)))
            .andExpect(status().isBadRequest());

        // Validate the Contenido in the database
        List<Contenido> contenidoList = contenidoRepository.findAll();
        assertThat(contenidoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContenidos() throws Exception {
        // Initialize the database
        contenidoRepository.saveAndFlush(contenido);

        // Get all the contenidoList
        restContenidoMockMvc.perform(get("/api/contenidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contenido.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].cantTemporadas").value(hasItem(DEFAULT_CANT_TEMPORADAS)))
            .andExpect(jsonPath("$.[*].fechaPublicacion").value(hasItem(DEFAULT_FECHA_PUBLICACION)))
            .andExpect(jsonPath("$.[*].caratula").value(hasItem(DEFAULT_CARATULA)));
    }
    
    @Test
    @Transactional
    public void getContenido() throws Exception {
        // Initialize the database
        contenidoRepository.saveAndFlush(contenido);

        // Get the contenido
        restContenidoMockMvc.perform(get("/api/contenidos/{id}", contenido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contenido.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.cantTemporadas").value(DEFAULT_CANT_TEMPORADAS))
            .andExpect(jsonPath("$.fechaPublicacion").value(DEFAULT_FECHA_PUBLICACION))
            .andExpect(jsonPath("$.caratula").value(DEFAULT_CARATULA));
    }

    @Test
    @Transactional
    public void getNonExistingContenido() throws Exception {
        // Get the contenido
        restContenidoMockMvc.perform(get("/api/contenidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContenido() throws Exception {
        // Initialize the database
        contenidoService.save(contenido);

        int databaseSizeBeforeUpdate = contenidoRepository.findAll().size();

        // Update the contenido
        Contenido updatedContenido = contenidoRepository.findById(contenido.getId()).get();
        // Disconnect from session so that the updates on updatedContenido are not directly saved in db
        em.detach(updatedContenido);
        updatedContenido
            .titulo(UPDATED_TITULO)
            .cantTemporadas(UPDATED_CANT_TEMPORADAS)
            .fechaPublicacion(UPDATED_FECHA_PUBLICACION)
            .caratula(UPDATED_CARATULA);

        restContenidoMockMvc.perform(put("/api/contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContenido)))
            .andExpect(status().isOk());

        // Validate the Contenido in the database
        List<Contenido> contenidoList = contenidoRepository.findAll();
        assertThat(contenidoList).hasSize(databaseSizeBeforeUpdate);
        Contenido testContenido = contenidoList.get(contenidoList.size() - 1);
        assertThat(testContenido.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testContenido.getCantTemporadas()).isEqualTo(UPDATED_CANT_TEMPORADAS);
        assertThat(testContenido.getFechaPublicacion()).isEqualTo(UPDATED_FECHA_PUBLICACION);
        assertThat(testContenido.getCaratula()).isEqualTo(UPDATED_CARATULA);
    }

    @Test
    @Transactional
    public void updateNonExistingContenido() throws Exception {
        int databaseSizeBeforeUpdate = contenidoRepository.findAll().size();

        // Create the Contenido

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContenidoMockMvc.perform(put("/api/contenidos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contenido)))
            .andExpect(status().isBadRequest());

        // Validate the Contenido in the database
        List<Contenido> contenidoList = contenidoRepository.findAll();
        assertThat(contenidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContenido() throws Exception {
        // Initialize the database
        contenidoService.save(contenido);

        int databaseSizeBeforeDelete = contenidoRepository.findAll().size();

        // Delete the contenido
        restContenidoMockMvc.perform(delete("/api/contenidos/{id}", contenido.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contenido> contenidoList = contenidoRepository.findAll();
        assertThat(contenidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
