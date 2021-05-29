package com.alethio.shop.category;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alethio.shop.model.domain.Category;
import com.alethio.shop.model.service.CategoryService;

@SpringBootTest
public class ListTest {

	@Autowired
	private CategoryService categoryService;

	@Test
	void contextLoads() {
		
		for (Category category : categoryService.getList()) {
			System.out.println("---");
			System.out.println("category id : " + category.getId());
			System.out.println("category name : " + category.getName());
		}
		
	}

}