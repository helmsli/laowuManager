package com.xinwei.fiber.service;

import java.util.List;
import com.xinwei.fiber.entity.Attachment;

public interface AttachmentService{
	
	/**
	 * 获取所有
	 */
	List<Attachment> getAll();
	
	/**
	 * 获取ById
	 */
	Attachment getByPrimaryKey(Long attachmentId);

	/**
	 * 根据附件名获取
	 * @param attachmentName
	 * @return
	 */
	Attachment getByAttachmentName(String attachmentName);
	
	/**
	 * 通过dataId查询
	 * @param dataId 业务数据ID
	 * @return
	 */
	List<Attachment> getByDataId(String dataId);
	
	/**
	 * 通过组groupId和type查询
	 * @param groupId 组ID
	 * @param type 类型
	 * @return
	 */
	List<Attachment> getByGroupIdAndTypeId(String groupId,String type);
	
	/**
	 * 保存
	 */
	void save(Attachment attachment);

	/**
	 * 删除
	 */
	void delete(Long attachmentId);
	
	/**
	 * 修改
	 */
	void update(Attachment attachment);

}
