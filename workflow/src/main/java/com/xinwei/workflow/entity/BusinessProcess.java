package com.xinwei.workflow.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
/**
 * 业务与流程关联类
 * @author xuejinku
 *
 */
public class BusinessProcess implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6418313888941600912L;

	private Integer tenantId;//业务类型标识，分区字段

    private String dataId;//业务数据Id

    private Long userId;//用户Id，子分区字段

    private Date startTime;//流程启动时间

    private Date endTime;//流程结束时间

    private String stateInfo;//状态信息

    private String procInstId;//流程实例ID

    private String status;//状态（正在进行，结束）
    
    private String notes;//备注
    
	private Map<String,Object> extData = new ConcurrentHashMap<String,Object>();//扩展数据域

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Map<String, Object> getExtData() {
		return extData;
	}

	public void setExtData(Map<String, Object> extData) {
		this.extData = extData;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}