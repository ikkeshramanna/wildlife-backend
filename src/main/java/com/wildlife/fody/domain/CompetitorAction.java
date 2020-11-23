package com.wildlife.fody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.wildlife.fody.domain.enumeration.ActionType;

/**
 * A CompetitorAction.
 */
@Entity
@Table(name = "competitor_action")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompetitorAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private ActionType action;

    @ManyToMany(mappedBy = "competitorActions")
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

    public ActionType getAction() {
        return action;
    }

    public CompetitorAction action(ActionType action) {
        this.action = action;
        return this;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public Set<Competitors> getCompetitors() {
        return competitors;
    }

    public CompetitorAction competitors(Set<Competitors> competitors) {
        this.competitors = competitors;
        return this;
    }

    public CompetitorAction addCompetitors(Competitors competitors) {
        this.competitors.add(competitors);
        competitors.getCompetitorActions().add(this);
        return this;
    }

    public CompetitorAction removeCompetitors(Competitors competitors) {
        this.competitors.remove(competitors);
        competitors.getCompetitorActions().remove(this);
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
        if (!(o instanceof CompetitorAction)) {
            return false;
        }
        return id != null && id.equals(((CompetitorAction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompetitorAction{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            "}";
    }
}
