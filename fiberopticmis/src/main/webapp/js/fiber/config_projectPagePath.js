var APP_RESULT=
	{"success":0,
	 "fail":1	
};
var APP_ContainAttach=
{"attach":0, 
  "attachKey":"commonbizfileKey"	 	
};

/**
 * 配置界面加载路径
 * ***/
var CONFIG_PATH=
		{
		    "STEP_pm_sanjibm_qingqiu_PATH":"userTemplate/approveTemplate/submitAttachment.html?projectName=coomarts",
		    //勘测审批第一步
		    
		    "STEP_NetworkAdminReview_PATH":"include/extensionTemp/approveTemp/task_financeApprove.html",
		    "STEP_PendingDesign_PATH":"include/openaccountTemp/approveTemp/task_design.html",
		    "STEP_FinanceAdminReview_PATH":"include/extensionTemp/approveTemp/task_financeApprove.html",
		    "STEP_kaihuxiugai_PATH":"include/openaccountTemp/quotation_info.html",
		    "STEP_AwaitingApplication_PATH":"include/openaccountTemp/approveTemp/task_customer_unified.html",
		    "STEP_PendingContractReview_PATH":"include/openaccountTemp/approveTemp/task_contractReview.html",
		    "STEP_PendingInstallation_PATH":"include/openaccountTemp/approveTemp/task_installation.html",
		    "STEP_PendingActivation_PATH":"include/openaccountTemp/approveTemp/task_activation.html",
		    "STEP_PendingInfoConfirmation_PATH":"include/openaccountTemp/approveTemp/task_conformation.html",
		    "STEP_PendingFinanceAdminReview_PATH":"include/openaccountTemp/approveTemp/task_finance.html",
		    
		    
		    
		    
		};


/***
 * 配置JS文件加载路径
 * **/
var SCRIPTFile={
		"STEP_NetworkAdminReview_PATH":"/js/fiber/approve/delayApprove.js",
	    "STEP_pm_sanjibm_qingqiu_PATH": "/js/process_global/project/model/submitAttachment.js",
	    "STEP_PendingDesign_PATH":"/js/fiber/approve/taskDesign.js",
	    "STEP_FinanceAdminReview_PATH":"/js/fiber/approve/delayApprove.js",
	    "STEP_kaihuxiugai_PATH":"/js/fiber/modify/openAccountModify.js",
	    "STEP_AwaitingApplication_PATH":"/js/fiber/approve/taskCustomerInfo_unified.js",
	    "STEP_PendingContractReview_PATH":"/js/fiber/approve/taskContractReview.js",
	    "STEP_PendingInstallation_PATH":"/js/fiber/approve/taskInstallate.js",
	    "STEP_PendingActivation_PATH":"/js/fiber/approve/taskActive.js",
	    "STEP_PendingInfoConfirmation_PATH":"/js/fiber/approve/taskInfoConform.js",
	    "STEP_PendingFinanceAdminReview_PATH":"/js/fiber/approve/taskFinance.js",
	   
	};

	var APPROVE_TITLE={
		AGREE:"同意",
		DISAGREE:"不同意",
	};
