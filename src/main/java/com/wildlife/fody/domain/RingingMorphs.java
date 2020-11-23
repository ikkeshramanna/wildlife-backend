package com.wildlife.fody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.wildlife.fody.domain.enumeration.MkType;

import com.wildlife.fody.domain.enumeration.ReasonForCaptureType;

import com.wildlife.fody.domain.enumeration.CaptureMethodType;

/**
 * A RingingMorphs.
 */
@Entity
@Table(name = "ringing_morphs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RingingMorphs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "mk_type", nullable = false)
    private MkType mkType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reason_for_capture", nullable = false)
    private ReasonForCaptureType reasonForCapture;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "capture_method", nullable = false)
    private CaptureMethodType captureMethod;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Double weight;

    @NotNull
    @Column(name = "head", nullable = false)
    private Double head;

    @NotNull
    @Column(name = "exposed_culmen", nullable = false)
    private Double exposedCulmen;

    @NotNull
    @Column(name = "culmen_to_skull", nullable = false)
    private Double culmenToSkull;

    @NotNull
    @Column(name = "wing", nullable = false)
    private Double wing;

    @NotNull
    @Column(name = "p_8", nullable = false)
    private Double p8;

    @NotNull
    @Column(name = "p_8_brush", nullable = false)
    private Double p8Brush;

    @NotNull
    @Column(name = "tail", nullable = false)
    private Double tail;

    @NotNull
    @Column(name = "tail_brush", nullable = false)
    private Double tailBrush;

    @NotNull
    @Column(name = "tarsus_length", nullable = false)
    private Double tarsusLength;

    @Column(name = "comments")
    private String comments;

    @OneToOne(mappedBy = "ringingMorphs")
    @JsonIgnore
    private Sighting sighting;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MkType getMkType() {
        return mkType;
    }

    public RingingMorphs mkType(MkType mkType) {
        this.mkType = mkType;
        return this;
    }

    public void setMkType(MkType mkType) {
        this.mkType = mkType;
    }

    public ReasonForCaptureType getReasonForCapture() {
        return reasonForCapture;
    }

    public RingingMorphs reasonForCapture(ReasonForCaptureType reasonForCapture) {
        this.reasonForCapture = reasonForCapture;
        return this;
    }

    public void setReasonForCapture(ReasonForCaptureType reasonForCapture) {
        this.reasonForCapture = reasonForCapture;
    }

    public CaptureMethodType getCaptureMethod() {
        return captureMethod;
    }

    public RingingMorphs captureMethod(CaptureMethodType captureMethod) {
        this.captureMethod = captureMethod;
        return this;
    }

    public void setCaptureMethod(CaptureMethodType captureMethod) {
        this.captureMethod = captureMethod;
    }

    public String getName() {
        return name;
    }

    public RingingMorphs name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public RingingMorphs age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public RingingMorphs weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHead() {
        return head;
    }

    public RingingMorphs head(Double head) {
        this.head = head;
        return this;
    }

    public void setHead(Double head) {
        this.head = head;
    }

    public Double getExposedCulmen() {
        return exposedCulmen;
    }

    public RingingMorphs exposedCulmen(Double exposedCulmen) {
        this.exposedCulmen = exposedCulmen;
        return this;
    }

    public void setExposedCulmen(Double exposedCulmen) {
        this.exposedCulmen = exposedCulmen;
    }

    public Double getCulmenToSkull() {
        return culmenToSkull;
    }

    public RingingMorphs culmenToSkull(Double culmenToSkull) {
        this.culmenToSkull = culmenToSkull;
        return this;
    }

    public void setCulmenToSkull(Double culmenToSkull) {
        this.culmenToSkull = culmenToSkull;
    }

    public Double getWing() {
        return wing;
    }

    public RingingMorphs wing(Double wing) {
        this.wing = wing;
        return this;
    }

    public void setWing(Double wing) {
        this.wing = wing;
    }

    public Double getp8() {
        return p8;
    }

    public RingingMorphs p8(Double p8) {
        this.p8 = p8;
        return this;
    }

    public void setp8(Double p8) {
        this.p8 = p8;
    }

    public Double getp8Brush() {
        return p8Brush;
    }

    public RingingMorphs p8Brush(Double p8Brush) {
        this.p8Brush = p8Brush;
        return this;
    }

    public void setp8Brush(Double p8Brush) {
        this.p8Brush = p8Brush;
    }

    public Double getTail() {
        return tail;
    }

    public RingingMorphs tail(Double tail) {
        this.tail = tail;
        return this;
    }

    public void setTail(Double tail) {
        this.tail = tail;
    }

    public Double getTailBrush() {
        return tailBrush;
    }

    public RingingMorphs tailBrush(Double tailBrush) {
        this.tailBrush = tailBrush;
        return this;
    }

    public void setTailBrush(Double tailBrush) {
        this.tailBrush = tailBrush;
    }

    public Double getTarsusLength() {
        return tarsusLength;
    }

    public RingingMorphs tarsusLength(Double tarsusLength) {
        this.tarsusLength = tarsusLength;
        return this;
    }

    public void setTarsusLength(Double tarsusLength) {
        this.tarsusLength = tarsusLength;
    }

    public String getComments() {
        return comments;
    }

    public RingingMorphs comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Sighting getSighting() {
        return sighting;
    }

    public RingingMorphs sighting(Sighting sighting) {
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
        if (!(o instanceof RingingMorphs)) {
            return false;
        }
        return id != null && id.equals(((RingingMorphs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RingingMorphs{" +
            "id=" + getId() +
            ", mkType='" + getMkType() + "'" +
            ", reasonForCapture='" + getReasonForCapture() + "'" +
            ", captureMethod='" + getCaptureMethod() + "'" +
            ", name='" + getName() + "'" +
            ", age=" + getAge() +
            ", weight=" + getWeight() +
            ", head=" + getHead() +
            ", exposedCulmen=" + getExposedCulmen() +
            ", culmenToSkull=" + getCulmenToSkull() +
            ", wing=" + getWing() +
            ", p8=" + getp8() +
            ", p8Brush=" + getp8Brush() +
            ", tail=" + getTail() +
            ", tailBrush=" + getTailBrush() +
            ", tarsusLength=" + getTarsusLength() +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
