package de.htw.saar.smartcity.aggregator.heatflux.base;

import de.htw.saar.smartcity.aggregator.heatflux.properties.HeatfluxApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.base.GroupSetupDataLoader;
import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItem;
import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItemValue;
import de.htw.saar.smartcity.aggregator.lib.entity.Tag;
import de.htw.saar.smartcity.aggregator.lib.service.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Setup data loader.
 */
@Component
public class HeatfluxSetupDataLoader extends GroupSetupDataLoader {


    private final TagService tagService;
    private final FormulaItemService formulaItemService;
    private final FormulaItemValueService formulaItemValueService;

    public HeatfluxSetupDataLoader(HeatfluxApplicationProperties applicationProperties,
                                   GroupTypeService groupTypeService,
                                   DataTypeService dataTypeService,
                                   TagService tagService,
                                   FormulaItemService formulaItemService,
                                   FormulaItemValueService formulaItemValueService) {

        super(applicationProperties, groupTypeService, dataTypeService);
        this.tagService = tagService;
        this.formulaItemService = formulaItemService;
        this.formulaItemValueService = formulaItemValueService;

        createDataTypeIfNotFound(applicationProperties.getShutterDataTypeName());
        createTagIfNotFound(applicationProperties.getTagNameInsideTemperature());
        createTagIfNotFound(applicationProperties.getTagNameOutsideTemperature());

        final FormulaItem formulaItem = createFormulaItem((applicationProperties.getFormulaItemNameUValue()));
        applicationProperties.getFormulaItemNameUValueDefaults().forEach(s -> createFormulaItemValue(s, formulaItem));
    }

    @Transactional
    Tag createTagIfNotFound(String tagName) {

        Tag tag = tagService.findTagByName(tagName);
        if(tag == null) {
            tag = new Tag();
            tag.setName(tagName);
            tagService.saveTag(tag);
        }
        return tag;
    }


    @Transactional
    FormulaItem createFormulaItem(String formulaItemName) {

        FormulaItem formulaItem = formulaItemService.findFormulaItemByName(formulaItemName);
        if(formulaItem == null) {
            formulaItem = new FormulaItem();
            formulaItem.setName(formulaItemName);
            formulaItemService.saveFormulaItem(formulaItem);
        }
        return formulaItem;
    }



    @Transactional
    FormulaItemValue createFormulaItemValue(String value, FormulaItem formulaItem) {

        FormulaItemValue formulaItemValue = formulaItemValueService.findFormulaItemValueByValue(value);
        if(formulaItemValue == null) {
            formulaItemValue = new FormulaItemValue();
            formulaItemValue.setValue(value);
            formulaItemValue.setFormulaItem(formulaItem);
            formulaItemValueService.saveFormulaItemValue(formulaItemValue);
        }
        return formulaItemValue;
    }
}
