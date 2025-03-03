package com.joey.EmployeeManagement.Repository;

import com.joey.EmployeeManagement.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findByEmail(String email);

}
