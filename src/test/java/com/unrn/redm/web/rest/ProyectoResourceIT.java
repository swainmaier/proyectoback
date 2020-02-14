package com.unrn.redm.web.rest;

import com.unrn.redm.RedmProyectosApp;
import com.unrn.redm.domain.Proyecto;
import com.unrn.redm.repository.ProyectoRepository;
import com.unrn.redm.service.ProyectoService;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.unrn.redm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProyectoResource} REST controller.
 */
@SpringBootTest(classes = RedmProyectosApp.class)
public class ProyectoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_SUMARY = "AAAAAAAAAA";
    private static final String UPDATED_SUMARY = "BBBBBBBBBB";

    private static final String DEFAULT_SLOGAN = "AAAAAAAAAA";
    private static final String UPDATED_SLOGAN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA_PUB = "AAAAAAAAAA";
    private static final String UPDATED_FECHA_PUB = "BBBBBBBBBB";

    private static final String DEFAULT_SINOPSIS = "AAAAAAAAAA";
    private static final String UPDATED_SINOPSIS = "BBBBBBBBBB";

    private static final String DEFAULT_STORYLINE = "AAAAAAAAAA";
    private static final String UPDATED_STORYLINE = "BBBBBBBBBB";

    private static final String DEFAULT_LOGLINE = "AAAAAAAAAA";
    private static final String UPDATED_LOGLINE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private ProyectoService proyectoService;

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

    private MockMvc restProyectoMockMvc;

    private Proyecto proyecto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProyectoResource proyectoResource = new ProyectoResource(proyectoService);
        this.restProyectoMockMvc = MockMvcBuilders.standaloneSetup(proyectoResource)
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
    public static Proyecto createEntity(EntityManager em) {
        Proyecto proyecto = new Proyecto()
            .titulo(DEFAULT_TITULO)
            .sumary(DEFAULT_SUMARY)
            .slogan(DEFAULT_SLOGAN)
            .code(DEFAULT_CODE)
            .fechaPub(DEFAULT_FECHA_PUB)
            .sinopsis(DEFAULT_SINOPSIS)
            .storyline(DEFAULT_STORYLINE)
            .logline(DEFAULT_LOGLINE)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE);
        return proyecto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proyecto createUpdatedEntity(EntityManager em) {
        Proyecto proyecto = new Proyecto()
            .titulo(UPDATED_TITULO)
            .sumary(UPDATED_SUMARY)
            .slogan(UPDATED_SLOGAN)
            .code(UPDATED_CODE)
            .fechaPub(UPDATED_FECHA_PUB)
            .sinopsis(UPDATED_SINOPSIS)
            .storyline(UPDATED_STORYLINE)
            .logline(UPDATED_LOGLINE)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);
        return proyecto;
    }

    @BeforeEach
    public void initTest() {
        proyecto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProyecto() throws Exception {
        int databaseSizeBeforeCreate = proyectoRepository.findAll().size();

        // Create the Proyecto
        restProyectoMockMvc.perform(post("/api/proyectos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proyecto)))
            .andExpect(status().isCreated());

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeCreate + 1);
        Proyecto testProyecto = proyectoList.get(proyectoList.size() - 1);
        assertThat(testProyecto.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testProyecto.getSumary()).isEqualTo(DEFAULT_SUMARY);
        assertThat(testProyecto.getSlogan()).isEqualTo(DEFAULT_SLOGAN);
        assertThat(testProyecto.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProyecto.getFechaPub()).isEqualTo(DEFAULT_FECHA_PUB);
        assertThat(testProyecto.getSinopsis()).isEqualTo(DEFAULT_SINOPSIS);
        assertThat(testProyecto.getStoryline()).isEqualTo(DEFAULT_STORYLINE);
        assertThat(testProyecto.getLogline()).isEqualTo(DEFAULT_LOGLINE);
        assertThat(testProyecto.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testProyecto.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createProyectoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proyectoRepository.findAll().size();

        // Create the Proyecto with an existing ID
        proyecto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProyectoMockMvc.perform(post("/api/proyectos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proyecto)))
            .andExpect(status().isBadRequest());

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProyectos() throws Exception {
        // Initialize the database
        proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList
        restProyectoMockMvc.perform(get("/api/proyectos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proyecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].sumary").value(hasItem(DEFAULT_SUMARY)))
            .andExpect(jsonPath("$.[*].slogan").value(hasItem(DEFAULT_SLOGAN)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].fechaPub").value(hasItem(DEFAULT_FECHA_PUB)))
            .andExpect(jsonPath("$.[*].sinopsis").value(hasItem(DEFAULT_SINOPSIS)))
            .andExpect(jsonPath("$.[*].storyline").value(hasItem(DEFAULT_STORYLINE)))
            .andExpect(jsonPath("$.[*].logline").value(hasItem(DEFAULT_LOGLINE)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))));
    }
    
    @Test
    @Transactional
    public void getProyecto() throws Exception {
        // Initialize the database
        proyectoRepository.saveAndFlush(proyecto);

        // Get the proyecto
        restProyectoMockMvc.perform(get("/api/proyectos/{id}", proyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proyecto.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.sumary").value(DEFAULT_SUMARY))
            .andExpect(jsonPath("$.slogan").value(DEFAULT_SLOGAN))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.fechaPub").value(DEFAULT_FECHA_PUB))
            .andExpect(jsonPath("$.sinopsis").value(DEFAULT_SINOPSIS))
            .andExpect(jsonPath("$.storyline").value(DEFAULT_STORYLINE))
            .andExpect(jsonPath("$.logline").value(DEFAULT_LOGLINE))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)));
    }

    @Test
    @Transactional
    public void getNonExistingProyecto() throws Exception {
        // Get the proyecto
        restProyectoMockMvc.perform(get("/api/proyectos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProyecto() throws Exception {
        // Initialize the database
        proyectoService.save(proyecto);

        int databaseSizeBeforeUpdate = proyectoRepository.findAll().size();

        // Update the proyecto
        Proyecto updatedProyecto = proyectoRepository.findById(proyecto.getId()).get();
        // Disconnect from session so that the updates on updatedProyecto are not directly saved in db
        em.detach(updatedProyecto);
        updatedProyecto
            .titulo(UPDATED_TITULO)
            .sumary(UPDATED_SUMARY)
            .slogan(UPDATED_SLOGAN)
            .code(UPDATED_CODE)
            .fechaPub(UPDATED_FECHA_PUB)
            .sinopsis(UPDATED_SINOPSIS)
            .storyline(UPDATED_STORYLINE)
            .logline(UPDATED_LOGLINE)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);

        restProyectoMockMvc.perform(put("/api/proyectos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProyecto)))
            .andExpect(status().isOk());

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate);
        Proyecto testProyecto = proyectoList.get(proyectoList.size() - 1);
        assertThat(testProyecto.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testProyecto.getSumary()).isEqualTo(UPDATED_SUMARY);
        assertThat(testProyecto.getSlogan()).isEqualTo(UPDATED_SLOGAN);
        assertThat(testProyecto.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProyecto.getFechaPub()).isEqualTo(UPDATED_FECHA_PUB);
        assertThat(testProyecto.getSinopsis()).isEqualTo(UPDATED_SINOPSIS);
        assertThat(testProyecto.getStoryline()).isEqualTo(UPDATED_STORYLINE);
        assertThat(testProyecto.getLogline()).isEqualTo(UPDATED_LOGLINE);
        assertThat(testProyecto.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testProyecto.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProyecto() throws Exception {
        int databaseSizeBeforeUpdate = proyectoRepository.findAll().size();

        // Create the Proyecto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProyectoMockMvc.perform(put("/api/proyectos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proyecto)))
            .andExpect(status().isBadRequest());

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProyecto() throws Exception {
        // Initialize the database
        proyectoService.save(proyecto);

        int databaseSizeBeforeDelete = proyectoRepository.findAll().size();

        // Delete the proyecto
        restProyectoMockMvc.perform(delete("/api/proyectos/{id}", proyecto.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
