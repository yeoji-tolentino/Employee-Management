package com.joey.EmployeeManagement.Controller;

import com.joey.EmployeeManagement.Model.Employee;
import com.joey.EmployeeManagement.Services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }
    @GetMapping
    public String homepage(Model model, Principal principal) {
        Iterable<Employee> employees = this.employeeService.getAll();

        double averageSalary = 0;
        double averageAge = 0;
        int count = 0;

        for (Employee employee : employees) {
                averageSalary += employee.getSalary();
                averageAge += employee.getAge();
                count++;
        }

        if (count > 0) {
            averageSalary /= count;
            averageAge /= count;
        }

        model.addAttribute("employees", employees);
        model.addAttribute("avgSalary", averageSalary);
        model.addAttribute("avgAge", averageAge);

        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("username", username);
            Iterable<Employee> carts = this.employeeService.getAll();

            System.out.println("User is logged in: " + username);
            System.out.println("Carts: " + carts);
        } else {
            System.out.println("No user is logged in.");
            return "redirect:/login_page";
        }

        return "index";
    }

    @GetMapping("/employee/{id}")
    public String homepage(@PathVariable Long id, Model model, Principal principal) {
        Optional<Employee> employee = this.employeeService.get(id);

        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("username", username);
            model.addAttribute("employee", employee.orElse(null));
            System.out.println("Employee Id: " + employee.get().getId());
        } else {
            System.out.println("No user is logged in.");
            return "redirect:/login_page";
        }

        return "employee";
    }

    @PostMapping("/create/employee")
    public ResponseEntity<Employee> createEmployee(@Valid @ModelAttribute Employee employee){
        Employee savedEmployee = this.employeeService.create(employee);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/" + savedEmployee.getId())
                .body(savedEmployee);
    }

    @PostMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, Model model, Principal principal){
        Boolean isDeleted = this.employeeService.delete(id);

        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(value = "keyword", required = false) String keyword, Model model, Principal principal) {
        List<Employee> employees;
        if (keyword == null || keyword.trim().isEmpty()) {
            employees = (List<Employee>) employeeService.getAll();
        } else {
            employees = employeeService.searchBooks(keyword);
        }

        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("username", username);

        } else {
            System.out.println("No user is logged in.");
            return "redirect:/login_page";
        }

        model.addAttribute("employees", employees);
        return "index";
    }
}
