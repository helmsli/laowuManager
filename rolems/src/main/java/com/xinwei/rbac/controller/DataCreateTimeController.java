package com.xinwei.rbac.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinwei.rbac.entity.DataCreateTime;
import com.xinwei.rbac.service.DataCreateTimeService;
import com.xinwei.security.vo.ResultVO;

@Controller
@RequestMapping("/dataCreateTime")
public class DataCreateTimeController extends BaseController {
	private Logger logger = LoggerFactory
			.getLogger(DataCreateTimeController.class);
	@Resource
	private DataCreateTimeService dataCreateTimeService;// 通用业务数据服务
	
	/**
	 * 新增
	 * @return
	 */
	@RequestMapping(value="/create", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String create(String createTime) {	
		ResultVO<Object> result = new  ResultVO<>();
		Date createTimeDate;
		try {
			createTimeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createTime);
			Long dataId = dataCreateTimeService.save(new DataCreateTime(createTimeDate));
			//给客户端响应
			result.setOthers("dataId", dataId);
		} catch (ParseException e) {
			result.setResult(ResultVO.FAILURE);
		}
		return result.toString();
	}
	

	/**
	 * 分页查询列表
	 * @param keywords
	 * @return
	 */
	@RequestMapping(value="/listAll", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String listAll(int startRow, int pageSize) {
		List<DataCreateTime> list = dataCreateTimeService.selectAll(startRow,pageSize);
		ResultVO<DataCreateTime> resultVO = new ResultVO<DataCreateTime>();
		resultVO.setLists(list);
		return resultVO.toString();
	}
	
	
	/**
	 * 分页查询某时间段的列表
	 * @param keywords
	 * @return
	 */
	@RequestMapping(value = "/listByBetweenTime", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String listByBetweenTime(String beginTime,
			String endTime,int startRow, int pageSize) {
		List<DataCreateTime> list =dataCreateTimeService.selectByBetweenTime(beginTime,endTime,startRow,pageSize);
		ResultVO<DataCreateTime> resultVO = new ResultVO<DataCreateTime>();
		resultVO.setLists(list);
		return resultVO.toString();
	}
}
