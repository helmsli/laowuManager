package com.xinwei.security;


public interface Constants {
	
	/**
	 * 登录用户
	 */
	String LOGIN_USER = "user";    
	
	/**
	 * 注册审核
	 */
	public interface Approve {
		
		//审核结果
		String Agree = "pass";//同意申请
		String Reject = "reject";//拒绝
		//审核状态码
		Integer CODE_NOT_APPROVED=0;//未审核状态码
		Integer CODE_Agree = 1;//审核通过
		Integer CODE_Reject = 2;//审核不通过
	}
	
}
