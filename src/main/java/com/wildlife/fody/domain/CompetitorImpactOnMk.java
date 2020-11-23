package com.wildlife.fody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.wildlife.fody.domain.enumeration.ImpactOnMKType;

/**
 * A CompetitorImpactOnMk.
 */
@Entity
@Table(name = "competitor_impact_on_mk")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompetitorImpactOnMk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "impact", nullable = false)
    private ImpactOnMKType impact;

    @ManyToMany(mappedBy = "competitorImpactOnMks")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Competitors> competitors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ImpactOnMKType getImpact() {
        return impact;
    }

    public CompetitorImpactOnMk impact(ImpactOnMKType impact) {
        this.impact = impact;
        return this;
    }

    public void setImpact(ImpactOnMKType impact) {
        this.impact = impact;
    }

    public Set<Competitors> getCompetitors() {
        return competitors;
    }

    public CompetitorImpactOnMk competitors(Set<Competitors> competitors) {
        this.competitors = competitors;
        return this;
    }

    public CompetitorImpactOnMk addCompetitors(Competitors competitors) {
        this.competitors.add(competitors);
        competitors.getCompetitorImpactOnMks().add(this);
        return this;
    }

    public CompetitorImpactOnMk removeCompetitors(Competitors competitors) {
        this.competitors.remove(competitors);
        competitors.getCompetitorImpactOnMks().remove(this);
        return this;
    }

    public void setCompetitors(Set<Competitors> competitors) {
        this.competitors = competitors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompetitorImpactOnMk)) {
            return false;
        }
        return id != null && id.equals(((CompetitorImpactOnMk) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompetitorImpactOnMk{" +
            "id=" + getId() +
            ", impact='" + getImpact() + "'" +
            "}";
    }
}
