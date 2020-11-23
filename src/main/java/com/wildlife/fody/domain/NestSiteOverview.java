package com.wildlife.fody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.wildlife.fody.domain.enumeration.VisitPurposeType;

import com.wildlife.fody.domain.enumeration.UseSignType;

/**
 * A NestSiteOverview.
 */
@Entity
@Table(name = "nest_site_overview")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NestSiteOverview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "number_people", nullable = false)
    private Integer numberPeople;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "purpose_of_visit", nullable = false)
    private VisitPurposeType purposeOfVisit;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "signs_of_use", nullable = false)
    private UseSignType signsOfUse;

    @Column(name = "nesting_substrate")
    private String nestingSubstrate;

    @Column(name = "maintenance_done")
    private String maintenanceDone;

    @Column(name = "maintenance_required")
    private String maintenanceRequired;

    @Column(name = "comments")
    private String comments;

    @OneToOne(mappedBy = "nestSiteOverview")
    @JsonIgnore
    private Sighting sighting;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberPeople() {
        return numberPeople;
    }

    public NestSiteOverview numberPeople(Integer numberPeople) {
        this.numberPeople = numberPeople;
        return this;
    }

    public void setNumberPeople(Integer numberPeople) {
        this.numberPeople = numberPeople;
    }

    public VisitPurposeType getPurposeOfVisit() {
        return purposeOfVisit;
    }

    public NestSiteOverview purposeOfVisit(VisitPurposeType purposeOfVisit) {
        this.purposeOfVisit = purposeOfVisit;
        return this;
    }

    public void setPurposeOfVisit(VisitPurposeType purposeOfVisit) {
        this.purposeOfVisit = purposeOfVisit;
    }

    public UseSignType getSignsOfUse() {
        return signsOfUse;
    }

    public NestSiteOverview signsOfUse(UseSignType signsOfUse) {
        this.signsOfUse = signsOfUse;
        return this;
    }

    public void setSignsOfUse(UseSignType signsOfUse) {
        this.signsOfUse = signsOfUse;
    }

    public String getNestingSubstrate() {
        return nestingSubstrate;
    }

    public NestSiteOverview nestingSubstrate(String nestingSubstrate) {
        this.nestingSubstrate = nestingSubstrate;
        return this;
    }

    public void setNestingSubstrate(String nestingSubstrate) {
        this.nestingSubstrate = nestingSubstrate;
    }

    public String getMaintenanceDone() {
        return maintenanceDone;
    }

    public NestSiteOverview maintenanceDone(String maintenanceDone) {
        this.maintenanceDone = maintenanceDone;
        return this;
    }

    public void setMaintenanceDone(String maintenanceDone) {
        this.maintenanceDone = maintenanceDone;
    }

    public String getMaintenanceRequired() {
        return maintenanceRequired;
    }

    public NestSiteOverview maintenanceRequired(String maintenanceRequired) {
        this.maintenanceRequired = maintenanceRequired;
        return this;
    }

    public void setMaintenanceRequired(String maintenanceRequired) {
        this.maintenanceRequired = maintenanceRequired;
    }

    public String getComments() {
        return comments;
    }

    public NestSiteOverview comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Sighting getSighting() {
        return sighting;
    }

    public NestSiteOverview sighting(Sighting sighting) {
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
        if (!(o instanceof NestSiteOverview)) {
            return false;
        }
        return id != null && id.equals(((NestSiteOverview) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NestSiteOverview{" +
            "id=" + getId() +
            ", numberPeople=" + getNumberPeople() +
            ", purposeOfVisit='" + getPurposeOfVisit() + "'" +
            ", signsOfUse='" + getSignsOfUse() + "'" +
            ", nestingSubstrate='" + getNestingSubstrate() + "'" +
            ", maintenanceDone='" + getMaintenanceDone() + "'" +
            ", maintenanceRequired='" + getMaintenanceRequired() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
