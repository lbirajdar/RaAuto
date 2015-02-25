package com.waas.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement (name="applicablewhen")
public class ApplicableWhen {
	
	private Context context;

	@XmlElement (name="context")
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	

}
