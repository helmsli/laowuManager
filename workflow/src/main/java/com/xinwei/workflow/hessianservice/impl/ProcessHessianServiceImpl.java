package com.xinwei.workflow.hessianservice.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.xinwei.workflow.constant.ProcessConstants;
import com.xinwei.workflow.dao.BusinessProcessMapper;
import com.xinwei.workflow.dao.CustomActivityNameMapper;
import com.xinwei.workflow.entity.Assignee;
import com.xinwei.workflow.entity.BusinessProcess;
import com.xinwei.workflow.entity.StateInfo;
import com.xinwei.workflow.hessianservice.ProcessHessianService;
import com.xinwei.workflow.service.ProcessService;

@Service
public class ProcessHessianServiceImpl implements ProcessHessianService {

	@Resource
	private TaskService taskService; //Activiti中用户任务服务
	@Resource
	private ProcessService processServiceImpl;//流程服务
	@Resource
	private BusinessProcessMapper businessProcessMapper;//业务与流程DAO
	@Resource
	private CustomActivityNameMapper customActivityNameMapper;//自定义流程节点名Dao
	@Override
	public String startProcess(String processDefinitionKey, Integer tenantId,
			String dataId, Long userId, Map<String, Object> variables) {
		//启动流程
		ProcessInstance processInstance = processServiceImpl
				.startProcess(processDefinitionKey, tenantId.toString(), 
				dataId, userId.toString(), variables);
		//获取流程实例
		HistoricProcessInstance hpi = processServiceImpl
				.getHistoryProcessInstance(processInstance.getId());
		//建立一条业务数据与流程的关联信息
		BusinessProcess record = new BusinessProcess();
		record.setTenantId(tenantId);
		record.setDataId(dataId);
		record.setUserId(userId);
		record.setStartTime(hpi.getStartTime());
		
		record.setStateInfo(null);
		record.setStatus(ProcessConstants.State.START);
		record.setProcInstId(processInstance.getId());
		businessProcessMapper.insert(record);
		
		return processInstance.getId();
	}
	
	@Override
	public BusinessProcess getProcessByTenantIdAndDataId(Integer tenantId,
			String dataId, String language) {
		Map<String,Object> queryMap = new ConcurrentHashMap<String,Object>();
		queryMap.put("tenantId", tenantId);
		queryMap.put("dataId", dataId);
		List<BusinessProcess> businessProcessList = businessProcessMapper.selectByConditions(queryMap);
		BusinessProcess bp =null;
		if(null != businessProcessList && !businessProcessList.isEmpty()){
			businessProcessList.get(0);
		}
		return bp;
	}

	@Override
	public Map<String, Object> getVariables(String processInstanceId) {
		// 获取当前流程中的所有流程变量
		Map<String, Object> variables = processServiceImpl
				.getVariables(processInstanceId);
		return variables;
	}
	
	@Override
	public Long countProcessByTenantId(Integer tenantId) {
		Map<String,Object> queryMap = new ConcurrentHashMap<String,Object>();
		queryMap.put("tenantId", tenantId);
		return businessProcessMapper.countByConditions(queryMap);
	}

	@Override
	public List<BusinessProcess> getProcessByTenantId(Integer tenantId,
			String language, int startRow, int pageSize) {
		Map<String,Object> queryMap = new ConcurrentHashMap<String,Object>();
		queryMap.put("tenantId", tenantId);
		queryMap.put("startRow", startRow);
		queryMap.put("pageSize", pageSize);
		List<BusinessProcess> businessProcessList = businessProcessMapper.selectByConditionsAndPage(queryMap);
		// 设置流程状态信息
		for(BusinessProcess bp : businessProcessList){
			bp.setStateInfo(getStateInfoByProcInstId(bp.getProcInstId(),language));
		}
		return businessProcessList;
	}

	@Override
	public Long countProcessByTenantIdAndStatus(Integer tenantId, String status) {
		Map<String,Object> queryMap = new ConcurrentHashMap<String,Object>();
		queryMap.put("tenantId", tenantId);
		queryMap.put("status", status);
		return businessProcessMapper.countByConditions(queryMap);
	}

	@Override
	public List<BusinessProcess> getProcessByTenantIdAndStatus(
			Integer tenantId, String status, String language, int startRow,
			int pageSize) {
		Map<String,Object> queryMap = new ConcurrentHashMap<String,Object>();
		queryMap.put("tenantId", tenantId);
		queryMap.put("status", status);
		queryMap.put("startRow", startRow);
		queryMap.put("pageSize", pageSize);
		List<BusinessProcess> businessProcessList = businessProcessMapper.selectByConditionsAndPage(queryMap);
		// 设置流程状态信息
		for(BusinessProcess bp : businessProcessList){
			bp.setStateInfo(getStateInfoByProcInstId(bp.getProcInstId(),language));
		}
		return businessProcessList;
	}

	@Override
	public Long countProcessByUserId(Long userId) {
		Map<String,Object> queryMap = new ConcurrentHashMap<String,Object>();
		queryMap.put("userId", userId);
		return businessProcessMapper.countByConditions(queryMap);
	}

	@Override
	public List<BusinessProcess> getProcessByUserId(Long userId,
			String language, int startRow, int pageSize) {
		
		Map<String,Object> queryMap = new ConcurrentHashMap<String,Object>();
		queryMap.put("userId", userId);
		queryMap.put("startRow", startRow);
		queryMap.put("pageSize", pageSize);
		List<BusinessProcess> businessProcessList = businessProcessMapper.selectByConditionsAndPage(queryMap);
		// 设置流程状态信息
		for(BusinessProcess bp : businessProcessList){
			bp.setStateInfo(getStateInfoByProcInstId(bp.getProcInstId(),language));
		}
		return businessProcessList;
	}

	@Override
	public Long countProcessByUserIdAndStatus(Long userId, String status) {
		Map<String,Object> queryMap = new ConcurrentHashMap<String,Object>();
		queryMap.put("userId", userId);
		queryMap.put("status", status);
		return businessProcessMapper.countByConditions(queryMap);
	}

	@Override
	public List<BusinessProcess> getProcessByUserIdAndStatus(Long userId,
			String status, String language, int startRow, int pageSize) {
		Map<String,Object> queryMap = new ConcurrentHashMap<String,Object>();
		queryMap.put("userId", userId);
		queryMap.put("status", status);
		queryMap.put("startRow", startRow);
		queryMap.put("pageSize", pageSize);
		List<BusinessProcess> businessProcessList = businessProcessMapper.selectByConditionsAndPage(queryMap);
		// 设置流程状态信息
		for(BusinessProcess bp : businessProcessList){
			bp.setStateInfo(getStateInfoByProcInstId(bp.getProcInstId(),language));
		}
		return businessProcessList;
	}


	@Override
	public BusinessProcess getProcessByProcInstId(String processInstanceId,
			String language) {
		BusinessProcess bp = businessProcessMapper.selectByProcInstId(processInstanceId);
		if(null != bp){
			//设置流程状态信息
			bp.setStateInfo(getStateInfoByProcInstId(bp.getProcInstId(),language));
		}
		return bp;
	}

	@Override
	public void deleteActiveProcessInstance(String processInstanceId,
			String deleteReason) {
		processServiceImpl.deleteActiveProcessInstance(processInstanceId, deleteReason);
		
	}

	@Override
	public void deleteFinishedProcessInstance(String processInstanceId) {
		
		processServiceImpl.deleteFinishedProcessInstance(processInstanceId);
	}

	
	@Override
	public String getStateInfoByProcInstId(String procInstId,String language){
		StateInfo stateInfo = new StateInfo();
		//状态名称
		String stateName = null;
		//流程定义Id
		String processDefinitionId = null;
		//任务Id
		String taskId = null;
		//办理人列表
		List<Assignee> assigneeList = null;
		// 活动Id
		String activityId = null; 
		// 获取流程当前的用户任务
		List<Task> taskList = taskService.createTaskQuery()
				.processInstanceId(procInstId).active()
				.orderByTaskCreateTime().desc()
				.list();
		// 如果存在当前用户任务
		if(null != taskList && !taskList.isEmpty()){
			Task currentTask = taskList.get(0);
			stateName = currentTask.getName();
			activityId = currentTask.getTaskDefinitionKey();
			processDefinitionId = currentTask.getProcessDefinitionId();
			//获取任务Id和任务办理人信息
			taskId = currentTask.getId();
			assigneeList = 	processServiceImpl.buildAssigneeByTaskId(taskId);
		}else{
			//当前用户任务为空，获取最近的历史活动节点
			HistoricActivityInstance historicActivity = processServiceImpl.getHistoryActivity(procInstId);
			stateName = historicActivity.getActivityName();
			activityId = historicActivity.getActivityId();
			processDefinitionId = historicActivity.getProcessDefinitionId();
		}
		
		String processDefinitionKey = null;//流程定义key
		String customStateName = null;//自定义的stateName
		if(StringUtils.isNotBlank(processDefinitionId)){
			processDefinitionKey = processDefinitionId.split(":")[0];
			//获取自定义的stateName
			customStateName = customActivityNameMapper.selectName(processDefinitionKey, activityId, language);
		}
		if (StringUtils.isNotBlank(customStateName)) {
			stateName = customStateName;
		} 
		
		stateInfo.setStateName(stateName);
		stateInfo.setTaskId(taskId);
		stateInfo.setAssigneeList(assigneeList);
	
		return stateInfo.toString();
	}

}
