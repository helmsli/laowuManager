package com.xinwei.fiber.service;

import java.util.List;
import java.util.Map;

import com.xinwei.fiber.entity.ApplicationInfo;
import com.xinwei.util.page.Page;


public interface ApplicationInfoService{
	
	/**
	 * 保存
	 */
	void save(ApplicationInfo record);

	/**
	 * 根据查询条件删除
	 * @param queryMap 查询条件
	 */
	void deleteByCondition(Map<String,Object> queryMap);
	
	/**
	 * 根据dataId获取申请信息对象
	 * @param dataId 数据Id
	 * @return
	 */
	ApplicationInfo getByDataId(String dataId);
	
	/**
	 * 根据查询条件分页获取申请信息对象
	 * @param queryMap 查询条件
	 * @return
	 */
	Page<ApplicationInfo> getByCondition(Map<String,Object> queryMap);
	 
	/**
	 * 根据查询条件获取流程实例ID集合
	 * @param queryMap 查询条件
	 * @return 流程实例ID集合
	 */
	List<String> getProcInstIdByCondition(Map<String,Object> queryMap);
}
