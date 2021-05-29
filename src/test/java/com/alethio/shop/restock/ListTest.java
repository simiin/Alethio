package com.alethio.shop.restock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alethio.shop.model.domain.Restock;
import com.alethio.shop.model.service.RestockService;

@SpringBootTest
public class ListTest {

	@Autowired
	private RestockService restockService;

	@Test
	void contextLoads() {
		
		for (Restock restock : restockService.getList()) {
			System.out.println("---");
			System.out.println("restock id : " + restock.getId());
			System.out.println("item id : " + restock.getItem_id());
			System.out.println("company name : " + restock.getCompany_name());
			System.out.println("encrypt item name : " + restock.getEncrypt_item_name());
			System.out.println("amount : " + restock.getAmount());
			System.out.println("category name (joined) : " + restock.getCategory_name());
		}
		
	}

}
