package com.xinwei.workflow.listener;

import javax.annotation.Resource;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.xinwei.workflow.constant.ProcessConstants;
import com.xinwei.workflow.dao.BusinessProcessMapper;

@Service
public class EndEventListener implements ExecutionListener {
	
	@Resource
	private BusinessProcessMapper businessProcessMapper;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String tenantId = execution.getTenantId(); //获取业务类型
		String businessKey = execution.getBusinessKey();// dataID
		
		if (StringUtils.isNotEmpty(tenantId) && StringUtils.isNotEmpty(businessKey)) {
			// 更新流程状态
			businessProcessMapper.updateStatusByTenantAndDataId(ProcessConstants.State.END, Integer.parseInt(tenantId), businessKey);
		}

	}

}