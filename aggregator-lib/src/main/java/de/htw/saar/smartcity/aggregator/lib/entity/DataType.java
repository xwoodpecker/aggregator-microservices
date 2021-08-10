package de.htw.saar.smartcity.aggregator.lib.entity;

import javax.persistence.*;

/**
 * The type Data type.
 */
@Entity
@Table(name ="data_types")
public class DataType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String unit;

    /**
     * Instantiates a new Data type.
     */
    public DataType() {

    }

    /**
     * Instantiates a new Data type.
     *
     * @param id   the id
     * @param name the name
     * @param unit the unit
     */
    public DataType(Long id, String name, String unit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
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
     * Gets unit.
     *
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets unit.
     *
     * @param unit the unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DataType other = (DataType) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DataType{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
