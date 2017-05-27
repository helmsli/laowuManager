package com.xinwei.fiber.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinwei.fiber.constant.ApprovedConstants;
import com.xinwei.fiber.constant.FiberConstants;
import com.xinwei.fiber.entity.ApplicationInfo;
import com.xinwei.fiber.entity.ExtensionApplication;
import com.xinwei.fiber.entity.ExtensionCustomerInfo;
import com.xinwei.fiber.service.ApplicationInfoService;
import com.xinwei.rbac.entity.CommonBiz;
import com.xinwei.rbac.service.CommonBizService;
import com.xinwei.security.entity.User;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.JsonUtil;
import com.xinwei.workflow.entity.UserTask;
import com.xinwei.workflow.hessianservice.ProcessHessianService;

/**
 * 光纤延期控制层
 * @author xuejinku
 *
 */
@Controller
@RequestMapping("/extension")
public class ExtensionController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(AccountController.class);
	@Resource
	private ProcessHessianService processHessianService;//流程服务
	@Resource
	private CommonBizService commonBizService;// 通用业务数据服务
	@Resource
	private ApplicationInfoService applicationInfoServiceImpl;//申请信息服务
	
	@Value("${procdefkey_fiberExtension}")
	private String processDefinitionKey;//流程定义key
	@Value("${tenantId_fiberExtension}")
	private Integer tenantId;//业务类型ID
	//设置流程中角色
	@Value("${roleId_seller}")
	private String roleId_seller;// 销售人员
	@Value("${roleId_financialAdmin}")
	private String roleId_financialAdmin;// 财务管理员
	@Value("${roleId_networkAdmin}")
	private String roleId_networkAdmin;// 网络管理员
	
	/**
	 * 创建延期申请
	 * @return
	 */
	@RequestMapping(value="/application", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String createApplication(CommonBiz commonBiz) {
	
		logger.debug("CreateApplication --> start");
		ResultVO<Object> result = new ResultVO<>();
		try {
			// 获取当前用户信息
			User currentUser = this.getCurrentUser();
			if (null != currentUser) {	
				// 获取用户Id
				Long userId = currentUser.getId();
				logger.debug("current user is :" + userId);
				// 设置创建者，创建时间
				commonBiz.setCreatePersonId(userId.toString());
				commonBiz.setCreatePersonTelno(currentUser.getPhone());
				commonBiz.setCreatePersonName(currentUser.getFirstname());
				commonBiz.setCreateTime(Calendar.getInstance().getTime());
				
				//设置业务归属者、业务类型
				commonBiz.setServiceOwner(userId.toString());
				commonBiz.setServiceType(FiberConstants.businessType.FIBER_EXTENSION);
				commonBiz.setStatus(ApprovedConstants.ProcessResult.CODE_SUCCESS);
				//保存申请数据
				Long dataId = commonBizService.save(commonBiz);
				// 设置流程变量
				Map<String, Object> variables = new HashMap<String, Object>();
				// 设置销售
				variables.put("seller", userId.toString());
				variables.put("sellerGroup", "");
				// 设置财务管理员
				variables.put("financeAdmin", null);
				variables.put("financeAdminGroup", roleId_financialAdmin);
				// 设置网络管理人员
				variables.put("networkAdmin", null);
				variables.put("networkAdminGroup", roleId_networkAdmin);
				
				// 启动流程
				String processInstanceId = processHessianService.startProcess(
						processDefinitionKey, tenantId, dataId.toString(), userId, variables);
				// 修改数据对象流程实例ID
				commonBizService.updateProcessIdByDataId(processInstanceId, dataId);
				// 保存申请信息
				saveApplicationInfo(commonBiz,userId, dataId.toString(), processInstanceId);
				// 给客户端响应
				result.setOthers("dataId", dataId);
			}else{
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			logger.error("CreateApplication Erro : " + e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.EXCEPTION);
		}
		logger.debug("CreateApplication --> result: " + result.toString());
		return result.toString();
	}
	
	//保存申请信息
	private void saveApplicationInfo(CommonBiz commonBiz, Long applicant, String dataId,
				String processInstanceId) {
		//保存申请信息
		ApplicationInfo applicationInfo = new ApplicationInfo();
		ExtensionApplication extensionApplication = JsonUtil.fromJson(commonBiz.getData1(), ExtensionApplication.class);
		ExtensionCustomerInfo customerInfo = extensionApplication.getFiberDelayCustomIfo();
			
		applicationInfo.setFormNo(commonBiz.getGroupId());
		applicationInfo.setDataId(dataId);
		applicationInfo.setIDNO(customerInfo.getIdNo());
		applicationInfo.setApplicant(applicant);
		applicationInfo.setApplyDate(commonBiz.getCreateTime());
		applicationInfo.setCustomerName(customerInfo.getCustomerName());
		applicationInfo.setApplyService(FiberConstants.businessType.FIBER_EXTENSION);
		applicationInfo.setTelNo(customerInfo.getTelNo());
		applicationInfo.setInstallationAddress(customerInfo.getMailAddress());
		applicationInfo.setProcInstId(processInstanceId);
		applicationInfoServiceImpl.save(applicationInfo);
	}
	
}
