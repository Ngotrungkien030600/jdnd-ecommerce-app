import com.example.demo.controllers.ItemController;
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
import static org.junit.Assert.assertNotNull;
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
    public void testGetItems() {
        List<Item> itemList = new ArrayList<>();
        Item item1 = new Item();
        item1.setId(1L);
        itemList.add(item1);

        when(itemRepository.findAll()).thenReturn(itemList);

        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<Item> responseItems = response.getBody();
        assertNotNull(responseItems);
        assertEquals(1, responseItems.size());

        verify(itemRepository, times(1)).findAll();
    }

    @Test
    public void testGetItemById() {
        Item item = new Item();
        item.setId(1L);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Item responseItem = response.getBody();
        assertNotNull(responseItem);
        assertEquals(1L, responseItem.getId().longValue());

        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetItemsByName() {
        List<Item> itemList = new ArrayList<>();
        Item item1 = new Item();
        item1.setName("TestItem");
        itemList.add(item1);

        when(itemRepository.findByName("TestItem")).thenReturn(itemList);

        ResponseEntity<List<Item>> response = itemController.getItemsByName("TestItem");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<Item> responseItems = response.getBody();
        assertNotNull(responseItems);
        assertEquals(1, responseItems.size());

        verify(itemRepository, times(1)).findByName("TestItem");
    }

    @Test
    public void testGetItemsByName_NotFound() {
        when(itemRepository.findByName("NonExistentItem")).thenReturn(new ArrayList<>());

        ResponseEntity<List<Item>> response = itemController.getItemsByName("NonExistentItem");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}
