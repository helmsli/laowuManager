package com.xinwei.fiber.entity;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 光纤延期用户信息.
 * 
 */
public class ExtensionCustomerInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2586231858358982989L;

	private Long customerID;
	
	private String telNo;

	private String customerName;
	private String idNo;
	
	private String mailAddress;
	/*
	private String email;
	private String contactNo;

	private String idType;


	private Date openAccountTime;

	private String userStatus;


	private String referralTelNo;*/

	/**
	 * Constructor.
	 */
	public ExtensionCustomerInfo() {
	}


	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}
	public String getTelNo() {
		return telNo;
	}
	
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}


/*

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getOpenAccountTime() {
		return openAccountTime;
	}

	public void setOpenAccountTime(Date openAccountTime) {
		this.openAccountTime = openAccountTime;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	
	public String getReferralTelNo() {
		return referralTelNo;
	}

	public void setReferralTelNo(String referralTelNo) {
		this.referralTelNo = referralTelNo;
	}
*/
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
