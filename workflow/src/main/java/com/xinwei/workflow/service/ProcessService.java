package com.xinwei.workflow.service;

import java.util.List;
import java.util.Map;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import com.xinwei.workflow.entity.Assignee;

/**
 * Activiti流程相关服务
 * @author xuejinku
 *
 */
public interface ProcessService {
	/**
	 * 启动一个流程实例
	 * @param processDefinitionKey 流程定义key
	 * @param tenantId 租户Id（标识业务类型）
	 * @param dataId 业务数据Id
	 * @param userId  启动流程的用户ID
	 * @param variables 设置流程中需要用的流程变量
	 * @return
	 */
	ProcessInstance startProcess(String processDefinitionKey,String tenantId,String dataId,
			String userId, Map<String, Object> variables);
	
	/**
	 * 根据流程实例Id获取某个正在执行的流程实例
	 * @param processInstanceId 流程实例Id
	 * @return
	 */
	ProcessInstance getProcessInstanceById(String processInstanceId);

	/**
	 * 根据流程实例Id获取某个正在执行的流程实例
	 * @param processInstanceId 流程实例Id
	 * @return
	 */
	ProcessInstance getProcessInstanceByBusinessKey(String businessKey);
	
	/**
	 * 查询历史流程实例
	 * @param processInstanceId 流程实例ID
	 * @return 历史流程实例对象
	 */
	HistoricProcessInstance getHistoryProcessInstance(
			String processInstanceId);
	
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
	 * 根据流程实例ID获取业务key
	 * @param processInstanceId
	 * @return
	 */
	String getBusinessKeyByByProcessInstanceId(String processInstanceId);
	
	/*
	 * 获取流程中相关数据
	 */
	
	/**
	 * 查询某个流程实例最近的历史活动节点
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	HistoricActivityInstance getHistoryActivity(
			String processInstanceId);
	
	/**
	 * 查询某个流程实例已经历的所有活动
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	List<HistoricActivityInstance> getHistoryActivities(
			String processInstanceId);
	
	/**
	 * 查询某个流程实例的所有流程变量
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	List<HistoricVariableInstance> getProcessVariables(
			String processInstanceId);
	/**
	 * 查询某个流程实例的执行过程中的所有流程变量
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	Map<String,Object> getVariables(String processInstanceId);
	
	/**
	 * 根据TaskId 获取用户任务待办人信息
	 * @param taskId 任务ID
	 */
	List<IdentityLink> getIdentityLinkOfTask(String taskId);
	
	/**
	 * 根据用户ID构建办理人信息
	 */
	List<Assignee> buildAssigneeByUserId (String userId);
	
	/**
	 * 根据正在进行的任务Id构建办理人信息
	 */
	List<Assignee> buildAssigneeByTaskId (String taskId);
}