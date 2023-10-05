import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemRepository itemRepository;

    private CartController cartController;

    @Before
    public void setUp() {
        cartController = new CartController();
        cartController.setUserRepository(userRepository);
        cartController.setCartRepository(cartRepository);
        cartController.setItemRepository(itemRepository);
    }

    @Test
    public void testAddToCart() {
        // Tạo đối tượng ModifyCartRequest
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("testUser");
        request.setItemId(1L);
        request.setQuantity(2);

        // Tạo đối tượng User và Cart
        User user = new User();
        user.setUsername("testUser");

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setItems(new ArrayList<>());

        // Tạo đối tượng Item
        Item item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setPrice(new BigDecimal("10.0"));

        // Thiết lập hành vi của các repository
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(cartRepository.save(cart)).thenReturn(cart);

        // Gọi phương thức addToCart và kiểm tra kết quả
        ResponseEntity<Cart> response = cartController.addToCart(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getItems().size());
    }

    @Test
    public void testRemoveFromCart() {
        // Tạo đối tượng ModifyCartRequest
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("testUser");
        request.setItemId(1L);
        request.setQuantity(1);

        // Tạo đối tượng User và Cart
        User user = new User();
        user.setUsername("testUser");

        Cart cart = new Cart();
        cart.setUser(user);

        // Tạo đối tượng Item
        Item item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setPrice(new BigDecimal("10.0"));

        // Thiết lập hành vi của các repository
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(cartRepository.save(cart)).thenReturn(cart);

        // Gọi phương thức removeFromCart và kiểm tra kết quả
        ResponseEntity<Cart> response = cartController.removeFromCart(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().getItems().size());
    }
}
