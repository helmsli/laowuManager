package com.xinwei.fiber.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 勘测结果
 */
public class DesignReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2401517226222889116L;
	private String accessBTSNo;//接入基站编号
/*
	private Integer distance;//距离
	
	private String fibreNo;//光纤号码

	private Double installationCostEstimated;//预计安装费用

	private String notes;//备注
*/
	public String getAccessBTSNo() {
		return accessBTSNo;
	}

	public void setAccessBTSNo(String accessBTSNo) {
		this.accessBTSNo = accessBTSNo;
	}
/*
	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public String getFibreNo() {
		return fibreNo;
	}

	public void setFibreNo(String fibreNo) {
		this.fibreNo = fibreNo;
	}

	public Double getInstallationCostEstimated() {
		return installationCostEstimated;
	}

	public void setInstallationCostEstimated(Double installationCostEstimated) {
		this.installationCostEstimated = installationCostEstimated;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}*/

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
