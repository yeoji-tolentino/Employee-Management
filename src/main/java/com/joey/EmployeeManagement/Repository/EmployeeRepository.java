package com.joey.EmployeeManagement.Repository;

import com.joey.EmployeeManagement.Model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Long> {

    @Query("SELECT e FROM Employee e WHERE " +
            "LOWER(e.fname) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.lname) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.department) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Employee> searchEmployees(@Param("keyword") String keyword);
}
