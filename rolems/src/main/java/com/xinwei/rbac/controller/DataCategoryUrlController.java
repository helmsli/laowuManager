package com.xinwei.rbac.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinwei.rbac.entity.DataCategoryUrl;
import com.xinwei.rbac.service.DataCategoryUrlService;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.page.Page;

@Controller
@RequestMapping("/dataCategoryUrl")
public class DataCategoryUrlController extends BaseController {
	private Logger logger = LoggerFactory
			.getLogger(DataCategoryUrlController.class);
	@Resource
	private DataCategoryUrlService service;// 通用业务数据服务
	
	/**
	 * 新增
	 */
	@RequestMapping(value = "/create", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String create(DataCategoryUrl url) {
		ResultVO<Object> result = new ResultVO<>();
		int save = service.save(url);
		// 给客户端响应
		result.setOthers("result", save);
		return result.toString();
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String update(DataCategoryUrl dataCategoryUrl) {
		service.updateByPrimaryKey(dataCategoryUrl);
		return new ResultVO<>().toString();
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String delete(String dataCategory,String serviceType) {
		service.deleteByCategoryAndServiceType(dataCategory, serviceType);    
		return new ResultVO<>().toString();
	}

	/**
	 * 分页查询所有列表
	 * @param keywords
	 * @return
	 */
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String list(int startRow, int pageSize) {
		List<DataCategoryUrl> list  = service.getAll(startRow,pageSize);
		Page<DataCategoryUrl> page = new Page<DataCategoryUrl>(service.countAll());
		if (list.size() != 0) {
			ResultVO<DataCategoryUrl> result = new ResultVO<DataCategoryUrl>();
			result.setLists(list);
			result.setPage(page);
			return result.toString();
		}
		return "";
	}
	
	/**
	 * 分页按条件查询权限列表
	 * @param dataCategory 数据分类
	 * @param serviceType 业务类型
	 * @param startRow
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/getByConditions", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getByConditions(String dataCategory,
			String serviceType,int startRow, int pageSize) {
		Page<DataCategoryUrl> page = new Page<DataCategoryUrl>(service.countByConditions(dataCategory, serviceType,startRow,pageSize));
		List<DataCategoryUrl> list = service.selectByCategoryAndServiceType(dataCategory, serviceType,startRow,pageSize);
		if (list.size() != 0) {
			ResultVO<DataCategoryUrl> result = new ResultVO<DataCategoryUrl>();
			result.setLists(list);
			result.setPage(page);
			return result.toString();
		}
		return "";
	}
}
