package de.htw.saar.smartcity.aggregator.lib.entity;

import javax.persistence.*;

/**
 * The type Formula item.
 */
@Entity
@Table(name ="formula_items")
public class FormulaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;


    /**
     * Instantiates a new Formula item.
     */
    public FormulaItem() {

    }

    /**
     * Instantiates a new Formula item.
     *
     * @param id   the id
     * @param name the name
     */
    public FormulaItem(Long id, String name) {
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
    public String toString() {
        final StringBuffer sb = new StringBuffer("FormulaItem{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
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
        FormulaItem other = (FormulaItem) obj;
        return id != null && id.equals(other.getId());
    }
}