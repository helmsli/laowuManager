package com.xinwei.workflow.controller;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/deployment")
public class DeploymentController {
	@Resource
	private RepositoryService repositoryService;
	private Logger logger = LoggerFactory.getLogger(DeploymentController.class);
	/**
     * 流程定义列表
     */
    @RequestMapping(value = "/process-list")
    public ModelAndView processList() {

        // 对应WEB-INF/views/deployment/process-list.jsp
        ModelAndView mav = new ModelAndView("deployment/process-list");

        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().list();

        mav.addObject("processDefinitionList", processDefinitionList);

        return mav;
    }

    /**
     * 部署流程资源
     */
    @RequestMapping(value = "/deploy")
    public String deploy(@RequestParam(value = "file", required = true) MultipartFile file,String tenantId) {

        try {
        	if(null != file && file.getSize() >0 && StringUtils.isNotEmpty(tenantId)){
        		
        		// 获取上传的文件名
        		String fileName = file.getOriginalFilename();
        		// 得到输入流（字节流）对象
        		InputStream fileInputStream = file.getInputStream();
        		
        		// 文件的扩展名
        		String extension = FilenameUtils.getExtension(fileName);
        		
        		// zip或者bar类型的文件用ZipInputStream方式部署
        		DeploymentBuilder deployment = repositoryService.createDeployment();
        		if (extension.equals("zip") || extension.equals("bar")) {
        			ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
        			deployment.addZipInputStream(zipInputStream);
        		} else {
        			// 其他类型的文件直接部署
        			deployment.addInputStream(fileName, fileInputStream);
        			deployment.tenantId(tenantId);
        		}
        		deployment.deploy();
        	}
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("error on deploy process, because of file input stream");
        }

        return "redirect:process-list";
    }

    /**
     * 读取流程资源
     *
     * @param processDefinitionId 流程定义ID
     * @param resourceName        资源名称
     */
    @RequestMapping(value = "/read-resource")
    public void readResource(@RequestParam("pdid") String processDefinitionId, @RequestParam("resourceName") String resourceName, HttpServletResponse response)
            throws Exception {
        ProcessDefinitionQuery pdq = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition pd = pdq.processDefinitionId(processDefinitionId).singleResult();

        // 通过接口读取
        InputStream resourceAsStream = repositoryService.getResourceAsStream(pd.getDeploymentId(), resourceName);

        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 删除部署的流程，级联删除流程实例
     *
     * @param deploymentId 流程部署ID
     */
    @RequestMapping(value = "/delete-deployment")
    public String deleteProcessDefinition(@RequestParam("deploymentId") String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
        return "redirect:process-list";
    }
	
}
