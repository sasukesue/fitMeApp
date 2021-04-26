package com.proj.app.service;

import com.proj.app.domain.Schedule;
import com.proj.app.repository.ScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Schedule}.
 */
@Service
@Transactional
public class ScheduleService {

    private final Logger log = LoggerFactory.getLogger(ScheduleService.class);

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * Save a schedule.
     *
     * @param schedule the entity to save.
     * @return the persisted entity.
     */
    public Schedule save(Schedule schedule) {
        log.debug("Request to save Schedule : {}", schedule);
        return scheduleRepository.save(schedule);
    }

    /**
     * Get all the schedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Schedule> findAll(Pageable pageable) {
        log.debug("Request to get all Schedules");
        return scheduleRepository.findAll(pageable);
    }

    /**
     * Get one schedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Schedule> findOne(Long id) {
        log.debug("Request to get Schedule : {}", id);
        return scheduleRepository.findById(id);
    }

    /**
     * Delete the schedule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Schedule : {}", id);
        scheduleRepository.deleteById(id);
    }
}
