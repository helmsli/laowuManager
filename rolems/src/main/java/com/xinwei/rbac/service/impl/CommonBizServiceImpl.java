package com.xinwei.rbac.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xinwei.rbac.constant.DataIdConstants;
import com.xinwei.rbac.dao.CommonBizMapper;
import com.xinwei.rbac.dao.XWSeqMapper;
import com.xinwei.rbac.entity.CommonBiz;
import com.xinwei.rbac.entity.DataPrivilege;
import com.xinwei.rbac.service.CommonBizService;
import com.xinwei.system.xwsequence.service.XwSysSeqService;
import com.xinwei.util.date.DateUtil;

@Service
public class CommonBizServiceImpl implements CommonBizService {
	@Resource
	private CommonBizMapper dao;
	
	@Resource
	private XWSeqMapper xwSeqMapperDao;
	
	@Resource(name = "xwSysSeqService")
	private XwSysSeqService xwSysSeqService;

	@Override
	public Long save(CommonBiz commonBiz) {
		Long dataId= generateDataId();
		commonBiz.setDataId(dataId);
		dao.insert(commonBiz);
		return dataId;
	
	}
	
	
	@Override
	public void deleteByDataId(Long dataId) {
		dao.deleteByPrimaryKey(dataId);
	}

	@Override
	public void update(CommonBiz commonBiz) {
		dao.updateByPrimaryKey(commonBiz);
	}

	@Override
	public CommonBiz getByDataId(Long dataId) {
		return dao.getByDataId(dataId);
	}

	@Override
	public List<CommonBiz> getAll(int startRow, int pageSize) {
		return dao.selectAll(startRow, pageSize);
	}

	@Override
	public List<CommonBiz> getCommonBizByGIdAndServiceType(String groupId,
			String serviceType, int startRow, int pageSize) {
		return dao.getCommonBizByGIdAndServiceType(groupId, serviceType,
				startRow, pageSize);
	}
	
	@Override
	public List<CommonBiz> getBizListByGIdAndServiceType(String groupId,
			String serviceType) {
		return dao.getBizListByGIdAndServiceType(groupId, serviceType);
	}

	@Override
	public void updateProcessIdByDataId(String processInstanceId, Long dataId) {
		dao.updateProcessIdByDataId(processInstanceId, dataId);
	}
	
	@Override
	public void updateStatusByDataId(String status, Long dataId) {
		dao.updateStatusByDataId(status,dataId);
		
	}  

	@Override
	public Long countAll() {
		return dao.countAll();
	}

	@Override
	public Long countByConditions(String groupId,
			String serviceType, int startRow, int pageSize) {
		return dao.countByConditions(groupId, serviceType,
				startRow, pageSize);
	}
	
	@Override
	public List<CommonBiz> getLatestBizByGroupId(String groupId) {
		return dao.getLatestBizByGroupId(groupId);
	}

	@Override
	public String generateGroupId() {
	//seq四位
	Long groupIdSeqCode = xwSysSeqService.getXwSequence(
					DataIdConstants.GROUP_ID_SEQ, 1).getStartSequence();
	  //工单编号格式为日期+四位，如201704191001
	return DateUtil.DateToString(new Date(), "yyyyMMdd")+addZeroForNum(String.valueOf(groupIdSeqCode),4);
	}
	
	@Override
	public List<CommonBiz> getByGroupId(String groupId) {
		return dao.getByGroupId(groupId);
	}
	
	@Override
	public Long generateDataId() {
		StringBuilder dataId = new StringBuilder();
		
		//seq4位
		Long groupIdSeqCode = xwSysSeqService.getXwSequence(
						DataIdConstants.GROUP_ID_SEQ, 1).getStartSequence();
		String str1 =addZeroForNum(String.valueOf(groupIdSeqCode),4);
		//seq8位
		Long dataIdChildSeqCode = xwSysSeqService.getXwSequence(
				DataIdConstants.DATA_ID_SEQ, 1).getStartSequence();
		
		String str2 = addZeroForNum(String.valueOf(dataIdChildSeqCode),7);
		//Long带符号的范围是-9223372036854775808到9223372036854775807。无符号的范围是0到18446744073709551615。
		dataId.append(DateUtil.DateToString(new Date(), "yyyyMMdd")).append(str1).append(str2);
		Long dataIdSeqCode = dataIdSeqCode = Long.valueOf(dataId.toString());
		return  dataIdSeqCode;
	}
	
	//str不足strLength位左补0 
	public static String addZeroForNum(String str,int strLength) {  
		  int strLen =str.length();  
		  if (strLen <strLength) {  
		   while (strLen< strLength) {  
		    StringBuffer sb = new StringBuffer();  
		    sb.append("0").append(str);
		    str= sb.toString();  
		    strLen= str.length();  
		   }  
		  }  
		  return str;  
		 }

	@Override
	public void updateCurrentValueBySeqName(String seqName) {
		xwSeqMapperDao.updateCurrentValueBySeqName(seqName);
	}

	@Override
	public List<CommonBiz> getBizListByCommonCondition(
			Map<String, Object> queryMap) {
		return dao.getBizListByCommonCondition(queryMap);
	}

    //按groupId和status查询,按serviceType分组，组内按创建日期降序排序，获取第一条
	@Override
	public List<CommonBiz> getLatestBizByGroupIdAndStatus(String groupId,
			String status) {
		return dao.getLatestBizByGroupIdAndStatus(groupId,status);
	}
}
