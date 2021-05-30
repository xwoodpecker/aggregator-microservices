package de.htw.saar.smartcity.aggregator.lib.entity;


import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean active;

    @OneToOne
    @JoinColumn(name = "group_type_id", nullable = false)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id", resolver = GroupTypeIdResolver.class)
    @JsonIdentityReference(alwaysAsId=true)
    private GroupType groupType;

    @ManyToMany(mappedBy = "groups")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Producer> producers = new ArrayList<>();

    @OneToMany(mappedBy="ownerGroup")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Aggregator> aggregators = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "group_formula_item_value",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "formula_item_value_id"))
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected List<FormulaItemValue> values = new ArrayList<>();

    public Group() {
    }

    public Group(Long id, String name, Boolean active, GroupType groupType, List<Producer> producers, List<Aggregator> aggregators, List<FormulaItemValue> values) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.groupType = groupType;
        this.producers = producers;
        this.aggregators = aggregators;
        this.values = values;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
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

    public List<Aggregator> getAggregators() {
        return aggregators;
    }

    public void setAggregators(List<Aggregator> aggregators) {
        this.aggregators = aggregators;
    }

    public List<FormulaItemValue> getValues() {
        return values;
    }

    public void setValues(List<FormulaItemValue> values) {
        this.values = values;
    }

    public void replaceOwnAttributesWithOther(Group other) {
        this.setName(other.getName());
        this.setActive(other.getActive());
        this.setGroupType(other.getGroupType());
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Group{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", active=").append(active);
        sb.append(", groupType=").append(groupType);
        sb.append(", producers=").append(producers);
        sb.append(", aggregators=").append(aggregators);
        sb.append(", values=").append(values);
        sb.append('}');
        return sb.toString();
    }
}

