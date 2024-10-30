package com.simonlai.doit.repository;

import com.simonlai.doit.model.Task;
import com.simonlai.doit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // findAllBy + var name of the foreign key in Task.java + var name of the primary key in User.java
    List<Task> findAllByUserIdId(final Long UserId);

    Optional<Task> findAllByTaskIdAndUserId(final Long taskId, final User userId);
}
