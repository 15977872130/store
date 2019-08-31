package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.District;

/**
 * 处理省/市/区数据的业务层接口
 */
public interface IDistrictService {

	/**
	 * 根据父级的行政代号获取全国所有的省/某省所有的市/某市所有的区的列表
	 * @param parent 父级的行政代号，如果需要获取全国所有的省，则使用"86"
	 * @return 全国所有的省/某省所有的市/某市所有的区的列表
	 */
	List<District> getByParent(String parent);
	
	/**
	 * 根据省/市/区的行政代号获取详情
	 * @param code 省/市/区的行政代号
	 * @return 匹配的省/市/区的详情，如果没有匹配的数据，则返回null
	 */
	District getByCode(String code);
	
}













