package de.htw.saar.smartcity.aggregator.lib.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Location.
 */
@Entity
@Table(name ="locations" ) //,
        //uniqueConstraints=
        //@UniqueConstraint(columnNames={"x", "y"}))
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private Double x;

    private Double y;

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private List<Producer> producers = new ArrayList<>();

    /**
     * Instantiates a new Location.
     */
    public Location() {

    }

    /**
     * Instantiates a new Location.
     *
     * @param id        the id
     * @param name      the name
     * @param x         the x
     * @param y         the y
     * @param producers the producers
     */
    public Location(Long id, String name, Double x, Double y, List<Producer> producers) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.producers = producers;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public Double getX() {
        return x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(Double x) {
        this.x = x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public Double getY() {
        return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(Double y) {
        this.y = y;
    }

    /**
     * Gets producers.
     *
     * @return the producers
     */
    public List<Producer> getProducers() {
        return producers;
    }

    /**
     * Sets producers.
     *
     * @param producers the producers
     */
    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(id, location.id) && Objects.equals(name, location.name) && Objects.equals(x, location.x) && Objects.equals(y, location.y);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Location{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }
}
