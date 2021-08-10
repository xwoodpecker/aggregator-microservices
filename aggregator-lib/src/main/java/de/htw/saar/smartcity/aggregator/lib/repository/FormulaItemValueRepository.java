package de.htw.saar.smartcity.aggregator.lib.repository;

import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItem;
import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItemValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Formula item value repository.
 */
@Repository
public interface FormulaItemValueRepository extends JpaRepository<FormulaItemValue, Long> {

    /**
     * Find formula item value by value formula item value.
     *
     * @param value the value
     * @return the formula item value
     */
    FormulaItemValue findFormulaItemValueByValue(String value);
}
