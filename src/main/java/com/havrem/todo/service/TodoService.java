package com.havrem.todo.service;

import com.havrem.todo.exception.ExceptionFactory;
import com.havrem.todo.mapper.TodoMapper;
import com.havrem.todo.dto.response.TodoResponse;
import com.havrem.todo.dto.request.CreateTodoRequest;
import com.havrem.todo.dto.request.UpdateTodoRequest;
import com.havrem.todo.model.FirebaseUser;
import com.havrem.todo.repository.TodoRepository;
import com.havrem.todo.model.Todo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final ExceptionFactory exceptionFactory;

    public TodoService(TodoRepository todoRepository, TodoMapper todoMapper, ExceptionFactory exceptionFactory) {
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
        this.exceptionFactory = exceptionFactory;
    }

    public List<TodoResponse> findByUserId(FirebaseUser user) {
        return todoRepository.findByUidOrderByIdDesc(user.uid()).stream().map(todoMapper::todoToTodoResponse).toList();
    }

    public TodoResponse createTodo(CreateTodoRequest request, FirebaseUser user) {
        Todo todo = todoMapper.todoFromCreateRequest(request);
        todo.setUid(user.uid());
        todoRepository.save(todo);

        return todoMapper.todoToTodoResponse(todo);
    }

    public TodoResponse updateTodo(FirebaseUser user, UpdateTodoRequest request, long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> exceptionFactory.notFound("Todo", id));
        if(!todo.getUid().equals(user.uid())) throw exceptionFactory.forbidden();

        todoMapper.updateTodoFromRequest(request, todo);
        todoRepository.save(todo);

        return todoMapper.todoToTodoResponse(todo);
    }

    public void deleteTodo(FirebaseUser user, long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> exceptionFactory.notFound("Todo", id));
        if(!todo.getUid().equals(user.uid())) throw exceptionFactory.forbidden();

        todoRepository.delete(todo);
    }
}
