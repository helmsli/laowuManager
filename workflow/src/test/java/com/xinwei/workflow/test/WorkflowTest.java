package com.xinwei.workflow.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.xinwei.workflow.dao.CustomActivityNameMapper;
import com.xinwei.workflow.entity.CustomActivityName;

public class WorkflowTest {

	private static CustomActivityNameMapper customActivityNameMapper;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"classpath:applicationContext.xml");
		customActivityNameMapper = (CustomActivityNameMapper)applicationContext.getBean("customActivityNameMapper");
	}

	@Test
	public void test() {
		
		CustomActivityName record1 = new CustomActivityName("fiberextension","fiberExtensionStart","zh","光纤延期申请开始");
		customActivityNameMapper.insert(record1);
		String customName = customActivityNameMapper.selectName("fiberextension", "fiberExtensionStart","zh");
		System.out.println(customName);
		
	}

}
