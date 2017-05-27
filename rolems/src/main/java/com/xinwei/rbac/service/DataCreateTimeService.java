package com.xinwei.rbac.service;

import java.util.List;

import com.xinwei.rbac.entity.DataCreateTime;

public interface DataCreateTimeService{
	
	/**
	 * 保存
	 */
	Long save(DataCreateTime dataCreateTime);

	/**
	 * 删除
	 */
	void delete(Long dataId);
	
	/**
	 * 修改
	 */
	void update(DataCreateTime dataCreateTime);
	
	/**
	 * 获取ById
	 */
	DataCreateTime selectByPrimaryKey(Long dataId) ;
	/**
	 * 分页查询某时间阶段所有
	 */
	List<DataCreateTime> selectAll(int startRow, int pageSize);

	/**
	 * 分页查询某时间阶段所有时间段
	 */
	List<DataCreateTime> selectByBetweenTime(String beginTime,String endTime,int startRow, int pageSize);
    
}
