package com.xinwei.workflow.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.User;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.xinwei.workflow.service.ProcessService;
import com.xinwei.workflow.service.UserAndGroupService;

public class fiberTest {

	private static UserAndGroupService userAndGroupServiceImpl;
	private static ProcessService processServiceImpl;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"classpath:applicationContext.xml");
		userAndGroupServiceImpl = (UserAndGroupService)applicationContext.getBean("userAndGroupServiceImpl");
		processServiceImpl = (ProcessService)applicationContext.getBean("processServiceImpl");
	}

	@Test
	public void createGroupAndUser() {
		
		//创建组
		userAndGroupServiceImpl.saveGroup("1", "fiananceGroup", "assignment");
		userAndGroupServiceImpl.saveGroup("2", "saleGroup", "assignment");
		userAndGroupServiceImpl.saveGroup("3", "networkGroup", "assignment");
		
		//创建用户
		userAndGroupServiceImpl.saveUser("1", "fiananceA", "fiananceA", "123456", "123@163.com","123456");
		userAndGroupServiceImpl.saveUser("2", "saleA", "saleA", "123456", "123@163.com","123456");
		userAndGroupServiceImpl.saveUser("3", "networkA", "networkA", "123456", "123@163.com","123456");
		
		//创建用户组间关系
		userAndGroupServiceImpl.saveMembership("1", "1");
		userAndGroupServiceImpl.saveMembership("2", "2");
		userAndGroupServiceImpl.saveMembership("3", "3");
		
		List<User> users = userAndGroupServiceImpl.getAllUser();
		System.out.println("all users : " + users.size());
		
		List<User> uses = userAndGroupServiceImpl.getUsersInGroup("1");
		System.out.println("usersInGroup : " + uses.size());
		
	}
	
	@Test
	public void startProcess() {
		Map<String,Object> variables = new HashMap <String,Object>();
		variables.put("fianance", "1");
		variables.put("sale", "2");
		variables.put("network", "3");
		processServiceImpl.startProcess("fiberextension", "fiberextension", "10001", "1", variables);
		
		processServiceImpl.startProcess("fiberextension", "fiberopen", "10002", "1", variables);
		
	}

}
