package com.xinwei.workflow.hessianservice.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xinwei.workflow.entity.WorkFlowProcessResult;
import com.xinwei.workflow.hessianservice.SyncUserGroupToActivitiService;
import com.xinwei.workflow.service.UserAndGroupService;

@Service
public class SyncUserGroupForActivitiServiceImpl implements SyncUserGroupToActivitiService {

	@Resource
	private IdentityService identityService ;
	@Resource
	private UserAndGroupService userAndGroupServiceImpl;
	private Logger logger = LoggerFactory.getLogger(SyncUserGroupForActivitiServiceImpl.class);
	
	@Override
	public WorkFlowProcessResult saveUser(String userId, String firstName, String lastName,
			String password, String email,String tel) {
		logger.debug("saveUser--> userId: " + userId + " firstName: " + firstName);
		WorkFlowProcessResult result = WorkFlowProcessResult.getSuccessResult("save user success!");
		try {
			//检查该用户是否已经存在
			User existUser = identityService.createUserQuery()
				.userId(userId.toString()).singleResult();
			if(null!= existUser){
				identityService.deleteUserInfo(userId, KEY_TEL);
				existUser.setFirstName(firstName);
				existUser.setLastName(lastName);
				existUser.setPassword(password);
				existUser.setEmail(email);
				identityService.saveUser(existUser);
				
			}else{ 
				// 根据传入的userID生成一个Activiti 中的User对象
				User user = identityService.newUser(userId.toString());
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setPassword(password);
				user.setEmail(email);
				// 保存用户到acticiti 数据库
				identityService.saveUser(user);
			}
			
			identityService.setUserInfo(userId, KEY_TEL, tel);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(WorkFlowProcessResult.DEFAULT_ERROR_CODE);
			result.setMessage("exception : " +e.getMessage());
		}
		logger.debug("saveUser--> result: " + result.toString());
		return result;
	}

	@Override
	public WorkFlowProcessResult saveGroup(String groupId, String groupName, String groupType){
		logger.debug("saveGroup--> groupId: " + groupId + "groupName: " + groupName );
		WorkFlowProcessResult result = WorkFlowProcessResult.getSuccessResult("save group success!");
		try {
			//检查该组是否已经存在
			Group existGroup = identityService.createGroupQuery()
					.groupId(groupId).singleResult();
			if(null!= existGroup){
				
				existGroup.setName(groupName);
				existGroup.setType(groupType);
				identityService.saveGroup(existGroup);
			   
			} else {
				// 创建新的组对象
				Group group = identityService.newGroup(groupId);
				group.setName(groupName);
				group.setType(groupType);
				// 保存组对象
				identityService.saveGroup(group);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(WorkFlowProcessResult.DEFAULT_ERROR_CODE);
			result.setMessage("exception : " +e.getMessage());
		}
		logger.debug("saveGroup--> result: " + result.toString());
		return result;
	}
	
	@Override
	public WorkFlowProcessResult saveMembership(String userId, String groupId){
		WorkFlowProcessResult result = WorkFlowProcessResult.getSuccessResult("save membership success!");
		logger.debug("saveMembership--> userId： " + userId + " groupId: " + groupId);
		 try {
			//将用户加入相应的组中
			identityService.createMembership(userId, groupId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(WorkFlowProcessResult.DEFAULT_ERROR_CODE);
			result.setMessage("exception : " +e.getMessage());
		} 
		logger.debug("saveMembership--> result: " + result.toString());
		return result;
	}

	@Override
	public WorkFlowProcessResult updateUser(String userId, String firstName, String lastName,
			String password, String email,String tel) {
		logger.debug("update User--> userId: " + userId + " firstName: " + firstName);
		WorkFlowProcessResult result = WorkFlowProcessResult.getSuccessResult("update user success!");
		try {
			// 检查该用户是否已经存在
			User existUser = identityService.createUserQuery().userId(userId)
					.singleResult();
			if (null != existUser) {

				identityService.deleteUserInfo(userId, KEY_TEL);
				existUser.setFirstName(firstName);
				existUser.setLastName(lastName);
				existUser.setPassword(password);
				existUser.setEmail(email);
				identityService.saveUser(existUser);
				identityService.setUserInfo(userId, KEY_TEL, tel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(WorkFlowProcessResult.DEFAULT_ERROR_CODE);
			result.setMessage("exception : " +e.getMessage());
		}
		logger.debug("update user--> result: " + result.toString());
		return result;
	}

	@Override
	public WorkFlowProcessResult updateGroup(String groupId, String groupName, String groupType) {
		logger.debug("update Group--> groupId: " + groupId + "groupName: " + groupName );
		WorkFlowProcessResult result = WorkFlowProcessResult.getSuccessResult("update group success!");
		try {
			// 检查该组是否已经存在
			Group existGroup = identityService.createGroupQuery()
					.groupId(groupId).singleResult();
			if (null != existGroup) {

				existGroup.setName(groupName);
				existGroup.setType(groupType);
				identityService.saveGroup(existGroup);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(WorkFlowProcessResult.DEFAULT_ERROR_CODE);
			result.setMessage("exception : " +e.getMessage());
		}
		logger.debug("update group--> result: " + result.toString());
		return result;
	}


	@Override
	public WorkFlowProcessResult deleteUser(String userId) {
		logger.debug("delete user--> userId: " + userId );
		WorkFlowProcessResult result = WorkFlowProcessResult.getSuccessResult("delete user success!");
		try {
			if(null != userId){
				identityService.deleteUser(userId);
			}else{
				result.setStatus(WorkFlowProcessResult.DEFAULT_ERROR_CODE);
				result.setMessage("userId is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(WorkFlowProcessResult.DEFAULT_ERROR_CODE);
			result.setMessage("exception : " +e.getMessage());
		}
		logger.debug("delete user--> result: " + result.toString());
		return result;
	}

	@Override
	public WorkFlowProcessResult deleteGroup(String groupId) {
		logger.debug("deleteGroup--> groupId: " + groupId );
		WorkFlowProcessResult result = WorkFlowProcessResult.getSuccessResult("delete group success!");
		try {
			if(null != groupId){
				identityService.deleteGroup(groupId);
			}else{
				result.setStatus(WorkFlowProcessResult.DEFAULT_ERROR_CODE);
				result.setMessage("groupId is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(WorkFlowProcessResult.DEFAULT_ERROR_CODE);
			result.setMessage("exception : " +e.getMessage());
		}
		logger.debug("delete group--> result: " + result.toString());
		return result;
	}

	@Override
	public WorkFlowProcessResult deleteMembership(String userId, String groupId) {
		logger.debug("deleteMembership--> userId: " + userId + " groupId: "
				+ groupId);
		WorkFlowProcessResult result = WorkFlowProcessResult
				.getSuccessResult("deleteMembership success!");
		try {
			if (null != userId && null != groupId) {
				identityService.deleteMembership(userId, groupId);
			} else {
				result.setStatus(WorkFlowProcessResult.DEFAULT_ERROR_CODE);
				result.setMessage("Id is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(WorkFlowProcessResult.DEFAULT_ERROR_CODE);
			result.setMessage("exception : " + e.getMessage());
		}
		logger.debug("delete membership--> result: " + result.toString());
		return result;
	}

	@Override
	public WorkFlowProcessResult deleteMembershipByUId(String userId) {
		logger.debug("deleteMembershipByUId --> userId: " + userId );
		WorkFlowProcessResult result = WorkFlowProcessResult.getSuccessResult("deleteMembershipByUId success!");
		try {
			if (null != userId) {
				List<Group> groups = userAndGroupServiceImpl.getGroupsContaintUser(userId);
				if (null != groups) {
					for (Group group : groups) {
						this.deleteMembership(userId, group.getId());
					}
				} 
			} else {
				result.setStatus(WorkFlowProcessResult.DEFAULT_ERROR_CODE);
				result.setMessage("userId is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(WorkFlowProcessResult.DEFAULT_ERROR_CODE);
			result.setMessage("exception : " + e.getMessage());
		}
		logger.debug("deleteMembershipByUId--> result: " + result.toString());
		return result;
	}

	@Override
	public WorkFlowProcessResult deleteMembershipByGroupId(String groupId) {
		logger.debug("deleteMembershipByGroupId--> groupId: " + groupId );
		WorkFlowProcessResult result = WorkFlowProcessResult.getSuccessResult("deleteMembershipByGroupId success!");
		try {
			if (null != groupId) {
				List<User> users = userAndGroupServiceImpl.getUsersInGroup(groupId);
				if (null != users) {
					for (User user : users) {
						this.deleteMembership(user.getId(),groupId);
					}
					
				}
			} else {
				result.setStatus(WorkFlowProcessResult.DEFAULT_ERROR_CODE);
				result.setMessage("groupId is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(WorkFlowProcessResult.DEFAULT_ERROR_CODE);
			result.setMessage("exception : " + e.getMessage());
		}
		logger.debug("deleteMembershipByGroupId--> result: " + result.toString());
		return result;
	}

}
