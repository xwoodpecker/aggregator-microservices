package de.htw.saar.smartcity.aggregator.lib.repository;

import de.htw.saar.smartcity.aggregator.lib.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Tag repository.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * Find tag by name tag.
     *
     * @param name the name
     * @return the tag
     */
    Tag findTagByName(String name);

}
