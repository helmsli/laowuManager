package com.xinwei.workflow.hessianservice;

import java.util.List;
import java.util.Map;

import com.xinwei.workflow.entity.BusinessProcess;

/**
 * Activiti流程相关服务（Hessian）
 * @author xuejinku
 *
 */
public interface ProcessHessianService {
	
	/**
	 * 启动一个流程实例
	 * @param processDefinitionKey 流程定义key
	 * @param tenantId 租户Id（标识业务类型）
	 * @param dataId 业务数据Id
	 * @param userId  启动流程的用户ID
	 * @param variables 设置流程中需要用的流程变量
	 * @return ProcessInstanceId 流程实例Id
	 */
	String startProcess(String processDefinitionKey,Integer tenantId,String dataId,
			Long userId, Map<String, Object> variables);
	
	/**
	 * 根据业务类型ID、业务数据ID获取业务流程关联对象
	 * @param tenantId 业务类型ID
	 * @param dataId 业务数据ID
	 * @param language 语言 （zh、en、es等）
	 * @return
	 */
	BusinessProcess getProcessByTenantIdAndDataId(Integer tenantId, String dataId, String language);
	
	/**
	 * 根据业务数据Id查询相应的流程实例中的所有流程变量
	 * @param tenantId 租户Id（标识业务类型）
	 * @param dataId 业务数据Id
	 * @return
	 */
	Map<String,Object> getVariables(String processInstanceId);
	
	/**
	 * 根据业务类型ID统计
	 * @param tenantId 业务类型ID
	 * @return
	 */
	Long countProcessByTenantId(Integer tenantId);
	
	/**
	 * 根据业务类型ID分页查询流程实例
	 * @param tenantId 业务类型ID
	 * @param language 语言 （zh、en、es等）
	 * @param startRow 起始记录位置，pageSize:每页记录数
	 * @return
	 */
	List<BusinessProcess> getProcessByTenantId(Integer tenantId, String language,int startRow,int pageSize);
	
	/**
	 * 根据业务类型ID和流程状态统计
	 * @param tenantId 业务类型ID
	 * @param status start：正在进行中，end:已经完成的
	 * @return
	 */
	Long countProcessByTenantIdAndStatus(Integer tenantId,String status);
	
	/**
	 * 根据业务类型ID分页查询流程实例
	 * @param tenantId 业务类型ID
	 * @param status start：正在进行中，end:已经完成的
	 * @param language 语言 （zh、en、es等）
	 * @param startRow 起始记录位置，pageSize:每页记录数
	 * @return
	 */
	List<BusinessProcess> getProcessByTenantIdAndStatus(Integer tenantId, String status, String language,int startRow,int pageSize);
	
	/**
	 * 根据流程创建者ID统计
	 * @param userId 流程创建者ID
	 * @return
	 */
	Long countProcessByUserId(Long userId);
	
	/**
	 * 根据流程创建者ID分页查询流程实例
	 * @param userId 流程创建者ID
	 * @param language 语言 （zh、en、es等）
	 * @param startRow 起始记录位置，pageSize:每页记录数
	 * @return
	 */
	List<BusinessProcess> getProcessByUserId(Long userId, String language, int startRow,int pageSize);
	
	/**
	 * 根据创建者Id和流程状态进行统计
	 * @param userId 流程创建者ID
	 * @param status start：正在进行中，end:已经完成的
	 * @return
	 */
	Long countProcessByUserIdAndStatus(Long userId, String status);
	
	/**
	 * 根据创建者Id和流程状态进行分页查询
	 * @param userId 流程创建者ID
	 * @param status start：正在进行中，end:已经完成的
	 * @param language 语言 （zh、en、es等）
	 * @param startRow 起始记录位置，pageSize:每页记录数
	 * @return
	 */
	List<BusinessProcess> getProcessByUserIdAndStatus(Long userId, String status, String language,int startRow,int pageSize);

	/**
	 * 根据流程实例Id获取流程信息
	 * @param processInstanceId 流程实例Id
	 * @param language 语言
	 * @return
	 */
	BusinessProcess getProcessByProcInstId(String processInstanceId, String language);
	/**
	 * 删除一个正在进行中的流程实例
	 * @param processInstanceId 流程实例ID
	 * @param deleteReason 删除原因
	 */
	void deleteActiveProcessInstance(String processInstanceId, String deleteReason);
	
	/**
	 * 删除一个已完成的流程实例
	 * @param processInstanceId 流程实例ID
	 */
	void deleteFinishedProcessInstance(String processInstanceId);

	/**
	 * 获取流程状态信息
	 * @param processInstanceId 流程实例ID 
	 * @param language 语言 （zh、en、es等）
	 * @return StateInfo对象json串
	 */
	String getStateInfoByProcInstId(String processInstanceId, String language);
}