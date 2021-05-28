List of included and required Files:
=========================================================================================================
GUI.java			- Main interface java file 
DBConnection.java		- java file for DBConnection class to load and query the database
Ingredient.java			- java file for the Ingredient class
Meal.java			- java file for the Meal class
readme.txt			- readme file with all info and instructions
food.jpg			- jpg file for GUI image **PUT IN BIN FOLDER** 
background.png			- png file for GUI background **PUT IN BIN FOLDER**
stylesheet.css			- css file for GUI css styling **PUT IN BIN FOLDER**
commons-lang-2.6.jar		- required jar for dB access **ADD TO JAVA BUILD PATH AS EXTERNAL JAR**
commons-logging-1.1.1.jar	- required jar for dB access **ADD TO JAVA BUILD PATH AS EXTERNAL JAR**
ucanaccess-4.0.1.jar		- required jar for dB access **ADD TO JAVA BUILD PATH AS EXTERNAL JAR**
hsqldb-2.3.1.jar		- required jar for dB access **ADD TO JAVA BUILD PATH AS EXTERNAL JAR**
jackcess-2.1.6			- required jar for dB access **ADD TO JAVA BUILD PATH AS EXTERNAL JAR**
controlsfx-8.40.12.jar		- required jar for COmboBox autofill **ADD TO JAVA BUILD PATH AS EXTERNAL JAR**
=========================================================================================================

Required files **NOT** included because of size:
=========================================================================================================
SR_Legacy.accdb				-required USDA database file
**download file from: https://www.ars.usda.gov/ARSUserFiles/80400525/Data/SR-Legacy/SR-Leg_DB.zip
**unzip
**PUT IN BIN FOLDER**
=========================================================================================================

*****Program requires all JARs to be added to the java build path********

*****Program requires all resources (jpg, png, css, and SR_Legacy.accdb	) to be PUT IN BIN FOLDER*****


=========================================================================================================
Program operation instructions:

run GUI
wait for GUI to load (about 15 seconds due to size of dB)
select ingredient from product drop down (can type to use autocomplete)
use spinner to adjust desired serving size
add ingredient to meal using add button
	use delete button to remove unwanted ingredient(s)
when meal is complete, click finalize meal button
	view nutritional info
if saving the meal is desired click save button after finalizing
	follow prompts to name meal (default is timestamp)
if loading a previous is desired, select meal name from saved meals dropdown
	click load button