package com.example.employee.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeOperationDTO {
    private Long id;
    private String name;
    private String operation; // "create", "update", "delete"

}