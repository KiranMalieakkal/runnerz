package dev.kme.runnerz.run;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;


public interface RunRepository extends ListCrudRepository<Run,Integer> {

    List<Run> findAllByLocation(String location);
}
