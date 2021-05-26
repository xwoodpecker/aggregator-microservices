package de.htw.saar.smartcity.aggregator.lib.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name ="producers")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Aggregator.class, name = "A"),

        @JsonSubTypes.Type(value = Sensor.class, name = "S") }
)
public abstract class Producer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @OneToOne
    @JoinColumn(name = "data_type_id", nullable = false)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id", resolver = DataTypeIdResolver.class)
    @JsonIdentityReference(alwaysAsId=true)
    protected DataType dataType;

    @ManyToMany
    @JoinTable(name = "producer_tag",
            joinColumns = @JoinColumn(name = "producer_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected List<Tag> tags = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "producer_group",
            joinColumns = @JoinColumn(name = "producer_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Group> groups = new ArrayList<>();


    public Producer() {
    }

    public Producer(Long id, DataType dataType, List<Tag> tags, List<Group> groups) {
        this.id = id;
        this.dataType = dataType;
        this.tags = tags;
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
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
        sb.append('}');
        return sb.toString();
    }
}
