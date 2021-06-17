package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.exception.CombinatorNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.exception.GroupNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.repository.AggregatorRepository;
import de.htw.saar.smartcity.aggregator.lib.repository.GroupRepository;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Group service.
 */
@Service
public class GroupService {

    private GroupRepository groupRepository;
    private AggregatorRepository aggregatorRepository;


    /**
     * Instantiates a new Group service.
     *
     * @param groupRepository      the group repository
     * @param aggregatorRepository the aggregator repository
     */
    public GroupService(GroupRepository groupRepository, AggregatorRepository aggregatorRepository) {

        this.groupRepository = groupRepository;
        this.aggregatorRepository = aggregatorRepository;
    }

    /**
     * Save group group.
     *
     * @param group the group
     * @return the group
     */
    public Group saveGroup(Group group) {

        if (groupRepository.findGroupByName(group.getName()) != null)
            throw new GroupNameAlreadyInUseException(group.getName());

        return groupRepository.save(group);
    }

    /**
     * Find group by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Group> findGroupById(Long id) {
        return groupRepository.findById(id);
    }

    /**
     * Find group by name group.
     *
     * @param name the name
     * @return the group
     */
    public Group findGroupByName(String name) {
        return groupRepository.findGroupByName(name);
    }

    /**
     * Find all groups list.
     *
     * @return the list
     */
    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }

    /**
     * Find all active groups list.
     *
     * @return the list
     */
    public List<Group> findAllActiveGroups() {
        return groupRepository.findAllByActiveTrue();
    }

    /**
     * Delete group.
     *
     * @param group the group
     */
    public void deleteGroup(Group group) {

        group.getProducers().forEach(p -> p.getGroups().removeIf(g -> g.equals(group)));
        group.setProducers(null);
        group.getAggregators().forEach(a -> a.setOwnerGroup(null));
        group.setAggregators(null);
        group.getValues().forEach(v -> v.getGroups().removeIf(g -> g.equals(group)));
        group.setValues(null);

        groupRepository.deleteById(group.getId());
    }
}
