package com.xinwei.rbac.entity;

import java.io.Serializable;

public class DataCategoryUrl implements Serializable{
	//数据分类
    private String dataCategory;
    //业务类型
    private String serviceType;
    //业务接口调用方式
    private String callInterfaceType;
    //业务调用接口
    private String callUrl;
    //业务回调接口
    private String callBackUrl;

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

    public String getCallInterfaceType() {
        return callInterfaceType;
    }

    public void setCallInterfaceType(String callInterfaceType) {
        this.callInterfaceType = callInterfaceType;
    }

    public String getCallUrl() {
        return callUrl;
    }

    public void setCallUrl(String callUrl) {
        this.callUrl = callUrl;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

	@Override
	public String toString() {
		return "DataCategoryUrl [dataCategory=" + dataCategory
				+ ", serviceType=" + serviceType + ", callInterfaceType="
				+ callInterfaceType + ", callUrl=" + callUrl + ", callBackUrl="
				+ callBackUrl + "]";
	}
    
    
}