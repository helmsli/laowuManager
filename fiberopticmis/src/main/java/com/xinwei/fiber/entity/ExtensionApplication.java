package com.xinwei.fiber.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ExtensionApplication implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -312519956029615578L;
	//延期信息
	private ExtensionOrderInfo fiberDelayOrderInfo;
	//延期客户信息
	private ExtensionCustomerInfo fiberDelayCustomIfo;
	
	public ExtensionOrderInfo getFiberDelayOrderInfo() {
		return fiberDelayOrderInfo;
	}


	public void setFiberDelayOrderInfo(ExtensionOrderInfo fiberDelayOrderInfo) {
		this.fiberDelayOrderInfo = fiberDelayOrderInfo;
	}


	public ExtensionCustomerInfo getFiberDelayCustomIfo() {
		return fiberDelayCustomIfo;
	}


	public void setFiberDelayCustomIfo(ExtensionCustomerInfo fiberDelayCustomIfo) {
		this.fiberDelayCustomIfo = fiberDelayCustomIfo;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
