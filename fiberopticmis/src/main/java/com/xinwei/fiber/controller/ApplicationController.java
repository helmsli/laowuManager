package com.xinwei.fiber.controller;

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

import com.xinwei.fiber.constant.ApprovedConstants;
import com.xinwei.fiber.constant.FiberConstants;
import com.xinwei.fiber.entity.ApplicationInfo;
import com.xinwei.fiber.entity.QueryParam;
import com.xinwei.fiber.service.ApplicationInfoService;
import com.xinwei.rbac.entity.CommonBiz;
import com.xinwei.rbac.service.CommonBizService;
import com.xinwei.security.entity.User;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.ListUtil;
import com.xinwei.util.page.Page;
import com.xinwei.workflow.entity.UserTask;
import com.xinwei.workflow.hessianservice.ProcessHessianService;
import com.xinwei.workflow.hessianservice.UserTaskHessianService;

/**
 * 光纤业务申请控制层
 * @author xuejinku
 *
 */
@Controller
@RequestMapping("/application")
public class ApplicationController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(ApplicationController.class);
	@Resource
	private ProcessHessianService processHessianService;// 流程服务
	@Resource
	private CommonBizService commonBizService;//通用数据服务
	@Resource
	private ApplicationInfoService applicationInfoServiceImpl; // 申请信息服务
	@Resource
	private UserTaskHessianService userTaskHessianService;//用户任务服务
	
	@Value("${tenantId_fiberOpenAccount}")
	private Integer tenantId_fiberOpenAccount;// 光纤开户业务类型ID
	@Value("${tenantId_fiberExtension}")
	private Integer tenantId_fiberExtension;// 光纤延期业务类型ID
	
	/**
	 * 分页查看我的申请列表
	 * @return
	 */
	@RequestMapping(value="/myApplication/list", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String listMyApplication(String language) {
		logger.debug("listMyApplication --> start !" );
		ResultVO<ApplicationInfo> result = new  ResultVO<ApplicationInfo>();
		try {
			//获取当前用户信息
			User currentUser = this.getCurrentUser();
			//判断如果当前用户不为空
			if(null != currentUser){
				//获取用户Id
				Long userId = currentUser.getId();
				logger.debug("currentUser's userid is : {}" , userId);
				
				if(StringUtils.isBlank(language)){
					language = "en";
				}
				// 设置查询参数
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("applicant", userId);
				// 获取分页数据
				Page<ApplicationInfo> page = applicationInfoServiceImpl.getByCondition(queryMap);
				// 设置流程状态信息
				setStateInfo(page.getList(),language);
				//给客户端响应
				result.setPage(page);
				result.setLists(page.getList());		
			}else{
				// 用户信息为空
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.EXCEPTION);
		}
		logger.debug("listMyApplication --> result: {}", result.getResult());
		return result.toString();
	}

	/**
	 * 根据查询条件分页查看我的申请列表
	 * @return
	 */
	@RequestMapping(value="/myApplicationByCondition/list", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String listMyApplicationByCondition(QueryParam queryParam) {
		logger.debug("listMyApplicationByCondition --> queryParam: {}" , queryParam.toString());
		ResultVO<ApplicationInfo> result = new  ResultVO<ApplicationInfo>();
		try {
			//获取当前用户信息
			User currentUser = this.getCurrentUser();
			//判断如果当前用户不为空
			if(null != currentUser && null != queryParam){
				//获取用户Id
				Long userId = currentUser.getId();
				logger.debug("currentUser's userid is : {}" , userId);
				String language = "en";//设置语言默认为英语
				if(StringUtils.isBlank(queryParam.getLanguage())){
					language = queryParam.getLanguage();
				}
				// 设置查询参数
				Map<String, Object> queryMap = new HashMap<String, Object>();
				
				queryMap.put("applicant", userId);
				if(null != queryParam.getOrderType() 
						&& queryParam.getOrderType().equals(tenantId_fiberOpenAccount)){
					queryMap.put("applyService", FiberConstants.businessType.FIBER_OPENACCOUNT);
				}
				if(null != queryParam.getOrderType() 
						&& queryParam.getOrderType().equals(tenantId_fiberExtension)){
					queryMap.put("applyService", FiberConstants.businessType.FIBER_EXTENSION);
				}
				if(StringUtils.isNotBlank(queryParam.getQueryItem()) 
						&& StringUtils.isNotBlank(queryParam.getItemValue())){
					queryMap.put(queryParam.getQueryItem(), queryParam.getItemValue());
				}
				queryMap.put("startTime", queryParam.getStartTime());
				queryMap.put("endTime", queryParam.getEndTime());
				// 获取分页数据
				Page<ApplicationInfo> page = applicationInfoServiceImpl.getByCondition(queryMap);
				// 设置流程状态信息
				setStateInfo(page.getList(),language);
				//给客户端响应
				result.setPage(page);
				result.setLists(page.getList());		
			}else{
				// 用户信息为空
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.EXCEPTION);
		}
		logger.debug("listMyApplicationByCondition --> result: {}", result.getResult());
		return result.toString();
	}
	
	/**
	 * 查看具体申请信息
	 */
	@RequestMapping(value="/preload", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String preload(String dataId) 
	{
		logger.debug("preloadApplication -->dataId: {}", dataId);
		ResultVO<CommonBiz> result = new  ResultVO<CommonBiz>();
		try {
			//获取当前用户信息
			User currentUser = this.getCurrentUser();
			//判断如果当前用户不为空
			if(null != currentUser && null!= dataId){
				//获取用户Id
				String userId = currentUser.getId().toString();
				logger.debug("currentUser's userid is : {}" , userId);
				CommonBiz commonBiz = commonBizService.getByDataId(Long.parseLong(dataId));
				if(null != commonBiz && commonBiz.getCreatePersonId().equals(userId)){	
					result.setOthers("commonBiz",commonBiz);
				}
			}else{
				// 任务对象为空
				result.setResult(ResultVO.FAILURE);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.EXCEPTION);
		}
		return result.toString();
	}
	
	/**
	 * 查看申请详细信息及其流程信息
	 */
	@RequestMapping(value="/detail", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getDetailInfo(String groupId, String processInstanceId, String language) 
	{
		logger.debug("getDetailInfo --> groupId: {}, language: {}" , groupId, language);
		ResultVO<CommonBiz> result = new  ResultVO<CommonBiz>();
		try {
			//获取当前用户信息
			User currentUser = this.getCurrentUser();
			//判断如果当前用户不为空
			if(null != currentUser){
				//获取用户Id
				String userId = currentUser.getId().toString();
				logger.debug("currentUser's userid is : {}" , userId);
				
				if(StringUtils.isBlank(language)){
					language = "en";
				}
				//获取流程状态信息
				String stateInfo = processHessianService.getStateInfoByProcInstId(processInstanceId, language);
				//给客户端响应
				result.setOthers("waitReviewInfo", stateInfo);
				//获取业务数据及流程数据集合
				List<CommonBiz> commonBizList = commonBizService
						.getLatestBizByGroupIdAndStatus(groupId, ApprovedConstants.ProcessResult.CODE_SUCCESS);
				result.setLists(commonBizList);		
			}else{
				// 用户信息为空
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.EXCEPTION);
		}
		logger.debug("getDetailInfo --> result: {}" , result.getResult());
		return result.toString();
	}
	
	/**
	 * 查看申请全部流程信息
	 */
	@RequestMapping(value="/process-info", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getprocessInfo(String groupId) 
	{
		logger.debug("getprocessInfo --> groupId: {}" , groupId);
		ResultVO<CommonBiz> result = new  ResultVO<CommonBiz>();
		try {
			//获取当前用户信息
			User currentUser = this.getCurrentUser();
			//判断如果当前用户不为空
			if(null != currentUser){
				//获取用户Id
				String userId = currentUser.getId().toString();
				logger.debug("currentUser's userid is : {}" , userId);
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("groupId", groupId);
				queryMap.put("status", ApprovedConstants.ProcessResult.CODE_SUCCESS);
				//获取流程数据集合
				List<CommonBiz> commonBizList = commonBizService.getBizListByCommonCondition(queryMap);
				result.setLists(commonBizList);		
			}else{
				// 用户信息为空
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.EXCEPTION);
		}
		logger.debug("getprocessInfo --> result: {}" , result.getResult());
		return result.toString();
	}
	
	/**
	 * 修改申请信息
	 * @return
	 */
	@RequestMapping(value="/application-modify", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String modifyApplication(CommonBiz commonBiz) {
		ResultVO<Object> result = new ResultVO<>();
		// 获取当前用户信息
		User currentUser = this.getCurrentUser();
		String status = ApprovedConstants.ProcessResult.CODE_SUCCESS;
		if (null != currentUser && null != commonBiz) {
			// 获取用户Id
			String userId = currentUser.getId().toString();
			List<Long> roleIds = currentUser.getRoleIds();
			List<String> groupIdList = ListUtil.fromLongToStringList(roleIds);
			// 获取之前的申请信息
			CommonBiz oldCommonBiz = commonBizService.getByDataId(commonBiz
					.getDataId());
			// 只有用户自己可以进行修改操作
			if (null != oldCommonBiz
					&& userId.equals(oldCommonBiz.getCreatePersonId())) {
				// 完成任务
				String taskId = commonBiz.getTaskId();// 任务Id
				logger.debug("application-modify --> taskId: {}", taskId);
				UserTask task = userTaskHessianService.getTask(taskId, "en");
				if (null != task) {
					// 如果任务尚未办理
					if (null == task.getCompleteTime()) {
						// 进行任务认证
						boolean authResult = userTaskHessianService
								.TaskAuthenticate(taskId, userId, groupIdList);
						if (authResult) {
							// 如果任务尚未签收，签收任务
							if (StringUtils.isEmpty(task.getAssignee())) {
								userTaskHessianService.claimTask(taskId, userId);
							}		
							result = processTask(commonBiz, result, currentUser, status,
									userId, oldCommonBiz, task);
						} else {
							// 没有权限
							result.setResult(ResultVO.NOAUTHORITY);
						}
					}else{
						// 用户任务已被办理
						result.setResult(ResultVO.TASKCOMPLETED);
					}
				} else {
					// 任务对象为空
					result.setResult(ResultVO.TASKNULL);
				}
			}else{
				// 没有权限
				result.setResult(ResultVO.NOAUTHORITY);
			}
		} else {
			// 当前用户信息为空
			result.setResult(ResultVO.USERNULL);
		}
		return result.toString();
	}

	private ResultVO<Object> processTask(CommonBiz commonBiz, ResultVO<Object> result,
			User currentUser, String status, String userId,
			CommonBiz oldCommonBiz,  UserTask task) {
		// 设置流程变量
		Map<String, Object> variables = new HashMap<String, Object>();
		// 获取结论
		String reviewResult = commonBiz.getResult();
		// 如果有结论信息
		if (null != reviewResult) {
			logger.debug("reviewResult: {}", reviewResult);
			// 设置审批结果
			setApproveResult(task, variables, reviewResult);
		}
		// 完成任务
		userTaskHessianService.completeTask(task.getTaskId(), variables);
		// 保存修改信息
		// 设置类型
		commonBiz.setServiceType(task.getTaskDefinitionKey());
		// 设置创建者
		commonBiz.setCreatePersonId(userId);
		commonBiz.setCreatePersonName(currentUser.getFirstname());
		commonBiz.setCreatePersonTelno(currentUser.getPhone());
		// 设置创建时间
		commonBiz.setCreateTime(Calendar.getInstance().getTime());
		commonBiz.setServiceOwner(userId);
		// 设置流程实例Id
		commonBiz.setProcessInstanceId(task.getProcInstId());
		commonBiz.setStatus(status);
		Long dataId = commonBizService.save(commonBiz);
		
		// 修改之前的申请信息
		oldCommonBiz.setData1(commonBiz.getData1());
		oldCommonBiz.setUpdatePerson(userId);
		oldCommonBiz.setUpdateTime(new Date());
		commonBizService.update(oldCommonBiz);

		result.setOthers("dataId", dataId);
		return result;
	}
	
	// 设置流程状态信息
	private void setStateInfo(List<ApplicationInfo> list, String language) {
		if(null != list && !list.isEmpty()){
			for(ApplicationInfo app : list){
				// 获取流程实例Id
				String procInstId = app.getProcInstId();
				// 获取流程信息
				String stateInfo = processHessianService.getStateInfoByProcInstId(procInstId, language);
				app.setStateInfo(stateInfo);
			}
		}	
	}

	// 设置审批结果
	private void setApproveResult(UserTask task, Map<String, Object> variables,
			String reviewResult) {
		if (ApprovedConstants.ApproveResult.CODE_AGREE.equals(reviewResult)) {
			// 如果审批意见为通过
			variables.put(task.getTaskDefinitionKey()
					+ FiberConstants.ActivitiContextKey.result,
					ApprovedConstants.ApproveResult.AGREE);
		} else if (ApprovedConstants.ApproveResult.CODE_REJECT
				.equals(reviewResult)) {
			// 审批意见为不通过
			variables.put(task.getTaskDefinitionKey()
					+ FiberConstants.ActivitiContextKey.result,
					ApprovedConstants.ApproveResult.REJECT);
		} else if (ApprovedConstants.ApproveResult.CODE_CANCEL
				.equals(reviewResult)) {
			// 审批意见为结束流程
			variables.put(task.getTaskDefinitionKey()
					+ FiberConstants.ActivitiContextKey.result,
					ApprovedConstants.ApproveResult.CANCEL);
		} else {
			// 审批意见为流转
			variables.put(task.getTaskDefinitionKey()
					+ FiberConstants.ActivitiContextKey.result,
					ApprovedConstants.ApproveResult.TRANS);
		}
	}
}
