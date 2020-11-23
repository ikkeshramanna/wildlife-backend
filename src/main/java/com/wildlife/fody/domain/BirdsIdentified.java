package com.wildlife.fody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.wildlife.fody.domain.enumeration.SeenDuringType;

import com.wildlife.fody.domain.enumeration.BirdType;

import com.wildlife.fody.domain.enumeration.SexType;

import com.wildlife.fody.domain.enumeration.StatusType;

import com.wildlife.fody.domain.enumeration.BirdLocationType;

/**
 * A BirdsIdentified.
 */
@Entity
@Table(name = "birds_identified")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BirdsIdentified implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "seen_during", nullable = false)
    private SeenDuringType seenDuring;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private BirdType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private SexType sex;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "bird_location", nullable = false)
    private BirdLocationType birdLocation;

    @Column(name = "southing")
    private String southing;

    @Column(name = "easting")
    private String easting;

    @Column(name = "comments")
    private String comments;

    @NotNull
    @Column(name = "color_combo_l", nullable = false)
    private String colorComboL;

    @NotNull
    @Column(name = "color_combo_r", nullable = false)
    private String colorComboR;

    @Column(name = "bird_irn")
    private String birdIRN;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "birds_identified_bird_behaviour",
               joinColumns = @JoinColumn(name = "birds_identified_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "bird_behaviour_id", referencedColumnName = "id"))
    private Set<BirdBehaviour> birdBehaviours = new HashSet<>();

    @OneToOne(mappedBy = "birdsIdentified")
    @JsonIgnore
    private Sighting sighting;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeenDuringType getSeenDuring() {
        return seenDuring;
    }

    public BirdsIdentified seenDuring(SeenDuringType seenDuring) {
        this.seenDuring = seenDuring;
        return this;
    }

    public void setSeenDuring(SeenDuringType seenDuring) {
        this.seenDuring = seenDuring;
    }

    public BirdType getType() {
        return type;
    }

    public BirdsIdentified type(BirdType type) {
        this.type = type;
        return this;
    }

    public void setType(BirdType type) {
        this.type = type;
    }

    public SexType getSex() {
        return sex;
    }

    public BirdsIdentified sex(SexType sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(SexType sex) {
        this.sex = sex;
    }

    public StatusType getStatus() {
        return status;
    }

    public BirdsIdentified status(StatusType status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public BirdLocationType getBirdLocation() {
        return birdLocation;
    }

    public BirdsIdentified birdLocation(BirdLocationType birdLocation) {
        this.birdLocation = birdLocation;
        return this;
    }

    public void setBirdLocation(BirdLocationType birdLocation) {
        this.birdLocation = birdLocation;
    }

    public String getSouthing() {
        return southing;
    }

    public BirdsIdentified southing(String southing) {
        this.southing = southing;
        return this;
    }

    public void setSouthing(String southing) {
        this.southing = southing;
    }

    public String getEasting() {
        return easting;
    }

    public BirdsIdentified easting(String easting) {
        this.easting = easting;
        return this;
    }

    public void setEasting(String easting) {
        this.easting = easting;
    }

    public String getComments() {
        return comments;
    }

    public BirdsIdentified comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getColorComboL() {
        return colorComboL;
    }

    public BirdsIdentified colorComboL(String colorComboL) {
        this.colorComboL = colorComboL;
        return this;
    }

    public void setColorComboL(String colorComboL) {
        this.colorComboL = colorComboL;
    }

    public String getColorComboR() {
        return colorComboR;
    }

    public BirdsIdentified colorComboR(String colorComboR) {
        this.colorComboR = colorComboR;
        return this;
    }

    public void setColorComboR(String colorComboR) {
        this.colorComboR = colorComboR;
    }

    public String getBirdIRN() {
        return birdIRN;
    }

    public BirdsIdentified birdIRN(String birdIRN) {
        this.birdIRN = birdIRN;
        return this;
    }

    public void setBirdIRN(String birdIRN) {
        this.birdIRN = birdIRN;
    }

    public Set<BirdBehaviour> getBirdBehaviours() {
        return birdBehaviours;
    }

    public BirdsIdentified birdBehaviours(Set<BirdBehaviour> birdBehaviours) {
        this.birdBehaviours = birdBehaviours;
        return this;
    }

    public BirdsIdentified addBirdBehaviour(BirdBehaviour birdBehaviour) {
        this.birdBehaviours.add(birdBehaviour);
        birdBehaviour.getBirdsIdentifieds().add(this);
        return this;
    }

    public BirdsIdentified removeBirdBehaviour(BirdBehaviour birdBehaviour) {
        this.birdBehaviours.remove(birdBehaviour);
        birdBehaviour.getBirdsIdentifieds().remove(this);
        return this;
    }

    public void setBirdBehaviours(Set<BirdBehaviour> birdBehaviours) {
        this.birdBehaviours = birdBehaviours;
    }

    public Sighting getSighting() {
        return sighting;
    }

    public BirdsIdentified sighting(Sighting sighting) {
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
        if (!(o instanceof BirdsIdentified)) {
            return false;
        }
        return id != null && id.equals(((BirdsIdentified) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BirdsIdentified{" +
            "id=" + getId() +
            ", seenDuring='" + getSeenDuring() + "'" +
            ", type='" + getType() + "'" +
            ", sex='" + getSex() + "'" +
            ", status='" + getStatus() + "'" +
            ", birdLocation='" + getBirdLocation() + "'" +
            ", southing='" + getSouthing() + "'" +
            ", easting='" + getEasting() + "'" +
            ", comments='" + getComments() + "'" +
            ", colorComboL='" + getColorComboL() + "'" +
            ", colorComboR='" + getColorComboR() + "'" +
            ", birdIRN='" + getBirdIRN() + "'" +
            "}";
    }
}
