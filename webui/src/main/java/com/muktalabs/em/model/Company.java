package com.muktalabs.em.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the company database table.
 * 
 */
@Entity
@Table(name="company")
@NamedQuery(name="Company.findAll", query="SELECT c FROM Company c")
public class Company extends Model implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="company_id")
	private String companyId;

	@Column(name="company_name")
	private String companyName;

	public Company() {
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}