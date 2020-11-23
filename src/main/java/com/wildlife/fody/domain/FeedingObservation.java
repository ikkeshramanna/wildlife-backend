package com.wildlife.fody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.wildlife.fody.domain.enumeration.FeedingObservationType;

import com.wildlife.fody.domain.enumeration.BreedAttemptType;

import com.wildlife.fody.domain.enumeration.BreedingStageType;

import com.wildlife.fody.domain.enumeration.BreedingOutcomeType;

import com.wildlife.fody.domain.enumeration.PreyItemType;

import com.wildlife.fody.domain.enumeration.PreySpeciesType;

import com.wildlife.fody.domain.enumeration.CloudType;

import com.wildlife.fody.domain.enumeration.WindType;

import com.wildlife.fody.domain.enumeration.RainType;

import com.wildlife.fody.domain.enumeration.FeedingObservationOutcomeType;

/**
 * A FeedingObservation.
 */
@Entity
@Table(name = "feeding_observation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FeedingObservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "no_field_workers", nullable = false)
    private Integer noFieldWorkers;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private FeedingObservationType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "breeding_attempt")
    private BreedAttemptType breedingAttempt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "breeding_stage", nullable = false)
    private BreedingStageType breedingStage;

    @Enumerated(EnumType.STRING)
    @Column(name = "breeding_outcome")
    private BreedingOutcomeType breedingOutcome;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "prey_item", nullable = false)
    private PreyItemType preyItem;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "prey_species", nullable = false)
    private PreySpeciesType preySpecies;

    @Column(name = "time")
    private String time;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cloud", nullable = false)
    private CloudType cloud;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "wind", nullable = false)
    private WindType wind;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rain", nullable = false)
    private RainType rain;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "outcome", nullable = false)
    private FeedingObservationOutcomeType outcome;

    @Column(name = "comment")
    private String comment;

    @OneToOne(mappedBy = "feedingObservation")
    @JsonIgnore
    private Sighting sighting;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNoFieldWorkers() {
        return noFieldWorkers;
    }

    public FeedingObservation noFieldWorkers(Integer noFieldWorkers) {
        this.noFieldWorkers = noFieldWorkers;
        return this;
    }

    public void setNoFieldWorkers(Integer noFieldWorkers) {
        this.noFieldWorkers = noFieldWorkers;
    }

    public FeedingObservationType getType() {
        return type;
    }

    public FeedingObservation type(FeedingObservationType type) {
        this.type = type;
        return this;
    }

    public void setType(FeedingObservationType type) {
        this.type = type;
    }

    public BreedAttemptType getBreedingAttempt() {
        return breedingAttempt;
    }

    public FeedingObservation breedingAttempt(BreedAttemptType breedingAttempt) {
        this.breedingAttempt = breedingAttempt;
        return this;
    }

    public void setBreedingAttempt(BreedAttemptType breedingAttempt) {
        this.breedingAttempt = breedingAttempt;
    }

    public BreedingStageType getBreedingStage() {
        return breedingStage;
    }

    public FeedingObservation breedingStage(BreedingStageType breedingStage) {
        this.breedingStage = breedingStage;
        return this;
    }

    public void setBreedingStage(BreedingStageType breedingStage) {
        this.breedingStage = breedingStage;
    }

    public BreedingOutcomeType getBreedingOutcome() {
        return breedingOutcome;
    }

    public FeedingObservation breedingOutcome(BreedingOutcomeType breedingOutcome) {
        this.breedingOutcome = breedingOutcome;
        return this;
    }

    public void setBreedingOutcome(BreedingOutcomeType breedingOutcome) {
        this.breedingOutcome = breedingOutcome;
    }

    public PreyItemType getPreyItem() {
        return preyItem;
    }

    public FeedingObservation preyItem(PreyItemType preyItem) {
        this.preyItem = preyItem;
        return this;
    }

    public void setPreyItem(PreyItemType preyItem) {
        this.preyItem = preyItem;
    }

    public PreySpeciesType getPreySpecies() {
        return preySpecies;
    }

    public FeedingObservation preySpecies(PreySpeciesType preySpecies) {
        this.preySpecies = preySpecies;
        return this;
    }

    public void setPreySpecies(PreySpeciesType preySpecies) {
        this.preySpecies = preySpecies;
    }

    public String getTime() {
        return time;
    }

    public FeedingObservation time(String time) {
        this.time = time;
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CloudType getCloud() {
        return cloud;
    }

    public FeedingObservation cloud(CloudType cloud) {
        this.cloud = cloud;
        return this;
    }

    public void setCloud(CloudType cloud) {
        this.cloud = cloud;
    }

    public WindType getWind() {
        return wind;
    }

    public FeedingObservation wind(WindType wind) {
        this.wind = wind;
        return this;
    }

    public void setWind(WindType wind) {
        this.wind = wind;
    }

    public RainType getRain() {
        return rain;
    }

    public FeedingObservation rain(RainType rain) {
        this.rain = rain;
        return this;
    }

    public void setRain(RainType rain) {
        this.rain = rain;
    }

    public FeedingObservationOutcomeType getOutcome() {
        return outcome;
    }

    public FeedingObservation outcome(FeedingObservationOutcomeType outcome) {
        this.outcome = outcome;
        return this;
    }

    public void setOutcome(FeedingObservationOutcomeType outcome) {
        this.outcome = outcome;
    }

    public String getComment() {
        return comment;
    }

    public FeedingObservation comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Sighting getSighting() {
        return sighting;
    }

    public FeedingObservation sighting(Sighting sighting) {
        this.sighting = sighting;
        return this;
    }

    public void setSighting(Sighting sighting) {
        this.sighting = sighting;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeedingObservation)) {
            return false;
        }
        return id != null && id.equals(((FeedingObservation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedingObservation{" +
            "id=" + getId() +
            ", noFieldWorkers=" + getNoFieldWorkers() +
            ", type='" + getType() + "'" +
            ", breedingAttempt='" + getBreedingAttempt() + "'" +
            ", breedingStage='" + getBreedingStage() + "'" +
            ", breedingOutcome='" + getBreedingOutcome() + "'" +
            ", preyItem='" + getPreyItem() + "'" +
            ", preySpecies='" + getPreySpecies() + "'" +
            ", time='" + getTime() + "'" +
            ", cloud='" + getCloud() + "'" +
            ", wind='" + getWind() + "'" +
            ", rain='" + getRain() + "'" +
            ", outcome='" + getOutcome() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
