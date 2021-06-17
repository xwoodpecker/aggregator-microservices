package de.htw.saar.smartcity.aggregator.lib.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import java.util.List;

@Entity
@DiscriminatorValue("S")
@SecondaryTable(name = "sensors")
//@JsonTypeName("S")
public class Sensor extends Producer {

    @Column(table = "sensors", unique = true, nullable = false)
    private String name;
    
    @Column(table = "sensors")
    private Double x;
    
    @Column(table = "sensors")
    private Double y;

    @Column(table = "sensors")
    private String location;

    @Column(table = "sensors")
    private String information;


    public Sensor() {
    }

    public Sensor(Long id, DataType dataType, List<Tag> tags, List<Group> groups,
                  String objectStorePath, boolean exportAsMetric,
                  String name, Double x, Double y, String location, String information) {

        super(id, dataType, tags, groups, objectStorePath, exportAsMetric);
        this.name = name;
        this.x = x;
        this.y = y;
        this.location = location;
        this.information = information;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void replaceOwnAttributesWithOther(Sensor other) {
        this.setDataType(other.getDataType());
        this.setName(other.getName());
        this.setX(other.getX());
        this.setY(other.getY());
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
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", location='").append(location).append('\'');
        sb.append(", information='").append(information).append('\'');
        sb.append(", objectStorePath='").append(objectStorePath).append('\'');
        sb.append(", exportAsMetric='").append(exportAsMetric).append('\'');
        sb.append('}');
        return sb.toString();
    }
}