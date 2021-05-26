package de.htw.saar.smartcity.aggregator.groups.controller;

import de.htw.saar.smartcity.aggregator.groups.exception.DataTypeNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/dataTypes")
public class DataTypeController {

    private final DataTypeService dataTypeService;

    public DataTypeController(DataTypeService dataTypeService) {

        this.dataTypeService = dataTypeService;
    }

    @GetMapping("/")
    public ResponseEntity getDataTypes() {

        return new ResponseEntity(
                dataTypeService.findAllDataTypes(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity getDataType(@PathVariable Long id) {

        return new ResponseEntity(
                dataTypeService.findDataTypeById(id)
                        .orElseThrow(() -> new DataTypeNotFoundException(id)),
                HttpStatus.OK
        );
    }

   
}
