 
package com.xinwei.security.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinwei.security.MessageCode;
import com.xinwei.security.entity.Role;
import com.xinwei.security.entity.User;
import com.xinwei.security.entity.UserRole;
import com.xinwei.security.exception.ServiceException;
import com.xinwei.security.service.RoleService;
import com.xinwei.security.service.UserRoleService;
import com.xinwei.security.service.UserService;
import com.xinwei.security.shiro.UserHelper;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.page.Page;

@Controller
@RequestMapping("/management/user")
public class UserControllerNew {

	@Autowired
	private UserService userService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	private RoleService roleService;
	
	
	
	@ModelAttribute("preloadUser")
	public User getOne(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			User user = userService.get(id);
			return user;
		}
		return null;
	}
	
	
	/**
	 * 查询用户列表
	 * @param keywords
	 * @return
	 */
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String list(String username,Long roleId,Long department_id) {
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("username", username);
		queryMap.put("roleId", roleId);
		queryMap.put("department_id", department_id);
		Page<User> page = userService.findByPage(queryMap);
		//设置用户角色
		List<User> users = page.getList();
		for (User user : users) {
			List<Role> roles = userRoleService.findRolesByUserId(user.getId());
			user.setRoles(roles);
		}
		
		ResultVO<User> resultVO = new ResultVO<User>();
		resultVO.setPage(page);
		resultVO.setLists(page.getList());
		//resultVO.setKeywords(keywords);
		
		return resultVO.toString();
	}
	
	
	
	/**
	 * 查询角色下的用户列表
	 * @param keywords
	 * @return
	 */
	@RequestMapping(value="/getRoleUsers/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getRoleUsers(Long role_id,Long containRoleId) {
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("role_id", role_id);
		queryMap.put("containRoleId", containRoleId);
		Page<User> page = userRoleService.findUsersByRoleId(queryMap);
		
		ResultVO<User> resultVO = new ResultVO<User>();
		resultVO.setPage(page); 
		resultVO.setLists(page.getList());
		//resultVO.setKeywords(keywords);
		
		return resultVO.toString();
	}
	
	
	
	
	/**
	 * 创建用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/create", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String create(User user,@RequestParam(value = "roleId[]", required = false)List<Long> roleId) {	
		user.setRoleIds(roleId);
		user.setCreateTime(new Date());
		userService.save(user);
		
		System.out.println(user);
		return new ResultVO<>().toString();
	}
	
	
	
	
	/**
	 * 获取单个用户信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/getUser", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getUser(Long id) {
		User user = userService.get(id);
		//设置用户角色
		List<Role> roles = userRoleService.findRolesByUserId(id);
		user.setRoles(roles);
		ResultVO<User> result = new ResultVO<>();
		result.setEntity(user);
		return result.toString();
	}
	
	
	/**
	 * 修改用户（管理员修改用户信息）
	 * @param user
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value="/update", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String update(
			@ModelAttribute("preloadUser")User user,
			@RequestParam(value = "roleId[]", required = false)List<Long> roleId) 
	{
		user.setRoleIds(roleId);
		userService.update(user);
		return new ResultVO<>().toString();
	}
	
	
	
	/**
	 * 修改用户（用户修改自身信息）
	 * @param user
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value="/updateSelf", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String updateSelf(@ModelAttribute("preloadUser")User user) throws IllegalAccessException, InvocationTargetException {
		userService.updateSelf(user);
		return new ResultVO<>().toString();
	}
	
	
	
	
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String delete(Long id) {
		userService.delete(id);
		return new ResultVO<>().toString();
	}
	

	
	/**
	 * 重置密码
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/resetPassword", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String reset(@ModelAttribute("preloadUser")User user) {
		userService.resetPassword(user);
		return new ResultVO<>().toString();
	}
	
	
	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/updatePassword", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String updatePassword(String oldPassword,String newPassword) {
		userService.updatePassword(oldPassword,newPassword);
		return new ResultVO<>().toString();
	}
	
	
	
	
	/**
	 * 禁用用户、启用用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/updateDisabled", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String updateDisabled(Long id) {
		User user = userService.get(id);
		user.setIsDisabled(user.getIsDisabled() == 0 ? 1 : 0);
		userService.updateSelf(user);
		return new ResultVO<>().toString();
	}
	
	
	
	
	
	/**
	 * 获取用户的角色
	 * @param id	用户id
	 * @return
	 */
	@RequestMapping(value="/edit/getRoles", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getRoles(Long id) {
		List<Role> hasRoles = userRoleService.findRolesByUserId(id);
		ResultVO<Object> result = new  ResultVO<>();
		result.setOthers("hasRoles", hasRoles);
		return result.toString();
	}
	
	/**
	 * 获取所有的角色
	 * @return
	 */
	@RequestMapping(value="/edit/getAllRoles", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getAllRoles() {
		List<Role> allRoles = roleService.findAll();
		ResultVO<Object> result = new  ResultVO<>();
		result.setOthers("allRoles", allRoles);
		return result.toString();
	}
	
	
	
	/**
	 * 保存分配角色 
	 * @param user  
	 * @return
	 */
	@RequestMapping(value="/create/userRole", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String assignRole(User user,Long[] roleIds) {
		//将前台中的userRole对象中的userId赋值
		List<UserRole> userRoles = new ArrayList<UserRole>();
		
		if(null != roleIds && 0 != roleIds.length)
		{
			UserRole userRole;
			for (Long roleId : roleIds) {
				if(roleId==null) continue;   //当前台传值roleIds=的情况，可以避免继续执行
				userRole = new UserRole();
				userRole.setUser_id(user.getId());
				userRole.setRole_id(roleId);
				userRoles.add(userRole);
			}
		}
		
		userRoleService.updateUserRole(userRoles, user.getId());
		
		return new ResultVO<>().toString();
	}
	
	
	
	/*
	@RequiresPermissions("User:edit")
	@RequestMapping(value="/lookup2delete/userRole/{userId}", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String listUserRole(Map<String, Object> map, @PathVariable Long userId) {
		List<UserRole> userRoles = userRoleService.find(userId);
		map.put("userRoles", userRoles);
		
		ResultVO<UserRole> result = new  ResultVO<UserRole>();
		result.setOthers("userRoles", userRoles);
		//result.setForwardUrl(LOOK_USER_ROLE);
		return result.toString();
	}*/
	
	
	
	
}
