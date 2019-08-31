package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTests {

	@Autowired
	private CartMapper mapper;
	
	@Test
	public void save() {
		Cart cart = new Cart();
		cart.setUid(1);
		cart.setPid(4);
		cart.setNum(1);
		cart.setPrice(4L);
		Integer rows = mapper.save(cart);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateNum() {
		Integer cid = 1;
		Integer num = 10;
		String modifiedUser = "admin";
		Date modifiedTime = new Date();
		Integer rows = mapper.updateNum(cid, num, modifiedUser, modifiedTime);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void findByCid() {
		Integer cid = 800;
		Cart result = mapper.findByCid(cid);
		System.err.println(result);
	}
	
	@Test
	public void findByUidAndPid() {
		Integer uid = 1;
		Integer pid = 2;
		Cart result = mapper.findByUidAndPid(uid, pid);
		System.err.println(result);
	}
	
	@Test
	public void findByUid() {
		Integer uid = 10;
		List<CartVO> list = mapper.findByUid(uid);
		System.err.println("count=" + list.size());
		for (CartVO item : list) {
			System.err.println(item);
		}
	}
	
	@Test
	public void findByCids() {
		Integer[] cids = { 9, 7, 8, 10 };
		List<CartVO> list = mapper.findByCids(cids);
		System.err.println("count=" + list.size());
		for (CartVO item : list) {
			System.err.println(item);
		}
	}
	
}










