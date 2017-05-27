package com.xinwei.fiber.service;

import com.xinwei.newcrm.fiber.dto.FiberFacadeContext;
import com.xinwei.rbac.entity.CommonBiz;
import com.xinwei.system.util.ProcessResult;
/**
 * 光纤服务
 * @author xuejinku
 *
 */
public interface FiberService {

	public abstract FiberFacadeContext buildFiberFacadeContext(
			String operatorId, String operatorName, String operatorType,
			String procInstId, String formNo);

	/**
	 * 开户服务
	 * @param groupId
	 * @return
	 */
	public abstract ProcessResult openAccount(CommonBiz infoConformCommonBiz);

	/**
	 * 延期服务
	 * @param groupId
	 * @return
	 */
	public abstract ProcessResult extension(String groupId);

}