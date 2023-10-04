package com.udacity.critter.user;

import com.udacity.critter.entities.Customer;
import com.udacity.critter.entities.Employee;
import com.udacity.critter.entities.Pet;
import com.udacity.critter.services.CustomersService;
import com.udacity.critter.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final CustomersService customersService;
    private final EmployeesService employeesService;

    @Autowired
    public UserController(CustomersService customersService, EmployeesService employeesService) {
        this.customersService = customersService;
        this.employeesService = employeesService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = mapCustomerDTOToEntity(customerDTO);
        return mapEntityToCustomerDTO(customersService.saveCustomer(customer, customerDTO.getPetIds()));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customersService.getAllCustomers();
        return customers.stream()
                .map(this::mapEntityToCustomerDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        return mapEntityToCustomerDTO(customersService.getCustomerByPetId(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = mapEmployeeDTOToEntity(employeeDTO);
        return mapEntityToEmployeeDTO(employeesService.saveEmployee(employee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return mapEntityToEmployeeDTO(employeesService.getEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeesService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeDTO employeeDTO) {
        List<Employee> employees = employeesService.getEmployeesForService(LocalDate.parse(employeeDTO.getName()), employeeDTO.getSkills());
        return employees.stream()
                .map(this::mapEntityToEmployeeDTO)
                .collect(Collectors.toList());
    }

    private CustomerDTO mapEntityToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());
        List<Long> petIds = customer.getPets().stream()
                .map(Pet::getId)
                .collect(Collectors.toList());
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    private Customer mapCustomerDTOToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());
        return customer;
    }

    private EmployeeDTO mapEntityToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        return employeeDTO;
    }

    private Employee mapEmployeeDTOToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }
}
