package com.wildlife.fody.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.wildlife.fody.domain.enumeration.TagType;

import com.wildlife.fody.domain.enumeration.TaggedAnimalSexType;

/**
 * A TaggedAnimal.
 */
@Entity
@Table(name = "tagged_animal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaggedAnimal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "date_of_tagging")
    private LocalDate dateOfTagging;

    @Column(name = "physical_traits")
    private String physicalTraits;

    @Enumerated(EnumType.STRING)
    @Column(name = "tag_type")
    private TagType tagType;

    @Column(name = "date_time")
    private LocalDate dateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex_type")
    private TaggedAnimalSexType sexType;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "taggedAnimals", allowSetters = true)
    private Employee taggedAnimal;

    @ManyToOne
    @JsonIgnoreProperties(value = "taggedAnimals", allowSetters = true)
    private Species species;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TaggedAnimal name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public TaggedAnimal dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfTagging() {
        return dateOfTagging;
    }

    public TaggedAnimal dateOfTagging(LocalDate dateOfTagging) {
        this.dateOfTagging = dateOfTagging;
        return this;
    }

    public void setDateOfTagging(LocalDate dateOfTagging) {
        this.dateOfTagging = dateOfTagging;
    }

    public String getPhysicalTraits() {
        return physicalTraits;
    }

    public TaggedAnimal physicalTraits(String physicalTraits) {
        this.physicalTraits = physicalTraits;
        return this;
    }

    public void setPhysicalTraits(String physicalTraits) {
        this.physicalTraits = physicalTraits;
    }

    public TagType getTagType() {
        return tagType;
    }

    public TaggedAnimal tagType(TagType tagType) {
        this.tagType = tagType;
        return this;
    }

    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public TaggedAnimal dateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public TaggedAnimalSexType getSexType() {
        return sexType;
    }

    public TaggedAnimal sexType(TaggedAnimalSexType sexType) {
        this.sexType = sexType;
        return this;
    }

    public void setSexType(TaggedAnimalSexType sexType) {
        this.sexType = sexType;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public TaggedAnimal updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Employee getTaggedAnimal() {
        return taggedAnimal;
    }

    public TaggedAnimal taggedAnimal(Employee employee) {
        this.taggedAnimal = employee;
        return this;
    }

    public void setTaggedAnimal(Employee employee) {
        this.taggedAnimal = employee;
    }

    public Species getSpecies() {
        return species;
    }

    public TaggedAnimal species(Species species) {
        this.species = species;
        return this;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaggedAnimal)) {
            return false;
        }
        return id != null && id.equals(((TaggedAnimal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaggedAnimal{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", dateOfTagging='" + getDateOfTagging() + "'" +
            ", physicalTraits='" + getPhysicalTraits() + "'" +
            ", tagType='" + getTagType() + "'" +
            ", dateTime='" + getDateTime() + "'" +
            ", sexType='" + getSexType() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
