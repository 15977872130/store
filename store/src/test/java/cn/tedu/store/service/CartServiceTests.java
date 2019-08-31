package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {

	@Autowired
	private ICartService service;
	
	@Test
	public void addToCart() {
		try {
			Integer uid = 3;
			Integer pid = 10000017;
			Integer num = 1;
			String username = "HAHAHA";
			service.addToCart(uid, pid, num, username);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void getByUid() {
		Integer uid = 10;
		List<CartVO> list = service.getByUid(uid);
		System.err.println("count=" + list.size());
		for (CartVO item : list) {
			System.err.println(item);
		}
	}

	@Test
	public void addNum() {
		try {
			Integer cid = 8;
			Integer uid = 10;
			String username = "HAHAHA";
			service.addNum(cid, uid, username);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void getByCids() {
		Integer[] cids = {7,8,9,10};
		Integer uid = 10;
		List<CartVO> list = service.getByCids(cids, uid);
		System.err.println("count=" + list.size());
		for (CartVO item : list) {
			System.err.println(item);
		}
	}
	
}










