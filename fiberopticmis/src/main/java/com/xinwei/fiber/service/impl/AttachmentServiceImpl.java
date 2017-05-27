package com.xinwei.fiber.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xinwei.fiber.dao.AttachmentMapper;
import com.xinwei.fiber.entity.Attachment;
import com.xinwei.fiber.service.AttachmentService;

@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Resource
	private AttachmentMapper attachmentMapper;
	
	@Override
	public List<Attachment> getAll() {
		return attachmentMapper.selectAll();
	}

	@Override
	public Attachment getByPrimaryKey(Long attachmentId) {
		return attachmentMapper.selectByPrimaryKey(attachmentId);
	}

	@Override
	public Attachment getByAttachmentName(String attachmentName) {
		return attachmentMapper.selectByAttachmentName(attachmentName);
	}

	@Override
	public List<Attachment> getByDataId(String dataId) {
		return attachmentMapper.selectByDataId(dataId);
	}

	@Override
	public List<Attachment> getByGroupIdAndTypeId(String groupId,
			String type) {
		return attachmentMapper.selectByGroupIdAndTypeId(groupId,type);
	}

	@Override
	public void save(Attachment attachment) {
		attachmentMapper.insert(attachment);
	}

	@Override
	public void delete(Long attachmentId) {
		attachmentMapper.deleteByPrimaryKey(attachmentId);
		
	}

	@Override
	public void update(Attachment attachment) {
		attachmentMapper.update(attachment);
		
	}

}
