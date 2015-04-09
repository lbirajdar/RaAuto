    
Supported Actions
-----------------
Following actions are supported as value of <"event name=">
 1. navigate – URL
 2. click – Button/Link
 3. setText – text Field – Input Data
 4. check/unCheck – Check box
 5. select – Single Value from dropdown – Value to be selected
 6. clear – Clear Text field
 7. getTable – Get and print the table contents
 8. submit – submit form/ submit Button click 
 9. browseFile – Browse to file for upload – Actual path to the file with file name
 10. findValueInTable – Find if the value is present in the table or not. Single/ Multiple/ No occurrence of value in table. - Value to be found
 11. pickDate – Date picker 
 12. rightClick - Right click on the control to get context menu
 13. doubleClick - Double click on control
 14. searchValueInTable - Find perticular value in table
 15. switchToFrame - Switch control to the frame with specified name
 16. wait - Ask system to wait for specified number of seconds
	e.g. 
					<"event name="wait" optional="false">
						<"context type="" value="">20<"/context">
					<"/event">
 17.  switchToWindow - Switch to window specified by the name (or in case of only one child window ChildWindow/ParentWindow as input data)
e.g. 
 Switch to child window 
					<"event name="SwitchToWindow" optional="false">
						<"context type="" value="">ChildWindow</context">
					<"/event">
					Switch to parent window
					<"event name="SwitchToWindow" optional="false">
						<"context type="" value="">ParentWindow<"/context">
					<"/event">
					Switch to  window named MyWindow
					<"event name="SwitchToWindow" optional="false">
						<"context type="" value="">MyWindow<"/context">
					<"/event">
18. dragFrom: Drag an element
 19. dropTo: Drop the dragged element to the element
 20. dropToOffset: Drag an element and drop to the location.
		Syntax:
			<"event name="dropToOffset" optional="true">
					<"context type="xpath" value="id('Fav')/li[1]">400,500<"/context">
				<"/event">				
 21. searchInTableAndClick :  Search for the value in table and click on specified column number in the table for the first found occurance of the value. ColumnNo shoud be blank if the search element itself asked to click
		Syntax:
			<"event name="searchInTableAndClick" optional="false">
					<"context type="xpath" value="id('main-content')/div[2]/div/div/table/tbody/tr" columnNo="4">12/31/12<"/context">
			<"/event">

Supported Attributes 
-------------------------------------------------
Following list is of options supported for attributes of Context  of Event– depending on what you use as identifier – Hint: use Firefox IDE, XpathChecker, Firebug to identify your object in <"context type="identifyBy" value="the identifier value">
 - ”type” = Id, classname, name, cssselector, xpath, linktest, partiallinktext, tagname
 - ”value”=your identifier value

Verification
------------
verificationSteps and correctivesteps are the two tags inside <"testcase"> which help in verifying the actual and expected output.

The individual <"context"> element of verificationSteps defined what to verify and how whereas <"context"> element applicableWhen inside defines the condition/criteria that needs to be satisfied in order to get the <"correctivestep name="testname"> executed

Following	 actions are supported as <"context action="from the below list">

 1. getText : Input: Value to be verified on the control
 2.  getOccurencesInTable : 
				Input :
					i.  Value to be searched in table in <"SearchData"> tag under context.
					ii. Number of expected ocurrences of that value in table in <"expectedCount"> tag under context.
					e.g.
					<"context action="getOccurencesInTable" type="xpath" value="id('main-content')/div[2]/div/div/table/tbody/tr">
						<"searchData">$336.21<"/searchData">
						<"expectedCount">1<"/expectedCount">
					<"/context">
					
 3. elementShouldNotExist : Element is deleted by any previous operations and expected to be not available.
					e.g. 
					<"context action="ElementShouldNotExist" type="id" value="sctl00_txtSearchValue">Search Employee<"/context">
					
 4. wait: A flexible wait time. Useful to wait for verification element to get populated(if required).
					Syntax:
					<"context action="wait" type="" value="">5<"/context">	
					
 5. Corrective Steps: If you would like to perform further operation when the verification is passed.
			1.	Corrective Step
					1.1	Events as mentioned 
				1.2	applicableWhen – The condition deciding when to take corrective action i.e. On existence of specific element on page. 