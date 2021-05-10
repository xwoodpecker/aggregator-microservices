package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.GroupType;
import de.htw.saar.smartcity.aggregator.lib.repository.GroupTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupTypeService {

    private GroupTypeRepository groupTypeRepository;

    public GroupTypeService(GroupTypeRepository groupTypeRepository) {
        this.groupTypeRepository = groupTypeRepository;
    }

    public GroupType findGroupTypeByName(String name) {
        return groupTypeRepository.findGroupTypeByName(name);
    }

    public GroupType saveGroupType(GroupType groupType) {
        return groupTypeRepository.save(groupType);
    }
}
