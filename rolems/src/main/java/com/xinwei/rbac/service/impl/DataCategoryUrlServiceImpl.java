package com.xinwei.rbac.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xinwei.rbac.dao.DataCategoryUrlMapper;
import com.xinwei.rbac.entity.DataCategoryUrl;
import com.xinwei.rbac.service.DataCategoryUrlService;
import com.xinwei.system.xwsequence.service.XwSysSeqService;

@Service
public class DataCategoryUrlServiceImpl implements DataCategoryUrlService {
	@Resource
	private DataCategoryUrlMapper dao;
	@Resource
	private XwSysSeqService xwSysSeqService;

	@Override
	public int save(DataCategoryUrl record) {
		return dao.insert(record);
	}

	@Override
	public void updateByPrimaryKey(DataCategoryUrl record) {
		dao.updateByPrimaryKey(record);
	}

	
	@Override
	public Long countAll() {
		return dao.countAll();
	}
	
	@Override
	public List<DataCategoryUrl> getAll(int startRow, int pageSize) {
		return dao.selectAll(startRow, pageSize);
	}


	@Override
	public Long countByConditions(String dataCategory, String servcieType, int startRow, int pageSize) {
		return dao.countByConditions(dataCategory,servcieType,startRow,pageSize);
	}
	@Override
	public List<DataCategoryUrl> selectByCategoryAndServiceType(
			String dataCategory, String servcieType, int startRow, int pageSize) {
		return dao.selectByCategoryAndServiceType(dataCategory,servcieType,startRow,pageSize);
	}

	@Override
	public void deleteByCategoryAndServiceType(String dataCategory,
			String serviceType) {
		dao.deleteByCategoryAndServiceType(dataCategory, serviceType);
		
	}
}
