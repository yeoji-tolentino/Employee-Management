package com.joey.EmployeeManagement.auth;

import com.joey.EmployeeManagement.Model.Address;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    private String fname;
    private String lname;
    private String phone;
    private int age;
    private String username;
    private String email;
    private String password;
    private Address address;
    private String department;
    private LocalDate birthdate;
    private Long employee_id;
    private int house_no;
    private String street;
    private String barangay;
    private String city;
    private double salary;

    public Address getAddress() {
        this.address = new Address(house_no,street,barangay,city);
        return address;
    }
}
