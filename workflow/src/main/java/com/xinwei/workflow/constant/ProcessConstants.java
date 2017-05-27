package com.xinwei.workflow.constant;

public interface ProcessConstants {

	/**
	 * 流程状态
	 */
	public interface State {
		String START = "start";//开始
		String END = "end";//结束
	}
	
	/**
	 * 排序方向
	 */
	public interface SortDirection{
		String ASC="ASC";//升序
		String DESC="DESC";//降序
	}
}
