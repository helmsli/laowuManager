package com.xinwei.fiber.entity;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *  申请人信息. 
 */
public class BasicInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6792787496100915041L;

	private String formNumber;
	
	private Long representativeId;
/*
	private String representative;
	
	private Date requestDate;

	private String applicantDepartment;

	private String applicant;

	private String applicantContactNo;
*/
	/**
	 * Constructor.
	 */
	public BasicInformation() {
	}
	
	
	public String getFormNumber() {
		return formNumber;
	}

	public Long getRepresentativeId() {
		return representativeId;
	}


	public void setRepresentativeId(Long representativeId) {
		this.representativeId = representativeId;
	}

	public void setFormNumber(String formNumber) {
		this.formNumber = formNumber;
	}

/*
	public String getRepresentative() {
		return representative;
	}


	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	public Date getRequestDate() {
		return requestDate;
	}


	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}


	public String getApplicantDepartment() {
		return applicantDepartment;
	}


	public void setApplicantDepartment(String applicantDepartment) {
		this.applicantDepartment = applicantDepartment;
	}


	public String getApplicant() {
		return applicant;
	}


	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}


	public String getApplicantContactNo() {
		return applicantContactNo;
	}


	public void setApplicantContactNo(String applicantContactNo) {
		this.applicantContactNo = applicantContactNo;
	}

*/
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
