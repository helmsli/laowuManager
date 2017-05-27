package com.xinwei.workflow.entity;

/**
 * 自定义流程中的节点名称
 * @author xuejinku
 *
 */
public class CustomActivityName {
	
    private String processDefKey;//流程定义key

    private String activityId;//节点定义Id（Task为TaskDefinitionkey）
    
    private String language;//语言

    private String name;//自定义的名称

    
    public CustomActivityName() {
		super();
	}

	public CustomActivityName(String processDefKey, String activityId, 
			String language, String name) {
		super();
		this.processDefKey = processDefKey;
		this.activityId = activityId;
		this.language = language;
		this.name = name;
	}

	public String getProcessDefKey() {
        return processDefKey;
    }

    public void setProcessDefKey(String processDefKey) {
        this.processDefKey = processDefKey;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}