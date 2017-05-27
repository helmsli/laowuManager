package com.xinwei.fiber.entity;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 客户合同信息
 */
public class CustomerInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6688281645601650548L;
	private  String customerName;//客户姓名
	private  Integer customerType;//客户类型
	private  String companyType;//公司类型
	private String fibreType;//光纤类型
	private String invoiceAddress;//发票地址
	private String IDNO;//身份证号
	private  String contactName;//联系人姓名
	private  String contactTelNo;//联系电话
	private String installationAddress;//安装地址
	private  Double installationFee;//安装费
	private  String cooTelTopupPhoneNo;//cootel充值号码
	/*private Integer invoicePeriod;//付款周期
	private  Integer contractPeriod;//订购周期
	private  String fibreOpticsBandwidth;
	private  String selectProduct;//选择产品
	private  Double priceCharge;//产品价格
	private  String iPAddress;
	private  String iPAddressFee;
	private  String installationArea;
	private  String notes;*/

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getFibreType() {
		return fibreType;
	}

	public void setFibreType(String fibreType) {
		this.fibreType = fibreType;
	}

	public String getIDNO() {
		return IDNO;
	}

	public void setIDNO(String iDNO) {
		IDNO = iDNO;
	}

	public String getInstallationAddress() {
		return installationAddress;
	}

	public void setInstallationAddress(String installationAddress) {
		this.installationAddress = installationAddress;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	public String getContactTelNo() {
		return contactTelNo;
	}

	public void setContactTelNo(String contactTelNo) {
		this.contactTelNo = contactTelNo;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public Double getInstallationFee() {
		return installationFee;
	}

	public void setInstallationFee(Double installationFee) {
		this.installationFee = installationFee;
	}

	public String getCooTelTopupPhoneNo() {
		return cooTelTopupPhoneNo;
	}

	public void setCooTelTopupPhoneNo(String cooTelTopupPhoneNo) {
		this.cooTelTopupPhoneNo = cooTelTopupPhoneNo;
	}

/*
	public Integer getInvoicePeriod() {
		return invoicePeriod;
	}

	public void setInvoicePeriod(Integer invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}

	public Integer getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(Integer contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public String getFibreOpticsBandwidth() {
		return fibreOpticsBandwidth;
	}

	public void setFibreOpticsBandwidth(String fibreOpticsBandwidth) {
		this.fibreOpticsBandwidth = fibreOpticsBandwidth;
	}

	public String getSelectProduct() {
		return selectProduct;
	}

	public void setSelectProduct(String selectProduct) {
		this.selectProduct = selectProduct;
	}

	public Double getPriceCharge() {
		return priceCharge;
	}

	public void setPriceCharge(Double priceCharge) {
		this.priceCharge = priceCharge;
	}

	public String getiPAddress() {
		return iPAddress;
	}

	public void setiPAddress(String iPAddress) {
		this.iPAddress = iPAddress;
	}

	public String getiPAddressFee() {
		return iPAddressFee;
	}

	public void setiPAddressFee(String iPAddressFee) {
		this.iPAddressFee = iPAddressFee;
	}

	public String getInstallationArea() {
		return installationArea;
	}

	public void setInstallationArea(String installationArea) {
		this.installationArea = installationArea;
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
