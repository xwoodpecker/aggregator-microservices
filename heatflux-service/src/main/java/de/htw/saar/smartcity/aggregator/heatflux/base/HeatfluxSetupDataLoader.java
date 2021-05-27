package de.htw.saar.smartcity.aggregator.heatflux.base;

import de.htw.saar.smartcity.aggregator.heatflux.properties.HeatfluxApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.base.GroupSetupDataLoader;
import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.entity.Tag;
import de.htw.saar.smartcity.aggregator.lib.repository.DataTypeRepository;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.TagService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Setup data loader.
 */
@Component
public class HeatfluxSetupDataLoader extends GroupSetupDataLoader {


    private final TagService tagService;

    public HeatfluxSetupDataLoader(HeatfluxApplicationProperties applicationProperties,
                                   GroupTypeService groupTypeService,
                                   DataTypeService dataTypeService,
                                   TagService tagService) {

        super(applicationProperties, groupTypeService, dataTypeService);
        this.tagService = tagService;

        createDataTypeIfNotFound(applicationProperties.getShutterDataTypeName());
        createTagIfNotFound(applicationProperties.getTagNameInsideTemperature());
        createTagIfNotFound(applicationProperties.getTagNameOutsideTemperature());
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
}
