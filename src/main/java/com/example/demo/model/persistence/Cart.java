package com.example.demo.model.persistence;

import org.apache.catalina.User;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany
	private List<AbstractReadWriteAccess.Item> items;

	@OneToOne(mappedBy = "cart")
	private User user;

	private BigDecimal total;

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<AbstractReadWriteAccess.Item> getItems() {
		return items;
	}

	public void setItems(List<AbstractReadWriteAccess.Item> items) {
		this.items = items;
	}

	public void addItem(AbstractReadWriteAccess.Item item) {
		if (items == null) {
			items = new ArrayList<>();
		}
		items.add(item);
		if (total == null) {
			total = BigDecimal.ZERO;
		}
		total = total.add(item.getPrice());
	}

	public void removeItem(AbstractReadWriteAccess.Item item) {
		if (items == null) {
			items = new ArrayList<>();
		}
		items.remove(item);
		if (total == null) {
			total = BigDecimal.ZERO;
		}
		total = total.subtract(item.getPrice());
	}
}
