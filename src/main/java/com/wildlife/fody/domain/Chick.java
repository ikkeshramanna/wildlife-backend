package com.wildlife.fody.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.wildlife.fody.domain.enumeration.ChicKStatusType;

import com.wildlife.fody.domain.enumeration.ChickConditionType;

/**
 * A Chick.
 */
@Entity
@Table(name = "chick")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Chick implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "chick_number", nullable = false)
    private Integer chickNumber;

    @NotNull
    @Column(name = "hatch_date", nullable = false)
    private LocalDate hatchDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "chick_status", nullable = false)
    private ChicKStatusType chickStatus;

    @NotNull
    @Column(name = "chick_age", nullable = false)
    private Integer chickAge;

    @NotNull
    @Column(name = "chick_active", nullable = false)
    private String chickActive;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "chick_condition", nullable = false)
    private ChickConditionType chickCondition;

    @NotNull
    @Column(name = "chick_ringed", nullable = false)
    private String chickRinged;

    @NotNull
    @Column(name = "blood_sample", nullable = false)
    private String bloodSample;

    @ManyToOne
    @JsonIgnoreProperties(value = "chicks", allowSetters = true)
    private EggsAndChick eggsAndChick;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getChickNumber() {
        return chickNumber;
    }

    public Chick chickNumber(Integer chickNumber) {
        this.chickNumber = chickNumber;
        return this;
    }

    public void setChickNumber(Integer chickNumber) {
        this.chickNumber = chickNumber;
    }

    public LocalDate getHatchDate() {
        return hatchDate;
    }

    public Chick hatchDate(LocalDate hatchDate) {
        this.hatchDate = hatchDate;
        return this;
    }

    public void setHatchDate(LocalDate hatchDate) {
        this.hatchDate = hatchDate;
    }

    public ChicKStatusType getChickStatus() {
        return chickStatus;
    }

    public Chick chickStatus(ChicKStatusType chickStatus) {
        this.chickStatus = chickStatus;
        return this;
    }

    public void setChickStatus(ChicKStatusType chickStatus) {
        this.chickStatus = chickStatus;
    }

    public Integer getChickAge() {
        return chickAge;
    }

    public Chick chickAge(Integer chickAge) {
        this.chickAge = chickAge;
        return this;
    }

    public void setChickAge(Integer chickAge) {
        this.chickAge = chickAge;
    }

    public String getChickActive() {
        return chickActive;
    }

    public Chick chickActive(String chickActive) {
        this.chickActive = chickActive;
        return this;
    }

    public void setChickActive(String chickActive) {
        this.chickActive = chickActive;
    }

    public ChickConditionType getChickCondition() {
        return chickCondition;
    }

    public Chick chickCondition(ChickConditionType chickCondition) {
        this.chickCondition = chickCondition;
        return this;
    }

    public void setChickCondition(ChickConditionType chickCondition) {
        this.chickCondition = chickCondition;
    }

    public String getChickRinged() {
        return chickRinged;
    }

    public Chick chickRinged(String chickRinged) {
        this.chickRinged = chickRinged;
        return this;
    }

    public void setChickRinged(String chickRinged) {
        this.chickRinged = chickRinged;
    }

    public String getBloodSample() {
        return bloodSample;
    }

    public Chick bloodSample(String bloodSample) {
        this.bloodSample = bloodSample;
        return this;
    }

    public void setBloodSample(String bloodSample) {
        this.bloodSample = bloodSample;
    }

    public EggsAndChick getEggsAndChick() {
        return eggsAndChick;
    }

    public Chick eggsAndChick(EggsAndChick eggsAndChick) {
        this.eggsAndChick = eggsAndChick;
        return this;
    }

    public void setEggsAndChick(EggsAndChick eggsAndChick) {
        this.eggsAndChick = eggsAndChick;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chick)) {
            return false;
        }
        return id != null && id.equals(((Chick) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Chick{" +
            "id=" + getId() +
            ", chickNumber=" + getChickNumber() +
            ", hatchDate='" + getHatchDate() + "'" +
            ", chickStatus='" + getChickStatus() + "'" +
            ", chickAge=" + getChickAge() +
            ", chickActive='" + getChickActive() + "'" +
            ", chickCondition='" + getChickCondition() + "'" +
            ", chickRinged='" + getChickRinged() + "'" +
            ", bloodSample='" + getBloodSample() + "'" +
            "}";
    }
}
