package com.xinwei.workflow.listener;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xinwei.workflow.hessianservice.UserTaskHessianService;
/**
 * 驳回监听器
 * @author xuejinku
 */
@Service("rejectListener")
public class RejectListener implements TaskListener {
	private Logger logger = LoggerFactory.getLogger(RejectListener.class);
	
	@Resource
	private UserTaskHessianService userTaskHessianServiceImpl;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		
		try {
			//获取流程实例Id
			String processInstanceId = delegateTask.getProcessInstanceId();
			//获取任务定义key
			String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
			//查询任务执行次数
			Long count = userTaskHessianServiceImpl.countTasksByDefinitionKey(processInstanceId, taskDefinitionKey);
			if(count.intValue() >=1 ){
				delegateTask.setDescription("reject");
			}
		} catch (Exception e) {
			logger.debug("rejectListener Exception -->{}",e.getMessage());
			e.printStackTrace();
		}
	}
}
	