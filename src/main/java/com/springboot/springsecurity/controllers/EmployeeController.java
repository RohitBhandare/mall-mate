package com.springboot.springsecurity.controllers;

import com.springboot.springsecurity.dto.DepartmentDTO;
import com.springboot.springsecurity.dto.EmployeeDTO;
import com.springboot.springsecurity.dto.EmployeeWithDepartmentDTO;
import com.springboot.springsecurity.models.Department;
import com.springboot.springsecurity.models.Employee;
import com.springboot.springsecurity.services.DepartmentService;
import com.springboot.springsecurity.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<EmployeeWithDepartmentDTO>> getAllEmployeesWithDepartment() {
        List<Employee> employees = employeeService.getAllEmployees();

        // Convert Employee entities to EmployeeWithDepartmentDTO
        List<EmployeeWithDepartmentDTO> employeesWithDepartment = employees.stream()
                .map(employee -> {
                    EmployeeWithDepartmentDTO employeeDTO = new EmployeeWithDepartmentDTO();
                    employeeDTO.setId(employee.getId());
                    employeeDTO.setEmail(employee.getEmail());
                    employeeDTO.setMobileNumber(employee.getMobileNumber());
                    employeeDTO.setAddress(employee.getAddress());
                    employeeDTO.setName(employee.getName());
                    employeeDTO.setDesignation(employee.getDesignation());

                    // Map department details to DepartmentDTO
                    if (employee.getDepartment() != null) {
                        Department department = employee.getDepartment();
                        DepartmentDTO departmentDTO = new DepartmentDTO(); // Assuming DepartmentDTO exists

                        departmentDTO.setName(department.getName());
                        //departmentDTO.getDescription(department.getDescription())
                        // Map other department fields as needed

                        employeeDTO.setDepartment(departmentDTO.getName());
                    }
                    return employeeDTO;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(employeesWithDepartment, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmail(employeeDTO.getEmail());
        employee.setMobileNumber(employeeDTO.getMobileNumber());
        employee.setAddress(employeeDTO.getAddress());
        employee.setName(employeeDTO.getName());
        employee.setDesignation(employeeDTO.getDesignation());

        // Fetch the Department based on the provided departmentID
        Department department = departmentService.getDepartmentById(employeeDTO.getDepartmentID());

        // Associate the Employee with the Department
        if (department != null) {
            employee.setDepartment(department);
        }

        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return updatedEmployee != null ?
                new ResponseEntity<>(updatedEmployee, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
