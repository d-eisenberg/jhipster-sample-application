package com.broscorp.web.rest;

import com.broscorp.OrderingApplicationApp;
import com.broscorp.domain.WeekManu;
import com.broscorp.repository.WeekManuRepository;
import com.broscorp.service.WeekManuService;
import com.broscorp.web.rest.errors.ExceptionTranslator;

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

import static com.broscorp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WeekManuResource} REST controller.
 */
@SpringBootTest(classes = OrderingApplicationApp.class)
public class WeekManuResourceIT {

    private static final byte[] DEFAULT_MENU = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MENU = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MENU_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MENU_CONTENT_TYPE = "image/png";

    @Autowired
    private WeekManuRepository weekManuRepository;

    @Autowired
    private WeekManuService weekManuService;

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

    private MockMvc restWeekManuMockMvc;

    private WeekManu weekManu;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WeekManuResource weekManuResource = new WeekManuResource(weekManuService);
        this.restWeekManuMockMvc = MockMvcBuilders.standaloneSetup(weekManuResource)
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
    public static WeekManu createEntity(EntityManager em) {
        WeekManu weekManu = new WeekManu()
            .menu(DEFAULT_MENU)
            .menuContentType(DEFAULT_MENU_CONTENT_TYPE);
        return weekManu;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WeekManu createUpdatedEntity(EntityManager em) {
        WeekManu weekManu = new WeekManu()
            .menu(UPDATED_MENU)
            .menuContentType(UPDATED_MENU_CONTENT_TYPE);
        return weekManu;
    }

    @BeforeEach
    public void initTest() {
        weekManu = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeekManu() throws Exception {
        int databaseSizeBeforeCreate = weekManuRepository.findAll().size();

        // Create the WeekManu
        restWeekManuMockMvc.perform(post("/api/week-manus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weekManu)))
            .andExpect(status().isCreated());

        // Validate the WeekManu in the database
        List<WeekManu> weekManuList = weekManuRepository.findAll();
        assertThat(weekManuList).hasSize(databaseSizeBeforeCreate + 1);
        WeekManu testWeekManu = weekManuList.get(weekManuList.size() - 1);
        assertThat(testWeekManu.getMenu()).isEqualTo(DEFAULT_MENU);
        assertThat(testWeekManu.getMenuContentType()).isEqualTo(DEFAULT_MENU_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createWeekManuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = weekManuRepository.findAll().size();

        // Create the WeekManu with an existing ID
        weekManu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeekManuMockMvc.perform(post("/api/week-manus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weekManu)))
            .andExpect(status().isBadRequest());

        // Validate the WeekManu in the database
        List<WeekManu> weekManuList = weekManuRepository.findAll();
        assertThat(weekManuList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWeekManus() throws Exception {
        // Initialize the database
        weekManuRepository.saveAndFlush(weekManu);

        // Get all the weekManuList
        restWeekManuMockMvc.perform(get("/api/week-manus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weekManu.getId().intValue())))
            .andExpect(jsonPath("$.[*].menuContentType").value(hasItem(DEFAULT_MENU_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].menu").value(hasItem(Base64Utils.encodeToString(DEFAULT_MENU))));
    }
    
    @Test
    @Transactional
    public void getWeekManu() throws Exception {
        // Initialize the database
        weekManuRepository.saveAndFlush(weekManu);

        // Get the weekManu
        restWeekManuMockMvc.perform(get("/api/week-manus/{id}", weekManu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(weekManu.getId().intValue()))
            .andExpect(jsonPath("$.menuContentType").value(DEFAULT_MENU_CONTENT_TYPE))
            .andExpect(jsonPath("$.menu").value(Base64Utils.encodeToString(DEFAULT_MENU)));
    }

    @Test
    @Transactional
    public void getNonExistingWeekManu() throws Exception {
        // Get the weekManu
        restWeekManuMockMvc.perform(get("/api/week-manus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeekManu() throws Exception {
        // Initialize the database
        weekManuService.save(weekManu);

        int databaseSizeBeforeUpdate = weekManuRepository.findAll().size();

        // Update the weekManu
        WeekManu updatedWeekManu = weekManuRepository.findById(weekManu.getId()).get();
        // Disconnect from session so that the updates on updatedWeekManu are not directly saved in db
        em.detach(updatedWeekManu);
        updatedWeekManu
            .menu(UPDATED_MENU)
            .menuContentType(UPDATED_MENU_CONTENT_TYPE);

        restWeekManuMockMvc.perform(put("/api/week-manus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWeekManu)))
            .andExpect(status().isOk());

        // Validate the WeekManu in the database
        List<WeekManu> weekManuList = weekManuRepository.findAll();
        assertThat(weekManuList).hasSize(databaseSizeBeforeUpdate);
        WeekManu testWeekManu = weekManuList.get(weekManuList.size() - 1);
        assertThat(testWeekManu.getMenu()).isEqualTo(UPDATED_MENU);
        assertThat(testWeekManu.getMenuContentType()).isEqualTo(UPDATED_MENU_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingWeekManu() throws Exception {
        int databaseSizeBeforeUpdate = weekManuRepository.findAll().size();

        // Create the WeekManu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeekManuMockMvc.perform(put("/api/week-manus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weekManu)))
            .andExpect(status().isBadRequest());

        // Validate the WeekManu in the database
        List<WeekManu> weekManuList = weekManuRepository.findAll();
        assertThat(weekManuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWeekManu() throws Exception {
        // Initialize the database
        weekManuService.save(weekManu);

        int databaseSizeBeforeDelete = weekManuRepository.findAll().size();

        // Delete the weekManu
        restWeekManuMockMvc.perform(delete("/api/week-manus/{id}", weekManu.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WeekManu> weekManuList = weekManuRepository.findAll();
        assertThat(weekManuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
