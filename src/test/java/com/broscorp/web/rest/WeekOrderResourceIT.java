package com.broscorp.web.rest;

import com.broscorp.OrderingApplicationApp;
import com.broscorp.domain.WeekOrder;
import com.broscorp.repository.WeekOrderRepository;
import com.broscorp.service.WeekOrderService;
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
 * Integration tests for the {@link WeekOrderResource} REST controller.
 */
@SpringBootTest(classes = OrderingApplicationApp.class)
public class WeekOrderResourceIT {

    private static final Integer DEFAULT_WEEKNUM = 1;
    private static final Integer UPDATED_WEEKNUM = 2;

    private static final Integer DEFAULT_DAYNUM = 1;
    private static final Integer UPDATED_DAYNUM = 2;

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final byte[] DEFAULT_USER_ORDER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_USER_ORDER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_USER_ORDER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_USER_ORDER_CONTENT_TYPE = "image/png";

    @Autowired
    private WeekOrderRepository weekOrderRepository;

    @Autowired
    private WeekOrderService weekOrderService;

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

    private MockMvc restWeekOrderMockMvc;

    private WeekOrder weekOrder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WeekOrderResource weekOrderResource = new WeekOrderResource(weekOrderService);
        this.restWeekOrderMockMvc = MockMvcBuilders.standaloneSetup(weekOrderResource)
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
    public static WeekOrder createEntity(EntityManager em) {
        WeekOrder weekOrder = new WeekOrder()
            .weeknum(DEFAULT_WEEKNUM)
            .daynum(DEFAULT_DAYNUM)
            .userId(DEFAULT_USER_ID)
            .userOrder(DEFAULT_USER_ORDER)
            .userOrderContentType(DEFAULT_USER_ORDER_CONTENT_TYPE);
        return weekOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WeekOrder createUpdatedEntity(EntityManager em) {
        WeekOrder weekOrder = new WeekOrder()
            .weeknum(UPDATED_WEEKNUM)
            .daynum(UPDATED_DAYNUM)
            .userId(UPDATED_USER_ID)
            .userOrder(UPDATED_USER_ORDER)
            .userOrderContentType(UPDATED_USER_ORDER_CONTENT_TYPE);
        return weekOrder;
    }

    @BeforeEach
    public void initTest() {
        weekOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createWeekOrder() throws Exception {
        int databaseSizeBeforeCreate = weekOrderRepository.findAll().size();

        // Create the WeekOrder
        restWeekOrderMockMvc.perform(post("/api/week-orders")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weekOrder)))
            .andExpect(status().isCreated());

        // Validate the WeekOrder in the database
        List<WeekOrder> weekOrderList = weekOrderRepository.findAll();
        assertThat(weekOrderList).hasSize(databaseSizeBeforeCreate + 1);
        WeekOrder testWeekOrder = weekOrderList.get(weekOrderList.size() - 1);
        assertThat(testWeekOrder.getWeeknum()).isEqualTo(DEFAULT_WEEKNUM);
        assertThat(testWeekOrder.getDaynum()).isEqualTo(DEFAULT_DAYNUM);
        assertThat(testWeekOrder.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testWeekOrder.getUserOrder()).isEqualTo(DEFAULT_USER_ORDER);
        assertThat(testWeekOrder.getUserOrderContentType()).isEqualTo(DEFAULT_USER_ORDER_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createWeekOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = weekOrderRepository.findAll().size();

        // Create the WeekOrder with an existing ID
        weekOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWeekOrderMockMvc.perform(post("/api/week-orders")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weekOrder)))
            .andExpect(status().isBadRequest());

        // Validate the WeekOrder in the database
        List<WeekOrder> weekOrderList = weekOrderRepository.findAll();
        assertThat(weekOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWeekOrders() throws Exception {
        // Initialize the database
        weekOrderRepository.saveAndFlush(weekOrder);

        // Get all the weekOrderList
        restWeekOrderMockMvc.perform(get("/api/week-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(weekOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].weeknum").value(hasItem(DEFAULT_WEEKNUM)))
            .andExpect(jsonPath("$.[*].daynum").value(hasItem(DEFAULT_DAYNUM)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].userOrderContentType").value(hasItem(DEFAULT_USER_ORDER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].userOrder").value(hasItem(Base64Utils.encodeToString(DEFAULT_USER_ORDER))));
    }
    
    @Test
    @Transactional
    public void getWeekOrder() throws Exception {
        // Initialize the database
        weekOrderRepository.saveAndFlush(weekOrder);

        // Get the weekOrder
        restWeekOrderMockMvc.perform(get("/api/week-orders/{id}", weekOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(weekOrder.getId().intValue()))
            .andExpect(jsonPath("$.weeknum").value(DEFAULT_WEEKNUM))
            .andExpect(jsonPath("$.daynum").value(DEFAULT_DAYNUM))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.userOrderContentType").value(DEFAULT_USER_ORDER_CONTENT_TYPE))
            .andExpect(jsonPath("$.userOrder").value(Base64Utils.encodeToString(DEFAULT_USER_ORDER)));
    }

    @Test
    @Transactional
    public void getNonExistingWeekOrder() throws Exception {
        // Get the weekOrder
        restWeekOrderMockMvc.perform(get("/api/week-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWeekOrder() throws Exception {
        // Initialize the database
        weekOrderService.save(weekOrder);

        int databaseSizeBeforeUpdate = weekOrderRepository.findAll().size();

        // Update the weekOrder
        WeekOrder updatedWeekOrder = weekOrderRepository.findById(weekOrder.getId()).get();
        // Disconnect from session so that the updates on updatedWeekOrder are not directly saved in db
        em.detach(updatedWeekOrder);
        updatedWeekOrder
            .weeknum(UPDATED_WEEKNUM)
            .daynum(UPDATED_DAYNUM)
            .userId(UPDATED_USER_ID)
            .userOrder(UPDATED_USER_ORDER)
            .userOrderContentType(UPDATED_USER_ORDER_CONTENT_TYPE);

        restWeekOrderMockMvc.perform(put("/api/week-orders")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWeekOrder)))
            .andExpect(status().isOk());

        // Validate the WeekOrder in the database
        List<WeekOrder> weekOrderList = weekOrderRepository.findAll();
        assertThat(weekOrderList).hasSize(databaseSizeBeforeUpdate);
        WeekOrder testWeekOrder = weekOrderList.get(weekOrderList.size() - 1);
        assertThat(testWeekOrder.getWeeknum()).isEqualTo(UPDATED_WEEKNUM);
        assertThat(testWeekOrder.getDaynum()).isEqualTo(UPDATED_DAYNUM);
        assertThat(testWeekOrder.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testWeekOrder.getUserOrder()).isEqualTo(UPDATED_USER_ORDER);
        assertThat(testWeekOrder.getUserOrderContentType()).isEqualTo(UPDATED_USER_ORDER_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingWeekOrder() throws Exception {
        int databaseSizeBeforeUpdate = weekOrderRepository.findAll().size();

        // Create the WeekOrder

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWeekOrderMockMvc.perform(put("/api/week-orders")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(weekOrder)))
            .andExpect(status().isBadRequest());

        // Validate the WeekOrder in the database
        List<WeekOrder> weekOrderList = weekOrderRepository.findAll();
        assertThat(weekOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWeekOrder() throws Exception {
        // Initialize the database
        weekOrderService.save(weekOrder);

        int databaseSizeBeforeDelete = weekOrderRepository.findAll().size();

        // Delete the weekOrder
        restWeekOrderMockMvc.perform(delete("/api/week-orders/{id}", weekOrder.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WeekOrder> weekOrderList = weekOrderRepository.findAll();
        assertThat(weekOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
