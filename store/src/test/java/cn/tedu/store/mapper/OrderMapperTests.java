package cn.tedu.store.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTests {

	@Autowired
	private OrderMapper mapper;
	
	@Test
	public void saveOrder() {
		Order order = new Order();
		order.setUid(1);
		order.setRecvName("小刘同学");
		Integer rows = mapper.saveOrder(order);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void saveOrderItem() {
		OrderItem orderItem = new OrderItem();
		orderItem.setOid(1);
		orderItem.setPid(1);
		orderItem.setNum(2);
		orderItem.setPrice(3L);
		Integer rows = mapper.saveOrderItem(orderItem);
		System.err.println("rows=" + rows);
	}
	
}








