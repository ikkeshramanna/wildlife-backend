package com.wildlife.fody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.wildlife.fody.domain.enumeration.FeedingTraitType;

/**
 * A Species.
 */
@Entity
@Table(name = "species")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Species implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Lob
    @Column(name = "picture", nullable = false)
    private byte[] picture;

    @Column(name = "picture_content_type", nullable = false)
    private String pictureContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "feeding_trait_type", nullable = false)
    private FeedingTraitType feedingTraitType;

    @NotNull
    @Column(name = "species_type", nullable = false)
    private String speciesType;

    @NotNull
    @Column(name = "common_name", nullable = false)
    private String commonName;

    @NotNull
    @Column(name = "latin_name", nullable = false)
    private String latinName;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_indigenous")
    private Boolean isIndigenous;

    @NotNull
    @Column(name = "add_date", nullable = false)
    private LocalDate addDate;

    @NotNull
    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @OneToMany(mappedBy = "species")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TaggedAnimal> taggedAnimals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "species", allowSetters = true)
    private SpeciesCategory speciesCategory;

    @ManyToMany(mappedBy = "species")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Country> countries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPicture() {
        return picture;
    }

    public Species picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public Species pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public FeedingTraitType getFeedingTraitType() {
        return feedingTraitType;
    }

    public Species feedingTraitType(FeedingTraitType feedingTraitType) {
        this.feedingTraitType = feedingTraitType;
        return this;
    }

    public void setFeedingTraitType(FeedingTraitType feedingTraitType) {
        this.feedingTraitType = feedingTraitType;
    }

    public String getSpeciesType() {
        return speciesType;
    }

    public Species speciesType(String speciesType) {
        this.speciesType = speciesType;
        return this;
    }

    public void setSpeciesType(String speciesType) {
        this.speciesType = speciesType;
    }

    public String getCommonName() {
        return commonName;
    }

    public Species commonName(String commonName) {
        this.commonName = commonName;
        return this;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getLatinName() {
        return latinName;
    }

    public Species latinName(String latinName) {
        this.latinName = latinName;
        return this;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public String getDescription() {
        return description;
    }

    public Species description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsIndigenous() {
        return isIndigenous;
    }

    public Species isIndigenous(Boolean isIndigenous) {
        this.isIndigenous = isIndigenous;
        return this;
    }

    public void setIsIndigenous(Boolean isIndigenous) {
        this.isIndigenous = isIndigenous;
    }

    public LocalDate getAddDate() {
        return addDate;
    }

    public Species addDate(LocalDate addDate) {
        this.addDate = addDate;
        return this;
    }

    public void setAddDate(LocalDate addDate) {
        this.addDate = addDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public Species updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Set<TaggedAnimal> getTaggedAnimals() {
        return taggedAnimals;
    }

    public Species taggedAnimals(Set<TaggedAnimal> taggedAnimals) {
        this.taggedAnimals = taggedAnimals;
        return this;
    }

    public Species addTaggedAnimal(TaggedAnimal taggedAnimal) {
        this.taggedAnimals.add(taggedAnimal);
        taggedAnimal.setSpecies(this);
        return this;
    }

    public Species removeTaggedAnimal(TaggedAnimal taggedAnimal) {
        this.taggedAnimals.remove(taggedAnimal);
        taggedAnimal.setSpecies(null);
        return this;
    }

    public void setTaggedAnimals(Set<TaggedAnimal> taggedAnimals) {
        this.taggedAnimals = taggedAnimals;
    }

    public SpeciesCategory getSpeciesCategory() {
        return speciesCategory;
    }

    public Species speciesCategory(SpeciesCategory speciesCategory) {
        this.speciesCategory = speciesCategory;
        return this;
    }

    public void setSpeciesCategory(SpeciesCategory speciesCategory) {
        this.speciesCategory = speciesCategory;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public Species countries(Set<Country> countries) {
        this.countries = countries;
        return this;
    }

    public Species addCountry(Country country) {
        this.countries.add(country);
        country.getSpecies().add(this);
        return this;
    }

    public Species removeCountry(Country country) {
        this.countries.remove(country);
        country.getSpecies().remove(this);
        return this;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Species)) {
            return false;
        }
        return id != null && id.equals(((Species) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Species{" +
            "id=" + getId() +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            ", feedingTraitType='" + getFeedingTraitType() + "'" +
            ", speciesType='" + getSpeciesType() + "'" +
            ", commonName='" + getCommonName() + "'" +
            ", latinName='" + getLatinName() + "'" +
            ", description='" + getDescription() + "'" +
            ", isIndigenous='" + isIsIndigenous() + "'" +
            ", addDate='" + getAddDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
