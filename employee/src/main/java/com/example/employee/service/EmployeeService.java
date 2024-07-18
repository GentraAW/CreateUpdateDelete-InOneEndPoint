package com.example.employee.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee.dto.EmployeeOperationDTO;
import com.example.employee.model.EmployeeModel;
import com.example.employee.repository.EmployeeRepository;

import jakarta.transaction.Transactional;



@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeModel> getAllEmployee(){
        return employeeRepository.findAll();
    }

    public EmployeeModel getEmployeeById(Long id){
        return employeeRepository.findById(id).orElse(null);
    }

    public EmployeeModel createEmployee(EmployeeModel employeeModel){
        return employeeRepository.save(employeeModel);
    }

    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public EmployeeModel updateEmployee(Long id, EmployeeModel employeeModel){
        EmployeeModel existingEmployee = employeeRepository.findById(id).orElse(null);

        if(existingEmployee != null){
            existingEmployee.setName(employeeModel.getName());
            
        }
        return employeeRepository.save(existingEmployee);
    }

    @Transactional
    public List<EmployeeModel> bulkUpdateEmployees(List<EmployeeOperationDTO> employeeOperations) {
        List<EmployeeModel> updatedEmployees = new ArrayList<>();

        for (EmployeeOperationDTO operation : employeeOperations) {
            switch (operation.getOperation().toLowerCase()) {
                case "create":
                    EmployeeModel newEmployee = new EmployeeModel();
                    newEmployee.setName(operation.getName());
                    updatedEmployees.add(employeeRepository.save(newEmployee));
                    break;

                case "update":
                    if (operation.getId() != null) {
                        EmployeeModel existingEmployee = employeeRepository.findById(operation.getId()).orElse(null);
                        if (existingEmployee != null) {
                            existingEmployee.setName(operation.getName());
                            updatedEmployees.add(employeeRepository.save(existingEmployee));
                        }
                    }
                    break;

                case "delete":
                    if (operation.getId() != null) {
                        deleteEmployee(operation.getId());
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Operation tidak diketahui: " + operation.getOperation());
            }
        }

        return updatedEmployees;
    }
}
