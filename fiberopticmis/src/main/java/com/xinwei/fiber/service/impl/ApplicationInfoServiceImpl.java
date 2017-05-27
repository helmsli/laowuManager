package com.xinwei.fiber.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xinwei.fiber.dao.ApplicationInfoMapper;
import com.xinwei.fiber.entity.ApplicationInfo;
import com.xinwei.fiber.service.ApplicationInfoService;
import com.xinwei.util.page.Page;

/**
 * 申请信息服务
 * @author xuejinku
 *
 */
@Service
public class ApplicationInfoServiceImpl implements ApplicationInfoService {

	@Resource
	private ApplicationInfoMapper applicationInfoMapper;
	
	@Override
	public void save(ApplicationInfo record) {
		applicationInfoMapper.insert(record);	
	}

	@Override
	public void deleteByCondition(Map<String, Object> queryMap) {
		
		applicationInfoMapper.deleteByCondition(queryMap);
	}

	@Override
	public List<String> getProcInstIdByCondition(Map<String, Object> queryMap) {
		
		return applicationInfoMapper.selectProcInstIdByCondition(queryMap);
	}

	@Override
	public ApplicationInfo getByDataId(String dataId) {
	
		return applicationInfoMapper.selectByDataId(dataId);
	}

	@Override
	public Page<ApplicationInfo> getByCondition(Map<String, Object> queryMap) {
		
		Long count = applicationInfoMapper.countByCondition(queryMap);
		Page<ApplicationInfo> page = new Page<ApplicationInfo>(count);
		queryMap.put("startRow", page.getStartRow());
		queryMap.put("pageSize", page.getPageSize());
		List<ApplicationInfo> lists = applicationInfoMapper.selectByCondition(queryMap);
		page.setList(lists);
		
		return page;
	}


}
