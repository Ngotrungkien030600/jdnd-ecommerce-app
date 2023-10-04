import com.example.demo.controllers.PostMapping;
import com.example.demo.controllers.User;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	private UserRepository userRepository;
	private CartRepository cartRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserController(
			UserRepository userRepository,
			CartRepository cartRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder
	) {
		this.userRepository = userRepository;
		this.cartRepository = cartRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		log.log("UserController.getUserById called with id {}", id);
		return ResponseEntity.of(userRepository.findById(id));
	}

	@GetMapping("/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		log.log("UserController.getUserByUsername called with username {}", username);
		User user = userRepository.findByUsername(username);
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}

	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		log.log("UserController.createUser called with username {}", createUserRequest.getUsername());
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);
		if (
				createUserRequest.getPassword().length() <= 6 ||
						!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())
		) {
			log.log("Cannot create user {} because the password is invalid", createUserRequest.getUsername());
			return ResponseEntity.badRequest().build();
		}
		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
		userRepository.save(user);
		log.info("New user {} created", createUserRequest.getUsername());
		return ResponseEntity.ok(user);
	}

}
