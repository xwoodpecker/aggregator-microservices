package de.htw.saar.smartcity.aggregator.management.controller;

import de.htw.saar.smartcity.aggregator.management.exception.GroupTypeNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Group type controller.
 */
@RestController
@RequestMapping(path = "/groupTypes")
public class GroupTypeController {

    private final GroupTypeService groupTypeService;

    /**
     * Instantiates a new Group type controller.
     *
     * @param groupTypeService the group type service
     */
    public GroupTypeController(GroupTypeService groupTypeService) {

        this.groupTypeService = groupTypeService;
    }

    /**
     * Gets group types.
     *
     * @return the group types
     */
    @GetMapping("/")
    public ResponseEntity getGroupTypes() {

        return new ResponseEntity(
                groupTypeService.findAllGroupTypes(),
                HttpStatus.OK
        );
    }

    /**
     * Gets group type.
     *
     * @param id the id
     * @return the group type
     */
    @GetMapping("/{id}")
    public ResponseEntity getGroupType(@PathVariable Long id) {

        return new ResponseEntity(
                groupTypeService.findGroupTypeById(id)
                        .orElseThrow(() -> new GroupTypeNotFoundException(id)),
                HttpStatus.OK
        );
    }

   
}
