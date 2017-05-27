package com.xinwei.fiber.controller;

import java.util.Calendar;
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
import com.xinwei.fiber.constant.FiberConstants.SortDirection;
import com.xinwei.fiber.entity.ApplicationInfo;
import com.xinwei.fiber.entity.QueryParam;
import com.xinwei.fiber.service.ApplicationInfoService;
import com.xinwei.fiber.service.FiberService;
import com.xinwei.rbac.entity.CommonBiz;
import com.xinwei.rbac.service.CommonBizService;
import com.xinwei.security.entity.User;
import com.xinwei.security.service.UserService;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.system.util.ProcessResult;
import com.xinwei.util.JsonUtil;
import com.xinwei.util.ListUtil;
import com.xinwei.util.page.Page;
import com.xinwei.workflow.entity.Assignee;
import com.xinwei.workflow.entity.UserTask;
import com.xinwei.workflow.hessianservice.ProcessHessianService;
import com.xinwei.workflow.hessianservice.UserTaskHessianService;

/**
 * 用户任务控制层
 * @author xuejinku
 *
 */
@Controller
@RequestMapping("/task")
public class UserTaskController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(UserTaskController.class);
	@Resource
	private UserTaskHessianService userTaskHessianService;//用户任务服务
	@Resource
	private ProcessHessianService processHessianService;// 流程服务
	@Resource
	private ApplicationInfoService applicationInfoServiceImpl; // 申请信息服务
	@Resource
	private CommonBizService commonBizService;//通用业务数据服务
	@Resource
	private UserService userService;//用户信息服务
	@Resource
	private FiberService fiberServiceImpl;//光纤服务
	
	@Value("${tenantId_fiberOpenAccount}")
	private Integer tenantId_fiberOpenAccount;// 光纤开户业务类型ID
	@Value("${tenantId_fiberExtension}")
	private Integer tenantId_fiberExtension;// 光纤延期业务类型ID
	
	/**
	 * 签收任务
	 * @return
	 */
	@RequestMapping(value="/claim", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String verifyCompanyName(String taskId) {
	
		ResultVO<Object> result = new  ResultVO<>();
		//获取当前用户信息
		User currentUser = this.getCurrentUser();
		//获取用户Id
		String userId = currentUser.getId().toString();
		List<Long> roleIdList = currentUser.getRoleIds();
		List<String> groupIdList = ListUtil.fromLongToStringList(roleIdList);
		//调用用户任务Hessian服务进行认证
		boolean authResult = userTaskHessianService.TaskAuthenticate(taskId, userId, groupIdList);
		
		if (authResult) {
			//认证通过，签收任务
			userTaskHessianService.claimTask(taskId, userId);
		}else{
			//没有权限
			result.setResult(ResultVO.NOAUTHORITY);
		}
		return result.toString();
	}
	
	/**
	 * 获取我的待办任务列表
	 * @return
	 */
	@RequestMapping(value="/myToDoTask/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String listMyToDoTask(String language) {
		logger.debug("listMyToDoTask --> start! ");
		ResultVO<UserTask> result = new  ResultVO<UserTask>();
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
				// 获取分页数据
				Long count = userTaskHessianService.countActiveTasksByUid(userId);
				Page<UserTask> page = new Page<UserTask>(count);
				List<UserTask> taskList = userTaskHessianService
						.getActiveTaskListByUid(userId, language, SortDirection.DESC, page.getStartRow(), page.getPageSize());
				
				// 设置业务数据信息
				setBusinessInfoForUserTask(taskList);
				
				//给客户端响应
				result.setPage(page);
				result.setLists(taskList);		
			}else{
				// 用户信息为空
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.EXCEPTION);
		}
		logger.debug("listMyToDoTask --> result: {}" , result.getResult());
		return result.toString();
	}
	
	/**
	 * 获取我的待办任务列表
	 * @return
	 */
	@RequestMapping(value="/myToDoTaskByCondition/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String listMyToDoTaskByCondition(QueryParam queryParam) {
		logger.debug("listMyToDoTaskByCondition --> queryParam: {}" , queryParam.toString());
		ResultVO<UserTask> result = new  ResultVO<UserTask>();
		try {
			//获取当前用户信息
			User currentUser = this.getCurrentUser();
			//判断如果当前用户不为空
			if(null != currentUser){
				//获取用户Id
				String userId = currentUser.getId().toString();
				logger.debug("currentUser's userid is : {}" , userId);
				String language = "en";
				if(StringUtils.isNotBlank(queryParam.getLanguage())){
					language = queryParam.getLanguage();
				}
				//待办任务分页数据
				Page<UserTask> page = new Page<UserTask>(0L);
				List<UserTask> taskList = null;
				Map<String, Object> queryMap = new HashMap<String, Object>();
				// 设置查询参数
				setQueryCondition(queryParam, queryMap);
				// 获取符合查询条件的流程实例Id集合
				List<String> processInstanceIds = applicationInfoServiceImpl.getProcInstIdByCondition(queryMap);
				if(null != processInstanceIds && !processInstanceIds.isEmpty()){

					// 获取待办任务分页数据
					Long count = userTaskHessianService.countActiveTasksByUidProcInstIds(userId, processInstanceIds);
					page = new Page<UserTask>(count);
					taskList = userTaskHessianService
							.getActiveTaskListByUidProcInstIds(userId, processInstanceIds, 
									language, SortDirection.DESC, page.getStartRow(), page.getPageSize());
					// 设置业务数据信息
					setBusinessInfoForUserTask(taskList);
				}
				//给客户端响应
				result.setPage(page);
				result.setLists(taskList);		
			}else{
				// 用户信息为空
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.EXCEPTION);
		}
		logger.debug("listMyToDoTaskByCondition --> result: {}" , result.getResult());
		return result.toString();
	}

	/**
	 * 获取已完成任务列表
	 */
	@RequestMapping(value="/getCompletedTask/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String listCompletedTask(String language) 
	{
		logger.debug("listCompletedTask --> start! ");
		ResultVO<UserTask> result = new  ResultVO<UserTask>();
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
				// 获取分页数据
				Long count = userTaskHessianService.countFinishedTasksByUid(userId);
				Page<UserTask> page = new Page<UserTask>(count);
				List<UserTask> taskList = userTaskHessianService
						.getFinishedTaskListByUid(userId, language, SortDirection.DESC, page.getStartRow(), page.getPageSize());
				
				// 设置业务数据、流程状态信息
				setBusinessProcessInfoForUserTask(taskList,language);
				
				//给客户端响应
				result.setPage(page);
				result.setLists(taskList);		
			}else{
				// 用户信息为空
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.EXCEPTION);
		}
		logger.debug("listCompletedTask --> result: {}" , result.getResult());
		return result.toString();
	}
	
	/**
	 * 根据查询条件分页获取已完成任务列表
	 */
	@RequestMapping(value="/getCompletedTaskByCondition/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getCompletedTaskByCondition(QueryParam queryParam) 
	{
		logger.debug("getCompletedTaskByCondition --> queryParam: {}" , queryParam.toString());
		ResultVO<UserTask> result = new  ResultVO<UserTask>();
		try {
			//获取当前用户信息
			User currentUser = this.getCurrentUser();
			//判断如果当前用户不为空
			if(null != currentUser){
				//获取用户Id
				String userId = currentUser.getId().toString();
				logger.debug("currentUser's userid is : {}" , userId);
				String language = "en";
				if(StringUtils.isNotBlank(queryParam.getLanguage())){
					language = queryParam.getLanguage();
				}
				Page<UserTask> page = new Page<UserTask>(0L);
				List<UserTask> taskList =  null;
				Map<String, Object> queryMap = new HashMap<String, Object>();
				// 设置查询参数
				setQueryCondition(queryParam, queryMap);
				// 获取符合查询条件的流程实例Id集合
				List<String> processInstanceIds = applicationInfoServiceImpl.getProcInstIdByCondition(queryMap);
				if(null != processInstanceIds && !processInstanceIds.isEmpty()){
					
					// 获取已完成任务分页数据
					Long count = userTaskHessianService.countFinishedTasksByUidProcInstIds(userId, processInstanceIds);
					page = new Page<UserTask>(count);
					taskList = userTaskHessianService
							.getFinishedTaskListByUidProcInstIds(userId, processInstanceIds, 
									language, SortDirection.DESC, page.getStartRow(), page.getPageSize());
					// 设置业务数据、流程状态信息
					setBusinessProcessInfoForUserTask(taskList,language);
				}
				
				//给客户端响应
				result.setPage(page);
				result.setLists(taskList);		
			}else{
				// 用户信息为空
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.EXCEPTION);
		}
		logger.debug("getCompletedTaskByCondition --> result: {}" , result.getResult());
		return result.toString();
	}

	
	/**
	 * 任务办理（完成任务）
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value="/complete", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String complete(CommonBiz commonBiz) {
		logger.debug("completeTask --> {}" , commonBiz.toString());
		ResultVO<Object> result = new ResultVO<>();
		try {
			//获取当前登录用户信息
			User currentUser = getCurrentUser();
			//判断如果当前用户不为空，并且业务数据对象不为空
			if(null != currentUser && null != commonBiz){
				//当前用户ID
				String userId = currentUser.getId().toString();
				logger.debug("currentUser's userid is : {}" , userId);
				// 当前用户角色列表
				List<Long> roleIdList = currentUser.getRoleIds();
				List<String> groupIdList = ListUtil.fromLongToStringList(roleIdList);
				// 任务Id
				String taskId = commonBiz.getTaskId();
				logger.debug("completeTask --> taskId: {}", taskId);
				UserTask task = userTaskHessianService.getTask(taskId, "en");
				if (null != task ) {
					//如果任务尚未办理
					if(null == task.getCompleteTime()){
						//进行任务认证
						boolean authResult = userTaskHessianService.TaskAuthenticate(taskId, userId, groupIdList);
						if(authResult){
							//如果任务尚未签收，签收任务
							if(StringUtils.isEmpty(task.getAssignee())){
								userTaskHessianService.claimTask(taskId, userId);
							}
							//办理任务
							result = processTask(commonBiz, result, currentUser, userId, task);
						}else{
							//没有权限
							result.setResult(ResultVO.NOAUTHORITY);
						}
					}else{
						// 用户任务已被办理
						result.setResult(ResultVO.TASKCOMPLETED);
					}
				} else {
					//任务对象为空
					result.setResult(ResultVO.TASKNULL);
				}
			}else{
				// 当前登录用户信息为空
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.EXCEPTION);
		}	
		logger.debug("completeTask --> result: {}" , result.getResult());
		return result.toString();
	}

	//处理任务
	private ResultVO<Object> processTask(CommonBiz commonBiz, ResultVO<Object> result,
			User currentUser, String userId, UserTask task) {
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
		
		// 设置流程变量
		Map<String, Object> variables = new HashMap<String, Object>();
		// 获取结论
		String reviewResult = commonBiz.getResult();
		// 如果有结论信息
		if (StringUtils.isNoneBlank(reviewResult)) {
			logger.debug("reviewResult: {}" , reviewResult);
			// 设置审批结果
			setApproveResult(task, variables, reviewResult);
		}
		// 光纤业务处理结果
		ProcessResult processResult = null;
		String status = ApprovedConstants.ProcessResult.CODE_SUCCESS;
		//光纤开户信息确认人员进行确认
		if(task.getTaskDefinitionKey().equals(FiberConstants.State.INFOCONFIRMER_CONFIRM)){
			if(ApprovedConstants.ApproveResult.CODE_AGREE.equals(reviewResult)){
			    // 如果信息确认通过，进行开户操作
				processResult = fiberServiceImpl.openAccount(commonBiz);
			}
		}
		// 光纤延期财务审核
		if(task.getTaskDefinitionKey().equals(FiberConstants.State.EXTENSION_FINANCEADMIN_REVIEW)){
			if(ApprovedConstants.ApproveResult.CODE_AGREE.equals(reviewResult)){
				// 如果财务审核通过,进行延期操作
				processResult = fiberServiceImpl.extension(commonBiz.getGroupId());
			}
			// 如果财务流转给网管
			if(ApprovedConstants.ApproveResult.CODE_TRANS.equals(reviewResult)){
				// 设置网管人员
				String resultExt = commonBiz.getResultExt();
				Assignee assignee = JsonUtil.fromJson(resultExt,Assignee.class);
				if(null != assignee){
					if(Assignee.User == assignee.getAssigneeType()){
						// 设置网络管理人员
						variables.put("networkAdmin", assignee.getId().toString());
						variables.put("networkAdminGroup","");
					}else{
						// 设置网络管理人员组
						variables.put("networkAdmin", null);
						variables.put("networkAdminGroup", assignee.getId().toString());
					}
				}
			}
		}
		// 光纤延期网管审核
		if(task.getTaskDefinitionKey().equals(FiberConstants.State.NETWORKADMIN_REVIEW)){
			if(ApprovedConstants.ApproveResult.CODE_AGREE.equals(reviewResult)){
				// 如果财务审核通过
				processResult = fiberServiceImpl.extension(commonBiz.getGroupId());
			}
		}
		// 如果操作失败
		if(null != processResult && !processResult.getStatus().equals(ProcessResult.SUCCESS_CODE)){
			result.setResult(processResult.getStatus().toString());
			status = processResult.getStatus().toString();
			// 记录操作结果
			commonBiz.setStatus(status);
			// 保存任务处理数据
			Long dataId = commonBizService.save(commonBiz);
			result.setOthers("dataId", dataId.toString());
			return result;
		}
		// 记录操作结果
		commonBiz.setStatus(status);
		// 保存任务处理数据
		Long dataId = commonBizService.save(commonBiz);
		// 完成任务
		userTaskHessianService.completeTask(task.getTaskId(), variables);
		result.setOthers("dataId", dataId.toString());
		return result;
	}

	// 设置审批结果
	private void setApproveResult(UserTask task, Map<String, Object> variables,
			String reviewResult) {
		if (ApprovedConstants.ApproveResult.CODE_AGREE.equals(reviewResult)) {
			// 如果审批意见为通过
			variables.put(task.getTaskDefinitionKey()
					+ FiberConstants.ActivitiContextKey.result,
					ApprovedConstants.ApproveResult.AGREE);
		} else if (ApprovedConstants.ApproveResult.CODE_REJECT.equals(reviewResult)) {
			// 审批意见为不通过
			variables.put(task.getTaskDefinitionKey()
					+ FiberConstants.ActivitiContextKey.result,
					ApprovedConstants.ApproveResult.REJECT);
		} else if(ApprovedConstants.ApproveResult.CODE_CANCEL.equals(reviewResult)){
			// 审批意见为结束流程
			variables.put(task.getTaskDefinitionKey()
					+ FiberConstants.ActivitiContextKey.result,
					ApprovedConstants.ApproveResult.CANCEL);
		}else{
			// 审批意见为流转
			variables.put(task.getTaskDefinitionKey()
					+ FiberConstants.ActivitiContextKey.result,
					ApprovedConstants.ApproveResult.TRANS);
		}
	}
	
	//设置查询条件
	private void setQueryCondition(QueryParam queryParam,
			Map<String, Object> queryMap) {
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
	}
	
	// 设置业务数据信息
	private void setBusinessInfoForUserTask(List<UserTask> taskList) {
		if(null != taskList && !taskList.isEmpty()){
			for(UserTask task : taskList){
				// 获取业务数据Id
				String dataId = task.getDataId();
				// 获取申请信息
				ApplicationInfo applicationInfo =applicationInfoServiceImpl.getByDataId(dataId);
				if(null != applicationInfo){
					//设置申请人信息
					User applicantInfo = userService.get(applicationInfo.getApplicant());
					applicationInfo.setApplicantInfo(applicantInfo);
					task.getExtData().put("applicationInfo", applicationInfo);
				}
			}
		}
	}

	// 设置业务数据信息和流程状态信息
	private void setBusinessProcessInfoForUserTask(List<UserTask> taskList,String language) {
		if (null != taskList && !taskList.isEmpty()) {
			for (UserTask task : taskList) {
				// 获取业务数据Id
				String dataId = task.getDataId();
				// 获取流程实例Id
				String procInstId = task.getProcInstId();
				// 获取申请信息
				ApplicationInfo applicationInfo = applicationInfoServiceImpl
						.getByDataId(dataId);
				if (null != applicationInfo) {
					// 设置申请人信息
					User applicantInfo = userService.get(applicationInfo
							.getApplicant());
					applicationInfo.setApplicantInfo(applicantInfo);
					task.getExtData().put("applicationInfo", applicationInfo);
				}
				// 获取流程状态信息
				String stateInfo = processHessianService.getStateInfoByProcInstId(procInstId, language);
				task.getExtData().put("stateInfo",stateInfo);
			}
		}
	}
}
