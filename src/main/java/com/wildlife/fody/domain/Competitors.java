package com.wildlife.fody.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.wildlife.fody.domain.enumeration.CompetitorBehaviourType;

import com.wildlife.fody.domain.enumeration.CompetitorLocationType;

/**
 * A Competitors.
 */
@Entity
@Table(name = "competitors")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Competitors implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mk_around")
    private String mkAround;

    @NotNull
    @Column(name = "no_of_individuals", nullable = false)
    private Integer noOfIndividuals;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "competitor_behaviour", nullable = false)
    private CompetitorBehaviourType competitorBehaviour;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "competitor_location", nullable = false)
    private CompetitorLocationType competitorLocation;

    @ManyToOne
    @JsonIgnoreProperties(value = "competitors", allowSetters = true)
    private Species competitorSpecies;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "competitors_competitor_impact_on_mk",
               joinColumns = @JoinColumn(name = "competitors_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "competitor_impact_on_mk_id", referencedColumnName = "id"))
    private Set<CompetitorImpactOnMk> competitorImpactOnMks = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "competitors_competitor_action",
               joinColumns = @JoinColumn(name = "competitors_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "competitor_action_id", referencedColumnName = "id"))
    private Set<CompetitorAction> competitorActions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "competitors", allowSetters = true)
    private Sighting sighting;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMkAround() {
        return mkAround;
    }

    public Competitors mkAround(String mkAround) {
        this.mkAround = mkAround;
        return this;
    }

    public void setMkAround(String mkAround) {
        this.mkAround = mkAround;
    }

    public Integer getNoOfIndividuals() {
        return noOfIndividuals;
    }

    public Competitors noOfIndividuals(Integer noOfIndividuals) {
        this.noOfIndividuals = noOfIndividuals;
        return this;
    }

    public void setNoOfIndividuals(Integer noOfIndividuals) {
        this.noOfIndividuals = noOfIndividuals;
    }

    public CompetitorBehaviourType getCompetitorBehaviour() {
        return competitorBehaviour;
    }

    public Competitors competitorBehaviour(CompetitorBehaviourType competitorBehaviour) {
        this.competitorBehaviour = competitorBehaviour;
        return this;
    }

    public void setCompetitorBehaviour(CompetitorBehaviourType competitorBehaviour) {
        this.competitorBehaviour = competitorBehaviour;
    }

    public CompetitorLocationType getCompetitorLocation() {
        return competitorLocation;
    }

    public Competitors competitorLocation(CompetitorLocationType competitorLocation) {
        this.competitorLocation = competitorLocation;
        return this;
    }

    public void setCompetitorLocation(CompetitorLocationType competitorLocation) {
        this.competitorLocation = competitorLocation;
    }

    public Species getCompetitorSpecies() {
        return competitorSpecies;
    }

    public Competitors competitorSpecies(Species species) {
        this.competitorSpecies = species;
        return this;
    }

    public void setCompetitorSpecies(Species species) {
        this.competitorSpecies = species;
    }

    public Set<CompetitorImpactOnMk> getCompetitorImpactOnMks() {
        return competitorImpactOnMks;
    }

    public Competitors competitorImpactOnMks(Set<CompetitorImpactOnMk> competitorImpactOnMks) {
        this.competitorImpactOnMks = competitorImpactOnMks;
        return this;
    }

    public Competitors addCompetitorImpactOnMk(CompetitorImpactOnMk competitorImpactOnMk) {
        this.competitorImpactOnMks.add(competitorImpactOnMk);
        competitorImpactOnMk.getCompetitors().add(this);
        return this;
    }

    public Competitors removeCompetitorImpactOnMk(CompetitorImpactOnMk competitorImpactOnMk) {
        this.competitorImpactOnMks.remove(competitorImpactOnMk);
        competitorImpactOnMk.getCompetitors().remove(this);
        return this;
    }

    public void setCompetitorImpactOnMks(Set<CompetitorImpactOnMk> competitorImpactOnMks) {
        this.competitorImpactOnMks = competitorImpactOnMks;
    }

    public Set<CompetitorAction> getCompetitorActions() {
        return competitorActions;
    }

    public Competitors competitorActions(Set<CompetitorAction> competitorActions) {
        this.competitorActions = competitorActions;
        return this;
    }

    public Competitors addCompetitorAction(CompetitorAction competitorAction) {
        this.competitorActions.add(competitorAction);
        competitorAction.getCompetitors().add(this);
        return this;
    }

    public Competitors removeCompetitorAction(CompetitorAction competitorAction) {
        this.competitorActions.remove(competitorAction);
        competitorAction.getCompetitors().remove(this);
        return this;
    }

    public void setCompetitorActions(Set<CompetitorAction> competitorActions) {
        this.competitorActions = competitorActions;
    }

    public Sighting getSighting() {
        return sighting;
    }

    public Competitors sighting(Sighting sighting) {
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
        if (!(o instanceof Competitors)) {
            return false;
        }
        return id != null && id.equals(((Competitors) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Competitors{" +
            "id=" + getId() +
            ", mkAround='" + getMkAround() + "'" +
            ", noOfIndividuals=" + getNoOfIndividuals() +
            ", competitorBehaviour='" + getCompetitorBehaviour() + "'" +
            ", competitorLocation='" + getCompetitorLocation() + "'" +
            "}";
    }
}
