package com.waas.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="meta")
public class SuiteMetaInfo {

	private String baseUrl;

	public SuiteMetaInfo() {
	}
	@XmlElement(name="baseurl", defaultValue="")
	public String getBaseUrl() {
		return baseUrl;
	}
	
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
}
