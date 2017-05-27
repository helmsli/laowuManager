package com.xinwei.rbac.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xinwei.rbac.entity.DataPrivilege;

public interface DataPrivilegeService {
	/**
	 * 保存
	 * @param dataPrivilege
	 * @return
	 */
	int save(DataPrivilege dataPrivilege);
	
	/**
	 * 根据创建时间查询创建人ID列表
	 * @param createTime 创建时间
	 * @return 创建人IDs
	 */
    List<Integer> selectPrivilegePersonIdsByCreateTime(String createTime);
    
    Long countByConditions(String beginTime,String endTime,List<Integer> privilegePersons,int startRow,int pageSize);
    /**
     * 
     * @param beginTime
     * @param endTime
     * @param privilegePersons
     * @param startRow 起始索引
	 * @param pageSize 获取记录数
     * @return
     */

	List<DataPrivilege> selectByCondition(String beginTime,String endTime,List<Integer> privilegePersons,int startRow,int pageSize);

	
    Long countAll();
    
	/**
	 * 分页查询所有
	 * @param startRow 起始索引
	 * @param pageSize 获取记录数
	 * @return
	 */
	
	List<DataPrivilege> getAll(@Param("startRow") int startRow, @Param("pageSize")int pageSize);
    
    /**
     * 根据条件进行删除
     * @param privilegePersons(*组)
     */
    void deleteByCondition(String createTime, List<Integer> privilegePersons);
    
   /**
    * 修改
    * @param dataPrivilege
    */
    void updateByCondition(DataPrivilege dataPrivilege);
}
