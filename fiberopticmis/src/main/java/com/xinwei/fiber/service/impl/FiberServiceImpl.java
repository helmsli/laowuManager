package com.xinwei.fiber.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.xinwei.fiber.constant.FiberConstants;
import com.xinwei.fiber.entity.ActivationInfomation;
import com.xinwei.fiber.entity.BasicInformation;
import com.xinwei.fiber.entity.CustomerInfo;
import com.xinwei.fiber.entity.DesignReport;
import com.xinwei.fiber.entity.ExtensionApplication;
import com.xinwei.fiber.entity.ExtensionCustomerInfo;
import com.xinwei.fiber.entity.ExtensionOrderInfo;
import com.xinwei.fiber.entity.InfoConformation;
import com.xinwei.fiber.entity.OpenAccountApplication;
import com.xinwei.fiber.service.FiberService;
import com.xinwei.newcrm.fiber.Const.FiberOpenConst;
import com.xinwei.newcrm.fiber.dto.FiberFacadeContext;
import com.xinwei.newcrm.fiber.entity.DelayOrderInfo;
import com.xinwei.newcrm.fiber.entity.FiberCustomerInfo;
import com.xinwei.newcrm.fiber.entity.FiberUserInfo;
import com.xinwei.newcrm.fiber.entity.NewSubscriberInfo;
import com.xinwei.newcrm.fiber.service.FiberOpenService;
import com.xinwei.newcrm.fiber.service.FiberOrderService;
import com.xinwei.rbac.entity.CommonBiz;
import com.xinwei.rbac.service.CommonBizService;
import com.xinwei.security.entity.User;
import com.xinwei.security.service.UserService;
import com.xinwei.system.util.ProcessResult;
import com.xinwei.util.JsonUtil;
import com.xinwei.util.date.DateStyle;
import com.xinwei.util.date.DateUtil;

@Service
public class FiberServiceImpl implements FiberService {
	
	private Logger logger = LoggerFactory.getLogger(FiberServiceImpl.class);
	@Resource
	private FiberOpenService fiberOpenService;// 光纤开户服务
	@Resource
	private FiberOrderService fiberOrderService;// 光纤延期服务
	@Resource
	private UserService userService;// 用户信息服务
	@Resource
	private CommonBizService commonBizService;// 通用业务数据服务

	@Override
	public FiberFacadeContext buildFiberFacadeContext(String operatorId,
			String operatorName, String operatorType, String procInstId,
			String formNo) {
		FiberFacadeContext fiberFacadeContext = new FiberFacadeContext();
		// 设置操作员Id
		fiberFacadeContext.setOperatorId(operatorId);
		// 设置操作员姓名
		fiberFacadeContext.setOperatorName(operatorName);
		// 设置来源
		fiberFacadeContext.setSource("FiberSystem");
		// 操作类型
		fiberFacadeContext.setOperatorType(operatorType);
		// 设置流程实例ID
		fiberFacadeContext.setTaskId(procInstId);
		// 操作时间
		fiberFacadeContext.setOperatorTime(new Date());
		// 设置表单编号
		fiberFacadeContext.setFormNo(formNo);
		return fiberFacadeContext;
	}

	@Override
	public ProcessResult openAccount(CommonBiz infoConformCommonBiz) {
		String groupId = infoConformCommonBiz.getGroupId();
		logger.debug("OpenAccount --> groupId: {} ", groupId);
		ProcessResult result = ProcessResult.getFailureResult("");
		// 解析commonBiz对象中相关对象
		BasicInformation basicInformation = null;// 1.申请基本信息对象
		DesignReport designReport = null;// 3.勘测结果
		CustomerInfo customerInfo = null;// 4.客户合同信息
		ActivationInfomation activationInfomation = null;// 5.开通信息
		InfoConformation infoConformation = null;// 6.信息确认对象
		//FinanceInfo financeInfo = null;// 7.财务信息对象

		// 1、2解析申请基本信息对象、客户信息对象
		List<CommonBiz> openAccountCommonBizList = commonBizService
				.getBizListByGIdAndServiceType(groupId,
						FiberConstants.businessType.FIBER_OPENACCOUNT);
		// 获取申请信息
		if (null != openAccountCommonBizList
				&& !openAccountCommonBizList.isEmpty()) {
			String openAccountApplicationStr = openAccountCommonBizList.get(0)
					.getData1();
			OpenAccountApplication openAccountApplication = JsonUtil.fromJson(
					openAccountApplicationStr, OpenAccountApplication.class);

			basicInformation = openAccountApplication.getBasicInformation();
			
		}

		// 3.解析勘测结果
		List<CommonBiz> designReportList = commonBizService
				.getBizListByGIdAndServiceType(groupId,
						FiberConstants.State.DESIGNER_DESIGN);
		if (null != designReportList && !designReportList.isEmpty()) {
			String designReportStr = designReportList.get(0).getData1();
			designReport = JsonUtil.fromJson(designReportStr,
					DesignReport.class);
		}

		// 4.解析客户合同信息
		List<CommonBiz> sellerApplicationList = commonBizService
				.getBizListByGIdAndServiceType(groupId,
						FiberConstants.State.SELLER_APPLICATION);
		if (null != sellerApplicationList && !sellerApplicationList.isEmpty()) {
			String sellerApplicationStr = sellerApplicationList.get(0)
					.getData1();
			customerInfo = JsonUtil.fromJson(sellerApplicationStr,
					CustomerInfo.class);
		}

		// 5.解析开通信息
		List<CommonBiz> activationList = commonBizService
				.getBizListByGIdAndServiceType(groupId,
						FiberConstants.State.ACTIVATIONOPERATOR_ACTIVATE);
		if (null != activationList && !activationList.isEmpty()) {
			String activationStr = activationList.get(0).getData1();
			activationInfomation = JsonUtil.fromJson(activationStr,
					ActivationInfomation.class);
		}

		// 6.解析信息确认对象
		String infoConformationStr = infoConformCommonBiz.getData1();
		infoConformation = JsonUtil.fromJson(infoConformationStr,
					InfoConformation.class);
		
		/*
		// 7.解析财务信息对象
		List<CommonBiz> financeInfoList = commonBizService
				.getBizListByGIdAndServiceType(groupId,
						FiberConstants.State.FINANCEADMIN_REVIEW);
		if (null != financeInfoList && !financeInfoList.isEmpty()) {
			String financeInfoStr = financeInfoList.get(0).getData1();
			financeInfo = JsonUtil.fromJson(financeInfoStr, FinanceInfo.class);
		}
		*/

		User user = userService.get(basicInformation.getRepresentativeId());
		// 构造FiberFacadeContext对象
		FiberFacadeContext fiberFacadeContext = buildFiberFacadeContext(user
				.getId().toString(), user.getFirstname(),
				FiberConstants.businessType.FIBER_OPENACCOUNT,
				openAccountCommonBizList.get(0).getProcessInstanceId(),
				basicInformation.getFormNumber());

		// 构造FiberUserInfo对象
		FiberUserInfo fiberUserInfo = new FiberUserInfo();
		fiberUserInfo.setFiberType(customerInfo.getFibreType());
		fiberUserInfo.setInstallationAddress(customerInfo
				.getInstallationAddress()); // Installation Address
		fiberUserInfo.setTeleNo(customerInfo.getCooTelTopupPhoneNo()); // CooTelTopupPhoneNo
		fiberUserInfo.setAccessBtsNo(designReport.getAccessBTSNo());
		fiberUserInfo.setUserIp(activationInfomation.getUserIP()); // IP Address
		fiberUserInfo.setGateWay(activationInfomation.getGateway());
		fiberUserInfo.setVlan(activationInfomation.getVLAN());
		fiberUserInfo.setDns(activationInfomation.getDNS());
		fiberUserInfo.setSwitchIp(activationInfomation.getSwitchIP());
		fiberUserInfo.setSwitchPort(activationInfomation.getSwitchPort());
		fiberUserInfo.setSubscriberId(infoConformation.getSubscriberId());
		fiberUserInfo.setActivationDate(infoConformation.getActivationDate());
		
		// 构造FiberCustomerInfo对象,从Customer Info 对象中获取相关信息
		FiberCustomerInfo fiberCustInfo = new FiberCustomerInfo();
		fiberCustInfo.setCustomer_name(customerInfo.getCustomerName());
		fiberCustInfo.setCompany_type(customerInfo.getCompanyType());
		fiberCustInfo.setAddress(customerInfo.getInvoiceAddress());
		fiberCustInfo.setContact_person(customerInfo.getContactName());
		fiberCustInfo.setContact_person_phone(customerInfo.getContactTelNo());
		fiberCustInfo.setCustomerType(customerInfo.getCustomerType());

		// 构造NewSubscriberInfo对象
		NewSubscriberInfo newSubscriberInfo = new NewSubscriberInfo();
		newSubscriberInfo.setFixFee(customerInfo.getInstallationFee());// 初装费
		Date effectTime = infoConformation.getActivationDate(); // 开通时间
		
		newSubscriberInfo.setEffectTime(effectTime);
		newSubscriberInfo.setPeriod(infoConformation.getContractPeriod());// 订购周期
		Date expireTime = DateUtil.addMonth(infoConformation.getActivationDate(), 
				infoConformation.getContractPeriod());
		//转换为2017-01-01 23:59:59 格式
		expireTime = DateUtil.addSecond(expireTime,-1);
		newSubscriberInfo.setExpireTime(expireTime);
		Date payStartTime = effectTime;// 付费开始时间
		newSubscriberInfo.setPayStartTime(payStartTime);
		newSubscriberInfo.setPayPeriod(infoConformation.getPaymentPeriod());//付费周期
		Date payEndTime = DateUtil.addMonth(payStartTime,infoConformation.getPaymentPeriod());
		//转换为2017-01-01 23:59:59 格式
		payEndTime = DateUtil.addSecond(payEndTime,-1);
		newSubscriberInfo.setPayEndTime(payEndTime);
		
		newSubscriberInfo.setProductId(infoConformation.getSelectProductId());// 产品Id
		newSubscriberInfo.setSubscriberIds(infoConformation.getSubscriberId());// 用户Id
		newSubscriberInfo.setOperatorId(user.getId().toString());
		newSubscriberInfo.setOperatorName(user.getFirstname());
		
		logger.debug("fiberFacadeContext: " + fiberFacadeContext.toString()
				+ "fiberUserInfo: " + fiberUserInfo + "fiberCustInfo: "
				+ fiberCustInfo + "newSubscriberInfo: " + newSubscriberInfo);
		// 获取之前信息确认列表
		List<CommonBiz> infoConfirmList = commonBizService
				.getBizListByGIdAndServiceType(groupId,FiberConstants.State.INFOCONFIRMER_CONFIRM);
		if(null != infoConfirmList && !infoConfirmList.isEmpty()){
			String status = infoConfirmList.get(0).getStatus();
			// 如果已经开过户
			if(String.valueOf(FiberOpenConst.ERROR_SUBSCRIBER_ERROR).equals(status)){
				//调用订购产品服务
				result = fiberOrderService.subscribeProduct(fiberFacadeContext, newSubscriberInfo);
			}else{
				// 开户服务
				result = fiberOpenService.fiberOpen(fiberFacadeContext, fiberUserInfo,
					fiberCustInfo, newSubscriberInfo);
			}
		}else{
			// 开户服务
			result = fiberOpenService.fiberOpen(fiberFacadeContext, fiberUserInfo,
				fiberCustInfo, newSubscriberInfo);
		}
		logger.debug("OpenAccount -->starus:{} , message: {}", result.getStatus().toString(),result.getMessage());
		return result;
	}

	@Override
	public ProcessResult extension(String groupId) {
		logger.debug("Fiber Extension --> groupId: {} ", groupId);
		ProcessResult result = ProcessResult.getFailureResult("");
		// 获取光纤延期申请信息
		List<CommonBiz> extensionCommonBizList = commonBizService
				.getBizListByGIdAndServiceType(groupId,
						FiberConstants.businessType.FIBER_EXTENSION);

		if (null != extensionCommonBizList && !extensionCommonBizList.isEmpty()) {

			CommonBiz extensionCommonBiz = extensionCommonBizList.get(0);
			ExtensionApplication extensionApplication = JsonUtil.fromJson(
					extensionCommonBiz.getData1(), ExtensionApplication.class);
			// 客户信息
			ExtensionCustomerInfo extensionCustomerInfo = extensionApplication
					.getFiberDelayCustomIfo();
			// 客户订购产品信息
			ExtensionOrderInfo ExtensionOrderInfo = extensionApplication
					.getFiberDelayOrderInfo();
			// 构造FiberFacadeContext对象
			FiberFacadeContext fiberFacadeContext = buildFiberFacadeContext(
					extensionCommonBiz.getCreatePersonId(),
					extensionCommonBiz.getCreatePersonName(),
					FiberConstants.businessType.FIBER_EXTENSION,
					extensionCommonBiz.getProcessInstanceId(),
					extensionCommonBiz.getGroupId());
			// 构造DelayOrderInfo对象
			DelayOrderInfo delayInfo = new DelayOrderInfo();
			delayInfo.setSubsId(ExtensionOrderInfo.getSubscriberId());
			delayInfo.setCustomerId(extensionCustomerInfo.getCustomerID());
			delayInfo.setOperateTime(extensionCommonBiz.getCreateTime());
			delayInfo.setOperatorId(extensionCommonBiz.getCreatePersonId());
			delayInfo.setOperatorName(extensionCommonBiz.getCreatePersonName());
			delayInfo.setReson(ExtensionOrderInfo.getReason());
			delayInfo.setSource("FiberSystem");
			delayInfo.setProductId(ExtensionOrderInfo.getProductId());
			Date startTime = DateUtil.addDay(ExtensionOrderInfo.getExpiryDate(), 1);
			delayInfo.setStartTime(startTime);
			Date endTime = DateUtil.addDay(startTime,ExtensionOrderInfo.getAdjustDays().intValue());
			//转换为2017-01-01 23:59:59 格式
			endTime = DateUtil.addSecond(endTime, -1);
			delayInfo.setEndTime(endTime);

			// 光纤延期
			result = fiberOrderService.delayOrderExpireTime(fiberFacadeContext,
					delayInfo);
		}
		logger.debug("FiberExtension --> result: {}", result.toString());
		return result;
	}
	public static void main(String[] args) {
		Date startTime = DateUtil.addDay(new Date(), 1);
		startTime = DateUtil.StringToDate(DateUtil.getDate(startTime), DateStyle.YYYY_MM_DD);
		System.out.println(startTime);
	}
}
