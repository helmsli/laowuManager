package com.xinwei.fiber.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 信息确认对象
 */
public class InfoConformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6034797208073959327L;

	private Long selectProductId;//产品Id
	
	private String subscriberId;//用户UID

	private Date activationDate;

	private Integer contractPeriod;
	
	private Integer paymentPeriod;
	
/*
	private String cooTelTopupNoConformation;
	private String selectProduct;//产品名
	private String notes;
*/
	

	public Long getSelectProductId() {
		return selectProductId;
	}

	public void setSelectProductId(Long selectProductId) {
		this.selectProductId = selectProductId;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public Integer getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(Integer contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	
	public Integer getPaymentPeriod() {
		return paymentPeriod;
	}

	public void setPaymentPeriod(Integer paymentPeriod) {
		this.paymentPeriod = paymentPeriod;
	}

/*
	public String getCooTelTopupNoConformation() {
		return cooTelTopupNoConformation;
	}

	public void setCooTelTopupNoConformation(String cooTelTopupNoConformation) {
		this.cooTelTopupNoConformation = cooTelTopupNoConformation;
	}

	public String getSelectProduct() {
		return selectProduct;
	}

	public void setSelectProduct(String selectProduct) {
		this.selectProduct = selectProduct;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
*/
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
