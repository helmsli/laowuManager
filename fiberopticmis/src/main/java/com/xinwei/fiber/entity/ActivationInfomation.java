package com.xinwei.fiber.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 开通信息
 */
public class ActivationInfomation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2006972548440715240L;

	private Date activationDate;//开通日期
    
	private String userIP;

	private String gateway;//网关

	private String VLAN;

	private String DNS;

	private Integer customerType;//客户类型
	
	private String switchIP;

	private String switchPort;
	
	private String notes;

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getVLAN() {
		return VLAN;
	}

	public void setVLAN(String vLAN) {
		VLAN = vLAN;
	}

	public String getDNS() {
		return DNS;
	}

	public void setDNS(String dNS) {
		DNS = dNS;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public String getSwitchIP() {
		return switchIP;
	}

	public void setSwitchIP(String switchIP) {
		this.switchIP = switchIP;
	}

	public String getSwitchPort() {
		return switchPort;
	}

	public void setSwitchPort(String switchPort) {
		this.switchPort = switchPort;
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
