package com.joey.EmployeeManagement.Services;

import com.joey.EmployeeManagement.Model.Employee;
import com.joey.EmployeeManagement.Repository.EmployeeRepository;
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

    public Employee create(Employee employee){
        return this.employeeRepository.save(employee);
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

    public List<Employee> searchBooks(String keyword) {
        return employeeRepository.searchEmployees(keyword);
    }
}
