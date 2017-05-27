package com.xinwei.workflow.service;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

/**
 * Activiti用户与组的相关服务
 * @author xuejinku
 */
public interface UserAndGroupService {

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
	void saveUser(String userId, String firstName, String lastName,
			String password, String email,String tel);
	/**
	 * 添加组
	 * @param groupId
	 * @param groupName
	 * @param groupType
	 */
	void saveGroup(String groupId, String groupName,
			String groupType);
	/**
	 * 给用户设置所在组
	 * @param userId
	 * @param groupId
	 */
	void saveMembership(String userId, String groupId);

	/**
	 * 根据用户Id获取用户对象
	 */
	User getUser(String userId);
	
	/**
	 * 根据用户Id、key获取用户扩展信息
	 */
	String getExtUserInfo(String userId,String key);
	
	/**
	 * 查询所有用户
	 * @return
	 */
	List<User> getAllUser();

	/**
	 * 获取用户所在的组列表
	 * @param userId
	 * @return
	 */
	List<Group> getGroupsContaintUser(String userId);

	/**
	 * 获取某个组的所有用户
	 * @param groupId
	 * @return
	 */
	List<User> getUsersInGroup(String groupId);
	
	/**
	 * 修改某个用户
	 * @param user
	 */
	void updateUser(String userId, String firstName, String lastName,
			String password, String email,String tel);
	
	/**
	 * 修改某个组
	 * @param group
	 */
	void updateGroup(String groupId, String groupName,
			String groupType);
	
	/**
	 * 删除某个用户
	 * @param userId
	 * @return 
	 */
	int deleteUser(String userId);
	
	/**
	 * 删除某个组
	 * @param groupId
	 * @return
	 */
	int deleteGroup(String groupId);
	
	/**
	 * 删除用户组间关系
	 * @param userId
	 * @param groupId
	 * @return
	 */
	int deleteMembership(String userId, String groupId);
	
	/**
	 * 根据用户Id删除该用户的所有组关系
	 * @param userId 
	 * @return
	 */
	int deleteMembershipByUId(String userId);
	
	/**
	 * 根据组ID删除用户组间关系
	 * @param groupId 
	 * @return 
	 */
	int deleteMembershipByGroupId(String groupId);
}
