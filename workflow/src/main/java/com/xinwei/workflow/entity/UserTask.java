package com.xinwei.workflow.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 用户任务对象(对Activiti的任务对象进行封装)
 */
public class UserTask implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3716748219874767379L;
	
	private String taskId;//任务ID
	private String taskDefinitionKey; //任务定义key
    private String taskName;//任务名称
    private String procInstId;//流程实例Id
    private Date createTime;//任务创建时间
    private Date completeTime;//任务完成时间
    private String assignee;//任务办理人Id
    private List<Assignee> assigneeList; //任务待办人对象列表(或任务办理者对象)
    private String businessName;// 业务名称
    private String dataId;//业务数据ID
    private String notes;//备注信息
	private Map<String,Object> extData = new ConcurrentHashMap<String,Object>();//扩展数据域

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public String getTaskName() {
		return taskName;
	}


	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}


	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getCompleteTime() {
		return completeTime;
	}


	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	
	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public List<Assignee> getAssigneeList() {
		return assigneeList;
	}

	public Map<String, Object> getExtData() {
		return extData;
	}

	public void setExtData(Map<String, Object> extData) {
		this.extData = extData;
	}

	public void setAssigneeList(List<Assignee> assigneeList) {
		this.assigneeList = assigneeList;
	}

	public String getBusinessName() {
		return businessName;
	}


	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}


	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
