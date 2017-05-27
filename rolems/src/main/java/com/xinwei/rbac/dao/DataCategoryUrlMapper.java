package com.xinwei.rbac.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xinwei.rbac.entity.DataCategoryUrl;

public interface DataCategoryUrlMapper {
    int insert(DataCategoryUrl record);

    int deleteByCategoryAndServiceType(@Param("dataCategory") String dataCategory,
    		@Param("serviceType") String serviceType);

    int updateByPrimaryKey(DataCategoryUrl record);
    
    Long countAll();
    
    List<DataCategoryUrl> selectAll(@Param("startRow") int startRow, @Param("pageSize")int pageSize);

    Long countByConditions(@Param("dataCategory") String dataCategory,
    		@Param("serviceType") String serviceType,@Param("startRow") int startRow, @Param("pageSize") int pageSize);

    List<DataCategoryUrl> selectByCategoryAndServiceType(@Param("dataCategory") String dataCategory,
    		@Param("serviceType") String serviceType,@Param("startRow") int startRow, @Param("pageSize") int pageSize);
}

