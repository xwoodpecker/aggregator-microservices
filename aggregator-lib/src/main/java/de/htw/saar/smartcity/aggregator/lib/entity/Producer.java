package de.htw.saar.smartcity.aggregator.lib.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Producer.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name ="producers")
public abstract class Producer implements Serializable {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * The Data type.
     */
    @ManyToOne
    @JoinColumn(name = "data_type_id", nullable = false)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id", resolver = DataTypeIdResolver.class, scope = DataType.class)
    @JsonIdentityReference(alwaysAsId=true)
    protected DataType dataType;

    /**
     * The Tags.
     */
    @ManyToMany
    @JoinTable(name = "producer_tag",
            joinColumns = @JoinColumn(name = "producer_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected List<Tag> tags = new ArrayList<>();

    /**
     * The Groups.
     */
    @ManyToMany
    @JoinTable(name = "producer_group",
            joinColumns = @JoinColumn(name = "producer_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected List<Group> groups = new ArrayList<>();

    /**
     * The Object store path.
     */
    @Column
    protected String objectStorePath;

    /**
     * The Export as metric.
     */
    @Column
    protected boolean exportAsMetric;


    /**
     * The Information.
     */
    @Column
    protected String information;

    /**
     * The Location.
     */
    @ManyToOne
    @JoinColumn(name = "location_id")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id", resolver = LocationIdResolver.class, scope = Location.class)
    @JsonIdentityReference(alwaysAsId=true)
    protected Location location;


    /**
     * Instantiates a new Producer.
     */
    public Producer() {
    }

    /**
     * Instantiates a new Producer.
     *
     * @param id              the id
     * @param dataType        the data type
     * @param tags            the tags
     * @param groups          the groups
     * @param objectStorePath the object store path
     * @param exportAsMetric  the export as metric
     * @param information     the information
     * @param location        the location
     */
    public Producer(Long id, DataType dataType, List<Tag> tags, List<Group> groups, String objectStorePath, boolean exportAsMetric, String information, Location location) {
        this.id = id;
        this.dataType = dataType;
        this.tags = tags;
        this.groups = groups;
        this.objectStorePath = objectStorePath;
        this.exportAsMetric = exportAsMetric;
        this.information = information;
        this.location = location;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets data type.
     *
     * @return the data type
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * Sets data type.
     *
     * @param dataType the data type
     */
    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    /**
     * Gets tags.
     *
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Sets tags.
     *
     * @param tags the tags
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Gets groups.
     *
     * @return the groups
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Sets groups.
     *
     * @param groups the groups
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    /**
     * Gets object store path.
     *
     * @return the object store path
     */
    public String getObjectStorePath() {
        return objectStorePath;
    }

    /**
     * Sets object store path.
     *
     * @param objectStorePath the object store path
     */
    public void setObjectStorePath(String objectStorePath) {
        this.objectStorePath = objectStorePath;
    }

    /**
     * Gets export as metric.
     *
     * @return the export as metric
     */
    public boolean getExportAsMetric() {
        return exportAsMetric;
    }

    /**
     * Sets export as metric.
     *
     * @param exportAsMetric the export as metric
     */
    public void setExportAsMetric(boolean exportAsMetric) {
        this.exportAsMetric = exportAsMetric;
    }

    /**
     * Gets information.
     *
     * @return the information
     */
    public String getInformation() {
        return information;
    }

    /**
     * Sets information.
     *
     * @param information the information
     */
    public void setInformation(String information) {
        this.information = information;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Producer other = (Producer) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Producer{");
        sb.append("id=").append(id);
        sb.append(", dataType=").append(dataType);
        sb.append(", tags=").append(tags);
        sb.append(", groups=").append(groups);
        sb.append(", objectStorePath='").append(objectStorePath).append('\'');
        sb.append(", exportAsMetric=").append(exportAsMetric);
        sb.append(", information='").append(information).append('\'');
        sb.append(", location=").append(location);
        sb.append('}');
        return sb.toString();
    }
}
