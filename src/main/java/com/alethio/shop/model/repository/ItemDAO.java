package com.alethio.shop.model.repository;

import java.util.List;

import com.alethio.shop.model.domain.Item;

public interface ItemDAO {

	public Item select(int id);
	public List<Item> selectAll();
	public void insert(Item item);
	public void update(Item item);
	public void delete(int id);
	public int countById(int id);

}
