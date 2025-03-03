package com.joey.EmployeeManagement.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "employees")

public class Employee extends Person{

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Id cannot be null")
    private Long employee_id;

    @NotBlank(message = "Department cannot be blank")
    private String department;

    @NotNull(message = "Salary is required")
    @Min(value = 0, message = "Salary cannot be negative")
    private double salary;

    private Role role = Role.EMPLOYEE;

    public Employee(){}

    public Employee(Long employee_id, String department, double salary, Role role, String fname, String lname, LocalDate birthdate, int age, Address address, String phone) {
        super(fname, lname, birthdate, age, address, phone);
        this.employee_id = employee_id;
        this.department = department;
        this.salary = salary;
    }
}
