package com.alethio.shop.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alethio.shop.model.domain.Item;
import com.alethio.shop.model.service.ItemService;

@SpringBootTest
public class ListTest {

	@Autowired
	private ItemService itemService;

	@Test
	void contextLoads() {
		
		for (Item item : itemService.getList()) {
			System.out.println("---");
			System.out.println("item id : " + item.getId());
			System.out.println("category id : " + item.getCategory_id());
			System.out.println("item name : " + item.getName());
			System.out.println("stock : " + item.getStock());
			System.out.println("category name (joined) : " + item.getCategory_name());
		}
		
	}

}