package com.havrem.todo.mapper;

import com.havrem.todo.dto.request.CreateTodoRequest;
import com.havrem.todo.dto.response.TodoResponse;
import com.havrem.todo.dto.request.UpdateTodoRequest;
import com.havrem.todo.model.Todo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    TodoResponse todoToTodoResponse(Todo todo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTodoFromRequest(UpdateTodoRequest request, Todo todo);

    @Mapping(target = "complete", constant = "false")
    @Mapping(target = "description", defaultValue = "No description.")
    @Mapping(target = "due", defaultExpression = "java(java.time.LocalDate.now().plusYears(1))")
    Todo todoFromCreateRequest(CreateTodoRequest request);
}
