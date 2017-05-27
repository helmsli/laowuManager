package com.xinwei.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.TaskInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xinwei.workflow.entity.Assignee;
import com.xinwei.workflow.service.ProcessService;
import com.xinwei.workflow.service.UserAndGroupService;

/**
 * 流程相关服务
 * @author xuejinku
 *
 */
@Service
public class ProcessServiceImpl implements ProcessService {
	
	@Resource
	private RuntimeService runtimeService;//activiti运行时服务
	@Resource
	private IdentityService identityService; //activiti用户、组服务
	@Resource
	private HistoryService historyService;// 与历史数据（历史表）相关的Service
	@Resource
	private TaskService taskService;// 与历史数据（历史表）相关的Service
	@Resource
	private UserAndGroupService userAndGroupServiceImpl;//用户组服务

	private Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);
	
	@Override
	public ProcessInstance startProcess(String processDefinitionKey,String tenantId,String dataId,String userId, Map<String,Object> variables){
		
		//businessKey
		String businessKey =dataId;
		//设置启动流程的人员Id
		identityService.setAuthenticatedUserId(userId);
		//启动一个流程实例
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKeyAndTenantId(processDefinitionKey, businessKey, variables, tenantId);
		return processInstance;
	}

	@Override
	public ProcessInstance getProcessInstanceById(String processInstanceId) {
		ProcessInstance processInstance = runtimeService
	    		.createProcessInstanceQuery()
	    		.processInstanceId(processInstanceId)
	    		.singleResult();
		return processInstance;
	}

	@Override
	public ProcessInstance getProcessInstanceByBusinessKey(
			String businessKey) {
		ProcessInstance processInstance = runtimeService
	    		.createProcessInstanceQuery()
	    		.processInstanceBusinessKey(businessKey)
	    		.singleResult();
		return processInstance;
	}

	@Override
	public HistoricProcessInstance getHistoryProcessInstance(
			String processInstanceId) {
		   HistoricProcessInstance hpi = historyService
	              .createHistoricProcessInstanceQuery()// 创建历史流程实例查询
	              .processInstanceId(processInstanceId)// 使用流程实例ID查询
	              .orderByProcessInstanceStartTime().asc()
	              .singleResult();
		 
	       logger.debug(hpi.getId() + "    " + hpi.getProcessDefinitionId() + "    " + hpi.getStartTime() + "    "
	                + hpi.getEndTime() + "     " + hpi.getDurationInMillis());
	       return hpi;
	}

	@Override
	public List<HistoricActivityInstance> getHistoryActivities(
			String processInstanceId) {
		List<HistoricActivityInstance> list = historyService
                .createHistoricActivityInstanceQuery()// 创建历史活动实例的查询
                .processInstanceId(processInstanceId)//
                .orderByActivityId().asc()
                .list();
  
	    return list;
	}

	@Override
	public HistoricActivityInstance getHistoryActivity(String processInstanceId) {
		HistoricActivityInstance historyActivity = null;
		List<HistoricActivityInstance> list = historyService
                .createHistoricActivityInstanceQuery()// 创建历史活动实例的查询
                .processInstanceId(processInstanceId)//
                .orderByHistoricActivityInstanceId().desc()
                .list();
		if (null != list && !list.isEmpty()) {
			historyActivity = list.get(0);
		}
		return historyActivity;
	}
	
	@Override
	public List<HistoricVariableInstance> getProcessVariables(
			String processInstanceId) {
		List<HistoricVariableInstance> list = historyService
                .createHistoricVariableInstanceQuery()// 创建一个历史的流程变量查询对象
                .processInstanceId(processInstanceId)//
                .list();
        
        return list;
	}
	
	@Override
	public String getBusinessKeyByByProcessInstanceId(String processInstanceId){
		String businessKey= null;
		// 获取正在执行的流程实例
		ProcessInstance processInstance = this
				.getProcessInstanceById(processInstanceId);
		if (null != processInstance) {
			 //获取业务key
			 businessKey= processInstance.getBusinessKey();
		} else {// 如果项目流程已经结束
			// 获取历史流程实例
			HistoricProcessInstance historicProcessInstance = this
					.getHistoryProcessInstance(processInstanceId);
			if (null != historicProcessInstance) {
				businessKey = historicProcessInstance.getBusinessKey();
			}
		}
		return businessKey;
	}
	
	@Override
	public Map<String, Object> getVariables(String processInstanceId) {
		// 获取当前流程中的所有流程变量
		Map<String, Object> variables = runtimeService
				.getVariables(processInstanceId);
		return variables;
	}

	@Override
	public void deleteActiveProcessInstance(String processInstanceId,
			String deleteReason) {
		runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
		
	}

	@Override
	public void deleteFinishedProcessInstance(String processInstanceId) {
		
		historyService.deleteHistoricProcessInstance(processInstanceId);
	}

	@Override
	public List<IdentityLink> getIdentityLinkOfTask(String taskId) {
		return taskService.getIdentityLinksForTask(taskId);
	}

	@Override
	public List<Assignee> buildAssigneeByUserId (String userId){
		//获取用户信息
		User user = userAndGroupServiceImpl.getUser(userId);
		List<Assignee> assigneeList = new ArrayList<Assignee>();
		Assignee assignee = new Assignee();
		if( null!= user){
			
			assignee.setId(Long.parseLong(user.getId()));
			assignee.setAssigneeType(Assignee.User);
			assignee.setAssigneeName(user.getFirstName());
			String tel = userAndGroupServiceImpl.getExtUserInfo(user.getId(), UserAndGroupService.KEY_TEL);
			assignee.setTel(tel);
			assigneeList.add(assignee);
		}
		
		return assigneeList;
	}
	
	@Override
	public List<Assignee> buildAssigneeByTaskId(String taskId) {
		List<Assignee> assigneeList = new ArrayList<Assignee>();
		//获取指定的办理人
		TaskInfo taskInfo = taskService
				.createTaskQuery()
				.taskId(taskId)
				.singleResult();
		if(null != taskInfo && StringUtils.isNotEmpty(taskInfo.getAssignee())){
			User user = userAndGroupServiceImpl.getUser(taskInfo.getAssignee());
			Assignee assignee = new Assignee();
			String assigneeName = user.getFirstName();
			String tel = userAndGroupServiceImpl
					.getExtUserInfo(user.getId(), UserAndGroupService.KEY_TEL);
			assignee.setId(Long.parseLong(user.getId()));
			assignee.setAssigneeType(Assignee.User);
			assignee.setAssigneeName(assigneeName);
			assignee.setTel(tel);
			assigneeList.add(assignee);
			return assigneeList;
		}
		//如没有指定的办理人，获取候选人或候选组信息
		List<IdentityLink> identities = this.getIdentityLinkOfTask(taskId);
		if(null != identities && !identities.isEmpty()){
			// 遍历候选人或候选组
			for(IdentityLink identity : identities){
				//如果有候选人
				if(StringUtils.isNotEmpty(identity.getUserId())){
					Assignee assignee = new Assignee();
					String assigneeName =
							identityService
							.createUserQuery()
							.userId(identity.getUserId())
							.singleResult()
							.getFirstName();
					String tel = userAndGroupServiceImpl
							.getExtUserInfo(identity.getUserId(), UserAndGroupService.KEY_TEL);
					assignee.setId(Long.parseLong(identity.getUserId()));
					assignee.setAssigneeType(Assignee.User);
					assignee.setAssigneeName(assigneeName);
					assignee.setTel(tel);
					assigneeList.add(assignee);
				
				}
				//如果有候选组
				if(StringUtils.isNotEmpty(identity.getGroupId())){
					Assignee assignee = new Assignee();
					String assigneeName =
							identityService
							.createGroupQuery()
							.groupId(identity.getGroupId())
							.singleResult()
							.getName();
					assignee.setId(Long.parseLong(identity.getGroupId()));
					assignee.setAssigneeType(Assignee.Role);
					assignee.setAssigneeName(assigneeName);
					assignee.setTel(null);
					assigneeList.add(assignee);
				}
			}
		}
		return assigneeList;
	}

}
