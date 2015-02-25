package com.waas.jaxb;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
//import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * @author Abhijit Naik
 *
 */
//@XmlRootElement(name="suite")
@XmlType (propOrder={"suiteInfo","metaInfo","testCases"})
public class Suite {
	
	private int 			m_id;
	private boolean			isComplete;
	private SuiteInfo 		suiteInfo;
	private SuiteMetaInfo 	metaInfo;
	private Collection<TestCase>testCases;
	
	public Suite() {
		suiteInfo = new SuiteInfo();;
		metaInfo = new SuiteMetaInfo();
		testCases = new ArrayList<TestCase>();
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
	public void setID(int id) {
		this.m_id = id;
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
	 * @return the testCaseNames
	 */
	@XmlElementWrapper (name="testcases")
	@XmlElement(name="testcase")
	public Collection<TestCase> getTestCases() {
		return testCases;
	}
	/**
	 * @param testcases the testcases to set
	 */
	public void setTestCases(Collection<TestCase> testCases) {
		this.testCases = testCases;
	}

	/**
	 * @return the suiteInfo
	 */
	@XmlElement (name="info")
	public SuiteInfo getSuiteInfo() {
		return suiteInfo;
	}
	/**
	 * @param suiteInfo the suiteInfo to set
	 */
	public void setSuiteInfo(SuiteInfo suiteInfo) {
		this.suiteInfo = suiteInfo;
	}
	/**
	 * @return the metaInfo
	 */
	@XmlElement(name="meta")
	public SuiteMetaInfo getMetaInfo() {
		return metaInfo;
	}
	/**
	 * @param metaInfo the metaInfo to set
	 */
	public void setMetaInfo(SuiteMetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}
	

}
