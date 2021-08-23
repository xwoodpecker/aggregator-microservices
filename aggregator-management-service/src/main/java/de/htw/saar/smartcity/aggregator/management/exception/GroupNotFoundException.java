package de.htw.saar.smartcity.aggregator.management.exception;

/**
 * The type Group not found exception.
 */
public class GroupNotFoundException  extends NotFoundException {

    /**
     * Instantiates a new Group not found exception.
     *
     * @param id the id
     */
    public GroupNotFoundException(Long id) {
        super("Could not find group with id " + id);
    }
}
