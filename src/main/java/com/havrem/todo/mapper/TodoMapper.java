package com.havrem.todo.mapper;

import com.havrem.todo.dto.request.CreateTodoRequest;
import com.havrem.todo.dto.response.TodoResponse;
import com.havrem.todo.dto.request.UpdateTodoRequest;
import com.havrem.todo.model.Todo;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    TodoResponse todoToTodoResponse(Todo todo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTodoFromRequest(UpdateTodoRequest request, @MappingTarget Todo todo);

    @Mapping(target = "completed", constant = "false")
    @Mapping(target = "description", defaultValue = "No description.")
    @Mapping(target = "due", defaultExpression = "java(java.time.LocalDate.now().plusYears(1))")
    Todo todoFromCreateRequest(CreateTodoRequest request);
}
