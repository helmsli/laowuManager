package com.xinwei.workflow.hessianservice;

import com.xinwei.workflow.entity.WorkFlowProcessResult;

/**
 * 将已有用户、组信息同步到Activiti服务
 * @author xuejinku
 */
public interface SyncUserGroupToActivitiService {

	String KEY_TEL = "tel";//用户信息 --电话号码
	/**
	 * 添加用户
	 * @param userId
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param email
	 * @param tel 
	 */
	WorkFlowProcessResult saveUser(String userId, String firstName, String lastName,
			String password, String email,String tel);
	/**
	 * 添加组
	 * @param groupId
	 * @param groupName
	 * @param groupType
	 */
	WorkFlowProcessResult saveGroup(String groupId, String groupName,
			String groupType);
	/**
	 * 给用户设置所在组
	 * @param userId
	 * @param groupId
	 */
	WorkFlowProcessResult saveMembership(String userId, String groupId);

	
	/**
	 * 修改某个用户
	 * @param user
	 */
	WorkFlowProcessResult updateUser(String userId, String firstName, String lastName,
			String password, String email,String tel);
	
	/**
	 * 修改某个组
	 * @param group
	 */
	WorkFlowProcessResult updateGroup(String groupId, String groupName,
			String groupType);
	
	/**
	 * 删除某个用户
	 * @param userId
	 * @return 
	 */
	WorkFlowProcessResult deleteUser(String userId);
	
	/**
	 * 删除某个组
	 * @param groupId
	 * @return
	 */
	WorkFlowProcessResult deleteGroup(String groupId);
	
	/**
	 * 删除用户组间关系
	 * @param userId
	 * @param groupId
	 * @return
	 */
	WorkFlowProcessResult deleteMembership(String userId, String groupId);
	
	/**
	 * 根据用户Id删除该用户的所有组关系
	 * @param userId 
	 * @return
	 */
	WorkFlowProcessResult deleteMembershipByUId(String userId);
	
	/**
	 * 根据组ID删除用户组间关系
	 * @param groupId 
	 * @return 
	 */
	WorkFlowProcessResult deleteMembershipByGroupId(String groupId);
}
