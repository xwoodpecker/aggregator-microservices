package de.htw.saar.smartcity.aggregator.lib.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Formula item value.
 */
@Entity
@Table(name ="formula_item_values")
public class FormulaItemValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "formula_item_id", nullable = false)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id", resolver = FormulaItemIdResolver.class)
    @JsonIdentityReference(alwaysAsId=true)
    private FormulaItem formulaItem;

    @ManyToMany(mappedBy = "values")
    @JsonIgnore
    private List<Group> groups = new ArrayList<>();

    /**
     * Instantiates a new Formula item value.
     */
    public FormulaItemValue() {

    }

    /**
     * Instantiates a new Formula item value.
     *
     * @param id          the id
     * @param value       the value
     * @param formulaItem the formula item
     * @param groups      the groups
     */
    public FormulaItemValue(Long id, String value, FormulaItem formulaItem, List<Group> groups) {
        this.id = id;
        this.value = value;
        this.formulaItem = formulaItem;
        this.groups = groups;
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
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets formula item.
     *
     * @return the formula item
     */
    public FormulaItem getFormulaItem() {
        return formulaItem;
    }

    /**
     * Sets formula item.
     *
     * @param formulaItem the formula item
     */
    public void setFormulaItem(FormulaItem formulaItem) {
        this.formulaItem = formulaItem;
    }

    /**
     * Gets groups.
     *
     * @return the groups
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Sets groups.
     *
     * @param groups the groups
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FormulaItemValue other = (FormulaItemValue) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FormulaItemValue{");
        sb.append("id=").append(id);
        sb.append(", value='").append(value).append('\'');
        sb.append(", formulaItem=").append(formulaItem);
        //sb.append(", groups=").append(groups);
        sb.append('}');
        return sb.toString();
    }
}