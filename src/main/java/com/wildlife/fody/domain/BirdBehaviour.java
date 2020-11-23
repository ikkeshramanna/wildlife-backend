package com.wildlife.fody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.wildlife.fody.domain.enumeration.BirdBehaviourType;

/**
 * A BirdBehaviour.
 */
@Entity
@Table(name = "bird_behaviour")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BirdBehaviour implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "behaviour", nullable = false)
    private BirdBehaviourType behaviour;

    @ManyToMany(mappedBy = "birdBehaviours")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<BirdsIdentified> birdsIdentifieds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BirdBehaviourType getBehaviour() {
        return behaviour;
    }

    public BirdBehaviour behaviour(BirdBehaviourType behaviour) {
        this.behaviour = behaviour;
        return this;
    }

    public void setBehaviour(BirdBehaviourType behaviour) {
        this.behaviour = behaviour;
    }

    public Set<BirdsIdentified> getBirdsIdentifieds() {
        return birdsIdentifieds;
    }

    public BirdBehaviour birdsIdentifieds(Set<BirdsIdentified> birdsIdentifieds) {
        this.birdsIdentifieds = birdsIdentifieds;
        return this;
    }

    public BirdBehaviour addBirdsIdentified(BirdsIdentified birdsIdentified) {
        this.birdsIdentifieds.add(birdsIdentified);
        birdsIdentified.getBirdBehaviours().add(this);
        return this;
    }

    public BirdBehaviour removeBirdsIdentified(BirdsIdentified birdsIdentified) {
        this.birdsIdentifieds.remove(birdsIdentified);
        birdsIdentified.getBirdBehaviours().remove(this);
        return this;
    }

    public void setBirdsIdentifieds(Set<BirdsIdentified> birdsIdentifieds) {
        this.birdsIdentifieds = birdsIdentifieds;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BirdBehaviour)) {
            return false;
        }
        return id != null && id.equals(((BirdBehaviour) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BirdBehaviour{" +
            "id=" + getId() +
            ", behaviour='" + getBehaviour() + "'" +
            "}";
    }
}
