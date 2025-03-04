package com.joey.EmployeeManagement.Services;

import com.joey.EmployeeManagement.Model.Address;
import com.joey.EmployeeManagement.Model.Employee;
import com.joey.EmployeeManagement.Model.Role;
import com.joey.EmployeeManagement.Repository.EmployeeRepository;
import com.joey.EmployeeManagement.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee create(RegisterRequest request){
        Employee employee = new Employee();
        employee.setFname(request.getFname());
        employee.setLname(request.getLname());
        employee.setPhone(request.getPhone());
        employee.setAge(request.getAge());
        employee.setBirthdate(request.getBirthdate());
        employee.setDepartment(request.getDepartment());
        employee.setEmployee_id(request.getEmployee_id());
        employee.setSalary(request.getSalary());
        employee.setRole(Role.EMPLOYEE);
        Address address = request.getAddress();

        if (address != null) {
            employee.setAddress(address);
        }

        return employeeRepository.save(employee);
    }

    public boolean delete(Long id){
        if(!employeeRepository.existsById(id)){
            return false;
        } else{
            employeeRepository.deleteById(id);
            return true;
        }
    }

    public Iterable<Employee> getAll(){
        return employeeRepository.findAll();
    }

    public Optional<Employee> get(Long id){
        return employeeRepository.findById(id);
    }

    public List<Employee> searchEmployees(String keyword) {
        return employeeRepository.searchEmployees(keyword);
    }

    public Employee update(RegisterRequest request, Long id){
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        existingEmployee.setFname(request.getFname());
        existingEmployee.setLname(request.getLname());
        existingEmployee.setPhone(request.getPhone());
        existingEmployee.setAge(request.getAge());
        existingEmployee.setBirthdate(request.getBirthdate());
        existingEmployee.setDepartment(request.getDepartment());
        existingEmployee.setEmployee_id(request.getEmployee_id());
        existingEmployee.setSalary(request.getSalary());
        existingEmployee.setRole(Role.EMPLOYEE);

        if (request.getAddress() != null) {
            existingEmployee.setAddress(request.getAddress());
        }
        return employeeRepository.save(existingEmployee);
    }
}
