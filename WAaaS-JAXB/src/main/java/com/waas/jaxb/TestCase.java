package com.waas.jaxb;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="testcase")
@XmlType (propOrder={"name","dependsOn","executeSteps","verificationSteps","testDataFile"})
public class TestCase {
	private String name;
	private String desc;

	private int 	m_id;
	private boolean	isComplete;	
	private boolean isCorrective;
	private String dependsOn;
	private String testDataFile;
	private Collection<ExecutionEvent> executeSteps;
	private VerificationSteps verificationSteps;
	
	public TestCase(){
		verificationSteps = new VerificationSteps();
	}
	
	@XmlElement (name="verificationSteps")
	public VerificationSteps getVerificationSteps() {
		
		return verificationSteps;
	}
	public void setVerificationSteps(VerificationSteps verificationSteps) {
		this.verificationSteps = verificationSteps;
	}
	@XmlElementWrapper (name="executionSteps")
	@XmlElement (name="event")
	public Collection<ExecutionEvent> getExecuteSteps() {
		return executeSteps;
	}
	public void setExecuteSteps(Collection<ExecutionEvent> executeSteps) {
		this.executeSteps = executeSteps;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the desc
	 */
	@XmlAttribute (name="description")
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc= desc;
	}
	
	/**
	 * @return the isCorrective
	 */
	@XmlAttribute(name="isCorrective", required=false)
	public boolean getIsCorrective() {
		return isCorrective;
	}
	/**
	 * @param isCorrective the isCorrective to set
	 */
	public void setIsCorrective(boolean isCorrective) {
		this.isCorrective = isCorrective;
	}
	
	
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
	
	/**
	 * @return the iscomplete attribute
	 */
	@XmlAttribute(name="iscomplete")
	public boolean getIsComplete() {
		return isComplete;
	}
	/**
	 * @param iscomplete the iscomplete to set
	 */
	public void setIsComplete(boolean iscomplete) {
		isComplete = iscomplete;
	}
	
	/**
	 * @return the dependsOn
	 */
	@XmlElement(name="dependson", defaultValue="")
	public String getDependsOn() {
		return dependsOn;		
	}
	/**
	 * @param dependsOn the dependsOn to set
	 */
	public void setDependsOn(String dependsOn) {
		this.dependsOn = dependsOn;
	}
	/**
	 * @return the testDataFile
	 */
	@XmlElement(name="testDataFile")
	public String getTestDataFile() {
		return testDataFile;
	}
	/**
	 * @param testDataFile the testDataFile to set
	 */
	public void setTestDataFile(String testDataFile) {
		this.testDataFile = testDataFile;
	}

}
