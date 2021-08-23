package de.htw.saar.smartcity.aggregator.management.controller;

import de.htw.saar.smartcity.aggregator.management.exception.DataTypeNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Data type controller.
 */
@RestController
@RequestMapping(path = "/dataTypes")
public class DataTypeController {

    private final DataTypeService dataTypeService;

    /**
     * Instantiates a new Data type controller.
     *
     * @param dataTypeService the data type service
     */
    public DataTypeController(DataTypeService dataTypeService) {

        this.dataTypeService = dataTypeService;
    }

    /**
     * Gets data types.
     *
     * @return the data types
     */
    @GetMapping("/")
    public ResponseEntity getDataTypes() {

        return new ResponseEntity(
                dataTypeService.findAllDataTypes(),
                HttpStatus.OK
        );
    }

    /**
     * Gets data type.
     *
     * @param id the id
     * @return the data type
     */
    @GetMapping("/{id}")
    public ResponseEntity getDataType(@PathVariable Long id) {

        return new ResponseEntity(
                dataTypeService.findDataTypeById(id)
                        .orElseThrow(() -> new DataTypeNotFoundException(id)),
                HttpStatus.OK
        );
    }

   
}
