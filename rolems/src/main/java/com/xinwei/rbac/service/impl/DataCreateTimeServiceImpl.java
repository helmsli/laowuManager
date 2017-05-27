package com.xinwei.rbac.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.rbac.dao.DataCreateTimeMapper;
import com.xinwei.rbac.entity.DataCreateTime;
import com.xinwei.rbac.service.CommonBizService;
import com.xinwei.rbac.service.DataCreateTimeService;
import com.xinwei.system.xwsequence.service.XwSysSeqService;

@Service
public class DataCreateTimeServiceImpl implements DataCreateTimeService {

	@Autowired
	private DataCreateTimeMapper dao;
	
	@Resource
	private CommonBizService commonBizService;
	
	@Resource
	private XwSysSeqService xwSysSeqService;

	@Override
	public Long save(DataCreateTime dataCreateTime) {
		
		Long dataId = commonBizService.generateDataId();

		// 设置编号
		dataCreateTime.setDataId(dataId);

		// 保存
		dao.insert(dataCreateTime);

		return dataId;
	}

	@Override
	public void update(DataCreateTime DataCreateTime) {
		dao.updateByPrimaryKey(DataCreateTime);
	}

	@Override
	public void delete(Long dataId) {
		dao.deleteByPrimaryKey(dataId);

	}

	@Override
	public DataCreateTime selectByPrimaryKey(Long dataId) {
		return dao.selectByPrimaryKey(dataId);
	}

	@Override
	public List<DataCreateTime> selectAll(int startRow, int pageSize) {
		return dao.selectAll(startRow, pageSize);
	}

	@Override
	public List<DataCreateTime> selectByBetweenTime(String beginTime,
			String endTime, int startRow, int pageSize) {
		return dao.getByRangeTime(beginTime, endTime, startRow, pageSize);
	}
}
