package com.xinwei.fiber.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 延期申请产品
 *
 */
public class ExtensionOrderInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8483646297296736480L;

	private String subscriberId;
	
	private Long productId;
	
	private Long adjustDays;
	
	private String reason;
	
	private Date adjustTime;
	
	private Date expiryDate;
	
	/*private String productName;
	
	private Long price;

	private Date effectiveDate;

	 */


	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getAdjustDays() {
		return adjustDays;
	}

	public void setAdjustDays(Long adjustDays) {
		this.adjustDays = adjustDays;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getAdjustTime() {
		return adjustTime;
	}

	public void setAdjustTime(Date adjustTime) {
		this.adjustTime = adjustTime;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	/*
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

*/
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}


}
