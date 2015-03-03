package com.raauto.jaxb;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement (name="event")
public class ExecutionEvent {

	private int 	m_id;
	private String	desc;
	private String name;
	private String optional;
	private Context context;
	
	
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
	 * @return the context
	 */
	
	public Context getContext() {
		return context;
	}
	/**
	 * @param context the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}
	/**
	 * @return the name
	 */
	@XmlAttribute(required=true)
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
	 * @return the optional
	 */
	@XmlAttribute(required=true)
	public String getOptional() {
		return optional;
	}
	/**
	 * @param optional the optional to set
	 */
	public void setOptional(String optional) {
		this.optional = optional;
	}
}
