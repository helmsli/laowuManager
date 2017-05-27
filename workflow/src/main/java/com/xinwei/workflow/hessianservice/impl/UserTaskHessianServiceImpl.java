package com.xinwei.workflow.hessianservice.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xinwei.workflow.constant.ProcessConstants;
import com.xinwei.workflow.dao.CustomActivityNameMapper;
import com.xinwei.workflow.entity.Assignee;
import com.xinwei.workflow.entity.UserTask;
import com.xinwei.workflow.hessianservice.UserTaskHessianService;
import com.xinwei.workflow.service.ProcessService;

@Service
public class UserTaskHessianServiceImpl implements UserTaskHessianService {

	private Logger logger = LoggerFactory.getLogger(UserTaskHessianServiceImpl.class);
	@Resource
	private TaskService taskService; //Activiti中用户任务服务
	@Resource
	private HistoryService historyService;// 与历史数据（历史表）相关的Service
	@Resource
	private ProcessService processServiceImpl;//流程相关服务
	@Resource
	private CustomActivityNameMapper customActivityNameMapper;//自定义流程节点名Dao
	
	
	@Override
	public List<UserTask> getActiveTasksByUid(String userId, String language, String sortDirection) {
		logger.debug("getActiveTasksByUid--> userId: {} , language: {}, sortDirection: {}" ,
				userId, language, sortDirection);
		//用户任务列表
		List<Task> taskList = null;
		if(ProcessConstants.SortDirection.ASC.equalsIgnoreCase(sortDirection)){
			// 使用综合查询接口
			taskList = taskService.createTaskQuery()
					.taskCandidateOrAssigned(userId)
					.orderByTaskCreateTime().asc()
					.list();
		}else{
			// 使用综合查询接口
			taskList = taskService.createTaskQuery()
					.taskCandidateOrAssigned(userId)
					.orderByTaskCreateTime().desc()
					.list();
		}
		return  buildUserTaskList(taskList, language);
		
	}
	
	@Override
	public List<UserTask> getFinishedTasksByUid(String userId, String language, String sortDirection) {
		logger.debug("getFinishedTasksByUid--> userId: {} , language: {}, sortDirection: {}" ,
				userId, language, sortDirection);
		
		List<HistoricTaskInstance> historicTaskList = null;
		if(ProcessConstants.SortDirection.ASC.equalsIgnoreCase(sortDirection)){
			historicTaskList = historyService
		            .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
		            .taskAssignee(userId)
		            .finished()
		            .orderByHistoricTaskInstanceEndTime().asc()
		            .list();
		}else{
			
			historicTaskList = historyService
					.createHistoricTaskInstanceQuery()// 创建历史任务实例查询
					.taskAssignee(userId)
					.finished()
					.orderByHistoricTaskInstanceEndTime().desc()
					.list();
		}
		// 构造UserTask对象列表
		return buildUserTaskListByHistoric(historicTaskList, language);
	}

	@Override
	public Long countActiveTasksByUid(String userId) {
		logger.debug("countActiveTasksByUid--> userId: {} ", userId);
		Long count = taskService.createTaskQuery()
				.taskCandidateOrAssigned(userId)
				.count();
		return count;
	}

	@Override
	public List<UserTask> getActiveTaskListByUid(String userId,
			String language, String sortDirection, int startRow, int pageSize) {
		List<Task> taskList = null;
		
		if(ProcessConstants.SortDirection.ASC.equalsIgnoreCase(sortDirection)){
			// 使用综合查询接口
			taskList = taskService
					.createTaskQuery()
					.taskCandidateOrAssigned(userId)
					.orderByTaskCreateTime().asc()
					.listPage(startRow, pageSize);// 分页获取

		}else{
			// 使用综合查询接口
			taskList = taskService
					.createTaskQuery()
					.taskCandidateOrAssigned(userId)
					.orderByTaskCreateTime().desc()
					.listPage(startRow, pageSize);// 分页获取
		}
		return buildUserTaskList(taskList, language);
	}

	@Override
	public Long countFinishedTasksByUid(String userId) {
		Long count = historyService
	            .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
	            .taskAssignee(userId)
	            .finished()
	            .count();
		 return count;
	}

	@Override
	public List<UserTask> getFinishedTaskListByUid(String userId,
			String language, String sortDirection, int startRow, int pageSize) {
		List<HistoricTaskInstance> historicTaskList = null;
		if(ProcessConstants.SortDirection.ASC.equalsIgnoreCase(sortDirection)){
			historicTaskList = historyService
		            .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
		            .taskAssignee(userId)
		            .finished()
		            .orderByHistoricTaskInstanceEndTime().asc()
		            .listPage(startRow, pageSize);
		}else{
			historicTaskList = historyService
					.createHistoricTaskInstanceQuery()// 创建历史任务实例查询
					.taskAssignee(userId)
					.finished()
					.orderByHistoricTaskInstanceEndTime().desc()
					.listPage(startRow, pageSize);
		}
		// 构造UserTask对象列表
		return buildUserTaskListByHistoric(historicTaskList,language);
	}
	
	@Override
	public Long countActiveTasksByUidProcInstIds(String userId,
			List<String> processInstanceIds) {
		Long count = taskService.createTaskQuery()
				.taskCandidateOrAssigned(userId)
				.processInstanceIdIn(processInstanceIds)
				.count();
		return count;
	}

	@Override
	public List<UserTask> getActiveTaskListByUidProcInstIds(String userId,
			List<String> processInstanceIds, String language, String sortDirection, int startRow,
			int pageSize) {
		List<Task> taskList = null;
		if(ProcessConstants.SortDirection.ASC.equalsIgnoreCase(sortDirection)){
			// 使用综合查询接口
			taskList = taskService
					.createTaskQuery()
					.taskCandidateOrAssigned(userId)
					.processInstanceIdIn(processInstanceIds)
					.orderByTaskCreateTime().asc()
					.listPage(startRow, pageSize);// 分页获取
		}else{
			// 使用综合查询接口
			taskList = taskService
					.createTaskQuery()
					.taskCandidateOrAssigned(userId)
					.processInstanceIdIn(processInstanceIds)
					.orderByTaskCreateTime().desc()
					.listPage(startRow, pageSize);// 分页获取
		}
		return buildUserTaskList(taskList, language);
	}

	@Override
	public Long countFinishedTasksByUidProcInstIds(String userId,
			List<String> processInstanceIds) {
		Long count = historyService
	            .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
	            .taskAssignee(userId)
	            .processInstanceIdIn(processInstanceIds)
	            .finished()
	            .count();
		 return count;
	}

	@Override
	public List<UserTask> getFinishedTaskListByUidProcInstIds(String userId,
			List<String> processInstanceIds, String language, String sortDirection, 
			int startRow, int pageSize) {
		List<HistoricTaskInstance> historicTaskList = null;
		if(ProcessConstants.SortDirection.ASC.equalsIgnoreCase(sortDirection)){
			historicTaskList = historyService
		            .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
		            .taskAssignee(userId)
		            .processInstanceIdIn(processInstanceIds)
		            .finished()
		            .orderByHistoricTaskInstanceEndTime().asc()
		            .listPage(startRow, pageSize);
		}
		historicTaskList = historyService
	            .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
	            .taskAssignee(userId)
	            .processInstanceIdIn(processInstanceIds)
	            .finished()
	            .orderByHistoricTaskInstanceEndTime().desc()
	            .listPage(startRow, pageSize);
		// 构造UserTask对象列表
		return buildUserTaskListByHistoric(historicTaskList,language);
	}
	
	@Override
	public Long countActiveTasksByUidAndTenantId(String userId,String tenantId) {
		
		 Long count = taskService.createTaskQuery()
				.taskCandidateOrAssigned(userId)
				.taskTenantId(tenantId)
				.count();
		return count;
	}
	
	@Override
	public List<UserTask> getActiveTaskListByUidAndTenantId(String userId,String tenantId,String language,
			String sortDirection, int startRow,int pageSize) {
		List<Task> taskList = null;
		if(ProcessConstants.SortDirection.ASC.equalsIgnoreCase(sortDirection)){
			// 使用综合查询接口
			taskList = taskService.createTaskQuery()
					.taskCandidateOrAssigned(userId)
					.taskTenantId(tenantId)
					.orderByTaskCreateTime().asc()
					.listPage(startRow, pageSize);//分页获取
		}else{
			// 使用综合查询接口
			taskList = taskService.createTaskQuery()
					.taskCandidateOrAssigned(userId)
					.taskTenantId(tenantId)
					.orderByTaskCreateTime().desc()
					.listPage(startRow, pageSize);//分页获取
		}
		return buildUserTaskList(taskList, language);
	}
	
	@Override
	public UserTask getTask(String taskId ,String language) {
		UserTask userTask = null;
		//获取任务信息
		TaskInfo taskInfo = getTaskInfoByTaskId(taskId);
		if(null != taskInfo){
			//构造用户任务对象
			userTask = buildUserTaskByTaskInfo(taskInfo, language);
		}
		return userTask;
	}

	@Override
	public String getTaskAssignee(String taskId) {
		String assignee = null;
		//获取任务信息
		TaskInfo taskInfo = getTaskInfoByTaskId(taskId);
		if(null != taskInfo){
			assignee = taskInfo.getAssignee();
		}
		return assignee;
	}
	
	@Override
	public void setTaskAssignee(String taskId, String userId) {
		Task task = taskService
				.createTaskQuery()
				.taskId(taskId)
				.singleResult();
		if(null != task && StringUtils.isNotBlank(userId)){
			taskService.setAssignee(taskId, userId);
		}
	}
	
	@Override
	public void addTaskCandidateUser(String taskId, String userId) {
		Task task = taskService
				.createTaskQuery()
				.taskId(taskId)
				.singleResult();
		if(null != task && StringUtils.isNotBlank(userId)){
			taskService.addCandidateUser(taskId, userId);
		}
		
	}

	@Override
	public void addTaskCandidateGroup(String taskId, String groupId) {
		Task task = taskService
				.createTaskQuery()
				.taskId(taskId)
				.singleResult();
		if(null != task && StringUtils.isNotBlank(groupId)){
			taskService.addCandidateGroup(taskId, groupId);
		}
		
	}
	
	@Override
	public void claimTask(String taskId, String userId) {
		 //任务签收
	     taskService.claim(taskId, userId);
	}

	@Override
	public void completeTask(String taskId, Map<String, Object> variables) {
		//完成任务
		taskService.complete(taskId, variables);
	}

	@Override
	public Long countFinishedTasksByUidAndTenantId(String userId,String tenantId) {
		Long count = historyService
	            .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
	            .taskAssignee(userId)
	            .taskTenantId(tenantId)
	            .finished()
	            .count();
		 return count;
	}
	
	@Override
	public List<UserTask> getFinishedTasksByUidAndTenantId(String userId,String tenantId, 
			String language, String sortDirection, int startRow,int pageSize) {
		logger.debug("getFinishedTasksByUidAndTenantId --> userId : " + userId + " tenantId:  " + tenantId );
		List<HistoricTaskInstance> historicTaskList = null;
		if(ProcessConstants.SortDirection.ASC.equalsIgnoreCase(sortDirection)){
			historicTaskList = historyService
		            .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
		            .taskAssignee(userId)
		            .taskTenantId(tenantId)
		            .finished()
		            .orderByHistoricTaskInstanceEndTime().asc()
		            .listPage(startRow, pageSize);
		}else{
			historicTaskList = historyService
					.createHistoricTaskInstanceQuery()// 创建历史任务实例查询
					.taskAssignee(userId)
					.taskTenantId(tenantId)
					.finished()
					.orderByHistoricTaskInstanceEndTime().desc()
					.listPage(startRow, pageSize);
		}
		// 构造UserTask对象列表
		return buildUserTaskListByHistoric(historicTaskList,language);
	}
	
	
	
	@Override
	public List<UserTask> getActiveTasksByProcessInstanceId(
			String processInstanceId, String language, String sortDirection) {
		List<Task> taskList = null;
		if(ProcessConstants.SortDirection.ASC.equalsIgnoreCase(sortDirection)){
			taskList = taskService.createTaskQuery()
					.processInstanceId(processInstanceId).active()
					.orderByTaskCreateTime().asc()
					.list();
		}
		taskList = taskService.createTaskQuery()
				.processInstanceId(processInstanceId).active()
				.orderByTaskCreateTime().desc()
				.list();
		return buildUserTaskList(taskList, language);
	}
	
	@Override
	public List<UserTask> getFinishedTasksByProcessInstanceId(
			String processInstanceId,String language, String sortDirection) {
		
		List<HistoricTaskInstance> historicTaskList = null;
		if(ProcessConstants.SortDirection.ASC.equalsIgnoreCase(sortDirection)){
			historicTaskList = historyService
					.createHistoricTaskInstanceQuery()
					.processInstanceId(processInstanceId).finished()
					.orderByHistoricTaskInstanceEndTime().asc()
					.list();
		}else{
			historicTaskList = historyService
					.createHistoricTaskInstanceQuery()
					.processInstanceId(processInstanceId).finished()
					.orderByHistoricTaskInstanceEndTime().desc()
					.list();
		}
		// 构造UserTask对象列表
		return buildUserTaskListByHistoric(historicTaskList,language);
	}
	
	@Override
	public List<UserTask> getTasksByDefinitionKey(String processInstanceId,
			String taskDefinitionkey,String language, String sortDirection) {
		List<Task> taskList = null;
		if(ProcessConstants.SortDirection.ASC.equalsIgnoreCase(sortDirection)){
			taskList = taskService.createTaskQuery()
					.processInstanceId(processInstanceId)
					.taskDefinitionKey(taskDefinitionkey)
					.orderByTaskCreateTime().asc()
					.list();
		}else{
			taskList = taskService.createTaskQuery()
					.processInstanceId(processInstanceId)
					.taskDefinitionKey(taskDefinitionkey)
					.orderByTaskCreateTime().desc()
					.list();
		}
		return buildUserTaskList(taskList, language);
	}
	
	@Override
	public Long countTasksByDefinitionKey(String processInstanceId,
			String taskDefinitionkey) {
		Long count = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId)
				.taskDefinitionKey(taskDefinitionkey)
				.count();
		return count;
	}
	
	@Override
	public List<UserTask> getActiveTasksByBusinessKey(
			String businessKey, String language, String sortDirection) {
		List<Task> taskList = null;
		if(ProcessConstants.SortDirection.ASC.equalsIgnoreCase(sortDirection)){
			taskList = taskService.createTaskQuery()
					.processInstanceBusinessKey(businessKey)
					.active()
					.orderByTaskCreateTime().asc()
					.list();
		}else{
			taskList = taskService.createTaskQuery()
					.processInstanceBusinessKey(businessKey)
					.active()
					.orderByTaskCreateTime().desc()
					.list();
		}
		return buildUserTaskList(taskList, language);
	}
	
	@Override
	public List<UserTask> getFinishedTasksByBusinessKey(
			String businessKey, String language, String sortDirection) {
		List<HistoricTaskInstance> historicTaskList = null;
		if(ProcessConstants.SortDirection.ASC.equalsIgnoreCase(sortDirection)){
			historicTaskList = historyService
		            .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
		            .processInstanceBusinessKey(businessKey)
		            .finished()
		            .orderByHistoricTaskInstanceEndTime().asc()
		            .list();
		}else{
			historicTaskList = historyService
		            .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
		            .processInstanceBusinessKey(businessKey)
		            .finished()
		            .orderByHistoricTaskInstanceEndTime().desc()
		            .list();
		}
		// 构造UserTask对象列表
		return buildUserTaskListByHistoric(historicTaskList,language);
	}
	
	
	
	/**
	 * 构建用户任务对象
	 * 
	 * @param taskInfo
	 *            Activiti中任务对象
	 * @return
	 */
	public UserTask buildUserTaskByTaskInfo(TaskInfo taskInfo, String language) {

		UserTask userTask = new UserTask();
		if (null != taskInfo) {
			//获取流程实例Id
			String processInstanceId = taskInfo.getProcessInstanceId();
			//获取businessName
			String businessName = taskInfo.getTenantId();
			//获取业务数据Id（businesskey）
			String dataId = processServiceImpl
					.getBusinessKeyByByProcessInstanceId(processInstanceId);
			
			String taskDefinitionKey = taskInfo.getTaskDefinitionKey();
			//根据processDefinitionId 获取processDefinitionKey
			String processDefinitionKey = null;
			String processDefinitionId = taskInfo.getProcessDefinitionId();
			if(StringUtils.isNotBlank(processDefinitionId)){
				processDefinitionKey = processDefinitionId.split(":")[0];
			}
			//获取自定义的taskName
			String taskName = customActivityNameMapper.selectName(processDefinitionKey, taskDefinitionKey, language);
			if (StringUtils.isBlank(taskName)) {
				taskName = taskInfo.getName();
			} 
			//构建用户任务对象
			userTask.setBusinessName(businessName);
			userTask.setDataId(dataId);
			userTask.setTaskId(taskInfo.getId());
			userTask.setTaskDefinitionKey(taskDefinitionKey);
			userTask.setTaskName(taskName);
			userTask.setProcInstId(processInstanceId);
			userTask.setCreateTime(taskInfo.getCreateTime());
			userTask.setAssignee(taskInfo.getAssignee());
			if(StringUtils.isNotBlank(taskInfo.getDescription())){
				//设置任务备注信息
				userTask.setNotes(taskInfo.getDescription());
			}else{
				userTask.setNotes("");
			}
		}
		return userTask;
	}
	
	/*
	 * 根据任务列表构造用户任务列表
	 */
	private List<UserTask> buildUserTaskList(List<Task> taskList,
			String language) {
		List<UserTask> userTaskList = new ArrayList<UserTask>();
		for (Task task : taskList) {
			try {
				// 构造UserTask对象
				UserTask userTask = buildUserTaskByTaskInfo(task,
						language);
				//设置任务完成时间
				userTask.setCompleteTime(null);
				//设置任务待办人信息
				List<Assignee> assigneeList = processServiceImpl.buildAssigneeByTaskId(task.getId());
				userTask.setAssigneeList(assigneeList);
				userTaskList.add(userTask);
			} catch (Exception e) {
				logger.debug("Exception : " + e.getMessage());
				e.printStackTrace();
				continue;
			}
		}
		return userTaskList;
	}
	
	/*
	 * 根据历史任务列表构造用户任务列表
	 */
	private List<UserTask> buildUserTaskListByHistoric(List<HistoricTaskInstance> historicTaskList,
			String language) {
		List<UserTask> userTaskList = new ArrayList<UserTask>();
		for (HistoricTaskInstance historicTask : historicTaskList) {
			try {
				// 构造UserTask对象
				UserTask userTask = buildUserTaskByTaskInfo(historicTask,
						language);
				//设置任务完成时间
				userTask.setCompleteTime(historicTask.getEndTime());
				//设置任务办理人信息
				List<Assignee> assigneeList = processServiceImpl.buildAssigneeByUserId(historicTask.getAssignee());
				userTask.setAssigneeList(assigneeList);
				userTaskList.add(userTask);
			} catch (Exception e) {
				logger.debug("Exception : " + e.getMessage());
				e.printStackTrace();
				continue;
			}
		}
		return userTaskList;
	}

	@Override
	public boolean TaskAuthenticate(String taskId, String userId, List<String> groupIdList) {
		TaskInfo taskInfo = getTaskInfoByTaskId(taskId);
		if(null != taskInfo){
			//获取任务办理人
			String assignee = taskInfo.getAssignee();
			if(StringUtils.isNotBlank(assignee) && assignee.equals(userId)){
				return true;
			}
		}
		// 获取任务候选人或候选组
		List<IdentityLink> identities = processServiceImpl.getIdentityLinkOfTask(taskId);
		if(null != identities && !identities.isEmpty()){
			// 遍历候选人或候选组
			for(IdentityLink identity : identities){
				//如果有候选人
				if(StringUtils.isNotEmpty(identity.getUserId()) && identity.getUserId().equals(userId)){
					return true;
				}
				//如果有候选组
				if(StringUtils.isNotEmpty(identity.getGroupId()) && groupIdList.contains(identity.getGroupId())){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean TaskFinished(String taskId) {
		HistoricTaskInstance historicTask = historyService
	            .createHistoricTaskInstanceQuery()
	            .taskId(taskId)
	            .singleResult();
		if(!"completed".equals(historicTask.getDeleteReason())){
			
			return false;
		}
		return true;
	}
	
	/*
	 * 根据任务ID获取任务信息
	 */
	private TaskInfo getTaskInfoByTaskId(String taskId) {
		TaskInfo taskInfo = taskService
				.createTaskQuery()
				.taskId(taskId)
				.singleResult();
		if(null == taskInfo){
			taskInfo = historyService
					.createHistoricTaskInstanceQuery()
					.taskId(taskId)
					.singleResult();
		}
		return taskInfo;
	}

}
