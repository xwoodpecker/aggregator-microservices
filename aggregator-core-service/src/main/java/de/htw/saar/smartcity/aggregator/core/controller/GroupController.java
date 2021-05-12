package de.htw.saar.smartcity.aggregator.core.controller;

import de.htw.saar.smartcity.aggregator.core.exception.GroupMemberNotFoundException;
import de.htw.saar.smartcity.aggregator.core.exception.GroupNotFoundException;
import de.htw.saar.smartcity.aggregator.core.exception.IllegalGroupDefinitionException;
import de.htw.saar.smartcity.aggregator.core.exception.MicroserviceNotFoundException;
import de.htw.saar.smartcity.aggregator.core.properties.CoreApplicationProperties;
import de.htw.saar.smartcity.aggregator.core.receiver.GroupsReceiver;
import de.htw.saar.smartcity.aggregator.lib.entity.Microservice;
import de.htw.saar.smartcity.aggregator.lib.entity.SensorType;
import de.htw.saar.smartcity.aggregator.lib.service.GroupMemberService;
import de.htw.saar.smartcity.aggregator.lib.service.MicroserviceService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import de.htw.saar.smartcity.aggregator.lib.entity.GroupMember;
import de.htw.saar.smartcity.aggregator.lib.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/groups")
public class GroupController {

    private final GroupService groupService;
    private final SensorService sensorService;
    private final GroupMemberService groupMemberService;
    private final MicroserviceService microserviceService;
    private final GroupsReceiver groupsReceiver;
    private CoreApplicationProperties coreApplicationProperties;

    public GroupController(GroupService groupService, SensorService sensorService, GroupMemberService groupMemberService, MicroserviceService microserviceService, GroupsReceiver groupsReceiver, CoreApplicationProperties coreApplicationProperties) {

        this.groupService = groupService;
        this.sensorService = sensorService;
        this.groupMemberService = groupMemberService;
        this.microserviceService = microserviceService;
        this.groupsReceiver = groupsReceiver;
        this.coreApplicationProperties = coreApplicationProperties;
    }

    @GetMapping("/")
    public ResponseEntity getGroups() {

        return new ResponseEntity(
                groupService.findAllGroups(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity getGroup(@PathVariable Long id) {

        return new ResponseEntity(
                groupService.findGroupById(id)
                        .orElseThrow(() -> new GroupNotFoundException(id)),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity postGroup(@RequestBody Group group) {

        Group saved = groupService.saveGroup(group);

        if(saved.isActive())
            groupsReceiver.groupActivated(saved);

        return new ResponseEntity(saved, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGroup(@PathVariable Long id) {

        Group group = groupService.findGroupById(id)
                .orElseThrow(() -> new GroupNotFoundException(id));

        groupService.deleteGroup(group);

        groupsReceiver.groupDeactivated(id);

        return new ResponseEntity(HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity putGroup(@RequestBody Group group, @PathVariable Long id) {

        Optional<Group> optOldGroup = groupService.findGroupById(id);
        Group saved;

        if(optOldGroup.isPresent()) {

            Group oldGroup = optOldGroup.get();
            boolean activeOldGroup = oldGroup.isActive();
            oldGroup.replaceOwnAttributesWithOther(group);
            saved = groupService.saveGroup(oldGroup);

            if(activeOldGroup && !saved.isActive())
                groupsReceiver.groupDeactivated(saved.getId());
            if(!activeOldGroup && saved.isActive())
                groupsReceiver.groupActivated(saved);

        } else {

            group.setId(id);
            saved = groupService.saveGroup(group);

            if(saved.isActive())
                groupsReceiver.groupActivated(saved);
        }

        return new ResponseEntity(saved, HttpStatus.OK);
    }

    @PutMapping("/{groupId}/members/{memberId}")
    public ResponseEntity putGroupMember(@PathVariable Long groupId, @PathVariable Long memberId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        GroupMember groupMember = groupMemberService.findGroupMemberById(memberId)
                .orElseThrow(() -> new GroupMemberNotFoundException(memberId));

        //todo: test
        if(group.getGroupType().getName() != coreApplicationProperties.getBasicGroupTypeName()) {
            List<SensorType> currentSensorTypes = group.getAllSensorsRecursive().stream().map(s -> s.getSensorType()).collect(Collectors.toList());
            currentSensorTypes.addAll(groupMember.getAllSensorsRecursive().stream().map(s -> s.getSensorType()).collect(Collectors.toList()));
            List<SensorType> sensorTypes = group.getGroupType().getSensorTypes();
            if(currentSensorTypes.size() > sensorTypes.size() || !sensorTypes.containsAll(currentSensorTypes))
                throw new IllegalGroupDefinitionException();
        }

        groupMember.getGroups().add(group);
        group.getMembers().add(groupMember);

        group = groupService.saveGroup(group);

        groupsReceiver.memberAddedToGroup(groupMember, group);

        return new ResponseEntity(group, HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}/members/{memberId}")
    public ResponseEntity deleteGroupMember(@PathVariable Long groupId, @PathVariable Long memberId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        GroupMember groupMember = groupMemberService.findGroupMemberById(memberId)
                .orElseThrow(() -> new GroupMemberNotFoundException(memberId));

        groupMember.getGroups().remove(group);
        group.getMembers().remove(groupMember);

        group = groupService.saveGroup(group);

        groupsReceiver.memberRemovedFromGroup(groupMember, group);

        return new ResponseEntity(group, HttpStatus.OK);
    }

    @PutMapping("/{groupId}/microservices/{microserviceId}")
    public ResponseEntity putGroupMicroservice(@PathVariable Long groupId, @PathVariable Long microserviceId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        Microservice microservice = microserviceService.findMicroServiceById(microserviceId)
                .orElseThrow(() -> new MicroserviceNotFoundException(microserviceId));

        microservice.getGroups().add(group);
        group.getMicroservices().add(microservice);
        group = groupService.saveGroup(group);

        return new ResponseEntity(group, HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}/microservices/{microserviceId}")
    public ResponseEntity deleteGroupMicroservice(@PathVariable Long groupId, @PathVariable Long microserviceId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        Microservice microservice = microserviceService.findMicroServiceById(microserviceId)
                .orElseThrow(() -> new MicroserviceNotFoundException(microserviceId));

        microservice.getGroups().remove(group);
        group.getMicroservices().remove(microservice);
        group = groupService.saveGroup(group);

        return new ResponseEntity(group, HttpStatus.OK);
    }


    /**@PutMapping("/{groupId}/sensors/{sensorId}")
    public ResponseEntity putGroupSensor(@PathVariable Long groupId, @PathVariable Long sensorId) {

    Optional<Sensor> sensor = sensorService.findSensorById(sensorId);
    if(!sensor.isPresent())
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No sensor with the given id found.");

    Optional<Group> group = groupService.findGroupById(groupId);
    if(!group.isPresent())
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No group with given id found.");

    Sensor s = sensor.get();
    Group g = group.get();
    s.getGroups().add(g);
    g.getMembers().add(s);
    g = groupService.saveGroup(g);

    groupsReceiver.memberAddedToGroup(s, g);

    return new ResponseEntity(g, HttpStatus.OK);
    }


     @PutMapping("/{parentGroupId}/groups/{childGroupId}")
     public ResponseEntity putGroupGroup(@PathVariable Long parentGroupId, @PathVariable Long childGroupId) {

     Optional<Group> parentGroup = groupService.findGroupById(parentGroupId);
     Optional<Group> childGroup = groupService.findGroupById(childGroupId);

     if(!parentGroup.isPresent() || !childGroup.isPresent())
     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No group with given id found.");

     Group p = parentGroup.get();
     Group c = childGroup.get();
     c.getGroups().add(p);
     p.getMembers().add(c);
     p = groupService.saveGroup(p);

     groupsReceiver.memberAddedToGroup(c, p);

     return new ResponseEntity(p, HttpStatus.OK);
     } **/
}
