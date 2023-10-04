package com.udacity.critter.services;

import com.udacity.critter.entities.Employee;
import com.udacity.critter.repositories.EmployeesRepository;
import com.udacity.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeesService {

    private final EmployeesRepository employeesRepository;

    @Autowired
    public EmployeesService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public Employee getEmployeeById(long employeeId) {
        return employeesRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + employeeId + " not found."));
    }

    public List<Employee> getEmployeesForService(LocalDate date, Set<EmployeeSkill> skills) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<Employee> availableEmployees = employeesRepository
                .findByDaysAvailableContainingAndSkillsIn(dayOfWeek, skills);
        return availableEmployees;
    }

    public Employee saveEmployee(Employee employee) {
        return employeesRepository.save(employee);
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = getEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeesRepository.save(employee);
    }

    public List<Employee> getEmployeesByIds(List<Long> employeeIds) {
        return null;
    }
}
