package com.xinwei.util;

import com.xinwei.rbac.service.CommonBizService;
import com.xinwei.util.spring.SpringFactory;

public class UpdateCurrentValueTask {  
	static CommonBizService service;
	static {
		service = (CommonBizService) SpringFactory
				.getBean("commonBizServiceImpl");
	}
      
    public void doTask(){  
    	System.out.println("do quertz scheduler... ...");  
        service.updateCurrentValueBySeqName("group_id_seq");
    }  
}  
