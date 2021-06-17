package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.GroupType;
import de.htw.saar.smartcity.aggregator.lib.exception.CombinatorNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.exception.GroupTypeNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.repository.GroupTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Group type service.
 */
@Service
public class GroupTypeService {

    private GroupTypeRepository groupTypeRepository;

    /**
     * Instantiates a new Group type service.
     *
     * @param groupTypeRepository the group type repository
     */
    public GroupTypeService(GroupTypeRepository groupTypeRepository) {
        this.groupTypeRepository = groupTypeRepository;
    }

    /**
     * Find group type by name group type.
     *
     * @param name the name
     * @return the group type
     */
    public GroupType findGroupTypeByName(String name) {
        return groupTypeRepository.findGroupTypeByName(name);
    }

    /**
     * Save group type group type.
     *
     * @param groupType the group type
     * @return the group type
     */
    public GroupType saveGroupType(GroupType groupType) {

        if (groupTypeRepository.findGroupTypeByName(groupType.getName()) != null)
            throw new GroupTypeNameAlreadyInUseException(groupType.getName());

        return groupTypeRepository.save(groupType);
    }

    /**
     * Find group type by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<GroupType> findGroupTypeById(Long id) {
        return groupTypeRepository.findById(id);
    }

    /**
     * Find all group types list.
     *
     * @return the list
     */
    public List<GroupType> findAllGroupTypes() {
        return groupTypeRepository.findAll();
    }
}
