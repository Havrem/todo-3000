package com.havrem.todo.repositories;

import com.havrem.todo.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUid(String uid);
}
