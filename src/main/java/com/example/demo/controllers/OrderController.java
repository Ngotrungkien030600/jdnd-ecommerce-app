import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

	@InjectMocks
	private OrderController orderController;

	@Mock
	private UserRepository userRepository;

	@Mock
	private OrderRepository orderRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSubmitOrder() {
		// Tạo user giả định
		User user = new User();
		user.setId(1L);
		user.setUsername("testUser");
		user.setPassword("testPassword");
		user.setCart(new Cart());

		// Tạo request giả định
		ModifyCartRequest cartRequest = new ModifyCartRequest();
		cartRequest.setUsername("testUser");
		cartRequest.setItemId(1L);
		cartRequest.setQuantity(2);

		// Thiết lập mock repository
		when(userRepository.findByUsername("testUser")).thenReturn(user);

		// Gọi phương thức submitOrder
		ResponseEntity<UserOrder> response = orderController.submitOrder("testUser");

		// Kiểm tra kết quả
		assertEquals(200, response.getStatusCodeValue());
		UserOrder resultOrder = response.getBody();
		assertEquals(user.getCart(), resultOrder.getCart());
	}

	@Test
	public void testSubmitOrderUserNotFound() {
		// Thiết lập mock repository để trả về null (không tìm thấy user)
		when(userRepository.findByUsername("nonexistentUser")).thenReturn(null);

		// Gọi phương thức submitOrder với user không tồn tại
		ResponseEntity<UserOrder> response = orderController.submitOrder("nonexistentUser");

		// Kiểm tra kết quả
		assertEquals(404, response.getStatusCodeValue());
	}

	@Test
	public void testGetOrderHistoryForUser() {
		// Tạo user giả định
		User user = new User();
		user.setId(1L);
		user.setUsername("testUser");
		user.setPassword("testPassword");
		user.setCart(new Cart());

		// Tạo danh sách đơn hàng giả định
		List<UserOrder> orderList = new ArrayList<>();
		UserOrder order1 = new UserOrder();
		order1.setId(1L);
		order1.setUser(user);
		UserOrder order2 = new UserOrder();
		order2.setId(2L);
		order2.setUser(user);
		orderList.add(order1);
		orderList.add(order2);

		// Thiết lập mock repository
		when(userRepository.findByUsername("testUser")).thenReturn(user);
		when(orderRepository.findByUser(user)).thenReturn(orderList);

		// Gọi phương thức getOrderHistoryForUser
		ResponseEntity<List<UserOrder>> response = orderController.getOrderHistoryForUser("testUser");

		// Kiểm tra kết quả
		assertEquals(200, response.getStatusCodeValue());
		List<UserOrder> resultOrders = response.getBody();
		assertEquals(2, resultOrders.size());
	}

	@Test
	public void testGetOrderHistoryForUserUserNotFound() {
		// Thiết lập mock repository để trả về null (không tìm thấy user)
		when(userRepository.findByUsername("nonexistentUser")).thenReturn(null);

		// Gọi phương thức getOrderHistoryForUser với user không tồn tại
		ResponseEntity<List<UserOrder>> response = orderController.getOrderHistoryForUser("nonexistentUser");

		// Kiểm tra kết quả
		assertEquals(404, response.getStatusCodeValue());
	}
}
