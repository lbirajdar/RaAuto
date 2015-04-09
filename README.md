## About RaAuto - Rapid Automation ##

**RaAuto is a  rapid test automation framework, which enables end users automate their test cases and workflows in a scritptless manner**

**In its current form, it is based on Selenium and is designed to support workflow and test automation of web applications** 

This framework, enables users rapidly automate the workflow of Web applications.  The salient features of this solution include, 

 - It can be used for quickly automating workflows & test scenarios.
 - It simplifies the process of automation, by removing coding/scripting part from it.
 - As part of it, this solution provides the XML template for writing automated workflows & test cases.
 - It can be used as a standalone utility or the wrappers can be written to generate the XML from a wizard.
 - The solution can easily be integrated with CI/CD system. 


## How to use RaAuto - The Rapid Automation Framework ##

 - **Pre-requisites**:
	 - JDK 1.6 or higher
	 - Basic knowledge of HTML for identifying objects and controls on a webpage
	 - Supported browsers - please read official selenium documentation for details at http://docs.seleniumhq.org/about/platforms.jsp 
	 
 - **Building the project and executables**
	 - Simply build project with mvn install command. It builds following resources under respective build(target) directories 
		 - {module}-{version}.jar
		 - {module}-{version}-sources.jar
		 - {module}-{version}-javadoc.jar
		
 - **Writing automated workflows/tests**
	 - template.xml(RaAuto-AUTO\samples\Sample-Suite.xml) provides the template to write the sequence of steps for automated workflows. Strictly follow the template to include your sequence of test steps and verification points
	 - template-user-data.properties (RaAuto-AUTO\samples\template-data.properties) guides you to write the user defined inputs. Edit it as per your need. Path to this file needs to be updated in suite xml (that you generated in above step) inside <-testdatafile-> tag
	 
 - **Running the automated workflow**
	 Update the `suites.properties` file and set the values for the followings at the minimum
	 `- raauto.suites.folder` = Location of your suite files in following format
   “d:/Trial/suites/” or d:\\Trial\\suites\\”  
	 `- raauto.suites` = mysuite1.xml,mysuite2.xml (A comma separated list of your suite files)	
	 `- raauto.path.to.browser.driver.exe` = Location of browser driver exe
   e.g. C:/Program Files/Internet Explorer/IEDriverServer.exe Must be
   specified for browsers other than Firefox   
	 -  Go to the target (RaAuto-AUTO\target) folder and run `java -jar RaAuto-AUTO-1.0.jar "{path-to-suites.properties}"`

 - **Reference**
	Use [Help.MD](./help.md) for more details on supported actions
