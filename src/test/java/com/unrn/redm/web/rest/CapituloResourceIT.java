package com.unrn.redm.web.rest;

import com.unrn.redm.RedmProyectosApp;
import com.unrn.redm.domain.Capitulo;
import com.unrn.redm.repository.CapituloRepository;
import com.unrn.redm.service.CapituloService;
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
 * Integration tests for the {@link CapituloResource} REST controller.
 */
@SpringBootTest(classes = RedmProyectosApp.class)
public class CapituloResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_SINOPSIS = "AAAAAAAAAA";
    private static final String UPDATED_SINOPSIS = "BBBBBBBBBB";

    private static final String DEFAULT_LOGLINE = "AAAAAAAAAA";
    private static final String UPDATED_LOGLINE = "BBBBBBBBBB";

    private static final String DEFAULT_CARATULA = "AAAAAAAAAA";
    private static final String UPDATED_CARATULA = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURACION = 1;
    private static final Integer UPDATED_DURACION = 2;

    private static final String DEFAULT_VIMEO_ID = "AAAAAAAAAA";
    private static final String UPDATED_VIMEO_ID = "BBBBBBBBBB";

    @Autowired
    private CapituloRepository capituloRepository;

    @Autowired
    private CapituloService capituloService;

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

    private MockMvc restCapituloMockMvc;

    private Capitulo capitulo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CapituloResource capituloResource = new CapituloResource(capituloService);
        this.restCapituloMockMvc = MockMvcBuilders.standaloneSetup(capituloResource)
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
    public static Capitulo createEntity(EntityManager em) {
        Capitulo capitulo = new Capitulo()
            .titulo(DEFAULT_TITULO)
            .numero(DEFAULT_NUMERO)
            .sinopsis(DEFAULT_SINOPSIS)
            .logline(DEFAULT_LOGLINE)
            .caratula(DEFAULT_CARATULA)
            .duracion(DEFAULT_DURACION)
            .vimeoID(DEFAULT_VIMEO_ID);
        return capitulo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Capitulo createUpdatedEntity(EntityManager em) {
        Capitulo capitulo = new Capitulo()
            .titulo(UPDATED_TITULO)
            .numero(UPDATED_NUMERO)
            .sinopsis(UPDATED_SINOPSIS)
            .logline(UPDATED_LOGLINE)
            .caratula(UPDATED_CARATULA)
            .duracion(UPDATED_DURACION)
            .vimeoID(UPDATED_VIMEO_ID);
        return capitulo;
    }

    @BeforeEach
    public void initTest() {
        capitulo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCapitulo() throws Exception {
        int databaseSizeBeforeCreate = capituloRepository.findAll().size();

        // Create the Capitulo
        restCapituloMockMvc.perform(post("/api/capitulos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(capitulo)))
            .andExpect(status().isCreated());

        // Validate the Capitulo in the database
        List<Capitulo> capituloList = capituloRepository.findAll();
        assertThat(capituloList).hasSize(databaseSizeBeforeCreate + 1);
        Capitulo testCapitulo = capituloList.get(capituloList.size() - 1);
        assertThat(testCapitulo.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testCapitulo.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCapitulo.getSinopsis()).isEqualTo(DEFAULT_SINOPSIS);
        assertThat(testCapitulo.getLogline()).isEqualTo(DEFAULT_LOGLINE);
        assertThat(testCapitulo.getCaratula()).isEqualTo(DEFAULT_CARATULA);
        assertThat(testCapitulo.getDuracion()).isEqualTo(DEFAULT_DURACION);
        assertThat(testCapitulo.getVimeoID()).isEqualTo(DEFAULT_VIMEO_ID);
    }

    @Test
    @Transactional
    public void createCapituloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = capituloRepository.findAll().size();

        // Create the Capitulo with an existing ID
        capitulo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCapituloMockMvc.perform(post("/api/capitulos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(capitulo)))
            .andExpect(status().isBadRequest());

        // Validate the Capitulo in the database
        List<Capitulo> capituloList = capituloRepository.findAll();
        assertThat(capituloList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCapitulos() throws Exception {
        // Initialize the database
        capituloRepository.saveAndFlush(capitulo);

        // Get all the capituloList
        restCapituloMockMvc.perform(get("/api/capitulos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capitulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].sinopsis").value(hasItem(DEFAULT_SINOPSIS)))
            .andExpect(jsonPath("$.[*].logline").value(hasItem(DEFAULT_LOGLINE)))
            .andExpect(jsonPath("$.[*].caratula").value(hasItem(DEFAULT_CARATULA)))
            .andExpect(jsonPath("$.[*].duracion").value(hasItem(DEFAULT_DURACION)))
            .andExpect(jsonPath("$.[*].vimeoID").value(hasItem(DEFAULT_VIMEO_ID)));
    }
    
    @Test
    @Transactional
    public void getCapitulo() throws Exception {
        // Initialize the database
        capituloRepository.saveAndFlush(capitulo);

        // Get the capitulo
        restCapituloMockMvc.perform(get("/api/capitulos/{id}", capitulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(capitulo.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.sinopsis").value(DEFAULT_SINOPSIS))
            .andExpect(jsonPath("$.logline").value(DEFAULT_LOGLINE))
            .andExpect(jsonPath("$.caratula").value(DEFAULT_CARATULA))
            .andExpect(jsonPath("$.duracion").value(DEFAULT_DURACION))
            .andExpect(jsonPath("$.vimeoID").value(DEFAULT_VIMEO_ID));
    }

    @Test
    @Transactional
    public void getNonExistingCapitulo() throws Exception {
        // Get the capitulo
        restCapituloMockMvc.perform(get("/api/capitulos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCapitulo() throws Exception {
        // Initialize the database
        capituloService.save(capitulo);

        int databaseSizeBeforeUpdate = capituloRepository.findAll().size();

        // Update the capitulo
        Capitulo updatedCapitulo = capituloRepository.findById(capitulo.getId()).get();
        // Disconnect from session so that the updates on updatedCapitulo are not directly saved in db
        em.detach(updatedCapitulo);
        updatedCapitulo
            .titulo(UPDATED_TITULO)
            .numero(UPDATED_NUMERO)
            .sinopsis(UPDATED_SINOPSIS)
            .logline(UPDATED_LOGLINE)
            .caratula(UPDATED_CARATULA)
            .duracion(UPDATED_DURACION)
            .vimeoID(UPDATED_VIMEO_ID);

        restCapituloMockMvc.perform(put("/api/capitulos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCapitulo)))
            .andExpect(status().isOk());

        // Validate the Capitulo in the database
        List<Capitulo> capituloList = capituloRepository.findAll();
        assertThat(capituloList).hasSize(databaseSizeBeforeUpdate);
        Capitulo testCapitulo = capituloList.get(capituloList.size() - 1);
        assertThat(testCapitulo.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testCapitulo.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCapitulo.getSinopsis()).isEqualTo(UPDATED_SINOPSIS);
        assertThat(testCapitulo.getLogline()).isEqualTo(UPDATED_LOGLINE);
        assertThat(testCapitulo.getCaratula()).isEqualTo(UPDATED_CARATULA);
        assertThat(testCapitulo.getDuracion()).isEqualTo(UPDATED_DURACION);
        assertThat(testCapitulo.getVimeoID()).isEqualTo(UPDATED_VIMEO_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCapitulo() throws Exception {
        int databaseSizeBeforeUpdate = capituloRepository.findAll().size();

        // Create the Capitulo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCapituloMockMvc.perform(put("/api/capitulos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(capitulo)))
            .andExpect(status().isBadRequest());

        // Validate the Capitulo in the database
        List<Capitulo> capituloList = capituloRepository.findAll();
        assertThat(capituloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCapitulo() throws Exception {
        // Initialize the database
        capituloService.save(capitulo);

        int databaseSizeBeforeDelete = capituloRepository.findAll().size();

        // Delete the capitulo
        restCapituloMockMvc.perform(delete("/api/capitulos/{id}", capitulo.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Capitulo> capituloList = capituloRepository.findAll();
        assertThat(capituloList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
