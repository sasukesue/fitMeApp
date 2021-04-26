package com.proj.app.web.rest;

import com.proj.app.AppApp;
import com.proj.app.domain.Schedule;
import com.proj.app.repository.ScheduleRepository;
import com.proj.app.service.ScheduleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.proj.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ScheduleResource} REST controller.
 */
@SpringBootTest(classes = AppApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ScheduleResourceIT {

    private static final ZonedDateTime DEFAULT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_AVAILABLE_SLOTS = 1;
    private static final Integer UPDATED_AVAILABLE_SLOTS = 2;

    private static final Long DEFAULT_FITNESS_CLASS_ID = 1L;
    private static final Long UPDATED_FITNESS_CLASS_ID = 2L;

    private static final Long DEFAULT_LOCATION_ID = 1L;
    private static final Long UPDATED_LOCATION_ID = 2L;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScheduleMockMvc;

    private Schedule schedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Schedule createEntity(EntityManager em) {
        Schedule schedule = new Schedule()
            .dateTime(DEFAULT_DATE_TIME)
            .availableSlots(DEFAULT_AVAILABLE_SLOTS)
            .fitnessClassId(DEFAULT_FITNESS_CLASS_ID)
            .locationId(DEFAULT_LOCATION_ID);
        return schedule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Schedule createUpdatedEntity(EntityManager em) {
        Schedule schedule = new Schedule()
            .dateTime(UPDATED_DATE_TIME)
            .availableSlots(UPDATED_AVAILABLE_SLOTS)
            .fitnessClassId(UPDATED_FITNESS_CLASS_ID)
            .locationId(UPDATED_LOCATION_ID);
        return schedule;
    }

    @BeforeEach
    public void initTest() {
        schedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchedule() throws Exception {
        int databaseSizeBeforeCreate = scheduleRepository.findAll().size();

        // Create the Schedule
        restScheduleMockMvc.perform(post("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedule)))
            .andExpect(status().isCreated());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeCreate + 1);
        Schedule testSchedule = scheduleList.get(scheduleList.size() - 1);
        assertThat(testSchedule.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testSchedule.getAvailableSlots()).isEqualTo(DEFAULT_AVAILABLE_SLOTS);
        assertThat(testSchedule.getFitnessClassId()).isEqualTo(DEFAULT_FITNESS_CLASS_ID);
        assertThat(testSchedule.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
    }

    @Test
    @Transactional
    public void createScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scheduleRepository.findAll().size();

        // Create the Schedule with an existing ID
        schedule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduleMockMvc.perform(post("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedule)))
            .andExpect(status().isBadRequest());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleRepository.findAll().size();
        // set the field null
        schedule.setDateTime(null);

        // Create the Schedule, which fails.

        restScheduleMockMvc.perform(post("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedule)))
            .andExpect(status().isBadRequest());

        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvailableSlotsIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleRepository.findAll().size();
        // set the field null
        schedule.setAvailableSlots(null);

        // Create the Schedule, which fails.

        restScheduleMockMvc.perform(post("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedule)))
            .andExpect(status().isBadRequest());

        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFitnessClassIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleRepository.findAll().size();
        // set the field null
        schedule.setFitnessClassId(null);

        // Create the Schedule, which fails.

        restScheduleMockMvc.perform(post("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedule)))
            .andExpect(status().isBadRequest());

        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = scheduleRepository.findAll().size();
        // set the field null
        schedule.setLocationId(null);

        // Create the Schedule, which fails.

        restScheduleMockMvc.perform(post("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedule)))
            .andExpect(status().isBadRequest());

        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchedules() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get all the scheduleList
        restScheduleMockMvc.perform(get("/api/schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(sameInstant(DEFAULT_DATE_TIME))))
            .andExpect(jsonPath("$.[*].availableSlots").value(hasItem(DEFAULT_AVAILABLE_SLOTS)))
            .andExpect(jsonPath("$.[*].fitnessClassId").value(hasItem(DEFAULT_FITNESS_CLASS_ID.intValue())))
            .andExpect(jsonPath("$.[*].locationId").value(hasItem(DEFAULT_LOCATION_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getSchedule() throws Exception {
        // Initialize the database
        scheduleRepository.saveAndFlush(schedule);

        // Get the schedule
        restScheduleMockMvc.perform(get("/api/schedules/{id}", schedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schedule.getId().intValue()))
            .andExpect(jsonPath("$.dateTime").value(sameInstant(DEFAULT_DATE_TIME)))
            .andExpect(jsonPath("$.availableSlots").value(DEFAULT_AVAILABLE_SLOTS))
            .andExpect(jsonPath("$.fitnessClassId").value(DEFAULT_FITNESS_CLASS_ID.intValue()))
            .andExpect(jsonPath("$.locationId").value(DEFAULT_LOCATION_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSchedule() throws Exception {
        // Get the schedule
        restScheduleMockMvc.perform(get("/api/schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchedule() throws Exception {
        // Initialize the database
        scheduleService.save(schedule);

        int databaseSizeBeforeUpdate = scheduleRepository.findAll().size();

        // Update the schedule
        Schedule updatedSchedule = scheduleRepository.findById(schedule.getId()).get();
        // Disconnect from session so that the updates on updatedSchedule are not directly saved in db
        em.detach(updatedSchedule);
        updatedSchedule
            .dateTime(UPDATED_DATE_TIME)
            .availableSlots(UPDATED_AVAILABLE_SLOTS)
            .fitnessClassId(UPDATED_FITNESS_CLASS_ID)
            .locationId(UPDATED_LOCATION_ID);

        restScheduleMockMvc.perform(put("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchedule)))
            .andExpect(status().isOk());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeUpdate);
        Schedule testSchedule = scheduleList.get(scheduleList.size() - 1);
        assertThat(testSchedule.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testSchedule.getAvailableSlots()).isEqualTo(UPDATED_AVAILABLE_SLOTS);
        assertThat(testSchedule.getFitnessClassId()).isEqualTo(UPDATED_FITNESS_CLASS_ID);
        assertThat(testSchedule.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSchedule() throws Exception {
        int databaseSizeBeforeUpdate = scheduleRepository.findAll().size();

        // Create the Schedule

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduleMockMvc.perform(put("/api/schedules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schedule)))
            .andExpect(status().isBadRequest());

        // Validate the Schedule in the database
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSchedule() throws Exception {
        // Initialize the database
        scheduleService.save(schedule);

        int databaseSizeBeforeDelete = scheduleRepository.findAll().size();

        // Delete the schedule
        restScheduleMockMvc.perform(delete("/api/schedules/{id}", schedule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
