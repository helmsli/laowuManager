package com.xinwei.fiber.dao;

import com.xinwei.fiber.entity.ApplicationInfo;

import java.util.List;
import java.util.Map;

public interface ApplicationInfoMapper {
    
	/**
	 * 插入一条记录
	 * @param record 
	 * @return
	 */
	int insert(ApplicationInfo record);

	/**
	 * 根据查询条件删除
	 * @param queryMap 查询条件
	 */
	void deleteByCondition(Map<String,Object> queryMap);
	
	/**
	 * 根据查询条件进行统计
	 * @param queryMap 查询条件
	 * @return
	 */
	Long countByCondition(Map<String,Object> queryMap);
	
	/**
	 * 根据查询条件获取申请信息对象集合
	 * @param queryMap 查询条件
	 * @return
	 */
	List<ApplicationInfo> selectByCondition(Map<String,Object> queryMap);
	
	/**
	 * 根据查询条件查询
	 * @param queryMap 查询条件
	 * @return 流程实例ID集合
	 */
    List<String> selectProcInstIdByCondition(Map<String,Object> queryMap);
    
    /**
	 * 根据dataId查询
	 * @param dataId 数据Id
	 * @return 
	 */
    ApplicationInfo selectByDataId(String dataId);
}