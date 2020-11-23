package com.wildlife.fody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EggsAndChick.
 */
@Entity
@Table(name = "eggs_and_chick")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EggsAndChick implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clutch_number")
    private String clutchNumber;

    @NotNull
    @Column(name = "eggs_present", nullable = false)
    private Boolean eggsPresent;

    @NotNull
    @Column(name = "chicks_present", nullable = false)
    private Boolean chicksPresent;

    @Column(name = "photo_taken")
    private String photoTaken;

    @Column(name = "comments")
    private String comments;

    @OneToMany(mappedBy = "eggsAndChick")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Egg> eggs = new HashSet<>();

    @OneToMany(mappedBy = "eggsAndChick")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Chick> chicks = new HashSet<>();

    @OneToOne(mappedBy = "eggsAndChick")
    @JsonIgnore
    private Sighting sighting;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClutchNumber() {
        return clutchNumber;
    }

    public EggsAndChick clutchNumber(String clutchNumber) {
        this.clutchNumber = clutchNumber;
        return this;
    }

    public void setClutchNumber(String clutchNumber) {
        this.clutchNumber = clutchNumber;
    }

    public Boolean isEggsPresent() {
        return eggsPresent;
    }

    public EggsAndChick eggsPresent(Boolean eggsPresent) {
        this.eggsPresent = eggsPresent;
        return this;
    }

    public void setEggsPresent(Boolean eggsPresent) {
        this.eggsPresent = eggsPresent;
    }

    public Boolean isChicksPresent() {
        return chicksPresent;
    }

    public EggsAndChick chicksPresent(Boolean chicksPresent) {
        this.chicksPresent = chicksPresent;
        return this;
    }

    public void setChicksPresent(Boolean chicksPresent) {
        this.chicksPresent = chicksPresent;
    }

    public String getPhotoTaken() {
        return photoTaken;
    }

    public EggsAndChick photoTaken(String photoTaken) {
        this.photoTaken = photoTaken;
        return this;
    }

    public void setPhotoTaken(String photoTaken) {
        this.photoTaken = photoTaken;
    }

    public String getComments() {
        return comments;
    }

    public EggsAndChick comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<Egg> getEggs() {
        return eggs;
    }

    public EggsAndChick eggs(Set<Egg> eggs) {
        this.eggs = eggs;
        return this;
    }

    public EggsAndChick addEgg(Egg egg) {
        this.eggs.add(egg);
        egg.setEggsAndChick(this);
        return this;
    }

    public EggsAndChick removeEgg(Egg egg) {
        this.eggs.remove(egg);
        egg.setEggsAndChick(null);
        return this;
    }

    public void setEggs(Set<Egg> eggs) {
        this.eggs = eggs;
    }

    public Set<Chick> getChicks() {
        return chicks;
    }

    public EggsAndChick chicks(Set<Chick> chicks) {
        this.chicks = chicks;
        return this;
    }

    public EggsAndChick addChick(Chick chick) {
        this.chicks.add(chick);
        chick.setEggsAndChick(this);
        return this;
    }

    public EggsAndChick removeChick(Chick chick) {
        this.chicks.remove(chick);
        chick.setEggsAndChick(null);
        return this;
    }

    public void setChicks(Set<Chick> chicks) {
        this.chicks = chicks;
    }

    public Sighting getSighting() {
        return sighting;
    }

    public EggsAndChick sighting(Sighting sighting) {
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
        if (!(o instanceof EggsAndChick)) {
            return false;
        }
        return id != null && id.equals(((EggsAndChick) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EggsAndChick{" +
            "id=" + getId() +
            ", clutchNumber='" + getClutchNumber() + "'" +
            ", eggsPresent='" + isEggsPresent() + "'" +
            ", chicksPresent='" + isChicksPresent() + "'" +
            ", photoTaken='" + getPhotoTaken() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
