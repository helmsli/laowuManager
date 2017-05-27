package com.xinwei.fiber.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import tk.mybatis.mapper.util.StringUtil;
import com.xinwei.fiber.entity.Attachment;
import com.xinwei.fiber.service.AttachmentService;
import com.xinwei.security.entity.User;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.system.xwsequence.service.XwSysSeqService;
import com.xinwei.util.ChineseToSpellUtil;
import com.xinwei.util.date.DateUtil;

@Controller
@RequestMapping("/attachment")
public class AttachmentController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(AttachmentController.class);
	@Resource
	private AttachmentService attachmentServiceImpl;
	@Resource
	private XwSysSeqService xwSysSeqService;
	
	private final String ATTACHMENT_SEQ = "attachment_seq";// 附件编号

	@Value("${uploadPath}")
	private String uploadPath;  
	
	/**
	 * 附件上传（支持多文件上传）
	 */
	@RequestMapping(value="/upload",produces = "text/html;charset=UTF-8")
	public @ResponseBody String attachmentUpload(HttpServletRequest request){
		logger.debug("upload file start...");
		//附件列表
		List<Attachment> attachmentList = new ArrayList<Attachment>();
		//给客户端响应信息
		ResultVO<Object> result = new  ResultVO<>();
        try {
        	//将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        	CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
        			request.getSession().getServletContext());
        	//文件上传地址
        	String path = request.getSession().getServletContext().getRealPath(uploadPath);
        	
        	//所属数据Id
        	String dataId = request.getParameter("dataId");
        	//所属组
        	String groupId = request.getParameter("groupId");
        	//附件类型
        	String type = request.getParameter("type");
        	
        	//检查form中是否有enctype="multipart/form-data"
			if(multipartResolver.isMultipart(request))
			{
			    //将request变成多部分request
			    MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			   //获取multiRequest 中所有的文件名
			    Iterator<String> iter=multiRequest.getFileNames();
			    while(iter.hasNext())
			    {
			        //一次遍历所有文件
			        MultipartFile file = multiRequest.getFile(iter.next());
			        if(file!=null)
			        {
			        	String originalFilename = file.getOriginalFilename();
			        	String postfix = "";
			    		String fileNamePrefix = originalFilename;
			    		if (originalFilename.contains("."))// 是否带后缀，如带后缀则进行分割
			    		{
			    			int splitPosition = originalFilename.lastIndexOf(".");
			    			postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
			    			fileNamePrefix = originalFilename.substring(0,splitPosition);
			    		}
			        	Date uploadDate = new Date();
			        	// 保存文件时的文件名
			            String attachmentName = DateUtil.DateToString(uploadDate, "yyyy-MM-dd-HH-mm-ss") + "_" + ChineseToSpellUtil.cn2FirstSpell(fileNamePrefix) + postfix;
			            File targetFile = new File(path, attachmentName);
						if(!targetFile.exists()){  
				            targetFile.mkdirs();  
				        }
			            //上传
			            file.transferTo(targetFile);
			            //当前用户
			            User user = getCurrentUser();
			            //构建附件对象
			            Attachment attachment = buildAttachment(attachmentName,file.getOriginalFilename(),
			            		user.getId().toString(),uploadDate,type,dataId,groupId);
						// 保存文件信息到数据库
						logger.info("AttachmentUpload --> attachment:  " + attachment.toString());
						attachmentServiceImpl.save(attachment);
						attachmentList.add(attachment);
						
			        }  
			    }   
			}else{
				logger.debug("filelist is null! ");
			}
			result.setOthers("attachmentList", attachmentList);
		} catch (Exception e) {
			logger.error("AttachmentUpload erro :" + e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.FAILURE);
		} 
		return result.toString(); 
	}

	
    /**
    * 根据附件名称进行下载
    * @param request
    * @param response
    * @param attachmentName
    */
	@RequestMapping(value="/fileDownLoad",produces = "text/html;charset=UTF-8")   
	public void fileDownLoad(HttpServletRequest request, HttpServletResponse response,String attachmentName) {  
		logger.debug("fileDownLoad --> attachmentName: " + attachmentName);
		FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    OutputStream os = null;
	    try{
	    	if(StringUtil.isNotEmpty(attachmentName)){
	    		//文件原始名字
	    		String originalFilename  = attachmentName;
		    	//根据附件名称获取附件对象
		    	Attachment attachment = attachmentServiceImpl.getByAttachmentName(attachmentName);
		    	//如果附件对象不为空
		    	if(null != attachment){
		    		// 获取文件原始名字
		    		originalFilename  = attachment.getOriginalFilename();
		    	}
	    		response.setHeader("Content-disposition", "attachment; filename="  
	    				+new String(originalFilename.getBytes(Charset.forName("UTF-8")),"ISO8859-1"));
	    		response.setContentType("application/force-download");// 设置强制下载不打开
	    		//文件上传地址
	    		String path = request.getSession().getServletContext().getRealPath(uploadPath);   
	    		byte buffer [] = new byte[1024*1024*1];//1M    
	    		fis = new FileInputStream(new File(path,attachmentName));  
	    		bis = new BufferedInputStream(fis);
	    		os = response.getOutputStream();
	    		int length = 0;
	    		while(-1!=(length=bis.read(buffer))){  
	    			os.write(buffer,0,length);//每次写1M  
	    		}  
	    		os.flush();
	    	}
	    }catch (Exception e) {
	    	logger.error("fileDownLoad erro : "+ e.getMessage());
	        e.printStackTrace();
	    }finally{  
	        try {  
	            if(os!=null){  
	                os.close();  
	            }
	            if(bis!=null){  
	                bis.close();  
	            }
	            if(fis!=null){
	            	fis.close();
	            }
	        } catch (IOException e) {  
	        	 e.printStackTrace();
	        }  
	    }
	}
		
	/**
	 * delete
	 * @param attachmentId
	 * @return
	 */
	@RequestMapping(value="/delete", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String delete(HttpServletRequest request,Long attachmentId) {
		ResultVO<Object> result = new ResultVO<>();
		// 删除服务器存储的文件
		Attachment attachment = attachmentServiceImpl
				.getByPrimaryKey(attachmentId);
		if (null != attachment) {
			String attachmentName = attachment.getAttachmentName();
			//文件上传地址
			String path = request.getSession().getServletContext().getRealPath(uploadPath);
			//待删除文件
			File file = new File(path, attachmentName);
			if (file.delete()) {
				//如果文件删除成功删除附件对象
				attachmentServiceImpl.delete(attachmentId);
			} else {
				result.setResult(ResultVO.FAILURE);
			}
		} else {
			result.setResult(ResultVO.FAILURE);
		}
		return result.toString();
	}
	
	/*
	 * 构建附件对象
	 */
	private Attachment buildAttachment(String attachmentName,
			String originalFilename, String userId, Date uploadDate,
			String type, String dataId, String groupId) throws UnsupportedEncodingException {
		// 生成附件编号
		Long attachmentId = xwSysSeqService.getXwSequence(ATTACHMENT_SEQ, 1)
						.getStartSequence();
		Attachment attachment = new Attachment();
		//设置ID
		attachment.setAttachmentId(attachmentId);;
		//设置附件名称
		attachment.setAttachmentName(attachmentName);;
		//设置附件原始名称
		attachment.setOriginalFilename(originalFilename);
		//设置文件下载路径
		attachment.setPath(attachmentName);
		//设置上传者
		attachment.setCreatePerson(userId);;
		//设置上传时间
		attachment.setCreateTime(uploadDate);
		//设置类型
		attachment.setType(type);
		//设置从属业务数据
		attachment.setDataId(dataId);
		//设置从属业务组
		attachment.setGroupId(groupId);
		return attachment;
	}
	
}
