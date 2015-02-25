package com.waas.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.waas.jaxb.Suite;


@XmlRootElement (name="wAaasMetaData", namespace="com.waas.jaxb")
@XmlType (propOrder={"suite"})
public class WAaasMetaData {
	
	private Suite suite;

	/**
	 * @return the suite
	 */
	@XmlElement(required=true, name="suite")
	public Suite getSuite() {
		return suite;
	}

	/**
	 * @param suite the suite to set
	 */
	public void setSuite(Suite suite) {
		this.suite = suite;
	}
}
