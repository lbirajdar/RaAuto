## How to use RaAuto ##

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
	 - template.xml(resources/template.xml) provides the template to write the sequence of steps for automated workflows. Strictly follow the template to include your sequence of test steps and verification points
	 - template-user-data.properties (resources/userdata/template-user-data.properties) guides you to write the user defined inputs. Edit it as per your need. Path to this file needs to be updated in suite xml (that you generated in above step) inside <-testdatafile-> tag
	 
 - **Running the automated workflow**
	 Update the suites.properties file and set the values for the followings at the minimum
	 - waas.suites.folder = Location of your suite files in following format
   “d:/Trial/suites/” or d:\\Trial\\suites\\”  
	 - waas.suites=mysuite1.xml,mysuite2.xml (A comma separated list of your suite files)	
	 - waas.path.to.browser.driver.exe = Location of browser driver exe
   e.g. C:/Program Files/Internet Explorer/IEDriverServer.exe Must be
   specified for browsers other than Firefox   
	 -  Go to the build(target) folder and run java -jar WAaaS-AUTO-1.0.jar "{path-to-suites.properties}"

 - **Reference**
	Use help.txt for more details on supported actions
