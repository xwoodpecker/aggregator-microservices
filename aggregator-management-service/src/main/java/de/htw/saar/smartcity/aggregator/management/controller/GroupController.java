package de.htw.saar.smartcity.aggregator.management.controller;

import de.htw.saar.smartcity.aggregator.management.exception.AggregatorNotFoundException;
import de.htw.saar.smartcity.aggregator.management.exception.FormulaItemNotFoundException;
import de.htw.saar.smartcity.aggregator.management.exception.GroupNotFoundException;
import de.htw.saar.smartcity.aggregator.management.exception.ProducerNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.entity.Aggregator;
import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItemValue;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * The type Group controller.
 */
@RestController
@RequestMapping(path = "/groups")
public class GroupController {

    private final GroupService groupService;
    private final ProducerService producerService;
    private final AggregatorService aggregatorService;
    private final FormulaItemValueService formulaItemValueService;

    /**
     * Instantiates a new Group controller.
     *
     * @param groupService            the group service
     * @param producerService         the producer service
     * @param aggregatorService       the aggregator service
     * @param formulaItemValueService the formula item value service
     */
    public GroupController(GroupService groupService,
                           ProducerService producerService,
                           AggregatorService aggregatorService,
                           FormulaItemValueService formulaItemValueService) {

        this.groupService = groupService;
        this.producerService = producerService;
        this.aggregatorService = aggregatorService;
        this.formulaItemValueService = formulaItemValueService;
    }

    /**
     * Gets groups.
     *
     * @return the groups
     */
    @GetMapping("/")
    public ResponseEntity getGroups() {

        return new ResponseEntity(
                groupService.findAllGroups(),
                HttpStatus.OK
        );
    }

    /**
     * Gets group.
     *
     * @param id the id
     * @return the group
     */
    @GetMapping("/{id}")
    public ResponseEntity getGroup(@PathVariable Long id) {

        return new ResponseEntity(
                groupService.findGroupById(id)
                        .orElseThrow(() -> new GroupNotFoundException(id)),
                HttpStatus.OK
        );
    }

    /**
     * Post group response entity.
     *
     * @param group the group
     * @return the response entity
     */
    @PostMapping("/")
    public ResponseEntity postGroup(@RequestBody Group group) {

        return new ResponseEntity(groupService.saveGroup(group), HttpStatus.OK);
    }

    /**
     * Delete group response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteGroup(@PathVariable Long id) {

        Group group = groupService.findGroupById(id)
                .orElseThrow(() -> new GroupNotFoundException(id));

        groupService.deleteGroup(group);

        return new ResponseEntity(HttpStatus.OK);

    }

    /**
     * Put group response entity.
     *
     * @param group the group
     * @param id    the id
     * @return the response entity
     */
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

    /**
     * Put producer response entity.
     *
     * @param groupId    the group id
     * @param producerId the producer id
     * @return the response entity
     */
    @PutMapping("/{groupId}/producers/{producerId}")
    public ResponseEntity putProducer(@PathVariable Long groupId, @PathVariable Long producerId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        Producer producer = producerService.findProducerById(producerId)
                .orElseThrow(() -> new ProducerNotFoundException(producerId));

        //PRIO 2 - todo: logic for correct group type definition ?

        producer.getGroups().add(group);
        group.getProducers().add(producer);

        group = groupService.updateGroup(group);

        return new ResponseEntity(group, HttpStatus.OK);
    }

    /**
     * Delete producer response entity.
     *
     * @param groupId    the group id
     * @param producerId the producer id
     * @return the response entity
     */
    @DeleteMapping("/{groupId}/producers/{producerId}")
    public ResponseEntity deleteProducer(@PathVariable Long groupId, @PathVariable Long producerId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        Producer producer = producerService.findProducerById(producerId)
                .orElseThrow(() -> new ProducerNotFoundException(producerId));

        producer.getGroups().remove(group);
        group.getProducers().remove(producer);

        group = groupService.updateGroup(group);

        return new ResponseEntity(group, HttpStatus.OK);
    }


    /**
     * Put aggregator response entity.
     *
     * @param groupId      the group id
     * @param aggregatorId the aggregator id
     * @return the response entity
     */
    @PutMapping("/{groupId}/aggregators/{aggregatorId}")
    public ResponseEntity putAggregator(@PathVariable Long groupId, @PathVariable Long aggregatorId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        Aggregator aggregator = aggregatorService.findAggregatorById(aggregatorId)
                .orElseThrow(() -> new AggregatorNotFoundException(aggregatorId));

        //PRIO 2 - todo: logic for correct group type definition ?

        aggregator.setOwnerGroup(group);
        group.getAggregators().add(aggregator);

        group = groupService.updateGroup(group);

        return new ResponseEntity(group, HttpStatus.OK);
    }

    /**
     * Delete aggregator response entity.
     *
     * @param groupId      the group id
     * @param aggregatorId the aggregator id
     * @return the response entity
     */
    @DeleteMapping("/{groupId}/aggregators/{aggregatorId}")
    public ResponseEntity deleteAggregator(@PathVariable Long groupId, @PathVariable Long aggregatorId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        Aggregator aggregator = aggregatorService.findAggregatorById(aggregatorId)
                .orElseThrow(() -> new AggregatorNotFoundException(aggregatorId));

        aggregator.setOwnerGroup(null);
        group.getAggregators().remove(aggregator);

        group = groupService.updateGroup(group);

        return new ResponseEntity(group, HttpStatus.OK);
    }


    /**
     * Put formula item value response entity.
     *
     * @param groupId            the group id
     * @param formulaItemValueId the formula item value id
     * @return the response entity
     */
    @PutMapping("/{groupId}/formulaItemValues/{formulaItemValueId}")
    public ResponseEntity putFormulaItemValue(@PathVariable Long groupId, @PathVariable Long formulaItemValueId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        FormulaItemValue formulaItemValue = formulaItemValueService.findFormulaItemValueById(formulaItemValueId)
                .orElseThrow(() -> new FormulaItemNotFoundException(formulaItemValueId));

        group.getValues().add(formulaItemValue);

        group = groupService.updateGroup(group);

        return new ResponseEntity(group, HttpStatus.OK);
    }

    /**
     * Delete formula item value response entity.
     *
     * @param groupId            the group id
     * @param formulaItemValueId the formula item value id
     * @return the response entity
     */
    @DeleteMapping("/{groupId}/formulaItemValues/{formulaItemValueId}")
    public ResponseEntity deleteFormulaItemValue(@PathVariable Long groupId, @PathVariable Long formulaItemValueId) {

        Group group = groupService.findGroupById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));

        FormulaItemValue formulaItemValue = formulaItemValueService.findFormulaItemValueById(formulaItemValueId)
                .orElseThrow(() -> new FormulaItemNotFoundException(formulaItemValueId));

        group.getValues().remove(formulaItemValue);

        group = groupService.updateGroup(group);

        return new ResponseEntity(group, HttpStatus.OK);
    }
}
