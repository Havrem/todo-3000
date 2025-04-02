package com.havrem.todo.services;

import com.havrem.todo.dtos.CreateTodoRequest;
import com.havrem.todo.dtos.UpdateTodoRequest;
import com.havrem.todo.repositories.TodoRepository;
import com.havrem.todo.models.Todo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findByUserId(String uid) {
        return todoRepository.findByUidOrderByIdDesc(uid);
    }

    public Todo createTodo(CreateTodoRequest request, String uid) {
        Todo todo = new Todo(request.title(), false, uid);
        todoRepository.save(todo);

        return todo;
    }

    public Todo updateTodo(UpdateTodoRequest request, long id) {
        Todo todo = todoRepository.findById(id).orElseThrow();

        if (request.completed() != null) todo.setCompleted(request.completed());
        if (request.title() != null) todo.setTitle(request.title());

        todoRepository.save(todo);
        return todo;
    }

    public Todo deleteTodo(long id) {
        Todo todo = todoRepository.findById(id).orElseThrow();
        todoRepository.delete(todo);

        return todo;
    }
}
