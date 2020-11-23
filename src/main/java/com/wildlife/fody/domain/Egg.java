package com.wildlife.fody.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.wildlife.fody.domain.enumeration.EggStatusType;

/**
 * A Egg.
 */
@Entity
@Table(name = "egg")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Egg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "egg_number")
    private String eggNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "egg_status")
    private EggStatusType eggStatus;

    @Column(name = "egg_lay_date")
    private LocalDate eggLayDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "eggs", allowSetters = true)
    private EggsAndChick eggsAndChick;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEggNumber() {
        return eggNumber;
    }

    public Egg eggNumber(String eggNumber) {
        this.eggNumber = eggNumber;
        return this;
    }

    public void setEggNumber(String eggNumber) {
        this.eggNumber = eggNumber;
    }

    public EggStatusType getEggStatus() {
        return eggStatus;
    }

    public Egg eggStatus(EggStatusType eggStatus) {
        this.eggStatus = eggStatus;
        return this;
    }

    public void setEggStatus(EggStatusType eggStatus) {
        this.eggStatus = eggStatus;
    }

    public LocalDate getEggLayDate() {
        return eggLayDate;
    }

    public Egg eggLayDate(LocalDate eggLayDate) {
        this.eggLayDate = eggLayDate;
        return this;
    }

    public void setEggLayDate(LocalDate eggLayDate) {
        this.eggLayDate = eggLayDate;
    }

    public EggsAndChick getEggsAndChick() {
        return eggsAndChick;
    }

    public Egg eggsAndChick(EggsAndChick eggsAndChick) {
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
        if (!(o instanceof Egg)) {
            return false;
        }
        return id != null && id.equals(((Egg) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Egg{" +
            "id=" + getId() +
            ", eggNumber='" + getEggNumber() + "'" +
            ", eggStatus='" + getEggStatus() + "'" +
            ", eggLayDate='" + getEggLayDate() + "'" +
            "}";
    }
}
