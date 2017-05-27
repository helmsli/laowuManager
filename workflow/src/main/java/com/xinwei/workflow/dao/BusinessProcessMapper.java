package com.xinwei.workflow.dao;

import com.xinwei.workflow.entity.BusinessProcess;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 业务流程关联数据DAO
 * @author xuejinku
 *
 */
public interface BusinessProcessMapper {
	
	/**
	 * 插入一条记录
	 * @param record
	 * @return
	 */
    int insert(BusinessProcess record);
    
    /**
     * 根据条件删除
     * @param queryMap （map中参数的key对应BusinessProcess的属性）
     */
    void deleteByConditions(Map<String, Object> queryMap);

    /**
     * 根据类型Id,业务数据Id修改流程实例Id
     * @param tenantId
     * @param dataId
     */
	void updateProcInstIdByTenantAndDataId(@Param("procInstId")String procInstId, @Param("tenantId")Integer tenantId,@Param("dataId")String dataId);
    
    /**
     * 根据类型Id,业务数据Id修改流程任务信息
     * @param tenantId
     * @param dataId
     */
	void updateStateInfoByTenantAndDataId(@Param("stateInfo")String stateInfo, @Param("tenantId")Integer tenantId,@Param("dataId")String dataId);
	
	/**
	 * 根据类型Id,业务数据Id修改流程状态信息
	 * @param tenantId
	 * @param dataId
	 */
	void updateStatusByTenantAndDataId(@Param("status")String status, @Param("tenantId")Integer tenantId,@Param("dataId")String dataId);
	
	/**
	 * 查询所有
	 * @return
	 */
    List<BusinessProcess> selectAll();
    
    /**
     * 根据查询条件进行统计
     * @param queryMap （map中参数的key对应BusinessProcess的属性）
     * @return 记录数
     */
	Long countByConditions(Map<String, Object> queryMap);
    
	/**
	 * 根据查询条件进行统计
     * @param queryMap （map中参数的key对应BusinessProcess的属性）
	 * @return
	 */
    List<BusinessProcess> selectByConditions(Map<String, Object> queryMap);
    
    /**
	 * 根据查询条件进行查询(分页)
	 * @param queryMap （map中参数的key对应BusinessProcess的属性，分页参数：startRow 起始行，pageSize 每页记录数）
	 * @return 
	 */
	List<BusinessProcess> selectByConditionsAndPage(
			Map<String, Object> queryMap);

	/**
	 * 根据流程实例Id获取流程信息
	 * @param procInstId 流程实例Id
	 * @return
	 */
	BusinessProcess selectByProcInstId(String procInstId);
    
}