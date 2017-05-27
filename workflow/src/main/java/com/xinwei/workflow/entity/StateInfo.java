package com.xinwei.workflow.entity;

import java.util.ArrayList;
import java.util.List;

import com.xinwei.workflow.util.JsonUtil;


/**
 * 状态信息
 */
public class StateInfo {

	private String stateName; //当前状态名
	private String taskId; //任务ID
	private List<Assignee> assigneeList; //任务办理者信息
	
	public StateInfo() {
		super();
	}

	public StateInfo(String stateName, String taskId, List<Assignee> assigneeList) {
		super();
		this.stateName = stateName;
		this.taskId = taskId;
		this.assigneeList = assigneeList;
	}

	public String getStateName() {
		return stateName;
	}


	public void setStateName(String stateName) {
		this.stateName = stateName;
	}


	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<Assignee> getAssigneeList() {
		return assigneeList;
	}

	public void setAssigneeList(List<Assignee> assigneeList) {
		this.assigneeList = assigneeList;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
	
	public static void main(String[] args) {
		
		StateInfo state = new StateInfo();
		
		Assignee a = new Assignee();
		a.setAssigneeType(Assignee.User);
		a.setId(10001L);
		a.setAssigneeName("saleA");
		a.setTel("13545678910");
		List<Assignee> assigneeList = new ArrayList<Assignee>();
		assigneeList.add(a);
		state.setStateName("finance_approval");
		state.setTaskId("22511");
		state.setAssigneeList(assigneeList);
		System.out.println(JsonUtil.toJson(state));
	}
}
