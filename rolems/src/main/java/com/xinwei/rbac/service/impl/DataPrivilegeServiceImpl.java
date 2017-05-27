package com.xinwei.rbac.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xinwei.rbac.controller.DataPrivilegeController;
import com.xinwei.rbac.dao.DataPrivilegeMapper;
import com.xinwei.rbac.entity.DataPrivilege;
import com.xinwei.rbac.service.CommonBizService;
import com.xinwei.rbac.service.DataPrivilegeService;
import com.xinwei.security.MessageCode;
import com.xinwei.security.exception.ServiceException;
import com.xinwei.system.xwsequence.service.XwSysSeqService;

@Service
public class DataPrivilegeServiceImpl implements DataPrivilegeService {
	private Logger logger = LoggerFactory
			.getLogger(DataPrivilegeController.class);
	@Resource
	private DataPrivilegeMapper dao;
	@Resource
	private XwSysSeqService xwSysSeqService;
	@Resource
	private CommonBizService commonBizService;

	@Override
	public int save(DataPrivilege record) {
		// 光纤延期
		Long dataId = commonBizService
				.generateDataId();
		record.setDataId(dataId);
		return dao.insert(record);
	}

	@Override
	public List<DataPrivilege> getAll(int startRow, int pageSize) {
		return dao.selectAll(startRow, pageSize);
	}

	@Override
	public List<Integer> selectPrivilegePersonIdsByCreateTime(String createTime) {
		return dao.selectPrivilegePersonIdsByCreateTime(createTime);
	}

	// 分页按条件查询
	@Override
	public List<DataPrivilege> selectByCondition(String beginTime,
			String endTime, List<Integer> privilegePersons, int startRow,
			int pageSize) {
		List<DataPrivilege> dataPrivileges = new ArrayList<DataPrivilege>();
		if (null == privilegePersons || 0 == privilegePersons.size()) {
			//throw new ServiceException(MessageCode.USER_ID_NULL);
			logger.debug("privilegePersons","创建人列表为空");
		}
		dataPrivileges = dao.selectByCondition(beginTime, endTime,
				privilegePersons, startRow, pageSize);
		return dataPrivileges;
	}

	@Override
	public void deleteByCondition(String createTime,
			List<Integer> privilegePersons) {
		if (null == privilegePersons || 0 == privilegePersons.size()) {
			throw new ServiceException(MessageCode.USER_ID_NULL);
		}
		dao.deleteByCondition(createTime, privilegePersons);
	}

	@Override
	public void updateByCondition(DataPrivilege dataPrivilege) {
		dao.updateByCondition(dataPrivilege);
	}

	@Override
	public Long countAll() {
		return dao.countAll();
	}

	@Override
	public Long countByConditions(String beginTime, String endTime,
			List<Integer> privilegePersons, int startRow, int pageSize) {
		return dao.countByConditions(beginTime, endTime,
				privilegePersons, startRow, pageSize);
	}
}
