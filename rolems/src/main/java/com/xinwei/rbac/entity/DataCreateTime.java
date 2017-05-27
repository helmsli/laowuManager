package com.xinwei.rbac.entity;

import java.io.Serializable;
import java.util.Date;

public class DataCreateTime implements Serializable{
    private Date createTime;

    private Long dataId;

    public DataCreateTime() {
		super();
	}
    
	public DataCreateTime(Date createTime) {
		super();
		this.createTime = createTime;
	}

	public DataCreateTime(Date createTime, Long dataId) {
		super();
		this.createTime = createTime;
		this.dataId = dataId;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

	@Override
	public String toString() {
		return "DataCreateTime [createTime=" + createTime + ", dataId="
				+ dataId + "]";
	}
    
}