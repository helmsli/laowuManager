package com.xinwei.fiber.constant;

/**
 * 任务审核相关常量
 */
public interface ApprovedConstants {
	
	public interface ApproveResult {
		//审核结论
		String AGREE = "pass";//同意申请
		String REJECT = "reject";//拒绝
		String CANCEL = "cancel";//取消
		String TRANS = "trans";//流转
		
		//审核结论码
		String CODE_AGREE  = "0";
		String CODE_REJECT = "1";
		String CODE_CANCEL = "2";
		String CODE_TRANS = "3";
	}
	// 用户任务处理结果
	public interface ProcessResult{
		
		String CODE_SUCCESS = "0";//操作成功
		String CODE_FAILURE = "1";//操作失败
	}
}
