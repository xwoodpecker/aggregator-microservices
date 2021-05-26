package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.repository.AggregatorRepository;
import de.htw.saar.smartcity.aggregator.lib.repository.GroupRepository;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private GroupRepository groupRepository;
    private AggregatorRepository aggregatorRepository;


    public GroupService(GroupRepository groupRepository, AggregatorRepository aggregatorRepository) {

        this.groupRepository = groupRepository;
        this.aggregatorRepository = aggregatorRepository;
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public Optional<Group> findGroupById(Long id) {
        return groupRepository.findById(id);
    }

    public Group findGroupByName(String name) {
        return groupRepository.findGroupByName(name);
    }

    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }

    public List<Group> findAllActiveGroups() {
        return groupRepository.findAllByActiveTrue();
    }

    public void deleteGroup(Group group) {
        group.getProducers().forEach(p -> p.getGroups().removeIf(g -> g.equals(group)));
        group.setProducers(null);
        //todo: aggregators test, deleted y/n ?
        group.getAggregators().forEach(a -> aggregatorRepository.deleteById(a.getId()));

        groupRepository.deleteById(group.getId());
    }
}
