package com.unrn.redm.web.rest;

import com.unrn.redm.RedmProyectosApp;
import com.unrn.redm.domain.Formato;
import com.unrn.redm.repository.FormatoRepository;
import com.unrn.redm.service.FormatoService;
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
 * Integration tests for the {@link FormatoResource} REST controller.
 */
@SpringBootTest(classes = RedmProyectosApp.class)
public class FormatoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    @Autowired
    private FormatoRepository formatoRepository;

    @Autowired
    private FormatoService formatoService;

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

    private MockMvc restFormatoMockMvc;

    private Formato formato;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormatoResource formatoResource = new FormatoResource(formatoService);
        this.restFormatoMockMvc = MockMvcBuilders.standaloneSetup(formatoResource)
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
    public static Formato createEntity(EntityManager em) {
        Formato formato = new Formato()
            .titulo(DEFAULT_TITULO);
        return formato;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Formato createUpdatedEntity(EntityManager em) {
        Formato formato = new Formato()
            .titulo(UPDATED_TITULO);
        return formato;
    }

    @BeforeEach
    public void initTest() {
        formato = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormato() throws Exception {
        int databaseSizeBeforeCreate = formatoRepository.findAll().size();

        // Create the Formato
        restFormatoMockMvc.perform(post("/api/formatoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formato)))
            .andExpect(status().isCreated());

        // Validate the Formato in the database
        List<Formato> formatoList = formatoRepository.findAll();
        assertThat(formatoList).hasSize(databaseSizeBeforeCreate + 1);
        Formato testFormato = formatoList.get(formatoList.size() - 1);
        assertThat(testFormato.getTitulo()).isEqualTo(DEFAULT_TITULO);
    }

    @Test
    @Transactional
    public void createFormatoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formatoRepository.findAll().size();

        // Create the Formato with an existing ID
        formato.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormatoMockMvc.perform(post("/api/formatoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formato)))
            .andExpect(status().isBadRequest());

        // Validate the Formato in the database
        List<Formato> formatoList = formatoRepository.findAll();
        assertThat(formatoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFormatoes() throws Exception {
        // Initialize the database
        formatoRepository.saveAndFlush(formato);

        // Get all the formatoList
        restFormatoMockMvc.perform(get("/api/formatoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formato.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)));
    }
    
    @Test
    @Transactional
    public void getFormato() throws Exception {
        // Initialize the database
        formatoRepository.saveAndFlush(formato);

        // Get the formato
        restFormatoMockMvc.perform(get("/api/formatoes/{id}", formato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formato.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO));
    }

    @Test
    @Transactional
    public void getNonExistingFormato() throws Exception {
        // Get the formato
        restFormatoMockMvc.perform(get("/api/formatoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormato() throws Exception {
        // Initialize the database
        formatoService.save(formato);

        int databaseSizeBeforeUpdate = formatoRepository.findAll().size();

        // Update the formato
        Formato updatedFormato = formatoRepository.findById(formato.getId()).get();
        // Disconnect from session so that the updates on updatedFormato are not directly saved in db
        em.detach(updatedFormato);
        updatedFormato
            .titulo(UPDATED_TITULO);

        restFormatoMockMvc.perform(put("/api/formatoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormato)))
            .andExpect(status().isOk());

        // Validate the Formato in the database
        List<Formato> formatoList = formatoRepository.findAll();
        assertThat(formatoList).hasSize(databaseSizeBeforeUpdate);
        Formato testFormato = formatoList.get(formatoList.size() - 1);
        assertThat(testFormato.getTitulo()).isEqualTo(UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void updateNonExistingFormato() throws Exception {
        int databaseSizeBeforeUpdate = formatoRepository.findAll().size();

        // Create the Formato

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormatoMockMvc.perform(put("/api/formatoes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formato)))
            .andExpect(status().isBadRequest());

        // Validate the Formato in the database
        List<Formato> formatoList = formatoRepository.findAll();
        assertThat(formatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFormato() throws Exception {
        // Initialize the database
        formatoService.save(formato);

        int databaseSizeBeforeDelete = formatoRepository.findAll().size();

        // Delete the formato
        restFormatoMockMvc.perform(delete("/api/formatoes/{id}", formato.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Formato> formatoList = formatoRepository.findAll();
        assertThat(formatoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
