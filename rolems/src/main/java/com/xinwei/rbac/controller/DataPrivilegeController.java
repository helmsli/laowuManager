package com.xinwei.rbac.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinwei.rbac.entity.DataPrivilege;
import com.xinwei.rbac.service.DataPrivilegeService;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.page.Page;

@Controller
@RequestMapping("/dataPrivilege")
public class DataPrivilegeController extends BaseController{

	private Logger logger = LoggerFactory
			.getLogger(DataPrivilegeController.class);
	@Resource
	private DataPrivilegeService service;// 数据权限服务

	/**
	 * 新增
	 */
	@RequestMapping(value = "/create", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String create(DataPrivilege dataPrivilege) {
		ResultVO<Object> result = new ResultVO<>();
		int save = service.save(dataPrivilege);
		// 给客户端响应
		result.setOthers("commbizId", save);
		return result.toString();
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String update(DataPrivilege dataPrivilege) {
		service.updateByCondition(dataPrivilege);
		return new ResultVO<>().toString();
	}

	/**
	 * 删除List
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String delete(String createTime,Integer[] privIds,int startRow,int pageSize) {
		if (privIds!=null) {
			service.deleteByCondition(createTime, Arrays.asList(privIds));
		}
		return new ResultVO<>().toString();
	}

	/**
	 * 分页查询所有列表
	 * @param keywords
	 * @return
	 */
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String list(int startRow,int pageSize) {
		List<DataPrivilege> list = service.getAll(startRow,pageSize);
		Page<DataPrivilege> page = new Page<DataPrivilege>(service.countAll());
		if (list.size() != 0) {
			ResultVO<DataPrivilege> result = new ResultVO<DataPrivilege>();
			result.setLists(list);
			result.setPage(page);
			return result.toString();
		}
		return "";
	}
	
	/**
	 * 分页按条件查询权限列表
	 * @param keywords
	 * @return
	 */
	@RequestMapping(value = "/getByCondition", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getByCondition(String beginTime,
			String endTime,Integer[] privIds,int startRow,int pageSize) {
		List<Integer> privilegePersonLongs= new ArrayList<Integer>();
		if (privilegePersonLongs!=null) {
			List<DataPrivilege> list =service.selectByCondition(beginTime,endTime, Arrays.asList(privIds),startRow,pageSize);
			Page<DataPrivilege> page = new Page<DataPrivilege>(service.countByConditions(beginTime,endTime, Arrays.asList(privIds),startRow,pageSize));
			if (list.size() != 0) {
				ResultVO<DataPrivilege> result = new ResultVO<DataPrivilege>();
				result.setLists(list);
				result.setPage(page);
				return result.toString();
		} 
		}
		return "";
	}
}
