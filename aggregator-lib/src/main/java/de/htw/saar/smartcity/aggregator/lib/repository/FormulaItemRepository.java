package de.htw.saar.smartcity.aggregator.lib.repository;

import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Formula item repository.
 */
@Repository
public interface FormulaItemRepository extends JpaRepository<FormulaItem, Long> {

    /**
     * Find formula item by name formula item.
     *
     * @param formulaItemName the formula item name
     * @return the formula item
     */
    FormulaItem findFormulaItemByName(String formulaItemName);
}
