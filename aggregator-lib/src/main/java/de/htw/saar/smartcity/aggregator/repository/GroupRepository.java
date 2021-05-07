package de.htw.saar.smartcity.aggregator.repository;

import de.htw.saar.smartcity.aggregator.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Group findGroupByName(String name);

    List<Group> findAllByActiveTrue();
}
