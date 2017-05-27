package com.xinwei.rbac.service;

import java.util.List;

import com.xinwei.rbac.entity.DataCategoryUrl;

public interface DataCategoryUrlService {
	/**
	 * 保存
	 * @param DataCategoryUrl
	 * @return
	 */
	int save(DataCategoryUrl dataCategoryUrl);
	
	/**按条件查询
	 */
	
	List<DataCategoryUrl> selectByCategoryAndServiceType(String dataCategory,String servcieType,int startRow, int pageSize);

	/**
	 * 获取所有
	 * @return
	 */
    List<DataCategoryUrl> getAll(int startRow, int pageSize);
    
    /**
     * 根据条件进行删除
     */
    void deleteByCategoryAndServiceType(String dataCategory,String serviceType);
    
    /**
	 * 修改
	 */
    void updateByPrimaryKey(DataCategoryUrl dataCategoryUrl);

	Long countByConditions(String dataCategory,String serviceType,int startRow, int pageSize);

	Long countAll();

}
