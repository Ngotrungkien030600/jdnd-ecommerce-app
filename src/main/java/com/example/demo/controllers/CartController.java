package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	private UserRepository userRepository;
	private CartRepository cartRepository;
	private ItemRepository itemRepository;

	@Autowired
	public CartController(UserRepository userRepository, CartRepository cartRepository, ItemRepository itemRepository) {
		this.userRepository = userRepository;
		this.cartRepository = cartRepository;
		this.itemRepository = itemRepository;
	}

	@PostMapping("/addToCart")
	public ResponseEntity<Cart> addToCart(@RequestBody ModifyCartRequest request) {
		User user = userRepository.findByUsername(request.getUsername());
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if (!item.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Cart cart = user.getCart();
		int quantityToAdd = request.getQuantity();
		for (int i = 0; i < quantityToAdd; i++) {
			cart.addItem(item.get());
		}
		cartRepository.save(cart);
		return ResponseEntity.ok(cart);
	}

	@PostMapping("/removeFromCart")
	public ResponseEntity<Cart> removeFromCart(@RequestBody ModifyCartRequest request) {
		User user = userRepository.findByUsername(request.getUsername());
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if (!item.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Cart cart = user.getCart();
		int quantityToRemove = request.getQuantity();
		for (int i = 0; i < quantityToRemove; i++) {
			cart.removeItem(item.get());
		}
		cartRepository.save(cart);
		return ResponseEntity.ok(cart);
	}
}
