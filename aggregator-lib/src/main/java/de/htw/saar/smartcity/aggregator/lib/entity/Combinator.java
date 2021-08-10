package de.htw.saar.smartcity.aggregator.lib.entity;

import javax.persistence.*;

/**
 * The type Combinator.
 */
@Entity
@Table(name = "combinators")
public class Combinator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    /**
     * Instantiates a new Combinator.
     */
    public Combinator() {
    }

    /**
     * Instantiates a new Combinator.
     *
     * @param id   the id
     * @param name the name
     */
    public Combinator(Long id, String name) {
        this.id = id;
        this.name = name;
    }


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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Combinator other = (Combinator) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Combinator{");
        sb.append("id=").append(id);
        sb.append("name=").append(name);
        //sb.append(", aggregators=").append(aggregators);
        sb.append('}');
        return sb.toString();
    }
}
