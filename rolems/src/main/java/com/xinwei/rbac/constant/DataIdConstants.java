package com.xinwei.rbac.constant;



public interface DataIdConstants {
	//四位的业务分类
	public interface BizType{
	//光纤开户
	String BIZ_TYPE_OPEN="1000";
	//光纤延期
	String BIZ_TYPE_EXTENSION="1001";
	//其它
	String BIZ_TYPE_OTHER="1002";
	
	}
	//10位的序号
	String COMMON_BIZ_SEQ = "common_biz_seq";// common_biz_id编号
	String GROUP_ID_SEQ = "group_id_seq";// 工单编号格式为日期+四位，如201704190001
	String DATA_ID_SEQ = "data_id_seq";// dataId编号
	
	
	//6为的子数据
	String CHILD_DATA="300000";
}
