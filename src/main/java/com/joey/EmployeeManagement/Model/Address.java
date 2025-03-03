package com.joey.EmployeeManagement.Model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class Address {

    @NotNull(message="House number required")
    private int house_no;

    @NotBlank(message="Street required")
    private String street;

    @NotBlank(message="Barangay required")
    private String barangay;

    @NotBlank(message="City required")
    private String city;

    public Address(){

    }

    public Address(int house_no, String street, String barangay, String city) {
        this.house_no = house_no;
        this.street = street;
        this.barangay = barangay;
        this.city = city;
    }

}