package com.wildlife.fody.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "country_name", nullable = false)
    private String countryName;

    @NotNull
    @Column(name = "add_date", nullable = false)
    private LocalDate addDate;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "country_species",
               joinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "species_id", referencedColumnName = "id"))
    private Set<Species> species = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public Country countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public LocalDate getAddDate() {
        return addDate;
    }

    public Country addDate(LocalDate addDate) {
        this.addDate = addDate;
        return this;
    }

    public void setAddDate(LocalDate addDate) {
        this.addDate = addDate;
    }

    public Set<Species> getSpecies() {
        return species;
    }

    public Country species(Set<Species> species) {
        this.species = species;
        return this;
    }

    public Country addSpecies(Species species) {
        this.species.add(species);
        species.getCountries().add(this);
        return this;
    }

    public Country removeSpecies(Species species) {
        this.species.remove(species);
        species.getCountries().remove(this);
        return this;
    }

    public void setSpecies(Set<Species> species) {
        this.species = species;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return id != null && id.equals(((Country) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", addDate='" + getAddDate() + "'" +
            "}";
    }
}
