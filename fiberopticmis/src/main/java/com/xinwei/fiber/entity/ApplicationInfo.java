package com.xinwei.fiber.entity;

import java.io.Serializable;
import java.util.Date;

import com.xinwei.security.entity.User;

/**
 * 申请信息
 * @author xuejinku
 *
 */
public class ApplicationInfo implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -7342061863737129704L;

	private String formNo;//工单编号

	private String dataId;//数据Id
	
	private String IDNO;//证件号码
	
	private Long applicant;//申请人ID
	
	private User applicantInfo;//申请人信息
	
    private Date applyDate;//申请日期
    
    private String customerName;//客户名
    
    private String applyService;//申请服务

    private String telNo;//电话号码

    private String installationAddress;//安装地址

    private String procInstId;//流程实例Id

    private String stateInfo;//流程状态信息
    
    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getIDNO() {
		return IDNO;
	}

	public void setIDNO(String iDNO) {
		IDNO = iDNO;
	}

	public Long getApplicant() {
	    return applicant;
	}

    public void setApplicant(Long applicant) {
	    this.applicant = applicant;
	}
	    
	public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getInstallationAddress() {
        return installationAddress;
    }

    public void setInstallationAddress(String installationAddress) {
        this.installationAddress = installationAddress;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getApplyService() {
		return applyService;
	}

	public void setApplyService(String applyService) {
		this.applyService = applyService;
	}

	public User getApplicantInfo() {
		return applicantInfo;
	}

	public void setApplicantInfo(User applicantInfo) {
		this.applicantInfo = applicantInfo;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
    
}