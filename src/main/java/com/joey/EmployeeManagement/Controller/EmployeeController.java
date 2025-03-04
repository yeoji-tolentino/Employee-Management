package com.joey.EmployeeManagement.Controller;

import com.joey.EmployeeManagement.Model.Employee;
import com.joey.EmployeeManagement.Services.EmployeeService;
import com.joey.EmployeeManagement.auth.RegisterRequest;
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
import java.util.Comparator;
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
    public String viewEmployee(@PathVariable Long id, Model model, Principal principal) {
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
    public String createEmployee(@Valid @ModelAttribute RegisterRequest employee){
        Employee savedEmployee = this.employeeService.create(employee);
        return "redirect:/";
    }

    @PostMapping("/update/employee/{id}")
    public String updateEmployee(@Valid @ModelAttribute RegisterRequest employee, @PathVariable Long id, Model model){
        Employee savedEmployee = this.employeeService.update(employee, id);
        return "redirect:/employee/"+ id;
    }

    @PostMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, Model model, Principal principal){
        Boolean isDeleted = this.employeeService.delete(id);

        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchEmployee(@RequestParam(value = "keyword", required = false) String keyword, Model model, Principal principal) {
        List<Employee> employees;
        if (keyword == null || keyword.trim().isEmpty()) {
            employees = (List<Employee>) employeeService.getAll();
        } else {
            employees = employeeService.searchEmployees(keyword);
        }

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

        } else {
            System.out.println("No user is logged in.");
            return "redirect:/login_page";
        }

        model.addAttribute("employees", employees);
        return "index";
    }

    @GetMapping("/employee/filter")
    public String filterEmployee(
            @RequestParam(value = "filterDepartment", required = false) String filterDepartment,
            @RequestParam(value = "filterAge", required = false) String filterAge,
            Model model, Principal principal) {

        List<Employee> employees;

        if (filterDepartment == null || filterDepartment.trim().isEmpty()) {
            employees = (List<Employee>) employeeService.getAll();
        } else {
            employees = employeeService.searchEmployees(filterDepartment);
        }

        if (!filterAge.trim().isEmpty()) {
            String[] ageRange = filterAge.split("-");
            int minAge = Integer.parseInt(ageRange[0]);
            int maxAge = Integer.parseInt(ageRange[1]);
            employees = employees.stream()
                    .filter(e -> e.getAge() >= minAge && e.getAge() <= maxAge)
                    .toList();
        }

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

        } else {
            System.out.println("No user is logged in.");
            return "redirect:/login_page";
        }

        model.addAttribute("employees", employees);
        return "index";
    }
}
