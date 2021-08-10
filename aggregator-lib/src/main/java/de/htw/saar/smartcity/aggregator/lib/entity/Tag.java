package de.htw.saar.smartcity.aggregator.lib.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Tag.
 */
@Entity
@Table(name ="tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private List<Producer> producers = new ArrayList<>();

    /**
     * Instantiates a new Tag.
     */
    public Tag() {

    }

    /**
     * Instantiates a new Tag.
     *
     * @param id        the id
     * @param name      the name
     * @param producers the producers
     */
    public Tag(Long id, String name, List<Producer> producers) {
        this.id = id;
        this.name = name;
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tag other = (Tag) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Tag{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        //sb.append(", producers=").append(producers);
        sb.append('}');
        return sb.toString();
    }
}
