package com.wildlife.fody.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.wildlife.fody.domain.enumeration.SpeciesCategoryType;

/**
 * A SpeciesCategory.
 */
@Entity
@Table(name = "species_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SpeciesCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "species_category_type", nullable = false)
    private SpeciesCategoryType speciesCategoryType;

    
    @Lob
    @Column(name = "picture", nullable = false)
    private byte[] picture;

    @Column(name = "picture_content_type", nullable = false)
    private String pictureContentType;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "add_date", nullable = false)
    private LocalDate addDate;

    @NotNull
    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

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

    public SpeciesCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpeciesCategoryType getSpeciesCategoryType() {
        return speciesCategoryType;
    }

    public SpeciesCategory speciesCategoryType(SpeciesCategoryType speciesCategoryType) {
        this.speciesCategoryType = speciesCategoryType;
        return this;
    }

    public void setSpeciesCategoryType(SpeciesCategoryType speciesCategoryType) {
        this.speciesCategoryType = speciesCategoryType;
    }

    public byte[] getPicture() {
        return picture;
    }

    public SpeciesCategory picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public SpeciesCategory pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public String getDescription() {
        return description;
    }

    public SpeciesCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getAddDate() {
        return addDate;
    }

    public SpeciesCategory addDate(LocalDate addDate) {
        this.addDate = addDate;
        return this;
    }

    public void setAddDate(LocalDate addDate) {
        this.addDate = addDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public SpeciesCategory updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpeciesCategory)) {
            return false;
        }
        return id != null && id.equals(((SpeciesCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpeciesCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", speciesCategoryType='" + getSpeciesCategoryType() + "'" +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            ", description='" + getDescription() + "'" +
            ", addDate='" + getAddDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
