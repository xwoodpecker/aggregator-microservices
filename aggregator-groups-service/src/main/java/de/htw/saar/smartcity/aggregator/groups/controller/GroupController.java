package de.htw.saar.smartcity.aggregator.groups.controller;

import de.htw.saar.smartcity.aggregator.groups.exception.AggregatorNotFoundException;
import de.htw.saar.smartcity.aggregator.groups.exception.FormulaItemNotFoundException;
import de.htw.saar.smartcity.aggregator.groups.exception.GroupNotFoundException;
import de.htw.saar.smartcity.aggregator.groups.exception.ProducerNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.entity.Aggregator;
import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItemValue;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/groups")
public class GroupController {

    private final GroupService groupService;
    private final ProducerService producerService;
    private final AggregatorService aggregatorService;
    private final FormulaItemValueService formulaItemValueService;

    public GroupController(GroupService groupService,
                           ProducerService producerService,
                           AggregatorService aggregatorService,
                           FormulaItemValueService formulaItemValueService) {

        this.groupService = groupService;
        this.producerService = producerService;
        this.aggregatorService = aggregatorService;
        this.formulaItemValueService = formulaItemValueService;
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

        return new ResponseEntity(groupService.saveGroup(group), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGroup(@PathVariable Long id) {

        Group group = groupService.findGroupById(id)
                .orElseThrow(() -> new GroupNotFoundException(id));

        groupService.deleteGroup(group);

        return new ResponseEntity(HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity putGroup(@RequestBody Group group, @PathVariable Long id) {

        Optional<Group> optOldGroup = groupService.findGroupById(id);
        Group saved;

        if(optOldGroup.isPresent()) {

            Group oldGroup = optOldGroup.get();
            oldGroup.replaceOwnAttributesWithOther(group);
            saved = groupService.updateGroup(oldGroup);

        } else {

            group.setId(id);
            saved = groupService.saveGroup(group);
        }

        return new ResponseEntity(saved, HttpStatus.OK);
    }

    @PutMapping("/{groupId}/producers/{producerId}")
    public ResponseEntity putProducer(@PathVariable Long groupId, @PathVariable Long producerId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        Producer producer = producerService.findProducerById(producerId)
                .orElseThrow(() -> new ProducerNotFoundException(producerId));

        //PRIO 2 - todo: logic for correct group type definition ?

        producer.getGroups().add(group);
        group.getProducers().add(producer);

        group = groupService.saveGroup(group);

        return new ResponseEntity(group, HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}/producers/{producerId}")
    public ResponseEntity deleteProducer(@PathVariable Long groupId, @PathVariable Long producerId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        Producer producer = producerService.findProducerById(producerId)
                .orElseThrow(() -> new ProducerNotFoundException(producerId));

        producer.getGroups().remove(group);
        group.getProducers().remove(producer);

        group = groupService.saveGroup(group);

        return new ResponseEntity(group, HttpStatus.OK);
    }


    @PutMapping("/{groupId}/aggregators/{aggregatorId}")
    public ResponseEntity putAggregator(@PathVariable Long groupId, @PathVariable Long aggregatorId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        Aggregator aggregator = aggregatorService.findAggregatorById(aggregatorId)
                .orElseThrow(() -> new AggregatorNotFoundException(aggregatorId));

        //PRIO 2 - todo: logic for correct group type definition ?

        aggregator.setOwnerGroup(group);
        group.getAggregators().add(aggregator);

        group = groupService.saveGroup(group);

        return new ResponseEntity(group, HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}/aggregators/{aggregatorId}")
    public ResponseEntity deleteAggregator(@PathVariable Long groupId, @PathVariable Long aggregatorId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        Aggregator aggregator = aggregatorService.findAggregatorById(aggregatorId)
                .orElseThrow(() -> new AggregatorNotFoundException(aggregatorId));

        aggregator.setOwnerGroup(null);
        group.getAggregators().remove(aggregator);

        group = groupService.saveGroup(group);

        return new ResponseEntity(group, HttpStatus.OK);
    }



    @PutMapping("/{groupId}/formulaItemValues/{formulaItemValueId}")
    public ResponseEntity putFormulaItemValue(@PathVariable Long groupId, @PathVariable Long formulaItemValueId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        FormulaItemValue formulaItemValue = formulaItemValueService.findFormulaItemValueById(formulaItemValueId)
                .orElseThrow(() -> new FormulaItemNotFoundException(formulaItemValueId));

        group.getValues().add(formulaItemValue);

        group = groupService.saveGroup(group);

        return new ResponseEntity(group, HttpStatus.OK);
    }

    @DeleteMapping("/{groupId}/formulaItemValues/{formulaItemValueId}")
    public ResponseEntity deleteFormulaItemValue(@PathVariable Long groupId, @PathVariable Long formulaItemValueId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        FormulaItemValue formulaItemValue = formulaItemValueService.findFormulaItemValueById(formulaItemValueId)
                .orElseThrow(() -> new FormulaItemNotFoundException(formulaItemValueId));

        group.getValues().remove(formulaItemValue);

        group = groupService.saveGroup(group);

        return new ResponseEntity(group, HttpStatus.OK);
    }
}
