package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
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

public class ItemControllerTest {

	@InjectMocks
	private ItemController itemController;

	@Mock
	private ItemRepository itemRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllItems() {
		// Tạo danh sách các item giả định
		List<Item> itemList = new ArrayList<>();
		Item item1 = new Item();
		item1.setId(1L);
		item1.setName("Item 1");
		Item item2 = new Item();
		item2.setId(2L);
		item2.setName("Item 2");
		itemList.add(item1);
		itemList.add(item2);

		// Thiết lập mock repository
		when(itemRepository.findAll()).thenReturn(itemList);

		// Gọi phương thức getAllItems
		ResponseEntity<List<Item>> response = itemController.getAllItems();

		// Kiểm tra kết quả
		assertEquals(200, response.getStatusCodeValue());
		List<Item> resultItems = response.getBody();
		assertEquals(2, resultItems.size());
	}

	@Test
	public void testGetItemById() {
		// Tạo item giả định
		Item item = new Item();
		item.setId(1L);
		item.setName("Test Item");

		// Thiết lập mock repository
		when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

		// Gọi phương thức getItemById
		ResponseEntity<Item> response = itemController.getItemById(1L);

		// Kiểm tra kết quả
		assertEquals(200, response.getStatusCodeValue());
		Item resultItem = response.getBody();
		assertEquals("Test Item", resultItem.getName());
	}

	@Test
	public void testGetItemByIdNotFound() {
		// Thiết lập mock repository để trả về Optional rỗng
		when(itemRepository.findById(1L)).thenReturn(Optional.empty());

		// Gọi phương thức getItemById với ID không tồn tại
		ResponseEntity<Item> response = itemController.getItemById(1L);

		// Kiểm tra kết quả
		assertEquals(404, response.getStatusCodeValue());
	}

	@Test
	public void testGetItemsByName() {
		// Tạo danh sách các item giả định
		List<Item> itemList = new ArrayList<>();
		Item item1 = new Item();
		item1.setId(1L);
		item1.setName("Test Item");
		Item item2 = new Item();
		item2.setId(2L);
		item2.setName("Test Item");

		itemList.add(item1);
		itemList.add(item2);

		// Thiết lập mock repository
		when(itemRepository.findByName("Test Item")).thenReturn(itemList);

		// Gọi phương thức getItemsByName
		ResponseEntity<List<Item>> response = itemController.getItemsByName("Test Item");

		// Kiểm tra kết quả
		assertEquals(200, response.getStatusCodeValue());
		List<Item> resultItems = response.getBody();
		assertEquals(2, resultItems.size());
	}

	@Test
	public void testGetItemsByNameNotFound() {
		// Thiết lập mock repository để trả về danh sách rỗng
		when(itemRepository.findByName("Nonexistent Item")).thenReturn(new ArrayList<>());

		// Gọi phương thức getItemsByName với tên không tồn tại
		ResponseEntity<List<Item>> response = itemController.getItemsByName("Nonexistent Item");

		// Kiểm tra kết quả
		assertEquals(404, response.getStatusCodeValue());
	}
}
