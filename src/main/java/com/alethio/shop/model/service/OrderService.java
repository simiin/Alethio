package com.alethio.shop.model.service;

import java.util.List;

import com.alethio.shop.model.domain.Order;

public interface OrderService {

	public List<Order> getList();
	public void regist(Order order);
	public void delete(int id);

}