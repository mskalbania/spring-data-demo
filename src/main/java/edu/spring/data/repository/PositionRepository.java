package edu.spring.data.repository;

import edu.spring.data.entity.Position;
import org.springframework.data.repository.CrudRepository;

public interface PositionRepository extends CrudRepository<Position, Integer> {
}
