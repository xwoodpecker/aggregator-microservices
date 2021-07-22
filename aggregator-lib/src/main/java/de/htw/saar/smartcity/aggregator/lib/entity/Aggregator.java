package de.htw.saar.smartcity.aggregator.lib.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("A")
@SecondaryTable(name = "aggregators")
//@JsonTypeName("A")
public class Aggregator extends Producer {

    @ManyToOne
    @JoinColumn(table = "aggregators", name="group_id")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Group ownerGroup;

    @ManyToOne
    @JoinColumn(table = "aggregators", name="combinator_id")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Combinator combinator;

    public Aggregator() {
    }


    public Aggregator(Long id, DataType dataType, List<Tag> tags, List<Group> groups, String objectStorePath, boolean exportAsMetric, String information, Location location, Group ownerGroup, Combinator combinator) {
        super(id, dataType, tags, groups, objectStorePath, exportAsMetric, information, location);
        this.ownerGroup = ownerGroup;
        this.combinator = combinator;
    }

    public Group getOwnerGroup() {
        return ownerGroup;
    }

    public void setOwnerGroup(Group ownerGroup) {
        this.ownerGroup = ownerGroup;
    }

    public Combinator getCombinator() {
        return combinator;
    }

    public void setCombinator(Combinator combinator) {
        this.combinator = combinator;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Aggregator{");
        sb.append(" id=").append(id);
        sb.append(", combinator=").append(combinator);
        //sb.append(", ownerGroup=").append(ownerGroup);
        sb.append(", dataType=").append(dataType);
        sb.append(", tags=").append(tags);
        sb.append(", objectStorePath='").append(objectStorePath).append('\'');
        sb.append(", exportAsMetric='").append(exportAsMetric).append('\'');
        //sb.append(", groups=").append(groups);
        sb.append('}');
        return sb.toString();
    }
}
