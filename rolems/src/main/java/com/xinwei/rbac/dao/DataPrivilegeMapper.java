package com.xinwei.rbac.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xinwei.rbac.entity.DataPrivilege;

public interface DataPrivilegeMapper {
	int insert(DataPrivilege record);

	int deleteByCondition(@Param("createTime") String createTime,
			@Param("privilegePersons") List<Integer> privilegePersons);
	
	int updateByCondition(DataPrivilege record);

	//通过创建时间查询创建人的Id列表
	List<Integer> selectPrivilegePersonIdsByCreateTime(String createTime);

	Long countByConditions(@Param("beginTime") String beginTime,@Param("endTime") String endTime
			,@Param("privilegePersons")List<Integer> privilegePersons,@Param("startRow") int startRow, @Param("pageSize")int pageSize);
	
	//按条件查询一段时间的权限列表
	List<DataPrivilege> selectByCondition(@Param("beginTime") String beginTime,@Param("endTime") String endTime
			,@Param("privilegePersons")List<Integer> privilegePersons,@Param("startRow") int startRow, @Param("pageSize")int pageSize);
	
	Long countAll();

	List<DataPrivilege> selectAll(@Param("startRow") int startRow, @Param("pageSize")int pageSize);
}