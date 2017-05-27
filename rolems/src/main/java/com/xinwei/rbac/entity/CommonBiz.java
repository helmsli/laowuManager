package com.xinwei.rbac.entity;

import java.io.Serializable;
import java.util.Date;

public class CommonBiz implements Serializable{
	//该表按照数据ID进行分区，数据ID的组成包括：
	//XXXX 四位的业务分类
	//YYMMDD 六位
	//XXXXXXXX 10位的序号
	//XXXXXXXX  8为的子数据
	private Long dataId;
    //数据分类
    private String dataCategory;
    //数据业务类型
    private String serviceType;
    //数据分组ID
    private String groupId;
    //数据分组名称
    private String groupName;
    //创建人ID
    private String createPersonId;
    //创建人名称
    private String createPersonName;
    //创建人电话
    private String createPersonTelno;
    //创建时间
    private Date createTime;
    //更新人
    private String updatePerson;
    //更新时间
    private Date updateTime;
    //数据当前结果
    private String result;
    //结果详细信息
    private String resultExt;
    //数据拥有者
    private String serviceOwner;
    //数据实例ID
    private String processInstanceId;
    //任务ID
    private String taskId;
    //扩展活动信息
    private String extActivitiInfo;
    //数据状态
    private String status;
    //扩展状态
    private String extStatus;
 
    private String data1;
 
    private String data2;

    private String data3;

    private String data4;

    private String data5;

    private String data6;

    private String data7;

    private String data8;

    private String data9;

    private String data10;

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public String getDataCategory() {
        return dataCategory;
    }

    public void setDataCategory(String dataCategory) {
        this.dataCategory = dataCategory;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getCreatePersonName() {
        return createPersonName;
    }

    public void setCreatePersonName(String createPersonName) {
        this.createPersonName = createPersonName;
    }

    public String getCreatePersonTelno() {
        return createPersonTelno;
    }

    public void setCreatePersonTelno(String createPersonTelno) {
        this.createPersonTelno = createPersonTelno;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultExt() {
        return resultExt;
    }

    public void setResultExt(String resultExt) {
        this.resultExt = resultExt;
    }

    public String getServiceOwner() {
        return serviceOwner;
    }

    public void setServiceOwner(String serviceOwner) {
        this.serviceOwner = serviceOwner;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getExtActivitiInfo() {
        return extActivitiInfo;
    }

    public void setExtActivitiInfo(String extActivitiInfo) {
        this.extActivitiInfo = extActivitiInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtStatus() {
        return extStatus;
    }

    public void setExtStatus(String extStatus) {
        this.extStatus = extStatus;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }

    public String getData4() {
        return data4;
    }

    public void setData4(String data4) {
        this.data4 = data4;
    }

    public String getData5() {
        return data5;
    }

    public void setData5(String data5) {
        this.data5 = data5;
    }

    public String getData6() {
        return data6;
    }

    public void setData6(String data6) {
        this.data6 = data6;
    }

    public String getData7() {
        return data7;
    }

    public void setData7(String data7) {
        this.data7 = data7;
    }

    public String getData8() {
        return data8;
    }

    public void setData8(String data8) {
        this.data8 = data8;
    }

    public String getData9() {
        return data9;
    }

    public void setData9(String data9) {
        this.data9 = data9;
    }

    public String getData10() {
        return data10;
    }

    public void setData10(String data10) {
        this.data10 = data10;
    }

	@Override
	public String toString() {
		return "CommonBiz [dataId=" + dataId + ", dataCategory=" + dataCategory
				+ ", serviceType=" + serviceType + ", groupId=" + groupId
				+ ", groupName=" + groupName + ", createPersonId="
				+ createPersonId + ", createPersonName=" + createPersonName
				+ ", createPersonTelno=" + createPersonTelno + ", createTime="
				+ createTime + ", updatePerson=" + updatePerson
				+ ", updateTime=" + updateTime + ", result=" + result
				+ ", resultExt=" + resultExt + ", serviceOwner=" + serviceOwner
				+ ", processInstanceId=" + processInstanceId + ", taskId="
				+ taskId + ", extActivitiInfo=" + extActivitiInfo + ", status="
				+ status + ", extStatus=" + extStatus + ", data1=" + data1
				+ ", data2=" + data2 + ", data3=" + data3 + ", data4=" + data4
				+ ", data5=" + data5 + ", data6=" + data6 + ", data7=" + data7
				+ ", data8=" + data8 + ", data9=" + data9 + ", data10="
				+ data10 + "]";
	}
}