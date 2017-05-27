package com.xinwei.workflow.dao;

import com.xinwei.workflow.entity.CustomActivityName;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CustomActivityNameMapper {
    int insert(CustomActivityName record);

    List<CustomActivityName> selectAll();
    
    /**
     * 根据流程定义key和节点Id获取自定义的节点名称
     * @param processDefKey流程定义key
     * @param activityId 节点Id
     * @param language 语言
     * @return 自定义名字
     */
    String selectName(@Param("processDefKey")String processDefKey,@Param("activityId")String activityId,
    		@Param("language") String language);
}