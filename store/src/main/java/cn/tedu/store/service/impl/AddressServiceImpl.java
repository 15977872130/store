package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.AddressNotFoundException;
import cn.tedu.store.service.ex.AddressSizeLimitException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;

@Service
public class AddressServiceImpl implements IAddressService {

	@Autowired
	public AddressMapper addressMapper;
	@Autowired
	public IDistrictService districtService;

	@Override
	public void addnew(Address address, Integer uid, String username) {
		// 根据参数uid获取该用户的收货地址数据的数量
		Integer count = countByUid(uid);
		// 判断数量是否大于或等于限制值
		if (count >= MAX_COUNT) {
			// 是：抛出AddressSizeLimitException
			throw new AddressSizeLimitException(
				"收货地址的数量达到上限");
		}

		// 判断收货地址数据的数量是否为0
		// 是：即将插入的收货地址是默认的，isDefault=1
		// 否：即将插入的收货地址不是默认的，isDefault=0
		Integer isDefault = count == 0 ? 1 : 0;
		// 补全数据：将isDefault封装到参数address中
		address.setIsDefault(isDefault);
		// 补全数据：将参数uid封装到参数address中
		address.setUid(uid);
		// 补全数据：将参数username封装为参数address中的createdUser和modifiedUser属性值
		address.setCreatedUser(username);
		address.setModifiedUser(username);
		// 补全数据：创建当前时间对象，封装为参数address中的createdTime和modifiedTime属性值
		Date now = new Date();
		address.setCreatedTime(now);
		address.setModifiedTime(now);
		// 补全数据：省、市、区的名称
		District province = districtService.getByCode(address.getProvinceCode());
		if (province != null) {
			address.setProvinceName(province.getName());
		}
		District city = districtService.getByCode(address.getCityCode());
		if (city != null) {
			address.setCityName(city.getName());
		}
		District area = districtService.getByCode(address.getAreaCode());
		if (area != null) {
			address.setAreaName(area.getName());
		}

		// 执行插入数据，并获取返回值
		save(address);
	}

	@Override
	public List<Address> getByUid(Integer uid) {
		List<Address> list = findByUid(uid);
		for (Address address : list) {
			address.setCreatedUser(null);
			address.setCreatedTime(null);
			address.setModifiedUser(null);
			address.setModifiedTime(null);
		}
		return list;
	}

	@Override
	@Transactional
	public void setDefault(Integer aid, Integer uid, String username) {
		// 根据参数aid查询收货地址数据
		Address result = findByAid(aid);
		// 判断查询结果是否为null
		if (result == null) {
			// 是：抛出AddressNotFoundException
			throw new AddressNotFoundException(
				"收货地址数据不存在，可能已经被删除");
		}

		// 判断查询结果中的uid和参数uid是否不一致，使用equals()而不要使用!=
		if (!result.getUid().equals(uid)) {
			// 是：抛出AccessDeniedException
			throw new AccessDeniedException("非法访问");
		}

		// 将当前用户的所有收货地址设置为非默认
		updateNonDefault(uid);

		// 将指定的收货地址设置为默认
		updateDefault(aid, username, new Date());
	}

	@Override
	@Transactional
	public void delete(Integer aid, Integer uid, String username) {
		// 根据参数aid查询收货地址数据
		Address result = findByAid(aid);
		// 判断查询结果是否为null：AddressNotFoundException
		if (result == null) {
			throw new AddressNotFoundException(
				"尝试访问的收货地址数据不存在，可能已经被删除");
		}
				
		// 判断查询结果中的uid与参数uid是否不匹配：AccessDeniedException
		if (!result.getUid().equals(uid)) {
			throw new AccessDeniedException("非法访问");
		}

		// 执行删除，并获取返回值
		deleteByAid(aid);
		
		// 判断查询结果中的isDefault是否为0
		if (result.getIsDefault().equals(0)) {
			// 删除的不是默认收货地址，可直接结束：return;
			return;
		}

		// 调用countByUid(uid)统计当前用户的收货地址数量
		Integer count = countByUid(uid);
		// 判断数量是否为0
		if (count == 0) {
			// 当前用户已经没有收货地址，可直接结束：return;
			return;
		}

		// 调用findLastModified(uid)找出最后修改的收货地址
		Address lastModified = findLastModified(uid);
		// 基于以上查询结果中的aid，将该收货地址设置为默认，并获取返回值
		Integer lastModifedAid = lastModified.getAid();
		updateDefault(lastModifedAid, username, new Date());
	}

	@Override
	public Address getByAid(Integer aid) {
		Address result = findByAid(aid);
		if (result == null) {
			throw new AddressNotFoundException("尝试访问的收货地址数据不存在");
		}
		result.setCreatedUser(null);
		result.setCreatedTime(null);
		result.setModifiedUser(null);
		result.setModifiedTime(null);
		return result;
	}
	
	/**
	 * 插入收货地址数据
	 * @param address 收货地址数据
	 */
	private void save(Address address) {
		Integer rows = addressMapper.save(address);
		if (rows != 1) {
			throw new InsertException("增加收货地址数据时出现未知错误");
		}
	}
	
	/**
	 * 根据收货地址数据的id删除收货地址
	 * @param aid 收货地址数据的id
	 */
	private void deleteByAid(Integer aid) {
		Integer rows = addressMapper.deleteByAid(aid);
		if (rows != 1) {
			throw new DeleteException("删除收货地址数据时出现未知错误");
		}
	}
	
	/**
	 * 将指定的收货地址设置为默认
	 * @param aid 收货地址数据的id
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 */
	private void updateDefault(Integer aid, String modifiedUser, Date modifiedTime) {
		Integer rows = addressMapper.updateDefault(aid, modifiedUser, modifiedTime);
		if (rows != 1) {
			throw new UpdateException("更新数据时出现未知错误[2]");
		}
	}
	
	/**
	 * 将某用户的所有收货地址设置为非默认
	 * @param uid 用户id
	 */
	private void updateNonDefault(Integer uid) {
		Integer rows = addressMapper.updateNonDefault(uid);
		if (rows < 1) {
			throw new UpdateException("更新数据时出现未知错误[1]");
		}
	}
	
	/**
	 * 根据用户id统计该用户的收货地址数据的数量
	 * @param uid 用户id
	 * @return 该用户的收货地址数据的数量
	 */
	private Integer countByUid(Integer uid) {
		return addressMapper.countByUid(uid);
	}
	
	/**
	 * 查询某用户的收货地址列表
	 * @param uid 用户id
	 * @return 该用户的收货地址列表
	 */
	private List<Address> findByUid(Integer uid) {
		return addressMapper.findByUid(uid);
	}
	
	/**
	 * 根据收货地址数据的id查询收货地址详情
	 * @param aid 收货地址数据的id
	 * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
	 */
	private Address findByAid(Integer aid) {
		return addressMapper.findByAid(aid);
	}
	
	/**
	 * 查询某用户最后修改1条收货地址
	 * @param uid 用户的id
	 * @return 该用户的最后修改1条收货地址
	 */
	private Address findLastModified(Integer uid) {
		return addressMapper.findLastModified(uid);
	}

	
}








