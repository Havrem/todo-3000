package com.havrem.todo.repository;

import com.havrem.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUidOrderByIdDesc(String uid);
}
