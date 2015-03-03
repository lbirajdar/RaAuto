package com.raauto.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement (name="correctiveStep")
public class CorrectiveStep implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4612748846470261821L;
	private int 	m_id;
	private String name;
	private ApplicableWhen applicableWhen;

	/**
	 * @return the m_Id
	 */
	@XmlAttribute(name="id")
	public int getID() {
		return m_id;
	}
	/**
	 * @param m_Id the m_Id to set
	 */
	public void setID(int string) {
		this.m_id = string;
	}
	
	@XmlAttribute (name="name", required=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlElement(name="applicableWhen", required=true)
	public ApplicableWhen getApplicableWhen() {
		return applicableWhen;
	}
	public void setApplicableWhen(ApplicableWhen applicableWhen) {
		this.applicableWhen = applicableWhen;
	}
}
