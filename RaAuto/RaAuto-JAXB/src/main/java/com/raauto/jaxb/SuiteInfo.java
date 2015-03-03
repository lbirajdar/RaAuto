package com.raauto.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="info")
public class SuiteInfo {

	private String suiteName;
	private String description;
	private String createdBy;
	private String editedOn;

	public SuiteInfo() {
	}

	@XmlElement(name="name", required=true)
	public String getSuiteName() {
		return suiteName;
	}

	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}

	@XmlElement(name="description", defaultValue="" )
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

		
	@XmlElement(name="createdBy", required=true)
	public String getCreatedBy()
	{
		return this.createdBy;
	}
	
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}


	@XmlElement(name="editedOn", required=true)
	public String getEditedOn()
	{
		return this.editedOn;
	}
	
	public void setEditedOn(String createdOn)
	{
		this.editedOn = createdOn;
	}
	
}
