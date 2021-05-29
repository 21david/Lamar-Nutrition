## Project introduction
The objective of this project was to create a program where a user could get nutrition facts about ingredients and meals as a way to facilitate access to this information and promote healthier eating habits.

## Interface
![Image](https://github.com/21david/Lamar-Nutrition/blob/master/GUI%20breakdown.png)

## How to get started and run the program:
1. Clone all files onto a local repository
2. Put all required resources in 'bin' folder (see below), including the databse file
3. Put all required libraries into the java build path (see below)
    - see [this video](https://youtu.be/zWTzZ4sNAFw "How to Import or Add Jar Libraries in Eclipse") to learn how in Eclipse
4. Run 'GUI.java' and wait a few seconds (10-15)

## Program operation instructions:
1. Select ingredients from product drop down (can type to use autocomplete)
1. Use spinner (up and down buttons) to adjust desired serving size
1. Add ingredient to meal using ADD button
    - use DELETE button to remove unwanted ingredient(s)
1. When meal is complete, click FINALIZE MEAL button
1. View nutritional info

If saving the meal is desired, click SAVE button after finalizing and
	follow prompts to name meal (default is timestamp)
  
If loading a previous meal is desired, select meal name from saved meals dropdown and
	click LOAD button


## List of included and required files:
|File|Description|
|-|-|
|GUI.java		| Main interface java file |
| DBConnection.java| Java file for DBConnection class to load and query the database|
| Ingredient.java		| Java file for the Ingredient class|
|Meal.java	| Java file for the Meal class|
|README.md| Readme file with all info and instructions|
| food.jpg	| JPG file for GUI image - **PUT IN BIN FOLDER** |
| background.png	| PNG file for GUI background - **PUT IN BIN FOLDER**|
|stylesheet.css	| CSS file for GUI CSS styling - **PUT IN BIN FOLDER**|
|commons-lang-2.6.jar	| Required jar for DB access - **ADD TO JAVA BUILD PATH AS EXTERNAL JAR**|
| commons-logging-1.1.1.jar	| Required jar for DB access - **ADD TO JAVA BUILD PATH AS EXTERNAL JAR**|
|ucanaccess-4.0.1.jar	| Required jar for DB access - **ADD TO JAVA BUILD PATH AS EXTERNAL JAR**|
| hsqldb-2.3.1.jar	| Required jar for DB access - **ADD TO JAVA BUILD PATH AS EXTERNAL JAR**|
| jackcess-2.1.6	| Required jar for DB access - **ADD TO JAVA BUILD PATH AS EXTERNAL JAR**|
|controlsfx-8.40.12.jar	| Required jar for ComboBox autofill - **ADD TO JAVA BUILD PATH AS EXTERNAL JAR**|

### Required files **NOT** included because of size:
|File|Description|
|-|-|
| SR_Legacy.accdb	| Required USDA database file|

**Download file from: https://www.ars.usda.gov/ARSUserFiles/80400525/Data/SR-Legacy/SR-Leg_DB.zip 
** then unzip and PUT IN BIN FOLDER**
