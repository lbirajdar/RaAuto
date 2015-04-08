package com.raauto.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement (name="context")
public class Context {

	private int 	m_id;
	private String action;
	private String type;
	private String value;
	private String text;
	//private String searchData;
	private String expectedCount;
	private String columnNo;
	private String takeElementScreenshot;
	
	/*@XmlAttribute (name="searchdata")
	public String getSearchData() {
		return searchData;
	}
	public void setSerachData(String searchData) {
		this.searchData = searchData;
	}
	*/
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

	@XmlAttribute (name="expectedcount")
	public String getExpectedCount() {
		return expectedCount;
	}
	public void setExpectedCount(String expectedCount) {
		this.expectedCount = expectedCount;
	}
	@XmlAttribute (name="columnNo")
	public String getColumnNo() {
		return columnNo;
	}
	public void setColumnNo(String columnNo) {
		this.columnNo = columnNo;
	}
	@XmlValue
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * @return the action
	 */
	@XmlAttribute
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the type
	 */
	@XmlAttribute
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the value
	 */
	@XmlAttribute
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
     * @return the takeElementScreenshot = true/false
     */
    @XmlAttribute 
    public String getTakeElementScreenshot() {
        return takeElementScreenshot;
    }
    
    public void setTakeElementScreenshot(String takeScreenshot) {
        this.takeElementScreenshot = takeScreenshot;
    }
    
	
}
