package com.joey.EmployeeManagement.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String fname;
    private String lname;
    private String phone;
    private int house_no;
    private String street;
    private String barangay;
    private String city;
    private int age;
    private String username;
    private String email;
    private String password;
}
