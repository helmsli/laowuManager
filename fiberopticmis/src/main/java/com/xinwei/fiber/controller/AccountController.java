package com.xinwei.fiber.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.xinwei.crm.product.domain.PlanProductInfo;
import com.xinwei.crm.product.dto.ProductQueryPage;
import com.xinwei.crm.product.service.PlanProductInfoService;
import com.xinwei.fiber.constant.ApprovedConstants;
import com.xinwei.fiber.constant.FiberConstants;
import com.xinwei.fiber.entity.ApplicationInfo;
import com.xinwei.fiber.entity.BasicInformation;
import com.xinwei.fiber.entity.CustomerInfo;
import com.xinwei.fiber.entity.OpenAccountApplication;
import com.xinwei.fiber.service.ApplicationInfoService;
import com.xinwei.rbac.entity.CommonBiz;
import com.xinwei.rbac.service.CommonBizService;
import com.xinwei.security.entity.User;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.JsonUtil;
import com.xinwei.util.page.Page;
import com.xinwei.workflow.hessianservice.ProcessHessianService;

/**
 * 光纤开户控制层 
 * @author xuejinku
 */
@Controller
@RequestMapping("/openAccount")
public class AccountController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(AccountController.class);
	@Resource
	private ProcessHessianService processHessianService;// 流程服务
	@Resource
	private CommonBizService commonBizService;// 通用业务数据服务
	@Resource
	private ApplicationInfoService applicationInfoServiceImpl;//申请信息服务
	@Resource
	private PlanProductInfoService planProductInfoService;//光纤产品信息服务
	
	@Value("${procdefkey_fiberOpenAccount}")
	private String processDefinitionKey;// 光纤开户流程定义key
	@Value("${tenantId_fiberOpenAccount}")
	private Integer tenantId;// 光纤开户业务类型ID
	// 光纤产品信息
	@Value("${productCustomerType}")
	private String productCustomerType;// 光纤产品客户类型
	@Value("${productType}")
	private Integer productType;// 光纤产品类型
	@Value("${operatorId}")
	private String operatorId;// 光纤查询产品操作员Id
	
	//从配置文件中获取流程角色Id
	@Value("${roleId_seller}")
	private String roleId_seller;// 销售人员
	@Value("${roleId_designer}")
	private String roleId_designer;// 勘测人员
	@Value("${roleId_contractReviewer}")
	private String roleId_contractReviewer;// 合同审核人员
	@Value("${roleId_installer}")
	private String roleId_installer;// 施工人员
	@Value("${roleId_activationOperator}")
	private String roleId_activationOperator;// 光纤开通人员
	@Value("${roleId_infoConfirmer}")
	private String roleId_infoConfirmer;//信息确认员
	@Value("${roleId_financialAdmin}")
	private String roleId_financialAdmin;// 财务管理员
	@Value("${roleId_networkAdmin}")
	private String roleId_networkAdmin;// 网管
	
	/**
	 * 获取角色Id
	 * @return
	 */
	@RequestMapping(value = "/getRoleId", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getRoleId(String roleName) {
		ResultVO<Object> result = new ResultVO<>();
		String roleId = null;
		if(FiberConstants.roleName.SELLER.equals(roleName.trim())){
			roleId = roleId_seller;
		}
		if(FiberConstants.roleName.NETWORKADMIN.equals(roleName.trim())){
			roleId = roleId_networkAdmin;
		}
		result.setOthers("roleId", roleId);
		return result.toString();
	}
	
	/**
	 * 获取光纤产品列表
	 * @return
	 */
	@RequestMapping(value = "/product/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String listProduct(String productName) {
		ResultVO<PlanProductInfo> result = new ResultVO<PlanProductInfo>();
		try {
			// 获取当前用户信息
			User currentUser = this.getCurrentUser();
			if (null != currentUser) {
				Page<PlanProductInfo> page = null;
				// 产品列表
				List<PlanProductInfo> productInfoList = new ArrayList<PlanProductInfo>(0);
				List<Integer> productTypes = new ArrayList<Integer>(1);
				productTypes.add(productType);
				// 统计全部光纤产品
				Integer count = planProductInfoService.findAllProductInfoCount(productTypes, 
						productCustomerType, null, operatorId);
				if(count > 0){
					if (StringUtils.isNotBlank(productName)) {
						// 获取全部产品列表
						List<PlanProductInfo> totalProductInfoList = getTotalProductInfo(
								productTypes, count);
						for (PlanProductInfo planProductInfo : totalProductInfoList) {
							// 根据产品名称进行筛选
							if (planProductInfo.getProductName().contains(
									productName)) {
								productInfoList.add(planProductInfo);
							}
						}
						if (!productInfoList.isEmpty()) {
							page = new Page<PlanProductInfo>(
									(long) productInfoList.size());
							int fromIndex = page.getStartRow();
							int toIndex = page.getStartRow()
									+ page.getPageSize();
							if (toIndex > productInfoList.size()) {
								toIndex = productInfoList.size();
							}
							productInfoList = productInfoList.subList(
									fromIndex, toIndex);
						}
					} else{
						page = new Page<PlanProductInfo>(count.longValue());
						int pageNum = page.getPageNum();
						int pageSize = page.getPageSize();
						// 分页获取产品列表
						productInfoList = getProductInfoByPage(productTypes,
								pageNum, pageSize);
					}
				}else{
					page = new Page<PlanProductInfo>(0L);
				}
				//给客户端响应
				result.setPage(page);
				result.setLists(productInfoList);
			}else{
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.EXCEPTION);
		}
		logger.debug("listProduct --> result: {}", result.getResult());
		return result.toString();
	}

	// 分页获取产品列表
	private List<PlanProductInfo> getProductInfoByPage(
			List<Integer> productTypes, int pageNum, int pageSize) {
		ProductQueryPage productQueryPage = new ProductQueryPage();
		productQueryPage.setCurrentPageNum(pageNum);
		productQueryPage.setPageSize(pageSize);
		List<PlanProductInfo> productInfoList = planProductInfoService.findProductByPage(productCustomerType, productTypes,
					productQueryPage, null, operatorId);
		return productInfoList;
	}
	// 获取全部产品列表
	private List<PlanProductInfo> getTotalProductInfo(
			List<Integer> productTypes, Integer count) {
		ProductQueryPage productQueryPage = new ProductQueryPage();
		productQueryPage.setCurrentPageNum(1);
		productQueryPage.setPageSize(count);
		List<PlanProductInfo> totalProductInfoList = planProductInfoService.findProductByPage(productCustomerType, productTypes,
				productQueryPage, null, operatorId);
		return totalProductInfoList;
	}
	
	/**
	 * 创建开户申请
	 * @return
	 */
	@RequestMapping(value = "/application", method = { RequestMethod.GET,
			RequestMethod.POST })
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
				commonBiz.setCreatePersonName(currentUser.getFirstname());
				commonBiz.setCreatePersonTelno(currentUser.getPhone());
				Date createTime = Calendar.getInstance().getTime();
				commonBiz.setCreateTime(createTime);
				
				// 获取申请信息
				String openAccountApplicationStr = commonBiz.getData1();
				OpenAccountApplication openAccountApplication = JsonUtil.fromJson(openAccountApplicationStr, OpenAccountApplication.class);
				// 获取销售代表Id
				Long representativeId = openAccountApplication.getBasicInformation().getRepresentativeId();
				
				// 设置业务归属者、业务类型
				commonBiz.setServiceOwner(representativeId.toString());
				commonBiz.setServiceType(FiberConstants.businessType.FIBER_OPENACCOUNT);
				commonBiz.setStatus(ApprovedConstants.ProcessResult.CODE_SUCCESS);
				// 保存业务数据
				Long dataId = commonBizService.save(commonBiz);
				// 设置流程变量
				Map<String, Object> variables = new HashMap<String, Object>();
				setVariablesForProcess(representativeId, variables);
				// 启动流程
				String processInstanceId = processHessianService.startProcess(
						processDefinitionKey, tenantId, dataId.toString(), representativeId, variables);
				// 修改数据对象流程实例ID
				commonBizService.updateProcessIdByDataId(processInstanceId, dataId);
				// 保存申请信息
				saveApplicationInfo(openAccountApplication, representativeId, dataId.toString(), processInstanceId,createTime);
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

	private void setVariablesForProcess(Long representativeId,
			Map<String, Object> variables) {
		// 设置销售
		variables.put("seller", representativeId);
		variables.put("sellerGroup", "");
		// 设置勘测
		variables.put("designer", null);
		variables.put("designerGroup", roleId_designer);
		// 设置合同审核者
		variables.put("contractReviewer", null);
		variables.put("contractReviewerGroup", roleId_contractReviewer);
		// 设置施工人员
		variables.put("installer", null);
		variables.put("installerGroup", roleId_installer);
		// 设置光纤开通人员
		variables.put("activationOperator", null);
		variables.put("activationOperatorGroup", roleId_activationOperator);
		// 设置信息确认员
		variables.put("infoConfirmer", null);
		variables.put("infoConfirmerGroup", roleId_infoConfirmer);
		// 设置财务管理员
		variables.put("financeAdmin", null);
		variables.put("financeAdminGroup", roleId_financialAdmin);
	}

	//保存申请信息
	private void saveApplicationInfo(OpenAccountApplication openAccountApplication,Long applicant,
			String dataId, String processInstanceId, Date createTime) {
		//保存申请信息
		ApplicationInfo applicationInfo = new ApplicationInfo();
		BasicInformation basicInformation = openAccountApplication.getBasicInformation();
		CustomerInfo customerInfo = openAccountApplication.getCustomerInfo();
		
		applicationInfo.setFormNo(basicInformation.getFormNumber());
		applicationInfo.setDataId(dataId);
		applicationInfo.setIDNO(customerInfo.getIDNO());
		applicationInfo.setApplicant(applicant);
		applicationInfo.setApplyDate(createTime);
		applicationInfo.setCustomerName(customerInfo.getCustomerName());
		applicationInfo.setApplyService(FiberConstants.businessType.FIBER_OPENACCOUNT);
		applicationInfo.setTelNo(customerInfo.getContactTelNo());
		applicationInfo.setInstallationAddress(customerInfo.getInstallationAddress());
		applicationInfo.setProcInstId(processInstanceId);
		applicationInfoServiceImpl.save(applicationInfo);
	}
   
}
