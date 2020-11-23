package com.wildlife.fody.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.wildlife.fody.domain.enumeration.NestSiteType;

import com.wildlife.fody.domain.enumeration.AreaType;

import com.wildlife.fody.domain.enumeration.NestType;

/**
 * A Sighting.
 */
@Entity
@Table(name = "sighting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sighting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nest_site", nullable = false)
    private NestSiteType nestSite;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "area", nullable = false)
    private AreaType area;

    @Enumerated(EnumType.STRING)
    @Column(name = "nest_type")
    private NestType nestType;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "date")
    private LocalDate date;

    @NotNull
    @Column(name = "observer", nullable = false)
    private String observer;

    @Column(name = "gps_latitude")
    private String gpsLatitude;

    @Column(name = "gps_coordinate")
    private String gpsCoordinate;

    @Column(name = "location_name")
    private String locationName;

    @NotNull
    @Column(name = "add_date", nullable = false)
    private LocalDate addDate;

    @NotNull
    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @OneToOne
    @JoinColumn(unique = true)
    private BirdsIdentified birdsIdentified;

    @OneToOne
    @JoinColumn(unique = true)
    private EggsAndChick eggsAndChick;

    @OneToOne
    @JoinColumn(unique = true)
    private FeedingObservation feedingObservation;

    @OneToOne
    @JoinColumn(unique = true)
    private NestSiteOverview nestSiteOverview;

    @OneToOne
    @JoinColumn(unique = true)
    private Maintenance maintenance;

    @OneToOne
    @JoinColumn(unique = true)
    private RingingMorphs ringingMorphs;

    @OneToMany(mappedBy = "sighting")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Photo> photos = new HashSet<>();

    @OneToMany(mappedBy = "sighting")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Competitors> competitors = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "sightings", allowSetters = true)
    private ObservationLocation observationLocation;

    @ManyToOne
    @JsonIgnoreProperties(value = "sightings", allowSetters = true)
    private Species species;

    @ManyToOne
    @JsonIgnoreProperties(value = "sightings", allowSetters = true)
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "sightings", allowSetters = true)
    private TaggedAnimal taggedAnimal;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NestSiteType getNestSite() {
        return nestSite;
    }

    public Sighting nestSite(NestSiteType nestSite) {
        this.nestSite = nestSite;
        return this;
    }

    public void setNestSite(NestSiteType nestSite) {
        this.nestSite = nestSite;
    }

    public AreaType getArea() {
        return area;
    }

    public Sighting area(AreaType area) {
        this.area = area;
        return this;
    }

    public void setArea(AreaType area) {
        this.area = area;
    }

    public NestType getNestType() {
        return nestType;
    }

    public Sighting nestType(NestType nestType) {
        this.nestType = nestType;
        return this;
    }

    public void setNestType(NestType nestType) {
        this.nestType = nestType;
    }

    public Integer getYear() {
        return year;
    }

    public Sighting year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public Sighting month(Integer month) {
        this.month = month;
        return this;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public LocalDate getDate() {
        return date;
    }

    public Sighting date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getObserver() {
        return observer;
    }

    public Sighting observer(String observer) {
        this.observer = observer;
        return this;
    }

    public void setObserver(String observer) {
        this.observer = observer;
    }

    public String getGpsLatitude() {
        return gpsLatitude;
    }

    public Sighting gpsLatitude(String gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
        return this;
    }

    public void setGpsLatitude(String gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public String getGpsCoordinate() {
        return gpsCoordinate;
    }

    public Sighting gpsCoordinate(String gpsCoordinate) {
        this.gpsCoordinate = gpsCoordinate;
        return this;
    }

    public void setGpsCoordinate(String gpsCoordinate) {
        this.gpsCoordinate = gpsCoordinate;
    }

    public String getLocationName() {
        return locationName;
    }

    public Sighting locationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public LocalDate getAddDate() {
        return addDate;
    }

    public Sighting addDate(LocalDate addDate) {
        this.addDate = addDate;
        return this;
    }

    public void setAddDate(LocalDate addDate) {
        this.addDate = addDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public Sighting updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public BirdsIdentified getBirdsIdentified() {
        return birdsIdentified;
    }

    public Sighting birdsIdentified(BirdsIdentified birdsIdentified) {
        this.birdsIdentified = birdsIdentified;
        return this;
    }

    public void setBirdsIdentified(BirdsIdentified birdsIdentified) {
        this.birdsIdentified = birdsIdentified;
    }

    public EggsAndChick getEggsAndChick() {
        return eggsAndChick;
    }

    public Sighting eggsAndChick(EggsAndChick eggsAndChick) {
        this.eggsAndChick = eggsAndChick;
        return this;
    }

    public void setEggsAndChick(EggsAndChick eggsAndChick) {
        this.eggsAndChick = eggsAndChick;
    }

    public FeedingObservation getFeedingObservation() {
        return feedingObservation;
    }

    public Sighting feedingObservation(FeedingObservation feedingObservation) {
        this.feedingObservation = feedingObservation;
        return this;
    }

    public void setFeedingObservation(FeedingObservation feedingObservation) {
        this.feedingObservation = feedingObservation;
    }

    public NestSiteOverview getNestSiteOverview() {
        return nestSiteOverview;
    }

    public Sighting nestSiteOverview(NestSiteOverview nestSiteOverview) {
        this.nestSiteOverview = nestSiteOverview;
        return this;
    }

    public void setNestSiteOverview(NestSiteOverview nestSiteOverview) {
        this.nestSiteOverview = nestSiteOverview;
    }

    public Maintenance getMaintenance() {
        return maintenance;
    }

    public Sighting maintenance(Maintenance maintenance) {
        this.maintenance = maintenance;
        return this;
    }

    public void setMaintenance(Maintenance maintenance) {
        this.maintenance = maintenance;
    }

    public RingingMorphs getRingingMorphs() {
        return ringingMorphs;
    }

    public Sighting ringingMorphs(RingingMorphs ringingMorphs) {
        this.ringingMorphs = ringingMorphs;
        return this;
    }

    public void setRingingMorphs(RingingMorphs ringingMorphs) {
        this.ringingMorphs = ringingMorphs;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public Sighting photos(Set<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public Sighting addPhotos(Photo photo) {
        this.photos.add(photo);
        photo.setSighting(this);
        return this;
    }

    public Sighting removePhotos(Photo photo) {
        this.photos.remove(photo);
        photo.setSighting(null);
        return this;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<Competitors> getCompetitors() {
        return competitors;
    }

    public Sighting competitors(Set<Competitors> competitors) {
        this.competitors = competitors;
        return this;
    }

    public Sighting addCompetitors(Competitors competitors) {
        this.competitors.add(competitors);
        competitors.setSighting(this);
        return this;
    }

    public Sighting removeCompetitors(Competitors competitors) {
        this.competitors.remove(competitors);
        competitors.setSighting(null);
        return this;
    }

    public void setCompetitors(Set<Competitors> competitors) {
        this.competitors = competitors;
    }

    public ObservationLocation getObservationLocation() {
        return observationLocation;
    }

    public Sighting observationLocation(ObservationLocation observationLocation) {
        this.observationLocation = observationLocation;
        return this;
    }

    public void setObservationLocation(ObservationLocation observationLocation) {
        this.observationLocation = observationLocation;
    }

    public Species getSpecies() {
        return species;
    }

    public Sighting species(Species species) {
        this.species = species;
        return this;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Sighting employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public TaggedAnimal getTaggedAnimal() {
        return taggedAnimal;
    }

    public Sighting taggedAnimal(TaggedAnimal taggedAnimal) {
        this.taggedAnimal = taggedAnimal;
        return this;
    }

    public void setTaggedAnimal(TaggedAnimal taggedAnimal) {
        this.taggedAnimal = taggedAnimal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sighting)) {
            return false;
        }
        return id != null && id.equals(((Sighting) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sighting{" +
            "id=" + getId() +
            ", nestSite='" + getNestSite() + "'" +
            ", area='" + getArea() + "'" +
            ", nestType='" + getNestType() + "'" +
            ", year=" + getYear() +
            ", month=" + getMonth() +
            ", date='" + getDate() + "'" +
            ", observer='" + getObserver() + "'" +
            ", gpsLatitude='" + getGpsLatitude() + "'" +
            ", gpsCoordinate='" + getGpsCoordinate() + "'" +
            ", locationName='" + getLocationName() + "'" +
            ", addDate='" + getAddDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
