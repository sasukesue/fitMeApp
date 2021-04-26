package com.proj.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;

/**
 * A Schedule.
 */
@Entity
@Table(name = "schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_time", nullable = false)
    private ZonedDateTime dateTime;

    @NotNull
    @Column(name = "available_slots", nullable = false)
    private Integer availableSlots;

    @NotNull
    @Column(name = "fitness_class_id", nullable = false)
    private Long fitnessClassId;

    @NotNull
    @Column(name = "location_id", nullable = false)
    private Long locationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public Schedule dateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getAvailableSlots() {
        return availableSlots;
    }

    public Schedule availableSlots(Integer availableSlots) {
        this.availableSlots = availableSlots;
        return this;
    }

    public void setAvailableSlots(Integer availableSlots) {
        this.availableSlots = availableSlots;
    }

    public Long getFitnessClassId() {
        return fitnessClassId;
    }

    public Schedule fitnessClassId(Long fitnessClassId) {
        this.fitnessClassId = fitnessClassId;
        return this;
    }

    public void setFitnessClassId(Long fitnessClassId) {
        this.fitnessClassId = fitnessClassId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public Schedule locationId(Long locationId) {
        this.locationId = locationId;
        return this;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Schedule)) {
            return false;
        }
        return id != null && id.equals(((Schedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Schedule{" +
            "id=" + getId() +
            ", dateTime='" + getDateTime() + "'" +
            ", availableSlots=" + getAvailableSlots() +
            ", fitnessClassId=" + getFitnessClassId() +
            ", locationId=" + getLocationId() +
            "}";
    }
}
