package com.example.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.dto.EmployeeOperationDTO;
import com.example.employee.model.EmployeeModel;
import com.example.employee.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

     @GetMapping
    public List<EmployeeModel> getAllEmployees() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/{id}")
    public EmployeeModel getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public EmployeeModel createEmployee(@RequestBody EmployeeModel employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{id}")
    public EmployeeModel updateEmployee(@PathVariable Long id, @RequestBody EmployeeModel employeeDetails) {
        return employeeService.updateEmployee(id, employeeDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        boolean isDeleted = employeeService.deleteEmployee(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id : " + id + " tidak ditemukan");
        }
        return ResponseEntity.ok("Berhasil dihapus " + id);
    }
    @PostMapping("/bulk")
    public ResponseEntity<List<EmployeeModel>> bulkUpdateEmployees(@RequestBody List<EmployeeOperationDTO> employeeOperations) {
        List<EmployeeModel> updatedEmployees = employeeService.bulkUpdateEmployees(employeeOperations);
        return ResponseEntity.ok(updatedEmployees);
    }
}
