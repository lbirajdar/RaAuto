package com.waas.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement (name="applicableWhen")
public class ApplicableWhen {
	
	private Context context;

	@XmlElement (name="context", required=true)
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	

}
