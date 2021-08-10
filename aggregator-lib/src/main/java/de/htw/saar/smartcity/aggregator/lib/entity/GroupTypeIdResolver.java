package de.htw.saar.smartcity.aggregator.lib.entity;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import org.springframework.stereotype.Component;

/**
 * The type Group type id resolver.
 */
@Component
public class GroupTypeIdResolver implements ObjectIdResolver {

    /**
     * the group type service
     */
    private GroupTypeService groupTypeService;

    /**
     * Instantiates a new Group type id resolver.
     *
     * @param groupTypeService the group type service
     */
    public GroupTypeIdResolver(GroupTypeService groupTypeService) {
        this.groupTypeService = groupTypeService;
    }

    @Override
    public void bindItem(ObjectIdGenerator.IdKey idKey, Object o) {

    }

    @Override
    public Object resolveId(ObjectIdGenerator.IdKey idKey) {
        if(idKey.key instanceof Long)
            return groupTypeService.findGroupTypeById((Long)idKey.key).get();
        else
            return null;
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object o) {
        return this;
    }

    @Override
    public boolean canUseFor(ObjectIdResolver objectIdResolver) {
        return false;
    }
}
