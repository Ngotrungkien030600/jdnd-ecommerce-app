import com.udacity.critter.entities.Customer;
import com.udacity.critter.entities.Employee;
import com.udacity.critter.entities.Pet;
import com.udacity.critter.pet.PetController;
import com.udacity.critter.pet.PetDTO;
import com.udacity.critter.pet.PetType;
import com.udacity.critter.schedule.ScheduleController;
import com.udacity.critter.schedule.ScheduleDTO;
import com.udacity.critter.user.CustomerDTO;
import com.udacity.critter.user.EmployeeDTO;
import com.udacity.critter.user.EmployeeSkill;
import com.udacity.critter.user.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CritterFunctionalTest {

    @InjectMocks
    private UserController userController;

    @InjectMocks
    private PetController petController;

    @InjectMocks
    private ScheduleController scheduleController;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("TestCustomer");
        customerDTO.setPhoneNumber("123-456-789");

        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());

        CustomerDTO newCustomer = userController.saveCustomer(customerDTO);
        assertEquals(newCustomer.getName(), customerDTO.getName());
    }

    @Test
    public void testCreateEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("TestEmployee");
        employeeDTO.setSkills(Collections.singleton(EmployeeSkill.FEEDING));

        when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());

        EmployeeDTO newEmployee = userController.saveEmployee(employeeDTO);
        assertEquals(newEmployee.getName(), employeeDTO.getName());
    }

    @Test
    public void testAddPetsToCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("TestCustomer");
        customerDTO.setPhoneNumber("123-456-789");

        PetDTO petDTO = new PetDTO();
        petDTO.setName("TestPet");
        petDTO.setType(PetType.CAT);

        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(petRepository.save(any(Pet.class))).thenReturn(new Pet());

        CustomerDTO newCustomer = userController.saveCustomer(customerDTO);
        petDTO.setOwnerId(newCustomer.getId());
        PetDTO newPet = petController.savePet(petDTO);

        assertEquals(newPet.getOwnerId(), newCustomer.getId());
    }

    // Add more test cases for other methods as needed.
}
