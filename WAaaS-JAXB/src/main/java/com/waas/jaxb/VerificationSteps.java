package com.waas.jaxb;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement (name="verificationsteps")
@XmlType (propOrder={"contexts", "correctiveSteps"})
public class VerificationSteps {
	
	private Collection<Context> contexts;
	private Collection<CorrectiveStep> correctiveSteps;
	
	public VerificationSteps() {
		contexts = new ArrayList<Context>();
		correctiveSteps = new ArrayList<CorrectiveStep>();
	}
	
	@XmlElementWrapper (name="contexts")
	@XmlElement(name="context")
	public Collection<Context> getContexts() {
		return contexts;
	}
	public void setContexts(Collection<Context> contexts) {
		this.contexts = contexts;
	}
	
	@XmlElementWrapper (name="correctiveSteps")
	@XmlElement(name="correctiveStep")
	public Collection<CorrectiveStep> getCorrectiveSteps() {
		return correctiveSteps;
	}
	public void setCorrectiveSteps(Collection<CorrectiveStep> correctiveSteps) {
		this.correctiveSteps = correctiveSteps;
	}


}
