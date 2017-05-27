package com.xinwei.workflow.entity;

import com.xinwei.workflow.util.JsonUtil;

/**
 * 任务待办人对象
 * @author xuejinku
 *
 */
public class Assignee extends IdEntity {
	
	public static final int Role = 0;
	public static final int User = 1;
	
	private int assigneeType;//待办人类型（用户、组）
	private String assigneeName;//待办人名称
	private String tel;//待办人电话号码
	
	public int getAssigneeType() {
		return assigneeType;
	}

	public void setAssigneeType(int assigneeType) {
		this.assigneeType = assigneeType;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public static void main(String[] args) {
		Assignee a = new Assignee();
		a.setAssigneeType(User);
		a.setId(10001L);
		a.setAssigneeName("saleA");
		a.setTel("13545678910");
		System.out.println(JsonUtil.toJson(a));
	}
}
