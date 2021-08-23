package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Group type not found exception.
 */
public class GroupTypeNotFoundException extends NotFoundException {
    /**
     * Instantiates a new Group type not found exception.
     *
     * @param id the id
     */
    public GroupTypeNotFoundException(Long id) {
        super("Could not find group type with id " + id);
    }
}
