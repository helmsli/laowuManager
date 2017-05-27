package com.xinwei.workflow.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WorkFlowProcessResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6701650853318737178L;

	public static final int SUCCESS_CODE = 0; // 成功

	public static final int DEFAULT_ERROR_CODE = 1; // 默认错误码

	// 结果是成功还是失败
	private Integer status; // 成功为0， 默认错误码为1
	// 成功或失败的消息
	private String message = "";

	public static WorkFlowProcessResult getSuccessResult(String message) {
		return new WorkFlowProcessResult(SUCCESS_CODE, message);
	}

	public static WorkFlowProcessResult getFailureResult(String message) {
		return new WorkFlowProcessResult(DEFAULT_ERROR_CODE, message);
	}

	public static WorkFlowProcessResult getFailureResult(String message,
			int errorCode) {
		return new WorkFlowProcessResult(errorCode, message);
	}

	private WorkFlowProcessResult(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
