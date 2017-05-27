package com.xinwei.rbac.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinwei.rbac.entity.CommonBiz;
import com.xinwei.rbac.service.CommonBizService;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.page.Page;

@Controller
@RequestMapping("/commonBiz")
public class CommonBizController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(CommonBizController.class);
	@Resource
	private CommonBizService service;// 通用业务数据服务

	/**
	 * 新增
	 */
	@RequestMapping(value = "/create", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String create(CommonBiz commonBiz) {
		ResultVO<Object> result = new ResultVO<>();
		Long save = service.save(commonBiz);
		// 给客户端响应
		result.setOthers("commbizId", save);
		return result.toString();
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String update(CommonBiz biz) {
		CommonBiz oldEntity = service.getByDataId(biz.getDataId());
		// FIXME
		biz.setCreatePersonId(oldEntity.getCreatePersonId());
		biz.setCreatePersonName(oldEntity.getCreatePersonName());
		biz.setCreateTime(oldEntity.getCreateTime());

		service.update(biz);
		return new ResultVO<>().toString();
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/updateProcessIdByDataId", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String updateProcessIdByDataId(String status, Long dataId) {
		service.updateStatusByDataId(status, dataId);
		return new ResultVO<>().toString();
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String delete(Long dataId) {
		service.deleteByDataId(dataId);
		return new ResultVO<>().toString();
	}

	/**
	 * 分页查询所有列表
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String list(int startRow, int pageSize) {
		List<CommonBiz> list = service.getAll(startRow, pageSize);
		Page<CommonBiz> page = new Page<CommonBiz>(service.countAll());
		if (list.size() != 0) {
			ResultVO<CommonBiz> resultVO = new ResultVO<CommonBiz>();
			resultVO.setPage(page);
			resultVO.setLists(list);
			return resultVO.toString();
		}
		return "";
	}

	/**
	 * 工单编号格式为日期+四位，如201704190001
	 * 
	 * @return groupId
	 */
	@RequestMapping(value = "/generateGroupId", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String generateGroupId(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		String groupId = service.generateGroupId();
		// 给客户端响应
		ResultVO<Object> result = new ResultVO<>();
		result.setOthers("groupId", groupId);
		return result.toString();
	}

	/**
	 * 按主键查询
	 * 
	 * @param dataId
	 * @return
	 */
	@RequestMapping(value = "/getCommonBizByDataId", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getCommonBizByDataId(Long dataId) {
		ResultVO<Object> result = new ResultVO<>();
		if (dataId != null) {
			CommonBiz record = service.getByDataId(dataId);
			// 给客户端响应
			result.setOthers("commonbiz", record);
			String string = result.toString();
			return string;
		}
		return "";
	}

	/**
	 * 按serviceType分组，组内按创建日期降序排序，获取第一条
	 * 
	 * @param groupId
	 * @return commonbiz
	 */
	@RequestMapping(value = "/getLatestBizByGroupId", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getLatestBizByGroupId(String groupId) {
		ResultVO<Object> result = new ResultVO<>();
		if (groupId != null) {
			List<CommonBiz> commonBizList = service
					.getLatestBizByGroupId(groupId);
			// 给客户端响应
			result.setOthers("commonBizList", commonBizList);
			return result.toString();
		}
		return "";
	}
	
	
	/**
	 * 按serviceType分组，组内按创建日期降序排序，获取第一条
	 * 
	 * @param groupId
	 * @return commonbiz
	 */
	@RequestMapping(value = "/getLatestBizByGroupIdAndStatus", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getLatestBizByGroupIdAndStatus(String groupId,String status) {
		ResultVO<Object> result = new ResultVO<>();
		if (groupId != null) {
			List<CommonBiz> commonBizList = service
					.getLatestBizByGroupIdAndStatus(groupId,status);
			// 给客户端响应
			result.setOthers("commonBizList", commonBizList);
			return result.toString();
		}
		return "";
	}

	//根据组ID和业务类型查询
	@RequestMapping(value = "/getCommonBizByGIdAndServiceType", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getCommonBizByGIdAndServiceType(String groupId,
			String serviceType, int startRow, int pageSize) {
		ResultVO<CommonBiz> result = new ResultVO<CommonBiz>();
		try {
			Page<CommonBiz> page = new Page<CommonBiz>(
					service.countByConditions(groupId, serviceType,
							startRow, pageSize));
			List<CommonBiz> commonBizList = service
					.getCommonBizByGIdAndServiceType(groupId, serviceType,
							startRow, pageSize);
			if (commonBizList != null) {
				result.setPage(page);
				result.setOthers("commonBizList", commonBizList);
			} else {
				result.setResult(ResultVO.FAILURE);
			}
		} catch (Exception e) {
			result.setResult(ResultVO.FAILURE);
			e.printStackTrace();
		}
		return result.toString();
	}

	// 根据组ID和业务类型查询不带分页
	@RequestMapping(value = "/getBizListByGIdAndServiceType", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getBizListByGIdAndServiceType(String groupId,
			String serviceType) {
		ResultVO<CommonBiz> result = new ResultVO<CommonBiz>();
		try {
			List<CommonBiz> commonBizList = service
					.getBizListByGIdAndServiceType(groupId, serviceType);
			if (commonBizList != null) {
				result.setOthers("commonBizList", commonBizList);
			} else {
				result.setResult(ResultVO.FAILURE);
			}
		} catch (Exception e) {
			result.setResult(ResultVO.FAILURE);
			e.printStackTrace();
		}
		return result.toString();
	}
	
	//根据组ID、业务类型、DataID(Long)，status、result查询不带分页
	@RequestMapping(value = "/getBizListByCommonCondition", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getBizListByCommonCondition(Long dataId,String serviceType,String groupId,
			String status,String result) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("dataId", dataId);
		queryMap.put("serviceType", serviceType);
		queryMap.put("groupId", groupId);
		queryMap.put("status", status);
		queryMap.put("result", result);
		ResultVO<CommonBiz> resultVO = new ResultVO<CommonBiz>();
		try {
			List<CommonBiz> commonBizList = service
					.getBizListByCommonCondition(queryMap);
			if (commonBizList != null) {
				resultVO.setOthers("commonBizList", commonBizList);
			} else {
				resultVO.setResult(ResultVO.FAILURE);
			}
		} catch (Exception e) {
			resultVO.setResult(ResultVO.FAILURE);
			e.printStackTrace();
		}
		return resultVO.toString();
	}
	
	// 根据组ID查询所有不带分页
	@RequestMapping(value = "/getByGroupId", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getByGroupId(String groupId,
			HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		ResultVO<CommonBiz> result = new ResultVO<CommonBiz>();
		try {
			List<CommonBiz> commonBizList = service.getByGroupId(groupId);
			if (commonBizList != null) {
				result.setOthers("commonBizList", commonBizList);
			} else {
				result.setResult(ResultVO.FAILURE);
			}
		} catch (Exception e) {
			result.setResult(ResultVO.FAILURE);
			e.printStackTrace();
		}
		return result.toString();
	}
}
