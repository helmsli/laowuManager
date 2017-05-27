package com.xinwei.rbac.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.xinwei.rbac.entity.CommonBiz;

public interface CommonBizService {

	/**
	 * 保存
	 */
	Long save(CommonBiz CommonBiz);

	/**
	 * 删除
	 */
	void deleteByDataId(Long dataId);

	/**
	 * 修改
	 */
	void update(CommonBiz CommonBiz);

	/**
	 * 根据dataId修改processInstanceId
	 */
	void updateProcessIdByDataId(String processInstanceId, Long dataId);

	/**
	 * 按主键查询
	 */
	CommonBiz getByDataId(Long dataId);

	Long countAll();

	/**
	 * 获取所有
	 */
	List<CommonBiz> getAll(int startRow, int pageSize);

	Long countByConditions(String groupId, String serviceType, int startRow,
			int pageSize);

	/**
	 * 根据组ID和业务类型查询
	 * 
	 * @param groupId
	 * @param serviceType
	 * @param startRow
	 *            起始索引
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	List<CommonBiz> getCommonBizByGIdAndServiceType(String groupId,
			String serviceType, int startRow, int pageSize);

	/**
	 * 工单编号格式为日期+四位，如201704190001
	 * 
	 * @param serviceType
	 * @return groupId
	 */
	String generateGroupId();

	/**
	 * 生成DataId 便于分区
	 * 
	 * @return dataId
	 * @throws ParseException 
	 */
	Long generateDataId();

	/**
	 * 按groupId查询,按serviceType分组，组内按创建日期降序排序，获取第一条
	 * 
	 * @param groupId
	 * @return
	 */
	List<CommonBiz> getLatestBizByGroupId(String groupId);
	
	/**
	 * 按groupId和status查询,按serviceType分组，组内按创建日期降序排序，获取第一条
	 * @param groupId
	 * @param status
	 * @return
	 */
	List<CommonBiz> getLatestBizByGroupIdAndStatus(String groupId,String status);

	/**
	 * 根据组ID和业务类型查询不带分页
	 * 
	 * @param groupId
	 * @param serviceType
	 * @return
	 */
	List<CommonBiz> getBizListByGIdAndServiceType(String groupId,
			String serviceType);

	/**
	 * 根据组ID查询不带分页
	 * 
	 * @param groupId
	 * @return
	 */
	List<CommonBiz> getByGroupId(String groupId);

	/**
	 * 根据业务数据Id修改状态
	 * 
	 * @param status
	 * @param dataId
	 */
	void updateStatusByDataId(String status, Long dataId);

	void updateCurrentValueBySeqName(String seqName);

	/**
	 * 根据组ID(groupId)、业务类型(serviceType)、dataId(Long)，status、result查询不带分页
	 * @param queryMap
	 * @return
	 */
	List<CommonBiz> getBizListByCommonCondition(Map<String, Object> queryMap);
}
