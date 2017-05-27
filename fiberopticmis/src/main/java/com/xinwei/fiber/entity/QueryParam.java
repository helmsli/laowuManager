package com.xinwei.fiber.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 用户任务查询参数对象
 * 
 * @author xuejinku
 *
 */
public class QueryParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4997143758103248289L;
	private Date startTime;
	private Date endTime;
	private Integer orderType;
	private String language;
	private String queryItem;
	private String itemValue;
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getQueryItem() {
		return queryItem;
	}

	public void setQueryItem(String queryItem) {
		this.queryItem = queryItem;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
