package com.xinwei.fiber.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 财务信息
 */
public class FinanceInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -613475659758765241L;

	private Date invoicePeriodStartTime;

	private Date invoicePeriodEndTime;
/*
	private String invoiceNumber;
	
	private Double invoiceAmount;
	
	private String notes;
*/
	
	public Date getInvoicePeriodStartTime() {
		return invoicePeriodStartTime;
	}


	public void setInvoicePeriodStartTime(Date invoicePeriodStartTime) {
		this.invoicePeriodStartTime = invoicePeriodStartTime;
	}


	public Date getInvoicePeriodEndTime() {
		return invoicePeriodEndTime;
	}


	public void setInvoicePeriodEndTime(Date invoicePeriodEndTime) {
		this.invoicePeriodEndTime = invoicePeriodEndTime;
	}

/*
	public String getInvoiceNumber() {
		return invoiceNumber;
	}


	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}


	public Double getInvoiceAmount() {
		return invoiceAmount;
	}


	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
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
