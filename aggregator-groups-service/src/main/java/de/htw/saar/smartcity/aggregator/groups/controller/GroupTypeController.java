package de.htw.saar.smartcity.aggregator.groups.controller;

import de.htw.saar.smartcity.aggregator.groups.exception.GroupTypeNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/groupTypes")
public class GroupTypeController {

    private final GroupTypeService groupTypeService;

    public GroupTypeController(GroupTypeService groupTypeService) {

        this.groupTypeService = groupTypeService;
    }

    @GetMapping("/")
    public ResponseEntity getGroupTypes() {

        return new ResponseEntity(
                groupTypeService.findAllGroupTypes(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity getGroupType(@PathVariable Long id) {

        return new ResponseEntity(
                groupTypeService.findGroupTypeById(id)
                        .orElseThrow(() -> new GroupTypeNotFoundException(id)),
                HttpStatus.OK
        );
    }

   
}
