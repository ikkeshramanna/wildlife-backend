package com.wildlife.fody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.wildlife.fody.domain.enumeration.StrutType;

import com.wildlife.fody.domain.enumeration.BoxConditionType;

import com.wildlife.fody.domain.enumeration.BeePlasticType;

import com.wildlife.fody.domain.enumeration.HatchType;

import com.wildlife.fody.domain.enumeration.StepsType;

import com.wildlife.fody.domain.enumeration.HandHoldsType;

import com.wildlife.fody.domain.enumeration.ClearingType;

import com.wildlife.fody.domain.enumeration.PathType;

import com.wildlife.fody.domain.enumeration.SiteDescriptionType;

import com.wildlife.fody.domain.enumeration.SideType;

import com.wildlife.fody.domain.enumeration.TreeSpeciesType;

/**
 * A Maintenance.
 */
@Entity
@Table(name = "maintenance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Maintenance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "struts", nullable = false)
    private StrutType struts;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "box_condition", nullable = false)
    private BoxConditionType boxCondition;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "bee_plastic", nullable = false)
    private BeePlasticType beePlastic;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hatch", nullable = false)
    private HatchType hatch;

    @Enumerated(EnumType.STRING)
    @Column(name = "steps")
    private StepsType steps;

    @Enumerated(EnumType.STRING)
    @Column(name = "handholds")
    private HandHoldsType handholds;

    @Column(name = "tree_needs_replacing")
    private String treeNeedsReplacing;

    @Column(name = "box_needs_replacing")
    private String boxNeedsReplacing;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "clearing", nullable = false)
    private ClearingType clearing;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "path", nullable = false)
    private PathType path;

    @Column(name = "additional_visit")
    private String additionalVisit;

    @Column(name = "drill_required")
    private String drillRequired;

    @Enumerated(EnumType.STRING)
    @Column(name = "site_description")
    private SiteDescriptionType siteDescription;

    @NotNull
    @Column(name = "bearing", nullable = false)
    private String bearing;

    @Enumerated(EnumType.STRING)
    @Column(name = "side")
    private SideType side;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tree_species", nullable = false)
    private TreeSpeciesType treeSpecies;

    @NotNull
    @Column(name = "height", nullable = false)
    private Float height;

    @Column(name = "comments")
    private String comments;

    @OneToOne(mappedBy = "maintenance")
    @JsonIgnore
    private Sighting sighting;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StrutType getStruts() {
        return struts;
    }

    public Maintenance struts(StrutType struts) {
        this.struts = struts;
        return this;
    }

    public void setStruts(StrutType struts) {
        this.struts = struts;
    }

    public BoxConditionType getBoxCondition() {
        return boxCondition;
    }

    public Maintenance boxCondition(BoxConditionType boxCondition) {
        this.boxCondition = boxCondition;
        return this;
    }

    public void setBoxCondition(BoxConditionType boxCondition) {
        this.boxCondition = boxCondition;
    }

    public BeePlasticType getBeePlastic() {
        return beePlastic;
    }

    public Maintenance beePlastic(BeePlasticType beePlastic) {
        this.beePlastic = beePlastic;
        return this;
    }

    public void setBeePlastic(BeePlasticType beePlastic) {
        this.beePlastic = beePlastic;
    }

    public HatchType getHatch() {
        return hatch;
    }

    public Maintenance hatch(HatchType hatch) {
        this.hatch = hatch;
        return this;
    }

    public void setHatch(HatchType hatch) {
        this.hatch = hatch;
    }

    public StepsType getSteps() {
        return steps;
    }

    public Maintenance steps(StepsType steps) {
        this.steps = steps;
        return this;
    }

    public void setSteps(StepsType steps) {
        this.steps = steps;
    }

    public HandHoldsType getHandholds() {
        return handholds;
    }

    public Maintenance handholds(HandHoldsType handholds) {
        this.handholds = handholds;
        return this;
    }

    public void setHandholds(HandHoldsType handholds) {
        this.handholds = handholds;
    }

    public String getTreeNeedsReplacing() {
        return treeNeedsReplacing;
    }

    public Maintenance treeNeedsReplacing(String treeNeedsReplacing) {
        this.treeNeedsReplacing = treeNeedsReplacing;
        return this;
    }

    public void setTreeNeedsReplacing(String treeNeedsReplacing) {
        this.treeNeedsReplacing = treeNeedsReplacing;
    }

    public String getBoxNeedsReplacing() {
        return boxNeedsReplacing;
    }

    public Maintenance boxNeedsReplacing(String boxNeedsReplacing) {
        this.boxNeedsReplacing = boxNeedsReplacing;
        return this;
    }

    public void setBoxNeedsReplacing(String boxNeedsReplacing) {
        this.boxNeedsReplacing = boxNeedsReplacing;
    }

    public ClearingType getClearing() {
        return clearing;
    }

    public Maintenance clearing(ClearingType clearing) {
        this.clearing = clearing;
        return this;
    }

    public void setClearing(ClearingType clearing) {
        this.clearing = clearing;
    }

    public PathType getPath() {
        return path;
    }

    public Maintenance path(PathType path) {
        this.path = path;
        return this;
    }

    public void setPath(PathType path) {
        this.path = path;
    }

    public String getAdditionalVisit() {
        return additionalVisit;
    }

    public Maintenance additionalVisit(String additionalVisit) {
        this.additionalVisit = additionalVisit;
        return this;
    }

    public void setAdditionalVisit(String additionalVisit) {
        this.additionalVisit = additionalVisit;
    }

    public String getDrillRequired() {
        return drillRequired;
    }

    public Maintenance drillRequired(String drillRequired) {
        this.drillRequired = drillRequired;
        return this;
    }

    public void setDrillRequired(String drillRequired) {
        this.drillRequired = drillRequired;
    }

    public SiteDescriptionType getSiteDescription() {
        return siteDescription;
    }

    public Maintenance siteDescription(SiteDescriptionType siteDescription) {
        this.siteDescription = siteDescription;
        return this;
    }

    public void setSiteDescription(SiteDescriptionType siteDescription) {
        this.siteDescription = siteDescription;
    }

    public String getBearing() {
        return bearing;
    }

    public Maintenance bearing(String bearing) {
        this.bearing = bearing;
        return this;
    }

    public void setBearing(String bearing) {
        this.bearing = bearing;
    }

    public SideType getSide() {
        return side;
    }

    public Maintenance side(SideType side) {
        this.side = side;
        return this;
    }

    public void setSide(SideType side) {
        this.side = side;
    }

    public TreeSpeciesType getTreeSpecies() {
        return treeSpecies;
    }

    public Maintenance treeSpecies(TreeSpeciesType treeSpecies) {
        this.treeSpecies = treeSpecies;
        return this;
    }

    public void setTreeSpecies(TreeSpeciesType treeSpecies) {
        this.treeSpecies = treeSpecies;
    }

    public Float getHeight() {
        return height;
    }

    public Maintenance height(Float height) {
        this.height = height;
        return this;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getComments() {
        return comments;
    }

    public Maintenance comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Sighting getSighting() {
        return sighting;
    }

    public Maintenance sighting(Sighting sighting) {
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
        if (!(o instanceof Maintenance)) {
            return false;
        }
        return id != null && id.equals(((Maintenance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Maintenance{" +
            "id=" + getId() +
            ", struts='" + getStruts() + "'" +
            ", boxCondition='" + getBoxCondition() + "'" +
            ", beePlastic='" + getBeePlastic() + "'" +
            ", hatch='" + getHatch() + "'" +
            ", steps='" + getSteps() + "'" +
            ", handholds='" + getHandholds() + "'" +
            ", treeNeedsReplacing='" + getTreeNeedsReplacing() + "'" +
            ", boxNeedsReplacing='" + getBoxNeedsReplacing() + "'" +
            ", clearing='" + getClearing() + "'" +
            ", path='" + getPath() + "'" +
            ", additionalVisit='" + getAdditionalVisit() + "'" +
            ", drillRequired='" + getDrillRequired() + "'" +
            ", siteDescription='" + getSiteDescription() + "'" +
            ", bearing='" + getBearing() + "'" +
            ", side='" + getSide() + "'" +
            ", treeSpecies='" + getTreeSpecies() + "'" +
            ", height=" + getHeight() +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
