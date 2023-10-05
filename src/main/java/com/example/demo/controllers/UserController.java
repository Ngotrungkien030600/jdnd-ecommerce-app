package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return ResponseEntity.of(userRepository.findById(id));
	}

	@GetMapping("/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			logger.error("User with username {} not found.", username);
			return ResponseEntity.notFound().build();
		} else {
			logger.info("User with username {} found.", username);
			return ResponseEntity.ok(user);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		String username = createUserRequest.getUsername();

		if (userRepository.findByUsername(username) != null) {
			logger.error("User with username {} already exists.", username);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		if (!isValidPassword(createUserRequest.getPassword(), createUserRequest.getConfirmPassword())) {
			logger.error("Invalid password for user with username {}.", username);
			return ResponseEntity.badRequest().build();
		}

		Cart cart = new Cart();
		cartRepository.save(cart);

		User user = new User();
		user.setUsername(username);
		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
		user.setCart(cart);

		userRepository.save(user);

		logger.info("Created user with username {}.", username);

		return ResponseEntity.ok(user);
	}

	private boolean isValidPassword(String password, String confirmPassword) {
		return password != null && password.length() >= 7 && password.equals(confirmPassword);
	}
}
