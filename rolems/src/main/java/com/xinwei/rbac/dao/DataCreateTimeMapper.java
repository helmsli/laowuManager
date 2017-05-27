package com.xinwei.rbac.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xinwei.rbac.entity.DataCreateTime;

public interface DataCreateTimeMapper {
    int insert(DataCreateTime dataCreateTime);
    
    int deleteByPrimaryKey(Long dataId);
    
    int updateByPrimaryKey(DataCreateTime dataCreateTime);

    DataCreateTime selectByPrimaryKey(Long dataId);
    
    Long countByRangeTime();
    
	List<DataCreateTime> getByRangeTime(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("startRow") int startRow, @Param("pageSize")int pageSize);
    Long countAll();
    
    List<DataCreateTime> selectAll(@Param("startRow") int startRow, @Param("pageSize")int pageSize);
}