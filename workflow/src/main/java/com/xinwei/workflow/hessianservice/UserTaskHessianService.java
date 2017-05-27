package com.xinwei.workflow.hessianservice;

import java.util.List;
import java.util.Map;
import com.xinwei.workflow.entity.UserTask;

/**
 * 用户任务相关服务
 * @author xuejinku
 *
 */
public interface UserTaskHessianService {
	
	/**
	 * 根据用户ID获取用户所有待办任务列表
	 * @param userId 用户ID
	 * @param language 语言（zh、en、es等）
	 * @param sortDirection 排序方向
	 * @return
	 */
	List<UserTask> getActiveTasksByUid(String userId, String language, String sortDirection);
	
	/**
	 * 根据用户ID获取用户已办理的所有任务列表
	 * @param userId 用户ID
	 * @param language 语言（zh、en、es等）
	 * @param sortDirection 排序方向
	 * @return 用户已完成任务列表
	 */
	List<UserTask> getFinishedTasksByUid(String userId, String language, String sortDirection);
	
	/**
	 * 根据用户Id统计用户待办任务数
	 * @param userId 用户Id
	 * @return
	 */
	Long countActiveTasksByUid(String userId);
	
	/**
	 * 根据用户ID分页获取用户待办任务列表
	 * @param userId 用户ID
	 * @param language 语言（zh、en、es等）
	 * @param sortDirection 排序方向
	 * @param startRow 起始索引
	 * @param pageSize 获取记录数
	 * @return
	 */
	List<UserTask> getActiveTaskListByUid(String userId, String language, String sortDirection, int startRow,int pageSize);
	
	/**
	 * 根据用户Id和流程实例Id集合统计用户待办任务数
	 * @param userId 用户Id
	 * @param processInstanceIds 流程实例Id集合
	 * @return
	 */
	Long countActiveTasksByUidProcInstIds(String userId,List<String> processInstanceIds);
	
	/**
	 * 根据用户ID和流程实例Id集合分页获取用户待办任务列表
	 * @param userId 用户ID
	 * @param processInstanceIds 流程实例Id集合
	 * @param language 语言（zh、en、es等）
	 * @param sortDirection 排序方向
	 * @param startRow 起始索引
	 * @param pageSize 获取记录数
	 * @return
	 */
	List<UserTask> getActiveTaskListByUidProcInstIds(String userId, List<String> processInstanceIds, String language, String sortDirection, int startRow,int pageSize);
	
	/**
	 * 根据用户ID统计用户已完成任务数
	 * @param userId 用户ID
	 * @return
	 */
	Long countFinishedTasksByUid(String userId);
	
	/**
	 * 根据用户ID分页获取用户已办理的任务列表
	 * @param userId 用户ID
	 * @param language 语言（zh、en、es等）
	 * @param sortDirection 排序方向
	 * @param startRow 起始索引
	 * @param pageSize 获取记录数
	 * @return 用户已完成任务列表
	 */
	List<UserTask> getFinishedTaskListByUid(String userId, String language, String sortDirection, int startRow,int pageSize);
	
	/**
	 * 根据用户ID和流程实例Id集合统计用户已完成任务数
	 * @param userId 用户ID
	 * @param processInstanceIds 流程实例Id集合
	 * @return
	 */
	Long countFinishedTasksByUidProcInstIds(String userId,List<String> processInstanceIds);
	
	/**
	 * 根据用户ID和流程实例Id集合分页获取用户已办理的任务列表
	 * @param userId 用户ID
	 * @param processInstanceIds 流程实例Id集合
	 * @param language 语言（zh、en、es等）
	 * @param sortDirection 排序方向
	 * @param startRow 起始索引
	 * @param pageSize 获取记录数
	 * @return 用户已完成任务列表
	 */
	List<UserTask> getFinishedTaskListByUidProcInstIds(String userId,List<String> processInstanceIds, String language, String sortDirection, int startRow,int pageSize);
	
	/**
	 * 根据用户ID和任务类别ID统计用户待办任务数
	 * @param userId 用户ID
	 * @param tenantId 业务种类标识
	 * @return
	 */
	Long countActiveTasksByUidAndTenantId(String userId,String tenantId);
	
	/**
	 * 根据用户ID和任务类别ID,分页获取用户待办任务列表
	 * @param userId 用户ID
	 * @param tenantId 业务种类标识
	 * @param language 语言（zh、en、es等）
	 * @param sortDirection 排序方向
	 * @param startRow 起始索引
	 * @param pageSize 获取记录数
	 * @return
	 */
	List<UserTask> getActiveTaskListByUidAndTenantId(String userId,String tenantId,String language,String sortDirection,int startRow,int pageSize);
	
	/**
	 * 根据用户ID和任务类别ID获取用户已完成任务数
	 * @param userId 用户ID
	 * @param tenantId 业务种类标识
	 * @return
	 */
	Long countFinishedTasksByUidAndTenantId(String userId,String tenantId);
	
	/**
	 * 根据用户ID和任务类别ID,分页获取用户已完成任务列表
	 * @param userId 用户ID
	 * @param tenantId 业务种类标识
	 * @param language 语言（zh、en、es等）
	 * @param sortDirection 排序方向
	 * @param startRow 起始索引
	 * @param pageSize 获取记录数
	 * @return
	 */
	List<UserTask> getFinishedTasksByUidAndTenantId(String userId,String tenantId,String language,String sortDirection,int startRow,int pageSize);
	
	/**
	 * 根据taskId获取某个用户任务（正在进行或已完成）
	 * @param taskId 任务ID
	 * @param language 语言（zh、en、es等）
	 * @return
	 */
	UserTask getTask(String taskId,String language);
	
	/**
	 * 根据taskId获取任务的办理人（未办理或已办理）
	 * @param taskId 任务ID
	 * @return 办理人ID
	 */
	String getTaskAssignee(String taskId);
	
	/**
	 * 设置任务的办理人（未办理）
	 * @param taskId 任务ID
	 * @param userId 办理人Id
	 * @return 
	 */
	void setTaskAssignee(String taskId, String userId);
	
	/**
	 * 添加任务的候选人（未办理）
	 * @param taskId 任务ID
	 * @param userId 候选人Id
	 * @return 
	 */
	void addTaskCandidateUser(String taskId, String userId);
	
	/**
	 * 添加任务的候选组（未办理）
	 * @param taskId 任务ID
	 * @param groupId 候选组Id
	 * @return 
	 */
	void addTaskCandidateGroup(String taskId, String groupId);
	
	/**
	 * 签收某个任务
	 * @param taskId 任务ID
	 * @param userId 用户ID
	 */
	void claimTask(String taskId, String userId);
	
	/**
	 * 完成任务
	 * @param taskId 任务Id
	 * @param variables 流程变量
	 */
	void completeTask(String taskId, Map<String, Object> variables);
	
	/**
	 * 根据流程实例ID查询某个流程当前待处理的所有用户任务
	 * @param processInstanceId 流程实例ID
	 * @param language 语言（zh、en、es等）
	 * @param sortDirection 排序方向
	 * @return
	 */
	List<UserTask> getActiveTasksByProcessInstanceId(
			String processInstanceId,String language,String sortDirection);
	
	/**
	 * 根据流程实例ID查询某个流程已经处理的所有用户任务
	 * @param processInstanceId 流程实例ID
	 * @param language 语言（zh、en、es等）
	 * @param sortDirection 排序方向
	 * @return
	 */
	List<UserTask> getFinishedTasksByProcessInstanceId(
			String processInstanceId,String language, String sortDirection);
	
	/** 
     * 根据流程实例ID和任务定义key查询任务集合 
     *  
     * @param processInstanceId 流程实例Id
     * @param taskDefinitionkey 任务定义key
     * @param language 语言（zh、en、es等）
     * @param sortDirection 排序方向
     * @return 
     */  
    List<UserTask> getTasksByDefinitionKey(String processInstanceId, String taskDefinitionkey,String language ,String sortDirection);  
	
    /**
	 * 根据流程实例ID和任务定义key统计该任务执行的次数
	 * @param processInstanceId 流程实例Id
     * @param taskDefinitionkey 任务定义key
	 * @return
	 */
	Long countTasksByDefinitionKey(String processInstanceId, String taskDefinitionkey);
	
	/**
	 * 根据业务key获取当前待处理的任务列表
	 * @param businessKey 业务key
	 * @param language 语言（zh、en、es等）
	 * @param sortDirection 排序方向
	 * @return 已完成任务列表
	 */
	List<UserTask> getActiveTasksByBusinessKey(String businessKey,String language, String sortDirection);
	
	/**
	 * 根据业务key获取已完成任务列表
	 * @param businessKey 业务key
	 * @param language 语言（zh、en、es等）
	 * @param sortDirection 排序方向
	 * @return 已完成任务列表
	 */
	List<UserTask> getFinishedTasksByBusinessKey(String businessKey,String language, String sortDirection);

	/**
	 * 用户任务认证
	 * @param taskId 任务Id
	 * @param userId 用户Id
	 * @param groupIdList 组ID的列表
	 * @return
	 */
	boolean TaskAuthenticate(String taskId,String userId,List<String> groupIdList);
	
	/**
	 * 判断任务是否已被办理
	 * @param taskId 任务Id
	 * @return true:已完成，false:未办理
	 */
	boolean TaskFinished(String taskId);
}
