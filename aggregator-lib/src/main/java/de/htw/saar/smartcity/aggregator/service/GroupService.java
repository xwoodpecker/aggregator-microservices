package de.htw.saar.smartcity.aggregator.service;

import de.htw.saar.smartcity.aggregator.entity.Group;
import de.htw.saar.smartcity.aggregator.repository.GroupRepository;
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

    public void deleteGroupById(Long id) {
        groupRepository.deleteById(id);
    }
}
