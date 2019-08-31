package cn.tedu.store;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	public DataSource dataSource;
	
	@Test
	public void getConnection() throws SQLException {
		Connection conn = dataSource.getConnection();
		System.err.println(conn);
	}
	
	@Test
	public void md5() {
		String salt = UUID.randomUUID().toString();
		String password = "123456";
		String md5Password = 
			DigestUtils.md5DigestAsHex(
				(password + salt).getBytes());
		System.err.println(salt);
		System.err.println(md5Password);
		// select 原始密码 from table where 密文=?
		// 123456	e10adc3949ba59abbe56e057f20f883e
		//			33b04ff0663297d21bc4864cc0521941
		//			3f5a2c03bdd9b7435168e50b3b021687
		//			c639f2dae18c8dc399f6fb9acf0addf3
		// 1234		81dc9bdb52d04dc20036dbd8313ed055
		// 0		cfcd208495d565ef66e7dff9f98764da
	}
	
	@Test
	public void uuid() {
		for (int i = 0; i < 20; i++) {
			System.err.println(UUID.randomUUID().toString());
		}
	}
	
	@Test
	public void md5x() {
		String salt = UUID.randomUUID().toString();
		String password = "123456";
		for (int i = 0; i < 5; i++) {
			password = DigestUtils
				.md5DigestAsHex((password + salt).getBytes());
			System.err.println(password);
		}
		// e10adc3949ba59abbe56e057f20f883e
		// 14e1b600b1fd579f47433b88e8d85291
		// c56d0e9a7ccec67b4ea131655038d604
		// f4cc399f0effd13c888e310ea2cf5399
		// c26be8aaf53b15054896983b43eb6a65
		
		// 435b4a78a695b75f00adb7c47fa17434
		// fce1043723830bfe1dd8a051cabb9a82
		// 3b4956e13188d49c6086a436f419c958
		// a31df73a9cad79ad603099c742c55ecd
		// eb51cbdc1b23041de4bbecb8f45beeed
	}
	
	@Test
	public void commonsMd5() {
		org.apache.commons.codec.digest.DigestUtils.md5("123456");
	}
	
}







