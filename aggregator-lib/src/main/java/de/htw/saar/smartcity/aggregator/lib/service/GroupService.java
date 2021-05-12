package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.repository.GroupRepository;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private GroupRepository groupRepository;


    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
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
        group.getMembers().forEach(m -> m.getGroups().removeIf(g -> g.getId() == group.getId()));
        group.setMembers(null);
        group.setMicroservices(null);
        group.getGroups().forEach(g -> g.getMembers().removeIf(m -> m.getId() == group.getId()));
        groupRepository.deleteById(group.getId());
    }
}
