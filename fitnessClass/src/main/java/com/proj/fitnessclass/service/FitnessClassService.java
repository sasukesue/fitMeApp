package com.proj.fitnessclass.service;

import com.proj.fitnessclass.domain.FitnessClass;
import com.proj.fitnessclass.repository.FitnessClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FitnessClass}.
 */
@Service
@Transactional
public class FitnessClassService {

    private final Logger log = LoggerFactory.getLogger(FitnessClassService.class);

    private final FitnessClassRepository fitnessClassRepository;

    public FitnessClassService(FitnessClassRepository fitnessClassRepository) {
        this.fitnessClassRepository = fitnessClassRepository;
    }

    /**
     * Save a fitnessClass.
     *
     * @param fitnessClass the entity to save.
     * @return the persisted entity.
     */
    public FitnessClass save(FitnessClass fitnessClass) {
        log.debug("Request to save FitnessClass : {}", fitnessClass);
        return fitnessClassRepository.save(fitnessClass);
    }

    /**
     * Get all the fitnessClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FitnessClass> findAll(Pageable pageable) {
        log.debug("Request to get all FitnessClasses");
        return fitnessClassRepository.findAll(pageable);
    }

    /**
     * Get one fitnessClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FitnessClass> findOne(Long id) {
        log.debug("Request to get FitnessClass : {}", id);
        return fitnessClassRepository.findById(id);
    }

    /**
     * Delete the fitnessClass by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FitnessClass : {}", id);
        fitnessClassRepository.deleteById(id);
    }
}
