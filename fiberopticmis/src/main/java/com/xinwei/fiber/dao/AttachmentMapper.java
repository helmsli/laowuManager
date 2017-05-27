package com.xinwei.fiber.dao;

import com.xinwei.fiber.entity.Attachment;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AttachmentMapper {
	
	/**
	 * 插入一条记录
	 * @param record
	 * @return
	 */
    int insert(Attachment record);

    /**
     * 修改一条记录
     * @param record
     */
    void update(Attachment record);
    
    /**
     * 删除一条记录
     * @param attachmentId 附件Id
     * @return
     */
    int deleteByPrimaryKey(Long attachmentId);
    
    /**
     * 根据主键获取附件对象
     * @param attachmentId
     * @return
     */
    Attachment selectByPrimaryKey(Long attachmentId);

    /**
     * 获取所有
     * @return
     */
    List<Attachment> selectAll();

    /**
     * 根据附件名称查询
     * @param attachmentName 附件名称
     * @return
     */
	Attachment selectByAttachmentName(String attachmentName);

	/**
	 * 根据业务数据Id查询
	 * @param dataId 数据Id
	 * @return
	 */
	List<Attachment> selectByDataId(String dataId);

	/**
	 * 根据组Id和类型查询
	 * @param groupId 组Id
	 * @param type 类型
	 * @return
	 */
	List<Attachment> selectByGroupIdAndTypeId(@Param("groupId")String groupId, @Param("type")String type);

}