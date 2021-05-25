package de.htw.saar.smartcity.aggregator.lib.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name ="members")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Group.class, name = "G"),

        @JsonSubTypes.Type(value = Sensor.class, name = "S") }
)
public abstract class GroupMember implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_member",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Group> groups = new ArrayList<>();

    @Transient
    private String name;

    public GroupMember() {
    }

    public GroupMember(Long id, List<Group> groups) {
        this.id = id;
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> parents) {
        this.groups = parents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //public abstract List<Sensor> getAllSensorsRecursive();
}
