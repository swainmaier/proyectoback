package com.unrn.redm.web.rest;

import com.unrn.redm.RedmProyectosApp;
import com.unrn.redm.domain.Temporada;
import com.unrn.redm.repository.TemporadaRepository;
import com.unrn.redm.service.TemporadaService;
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
 * Integration tests for the {@link TemporadaResource} REST controller.
 */
@SpringBootTest(classes = RedmProyectosApp.class)
public class TemporadaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final Integer DEFAULT_CANT_CAPITULOS = 1;
    private static final Integer UPDATED_CANT_CAPITULOS = 2;

    private static final Integer DEFAULT_DURACION_TOTAL = 1;
    private static final Integer UPDATED_DURACION_TOTAL = 2;

    @Autowired
    private TemporadaRepository temporadaRepository;

    @Autowired
    private TemporadaService temporadaService;

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

    private MockMvc restTemporadaMockMvc;

    private Temporada temporada;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TemporadaResource temporadaResource = new TemporadaResource(temporadaService);
        this.restTemporadaMockMvc = MockMvcBuilders.standaloneSetup(temporadaResource)
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
    public static Temporada createEntity(EntityManager em) {
        Temporada temporada = new Temporada()
            .titulo(DEFAULT_TITULO)
            .numero(DEFAULT_NUMERO)
            .cantCapitulos(DEFAULT_CANT_CAPITULOS)
            .duracionTotal(DEFAULT_DURACION_TOTAL);
        return temporada;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Temporada createUpdatedEntity(EntityManager em) {
        Temporada temporada = new Temporada()
            .titulo(UPDATED_TITULO)
            .numero(UPDATED_NUMERO)
            .cantCapitulos(UPDATED_CANT_CAPITULOS)
            .duracionTotal(UPDATED_DURACION_TOTAL);
        return temporada;
    }

    @BeforeEach
    public void initTest() {
        temporada = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemporada() throws Exception {
        int databaseSizeBeforeCreate = temporadaRepository.findAll().size();

        // Create the Temporada
        restTemporadaMockMvc.perform(post("/api/temporadas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(temporada)))
            .andExpect(status().isCreated());

        // Validate the Temporada in the database
        List<Temporada> temporadaList = temporadaRepository.findAll();
        assertThat(temporadaList).hasSize(databaseSizeBeforeCreate + 1);
        Temporada testTemporada = temporadaList.get(temporadaList.size() - 1);
        assertThat(testTemporada.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testTemporada.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testTemporada.getCantCapitulos()).isEqualTo(DEFAULT_CANT_CAPITULOS);
        assertThat(testTemporada.getDuracionTotal()).isEqualTo(DEFAULT_DURACION_TOTAL);
    }

    @Test
    @Transactional
    public void createTemporadaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = temporadaRepository.findAll().size();

        // Create the Temporada with an existing ID
        temporada.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemporadaMockMvc.perform(post("/api/temporadas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(temporada)))
            .andExpect(status().isBadRequest());

        // Validate the Temporada in the database
        List<Temporada> temporadaList = temporadaRepository.findAll();
        assertThat(temporadaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTemporadas() throws Exception {
        // Initialize the database
        temporadaRepository.saveAndFlush(temporada);

        // Get all the temporadaList
        restTemporadaMockMvc.perform(get("/api/temporadas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(temporada.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].cantCapitulos").value(hasItem(DEFAULT_CANT_CAPITULOS)))
            .andExpect(jsonPath("$.[*].duracionTotal").value(hasItem(DEFAULT_DURACION_TOTAL)));
    }
    
    @Test
    @Transactional
    public void getTemporada() throws Exception {
        // Initialize the database
        temporadaRepository.saveAndFlush(temporada);

        // Get the temporada
        restTemporadaMockMvc.perform(get("/api/temporadas/{id}", temporada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(temporada.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.cantCapitulos").value(DEFAULT_CANT_CAPITULOS))
            .andExpect(jsonPath("$.duracionTotal").value(DEFAULT_DURACION_TOTAL));
    }

    @Test
    @Transactional
    public void getNonExistingTemporada() throws Exception {
        // Get the temporada
        restTemporadaMockMvc.perform(get("/api/temporadas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemporada() throws Exception {
        // Initialize the database
        temporadaService.save(temporada);

        int databaseSizeBeforeUpdate = temporadaRepository.findAll().size();

        // Update the temporada
        Temporada updatedTemporada = temporadaRepository.findById(temporada.getId()).get();
        // Disconnect from session so that the updates on updatedTemporada are not directly saved in db
        em.detach(updatedTemporada);
        updatedTemporada
            .titulo(UPDATED_TITULO)
            .numero(UPDATED_NUMERO)
            .cantCapitulos(UPDATED_CANT_CAPITULOS)
            .duracionTotal(UPDATED_DURACION_TOTAL);

        restTemporadaMockMvc.perform(put("/api/temporadas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTemporada)))
            .andExpect(status().isOk());

        // Validate the Temporada in the database
        List<Temporada> temporadaList = temporadaRepository.findAll();
        assertThat(temporadaList).hasSize(databaseSizeBeforeUpdate);
        Temporada testTemporada = temporadaList.get(temporadaList.size() - 1);
        assertThat(testTemporada.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testTemporada.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTemporada.getCantCapitulos()).isEqualTo(UPDATED_CANT_CAPITULOS);
        assertThat(testTemporada.getDuracionTotal()).isEqualTo(UPDATED_DURACION_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingTemporada() throws Exception {
        int databaseSizeBeforeUpdate = temporadaRepository.findAll().size();

        // Create the Temporada

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemporadaMockMvc.perform(put("/api/temporadas")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(temporada)))
            .andExpect(status().isBadRequest());

        // Validate the Temporada in the database
        List<Temporada> temporadaList = temporadaRepository.findAll();
        assertThat(temporadaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTemporada() throws Exception {
        // Initialize the database
        temporadaService.save(temporada);

        int databaseSizeBeforeDelete = temporadaRepository.findAll().size();

        // Delete the temporada
        restTemporadaMockMvc.perform(delete("/api/temporadas/{id}", temporada.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Temporada> temporadaList = temporadaRepository.findAll();
        assertThat(temporadaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
