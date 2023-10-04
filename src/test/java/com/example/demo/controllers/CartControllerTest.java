import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemRepository itemRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddToCart() {
        // Tạo user và item giả định
        User user = new User();
        user.setUsername("testUser");
        Item item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setPrice(BigDecimal.valueOf(10.99));

        // Tạo request giả định
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("testUser");
        request.setItemId(1L);
        request.setQuantity(3);

        // Thiết lập mock repository
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        // Gọi phương thức addToCart
        ResponseEntity<Cart> response = cartController.addToCart(request);

        // Kiểm tra kết quả
        assertEquals(200, response.getStatusCodeValue());
        Cart cart = response.getBody();
        assertEquals(3, cart.getItems().size());
    }

    @Test
    public void testRemoveFromCart() {
        // Tạo user và item giả định
        User user = new User();
        user.setUsername("testUser");
        Item item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setPrice(BigDecimal.valueOf(10.99));

        // Tạo request giả định
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("testUser");
        request.setItemId(1L);
        request.setQuantity(2);

        // Thiết lập mock repository
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        // Gọi phương thức removeFromCart
        ResponseEntity<Cart> response = cartController.removeFromCart(request);

        // Kiểm tra kết quả
        assertEquals(200, response.getStatusCodeValue());
        Cart cart = response.getBody();
        assertEquals(0, cart.getItems().size());
    }
}
