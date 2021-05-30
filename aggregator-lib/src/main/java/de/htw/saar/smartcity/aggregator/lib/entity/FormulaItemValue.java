package de.htw.saar.smartcity.aggregator.lib.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name ="formula_item_values")
public class FormulaItemValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String value;

    @OneToOne
    @JoinColumn(name = "formula_item_id", nullable = false)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id", resolver = FormulaItemIdResolver.class)
    @JsonIdentityReference(alwaysAsId=true)
    private FormulaItem formulaItem;

    public FormulaItemValue() {

    }

    public FormulaItemValue(Long id, String value, FormulaItem formulaItem) {
        this.id = id;
        this.value = value;
        this.formulaItem = formulaItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FormulaItem getFormulaItem() {
        return formulaItem;
    }

    public void setFormulaItem(FormulaItem formulaItem) {
        this.formulaItem = formulaItem;
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
        sb.append('}');
        return sb.toString();
    }
}