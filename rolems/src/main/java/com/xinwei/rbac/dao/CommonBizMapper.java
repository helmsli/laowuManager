package com.xinwei.rbac.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xinwei.rbac.entity.CommonBiz;

public interface CommonBizMapper {
	int insert(CommonBiz record);

	int deleteByPrimaryKey(Long dataId);

	int updateByPrimaryKey(CommonBiz record);

    Long countAll();

    List<CommonBiz> selectAll(@Param("startRow") int startRow, @Param("pageSize")int pageSize);

	CommonBiz getByDataId(Long dataId);

	Long countByConditions(@Param("groupId") String groupId,
			@Param("serviceType") String serviceType, @Param("startRow") int startRow, @Param("pageSize")int pageSize);
	
	List<CommonBiz> getCommonBizByGIdAndServiceType(@Param("groupId") String groupId,
			@Param("serviceType") String serviceType, @Param("startRow") int startRow, @Param("pageSize")int pageSize);

	void updateProcessIdByDataId(@Param("processInstanceId") String processInstanceId, @Param("dataId") Long dataId);

	List<CommonBiz> getLatestBizByGroupId(String groupId);

	List<CommonBiz> getBizListByGIdAndServiceType(@Param("groupId") String groupId,
			@Param("serviceType") String serviceType);

	List<CommonBiz> getByGroupId(String groupId);
	
	void updateStatusByDataId(@Param("status") String status,
			@Param("dataId") Long dataId);

	List<CommonBiz> getBizListByCommonCondition(Map<String, Object> queryMap);

	List<CommonBiz> getLatestBizByGroupIdAndStatus(@Param("groupId") String groupId, @Param("status") String status);
}