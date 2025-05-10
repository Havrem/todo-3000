package com.havrem.todo.exception;

public record ErrorDetail (String code, String message, String location, String locationType){}
