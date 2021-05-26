package de.htw.saar.smartcity.aggregator.lib.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

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
    private String unit;
    
    @Column(table = "sensors")
    private Double x;
    
    @Column(table = "sensors")
    private Double y;

    @Column(table = "sensors")
    private String location;

    @Column(table = "sensors")
    private String information;

    @Column(table = "sensors")
    private String objectStoreHash;


    public Sensor() {
    }

    public Sensor(Long id, DataType dataType, List<Tag> tags, List<Group> groups, String name, String unit, Double x, Double y, String location, String information, String objectStoreHash) {
        super(id, dataType, tags, groups);
        this.name = name;
        this.unit = unit;
        this.x = x;
        this.y = y;
        this.location = location;
        this.information = information;
        this.objectStoreHash = objectStoreHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getObjectStoreHash() {
        return objectStoreHash;
    }

    public void setObjectStoreHash(String objectStoreHash) {
        this.objectStoreHash = objectStoreHash;
    }


    public void replaceOwnAttributesWithOther(Sensor other) {
        this.setDataType(other.getDataType());
        this.setName(other.getName());
        this.setUnit(other.getUnit());
        this.setX(other.getX());
        this.setY(other.getY());
        this.setLocation(other.getLocation());
        this.setInformation(other.getInformation());
        this.setObjectStoreHash(other.getObjectStoreHash());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Sensor{");
        sb.append("id=").append(id);
        sb.append(", dataType=").append(dataType);
        sb.append(", tags=").append(tags);
        sb.append(", name='").append(name).append('\'');
        sb.append(", unit='").append(unit).append('\'');
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", location='").append(location).append('\'');
        sb.append(", information='").append(information).append('\'');
        sb.append(", objectStoreHash='").append(objectStoreHash).append('\'');
        sb.append('}');
        return sb.toString();
    }
}