package de.htw.saar.smartcity.aggregator.lib.entity;


import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("G")
@SecondaryTable(name = "groups")
public class Group extends GroupMember {

    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<GroupMember> members = new ArrayList<>();


    @Column(table = "groups", unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "group_microservice",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "microservice_id"))
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Microservice> microservices = new ArrayList<>();

    @Column(table = "groups", nullable = false)
    @ColumnDefault("0")
    private Boolean active;


    @OneToOne
    @JoinColumn(name = "group_type_id", nullable = false, table = "groups")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id", resolver = GroupTypeIdResolver.class)
    @JsonIdentityReference(alwaysAsId=true)
    private GroupType groupType;

    public Group() {
    }

    public Group(List<GroupMember> members, String name, List<Microservice> microservices, Boolean active, GroupType groupType) {
        this.members = members;
        this.name = name;
        this.microservices = microservices;
        this.active = active;
        this.groupType = groupType;
    }

    public List<GroupMember> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMember> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String groupName) {
        this.name = groupName;
    }

    public List<Microservice> getMicroservices() {
        return microservices;
    }

    public void setMicroservices(List<Microservice> microservices) {
        this.microservices = microservices;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public void replaceOwnAttributesWithOther(Group other) {
        this.setName(other.getName());
        this.setActive(other.isActive());
        this.setGroupType(other.getGroupType());
    }

    @JsonIgnore
    public List<Sensor> getAllSensorsRecursive() {
        List<Sensor> sensors = new ArrayList<>();
        members.forEach(m -> sensors.addAll(m.getAllSensorsRecursive()));
        return sensors;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Group{");
        sb.append(", id=").append(id);
        sb.append(", groupName='").append(name).append('\'');
        sb.append(", groupType=").append(groupType);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Group other = (Group) obj;
        return id != null && id.equals(other.getId());
    }
}

