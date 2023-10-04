import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserById() {
        // Tạo user giả định
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        // Thiết lập mock repository
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        // Gọi phương thức getUserById
        ResponseEntity<User> response = userController.getUserById(1L);

        // Kiểm tra kết quả
        assertEquals(200, response.getStatusCodeValue());
        User resultUser = response.getBody();
        assertEquals(user.getUsername(), resultUser.getUsername());
    }

    @Test
    public void testGetUserByIdUserNotFound() {
        // Thiết lập mock repository để trả về Optional.empty() (không tìm thấy user)
        when(userRepository.findById(2L)).thenReturn(java.util.Optional.empty());

        // Gọi phương thức getUserById với id không tồn tại
        ResponseEntity<User> response = userController.getUserById(2L);

        // Kiểm tra kết quả
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testGetUserByUsername() {
        // Tạo user giả định
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        // Thiết lập mock repository
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        // Gọi phương thức getUserByUsername
        ResponseEntity<User> response = userController.getUserByUsername("testUser");

        // Kiểm tra kết quả
        assertEquals(200, response.getStatusCodeValue());
        User resultUser = response.getBody();
        assertEquals(user.getUsername(), resultUser.getUsername());
    }

    @Test
    public void testGetUserByUsernameUserNotFound() {
        // Thiết lập mock repository để trả về null (không tìm thấy user)
        when(userRepository.findByUsername("nonexistentUser")).thenReturn(null);

        // Gọi phương thức getUserByUsername với username không tồn tại
        ResponseEntity<User> response = userController.getUserByUsername("nonexistentUser");

        // Kiểm tra kết quả
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testCreateUser() {
        // Tạo request giả định
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("testUser");
        createUserRequest.setPassword("password");
        createUserRequest.setConfirmPassword("password");

        // Thiết lập mock repository
        when(bCryptPasswordEncoder.encode("password")).thenReturn("encodedPassword");

        // Gọi phương thức createUser
        ResponseEntity<User> response = userController.createUser(createUserRequest);

        // Kiểm tra kết quả
        assertEquals(200, response.getStatusCodeValue());
        User resultUser = response.getBody();
        assertEquals(createUserRequest.getUsername(), resultUser.getUsername());
        assertEquals("encodedPassword", resultUser.getPassword());
        verify(cartRepository, times(1)).save(any(Cart.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateUserInvalidPassword() {
        // Tạo request giả định với mật khẩu không hợp lệ
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("testUser");
        createUserRequest.setPassword("pass");
        createUserRequest.setConfirmPassword("pass");

        // Gọi phương thức createUser với mật khẩu không hợp lệ
        ResponseEntity<User> response = userController.createUser(createUserRequest);

        // Kiểm tra kết quả
        assertEquals(400, response.getStatusCodeValue());
        verify(cartRepository, times(0)).save(any(Cart.class));
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testCreateUserPasswordMismatch() {
        // Tạo request giả định với xác nhận mật khẩu không khớp
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("testUser");
        createUserRequest.setPassword("password");
        createUserRequest.setConfirmPassword("differentPassword");

        // Gọi phương thức createUser với xác nhận mật khẩu không khớp
        ResponseEntity<User> response = userController.createUser(createUserRequest);

        // Kiểm tra kết quả
        assertEquals(400, response.getStatusCodeValue());
        verify(cartRepository, times(0)).save(any(Cart.class));
        verify(userRepository, times(0)).save(any(User.class));
    }
}
