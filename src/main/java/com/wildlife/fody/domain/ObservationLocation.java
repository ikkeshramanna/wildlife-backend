package com.wildlife.fody.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ObservationLocation.
 */
@Entity
@Table(name = "observation_location")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ObservationLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "gps_latitude")
    private String gpsLatitude;

    @Column(name = "gps_coordinate")
    private String gpsCoordinate;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "description")
    private String description;

    @Column(name = "add_date")
    private LocalDate addDate;

    @Column(name = "update_date")
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

    public ObservationLocation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGpsLatitude() {
        return gpsLatitude;
    }

    public ObservationLocation gpsLatitude(String gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
        return this;
    }

    public void setGpsLatitude(String gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public String getGpsCoordinate() {
        return gpsCoordinate;
    }

    public ObservationLocation gpsCoordinate(String gpsCoordinate) {
        this.gpsCoordinate = gpsCoordinate;
        return this;
    }

    public void setGpsCoordinate(String gpsCoordinate) {
        this.gpsCoordinate = gpsCoordinate;
    }

    public String getLocationName() {
        return locationName;
    }

    public ObservationLocation locationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDescription() {
        return description;
    }

    public ObservationLocation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getAddDate() {
        return addDate;
    }

    public ObservationLocation addDate(LocalDate addDate) {
        this.addDate = addDate;
        return this;
    }

    public void setAddDate(LocalDate addDate) {
        this.addDate = addDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public ObservationLocation updateDate(LocalDate updateDate) {
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
        if (!(o instanceof ObservationLocation)) {
            return false;
        }
        return id != null && id.equals(((ObservationLocation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObservationLocation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", gpsLatitude='" + getGpsLatitude() + "'" +
            ", gpsCoordinate='" + getGpsCoordinate() + "'" +
            ", locationName='" + getLocationName() + "'" +
            ", description='" + getDescription() + "'" +
            ", addDate='" + getAddDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
