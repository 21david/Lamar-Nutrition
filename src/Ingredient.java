/*Jeremy Lovelace, Chris Blackwell, David Espinosa, Bilal Mahmood
CPSC 4360 Spring 2019
Estimating Scores of Nutrition Facts for Meals on Restaurant Menus and Home
*/

/*
   This class represents a single ingredient,
   and it has getters and setters for all the
   nutrition information of an ingredient.
 */

import java.io.Serializable;

//Ingredient class implements Serializable for saving meals
public class Ingredient implements Serializable {

	private static final long serialVersionUID = -4022463093147251770L;

	/* cals code 208 unit cal
	 * fat code 204 unit g
	 * sat fat code 606 unit g
	 * trans fat code 605 unit g
	 * cholesterol code 601 unit mg
	 * sodium code 307 unit mg
	 * carb code 205 unit g
	 * fiber code 291 unit g
	 * sugar code 269 unit g
	 * protein code 203 unit g
	 *  
	 */
	//variable for the nutrient codes in the database
	//cals(cal)=208;fat(g)=204;satFat(g)=606;transFat(g)=605;cholesterol(mg)=601
	//sodium(mg)=307;carbs(g)=205;fiber(g)=291;sugar(g)=269;protein(g)=203
	final int[] nutrientCodeList = {208,204,606,605,601,307,205,291,269,203};
	
	//variables for all ingredient info
	private String NBD_no;
	private String Long_Desc;
	private String seqCode;
	private double servingHouseholdAmount;
	private String Msre_Desc;
	private double servingGm_Wgt;
	private double totalGm_Wgt;
	private double numServings;
	private double totalCals;
	private double totalFat;
	private double totalSatFat;
	private double totalTransFat;
	private double totalCholesterol;
	private double totalSodium;
	private double totalCarbs;
	private double totalFiber;
	private double totalSugar;
	private double totalProtein;
	
	//variable for database connection
	transient private DBConnection database;

	//Ingredient constructor
	//receives the ingredient db name, number, seq (for standard serving), number of servings
	// and the database connection
	public Ingredient (String name, String num, String seq, Double servings, DBConnection dB) {		
		Long_Desc = name;
		numServings = servings;
		database = dB;
		
		//unique number identifier for each ingredient in the db
		NBD_no = num;
		seqCode = seq;
		
		//get the servings amounts (household and grams)
		getServingAmounts();
		
		//get all the nutritional info of the ingredient
		getNutrition();
	}// end of Ingredient constructor

	
	// method to get the serving amounts (household and grams)
	// using database queries with the NBD number and serving type 
	// sequence number
	private void getServingAmounts() {  
		//query db for the household serving amount
		servingHouseholdAmount = Double.parseDouble(this.database.executeQuery(
				  "SELECT WEIGHT.Amount " 
				+ "FROM WEIGHT WHERE NDB_No='" + this.NBD_no + "' AND Seq='"
				+ this.seqCode + "'"));
		//query db for the unit of the household serving amount
		Msre_Desc = this.database.executeQuery(
				  "SELECT WEIGHT.Msre_Desc " 
				+ "FROM WEIGHT WHERE NDB_No='" + this.NBD_no + "' AND Seq='" 
				+ this.seqCode + "'");
		//query db for the standard serving amount in grams
		servingGm_Wgt = Double.parseDouble(this.database.executeQuery(
				  "SELECT WEIGHT.Gm_Wgt " 
				+ "FROM WEIGHT WHERE NDB_No='" + this.NBD_no + "' AND Seq='"
				+ this.seqCode + "'"));
		totalGm_Wgt = servingGm_Wgt * numServings;
	}// end of getServingAmounts method
	
	//method to get all the nutritional info for an ingredient with
	// a query using the NDB number and the nutrient code
	private void getNutrition() {
		//temp array for all the nutrient data in the order:
		//cals,fat,satFat,transFat,cholesterol,sodium,carbs,fiber,sugar,protein
		double[] tempNutrientArray = new double[nutrientCodeList.length];
		
		//loop through each code in the nutrient code list executing a new query
		for (int i = 0; i < nutrientCodeList.length; i++) {
			//temp string for the value returned by the query
			String tempValue = this.database.executeQuery(
					"SELECT NUT_DATA.Nutr_Val " + 
					"FROM NUT_DATA WHERE NDB_No='" + this.NBD_no + 
					"' AND Nutr_No='" + nutrientCodeList[i] + "'");
			
			//if the value isn't null then store in the nutrient array
			if(tempValue != null && !tempValue.isEmpty()) { 
				tempNutrientArray[i] = Double.parseDouble(tempValue);
			} else { //if query returns null, store 0.0 for current nutrient
				tempNutrientArray[i] = 0.0;
			}
		}
		
		//db has nutrient info listed per 100 grams
		//this is variable for ratio based on total gram weight to 
		//multiply by db result to get total amount per nutrient
		double gramRatio = totalGm_Wgt / 100;
		
		//do math with gramRatio, round to 1 decimal place
		//and store in appropriate variable
		totalCals = round(tempNutrientArray[0] * gramRatio, 1);
		totalFat = round(tempNutrientArray[1] * gramRatio, 1);
		totalSatFat = round(tempNutrientArray[2] * gramRatio, 1);
		totalTransFat = round(tempNutrientArray[3] * gramRatio, 1);
		totalCholesterol = round(tempNutrientArray[4] * gramRatio, 1);
		totalSodium = round(tempNutrientArray[5] * gramRatio, 1);
		totalCarbs = round(tempNutrientArray[6] * gramRatio, 1);
		totalFiber = round(tempNutrientArray[7] * gramRatio, 1);
		totalSugar = round(tempNutrientArray[8] * gramRatio, 1);
		totalProtein = round(tempNutrientArray[9] * gramRatio, 1);
	}// end of getServingAmounts method
	
	//getter method to return the unique NDB number for ingredient
	public String getNBD_No () {
		return this.NBD_no;
	}

	//getter method to return the name of ingredient
	public String getLong_Desc () {
		return this.Long_Desc;
	}

	//getter method to return standard serving amount for ingredient
	public double getServingHouseholdAmount () {
		return this.servingHouseholdAmount;
	}

	//getter method to return standard serving amount unit for ingredient
	public String getMsre_Desc () {
		return this.Msre_Desc;
	}

	//getter method to return gram weight for ingredient
	public double getTotalGm_Wgt () {
		return this.totalGm_Wgt;
	}

	//getter method to return number of servings for user
	public double getNumServings () {
		return this.numServings;
	}

	//getter method to return total calories for ingredient
	public double getTotalCals () {
		return this.totalCals;
	}

	//getter method to return total fat for ingredient
	public double getTotalFat () {
		return this.totalFat;
	}

	//getter method to return total saturated fat for ingredient	
	public double getTotalSatFat () {
		return this.totalSatFat;
	}

	//getter method to return total trans fat for ingredient
	public double getTotalTransFat () {
		return this.totalTransFat;
	}

	//getter method to return total cholesterol for ingredient
	public double getTotalCholesterol () {
		return this.totalCholesterol;
	}

	//getter method to return total sodium for ingredient
	public double getTotalSodium () {
		return this.totalSodium;
	}

	//getter method to return total carbs for ingredient
	public double getTotalCarbs () {
		return this.totalCarbs;
	}

	//getter method to return total fiber for ingredient
	public double getTotalFiber () {
		return this.totalFiber;
	}

	//getter method to return total sugar for ingredient
	public double getTotalSugar () {
		return this.totalSugar;
	}

	//getter method to return total protein for ingredient
	public double getTotalProtein () {
		return this.totalProtein;
	}

	// method to print the total name and nutritional info to the
	//  console (for testing)
	public void printAll () {
		System.out.println("NBD_no " + this.NBD_no);
		System.out.println("Long_Desc " + this.Long_Desc);
		System.out.println("servingHouseholdAmount " + 
				this.servingHouseholdAmount);
		System.out.println("Msre_Desc " + this.Msre_Desc);
		System.out.println("totalGm_Wgt " + this.totalGm_Wgt);
		System.out.println("numServings " + this.numServings);
		System.out.println("totalCals " + this.totalCals);
		System.out.println("totalFat " + this.totalFat);
		System.out.println("totalSatFat " + this.totalSatFat);
		System.out.println("totalTransFat " + this.totalTransFat);
		System.out.println("totalCholesterol " + this.totalCholesterol);
		System.out.println("totalSodium " + this.totalSodium);
		System.out.println("totalCarbs " + this.totalCarbs);
		System.out.println("totalFiber " + this.totalFiber);
		System.out.println("totalSugar " + this.totalSugar);
		System.out.println("totalProtein " + this.totalProtein);
	}// end of printAll method


	//method to round doubles to a specified precision
	private static double round (double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round(value * scale) / scale;
	}// end of round method

}
