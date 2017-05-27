package com.xinwei.workflow.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.xinwei.workflow.entity.BusinessProcess;
import com.xinwei.workflow.entity.UserTask;
import com.xinwei.workflow.hessianservice.ProcessHessianService;
import com.xinwei.workflow.hessianservice.UserTaskHessianService;

public class HessianServiceTest {

	
	private static ProcessHessianService processHessianServiceImpl;
	private static UserTaskHessianService userTaskHessianServiceImpl;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"classpath:applicationContext.xml");
		processHessianServiceImpl = (ProcessHessianService)applicationContext.getBean("processHessianServiceImpl");
		userTaskHessianServiceImpl = (UserTaskHessianService)applicationContext.getBean("userTaskHessianServiceImpl");
	}

	
	@Test
	public void startProcess() {
		Map<String,Object> variables = new HashMap <String,Object>();
		variables.put("fianance", "1");
		variables.put("sale", "2");
		variables.put("network", "3");
		processHessianServiceImpl.startProcess("fiberextension", 1, "10001", 2l, variables);
		
	}
	@Test
	public void processHessianServiceTest() {
		/*Map<String, Object> varibales =  processHessianServiceImpl.getVariables("22501");
		System.out.println(varibales.toString());
		
		List<BusinessProcess> bps1 = processHessianServiceImpl.getProcessByTenantId(1, "zh", 0, 10);
		for(BusinessProcess bp :bps1){
			System.out.println(bp.getDataId());
		}
		
		List<BusinessProcess> bps2 = processHessianServiceImpl.getProcessByUserId(2l, "zh", 0, 10);
		for(BusinessProcess bp :bps2){
			System.out.println(bp.getDataId());
		}
		
		List<BusinessProcess> bps3 = processHessianServiceImpl.getProcessByTenantIdAndStatus(1, "start","zh", 0, 10);
		for(BusinessProcess bp :bps3){
			System.out.println(bp.getDataId());
		}
		List<BusinessProcess> bps4 = processHessianServiceImpl.getProcessByUserIdAndStatus(2l, "start","zh", 0, 10);
		for(BusinessProcess bp :bps4){
			System.out.println(bp.getDataId());
		}*/
		String stateInfo = processHessianServiceImpl.getStateInfoByProcInstId("13257", "en");
		System.out.println(stateInfo);
	}
	@Test
	public void UserTaskHessianServiceTest(){
		List<UserTask> tasks = userTaskHessianServiceImpl.getActiveTaskListByUid("58", "en", 0, 10);
		for(UserTask task :tasks){
			System.out.println(task.getTaskName());
			System.out.println(task.getTaskId());
		}
	}

}
