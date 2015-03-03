package com.raauto.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.raauto.jaxb.Suite;


@XmlRootElement (name="raAutoMetaData", namespace="com.raauto.jaxb")
@XmlType (propOrder={"suite"})
public class RaAutoMetaData {
	
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
