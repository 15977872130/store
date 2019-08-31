package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressMapperTests {

	@Autowired
	private AddressMapper mapper;
	
	@Test
	public void save() {
		Address address = new Address();
		address.setUid(1);
		address.setName("Lucy");
		address.setProvinceCode("1234567");
		Integer rows = mapper.save(address);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void deleteByAid() {
		Integer aid = 18;
		Integer rows = mapper.deleteByAid(aid);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateDefault() {
		Integer aid = 19;
		String modifiedUser = "编辑";
		Date modifiedTime = new Date();
		Integer rows = mapper.updateDefault(aid, modifiedUser, modifiedTime);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateNonDefault() {
		Integer uid = 10;
		Integer rows = mapper.updateNonDefault(uid);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void countByUid() {
		Integer uid = 1;
		Integer count = mapper.countByUid(uid);
		System.err.println("count=" + count);
	}
	
	@Test
	public void findByUid() {
		Integer uid = 10;
		List<Address> list = mapper.findByUid(uid);
		System.err.println("count=" + list.size());
		for (Address item : list) {
			System.err.println(item);
		}
	}
	
	@Test
	public void findByAid() {
		Integer aid = 19;
		Address result = mapper.findByAid(aid);
		System.err.println(result);
	}
	
	@Test
	public void findLastModified() {
		Integer uid = 10;
		Address result = mapper.findLastModified(uid);
		System.err.println(result);
	}
	
}










