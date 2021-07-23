package de.htw.saar.smartcity.aggregator.lib.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;

@Entity
@DiscriminatorValue("S")
@SecondaryTable(name = "sensors")
//@JsonTypeName("S")
public class Sensor extends Producer {

    @Column(table = "sensors", unique = true, nullable = false)
    private String name;



    public Sensor() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void replaceOwnAttributesWithOther(Sensor other) {
        this.setDataType(other.getDataType());
        this.setName(other.getName());
        this.setLocation(other.getLocation());
        this.setInformation(other.getInformation());
        this.setExportAsMetric(other.getExportAsMetric());
        this.setObjectStorePath(other.getObjectStorePath());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Sensor{");
        sb.append("id=").append(id);
        sb.append(", dataType=").append(dataType);
        sb.append(", tags=").append(tags);
        //sb.append(", groups=").append(groups);
        sb.append(", name='").append(name).append('\'');
        sb.append(", objectStorePath='").append(objectStorePath).append('\'');
        sb.append(", exportAsMetric='").append(exportAsMetric).append('\'');
        sb.append('}');
        return sb.toString();
    }
}