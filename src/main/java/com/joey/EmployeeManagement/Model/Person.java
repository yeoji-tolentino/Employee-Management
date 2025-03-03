package com.joey.EmployeeManagement.Model;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public abstract class Person {

    @NotBlank(message = "First name cannot be blank")
    String fname;

    @NotBlank(message = "Last name cannot be blank")
    String lname;

    @NotNull(message = "Birthdate is required")
    LocalDate birthdate;

    @Min(value= 18, message = "Users aged 18 years and older can create an account.")
    @NotNull(message = "Age is required")
    int age;

    @Embedded
    @Valid
    private Address address;

    @NotNull(message = "Phone is required")
    String phone;

    Person(){}

    public Person(String fname, String lname, LocalDate birthdate, int age, Address address, String phone) {
        this.fname = fname;
        this.lname = lname;
        this.birthdate = birthdate;
        this.age = age;
        this.address = address;
        this.phone = phone;
    }
}
