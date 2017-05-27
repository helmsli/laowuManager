package com.xinwei.fiber.constant;

/**
 * 光纤延期相关常量
 * 
 * @author fangping
 */
public class FiberConstants {
	/**
	 * 业务类型
	 */
	public interface businessType{
		String FIBER_OPENACCOUNT = "openAccount";//光纤开户
		String FIBER_EXTENSION = "extension";//光纤延期
	}
	
	/**
	 * 流程状态
	*/
	public interface State {
		//光纤延期
		String FIBER_EXTENSION_START = "fiber_extension_start";//光纤延期开始
		String EXTENSION_FINANCEADMIN_REVIEW = "extension_financeAdmin_review";//财务审批
		String EXTENSION_SELLER_MODIFY = "extension_seller_modify";// 销售修改
		String NETWORKADMIN_REVIEW = "networkAdmin_review";//网管审批
		String FIBER_EXTENSION_END = "fiber_extension_end";//光纤延期结束
		
		//光纤开户
		String FIBER_OPENACCOUNT_START = "fiber_openaccount_start";//光纤开户开始
		String DESIGNER_DESIGN = "designer_design";//勘测
		String SELLER_APPLICATION = "seller_application";// 客户申请
		String SELLER_MODIFY = "seller_modify";//销售修改开户申请
		String CONTRACTREVIEWER_REVIEW = "contractReviewer_review";//合同审核
		String INSTALLER_INSTALLATION = "installer_installation";//施工
		String ACTIVATIONOPERATOR_ACTIVATE = "activationOperator_activate";//开通
		String INFOCONFIRMER_CONFIRM = "infoConfirmer_confirm";// 信息确认
		String FINANCEADMIN_REVIEW = "financeAdmin_review";//财务审批
		String FIBER_OPENACCOUNT_END = "fiber_openaccount_end";//光纤开户结束
	}
	 
	public interface ActivitiContextKey {
		String result = "_result";
	}
	
	/**
	 * 错误码
	 */
	public interface ErrorCode {
		//项目变更状态码
		String STATUS_NOT_ALLOW_CHANGE = "2";//该状态不允许修改
		String NOT_AUTHORITY_CHANGE = "3";// 该用户没有权限修改
	}
	
	/**
	 * 排序方向
	 */
	public interface SortDirection{
		String ASC="ASC";//升序
		String DESC="DESC";//降序
	}
	
	public interface roleName{
		String SELLER = "seller";
		String NETWORKADMIN = "networkAdmin";
	}
}
