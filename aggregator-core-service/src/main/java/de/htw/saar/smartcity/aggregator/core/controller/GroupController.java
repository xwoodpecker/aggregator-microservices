package de.htw.saar.smartcity.aggregator.core.controller;

import de.htw.saar.smartcity.aggregator.core.receiver.GroupsReceiver;
import de.htw.saar.smartcity.aggregator.lib.entity.Microservice;
import de.htw.saar.smartcity.aggregator.lib.service.GroupMemberService;
import de.htw.saar.smartcity.aggregator.lib.service.MicroserviceService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import de.htw.saar.smartcity.aggregator.lib.entity.GroupMember;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/groups")
public class GroupController {

    private final GroupService groupService;
    private final SensorService sensorService;
    private final GroupMemberService groupMemberService;
    private final MicroserviceService microserviceService;
    private final GroupsReceiver groupsReceiver;

    public GroupController(GroupService groupService, SensorService sensorService, GroupMemberService groupMemberService, MicroserviceService microserviceService, GroupsReceiver groupsReceiver) {
        this.groupService = groupService;
        this.sensorService = sensorService;
        this.groupMemberService = groupMemberService;
        this.microserviceService = microserviceService;
        this.groupsReceiver = groupsReceiver;
    }


    @GetMapping("/")
    public ResponseEntity getGroups() {
        List<Group> groups = groupService.findAllGroups();

        return new ResponseEntity(groups, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getGroup(@PathVariable Long id) {
        ResponseEntity response;
        Optional<Group> group = groupService.findGroupById(id);

        if(group.isPresent())
            response = new ResponseEntity(group.get(), HttpStatus.OK);
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No group with the given id found.");

        return response;
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
        groupService.deleteGroupById(id);

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


    @PutMapping("/{groupId}/sensors/{sensorId}")
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
    }


    @PutMapping("/{groupId}/members/{memberId}")
    public ResponseEntity putGroupMember(@PathVariable Long groupId, @PathVariable Long memberId) {

        Optional<Group> group = groupService.findGroupById(groupId);
        if(!group.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No group with given id found.");


        Optional<GroupMember> groupMember = groupMemberService.findGroupMemberById(memberId);
        if(!groupMember.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No group member with given id found.");

        Group g = group.get();
        GroupMember gm = groupMember.get();
        gm.getGroups().add(g);
        g.getMembers().add(gm);
        g = groupService.saveGroup(g);

        groupsReceiver.memberAddedToGroup(gm, g);

        return new ResponseEntity(g, HttpStatus.OK);
    }


    @PutMapping("/{groupId}/microservices/{microserviceId}")
    public ResponseEntity putGroupMicroservice(@PathVariable Long groupId, @PathVariable Long microserviceId) {

        Optional<Group> group = groupService.findGroupById(groupId);
        if(!group.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No group with given id found.");


        Optional<Microservice> microservice = microserviceService.findMicroServiceById(microserviceId);
        if(!microservice.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No microservice with given id found.");

        Group g = group.get();
        Microservice ms = microservice.get();
        ms.getGroups().add(g);
        g.getMicroservices().add(ms);
        g = groupService.saveGroup(g);
        return new ResponseEntity(g, HttpStatus.OK);
    }

}
