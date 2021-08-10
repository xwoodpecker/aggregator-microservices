package de.htw.saar.smartcity.aggregator.lib.entity;


import javax.persistence.*;
import java.util.Objects;

/**
 * The type Historic combinator.
 */
@Entity
@Table(name = "historic_combinators")
public class HistoricCombinator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    /**
     * Instantiates a new Historic combinator.
     */
    public HistoricCombinator() {
    }

    /**
     * Instantiates a new Historic combinator.
     *
     * @param id   the id
     * @param name the name
     */
    public HistoricCombinator(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricCombinator that = (HistoricCombinator) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HistoricCombinator{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
