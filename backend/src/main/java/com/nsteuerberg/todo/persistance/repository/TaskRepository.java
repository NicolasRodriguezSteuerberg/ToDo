package com.nsteuerberg.todo.persistance.repository;

import com.nsteuerberg.todo.persistance.entity.TaskEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, Long> {
}
