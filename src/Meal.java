/*Jeremy Lovelace, Chris Blackwell, David Espinosa, Bilal Mahmood
CPSC 4360 Spring 2019
Estimating Scores of Nutrition Facts for Meals on Restaurant Menus and Home
*/

/*
   This class represents a meal, which consists of multiple
   ingredients at different serving sizes. It has getters
   and setters for all the nutrition information of a meal.
 */

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;

// Meal class implements Serializable for saving meals
public class Meal implements Serializable {

	private static final long serialVersionUID = -7837230259736554872L;
	
	// ArrayList of all Ingredient object in the meal
	private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
	
	// variables for all nutritional info
	private double totalCalories = 0.0;
	private double totalFat = 0.0;
	private double totalSatFat = 0.0;
	private double totalTransFat = 0.0;
	private double totalCholesterol = 0.0;
	private double totalSodium = 0.0;
	private double totalCarbs = 0.0;
	private double totalFiber = 0.0;
	private double totalSugar = 0.0;
	private double totalProtein = 0.0;
	private boolean isFinalized = false; 
	private String mealName = getTimestamp();;
	
	//Meal constructor
	public Meal () {

	}// end of Meal constructor
	
	//method to add Ingredient (ing) to the meal
	//method also adds to the running nutritional totals
	// by calling calculateTotals() method
	public void pushIngredient (Ingredient ing) {
		this.ingredients.add(ing); 
		calculateTotals();
	}// end of pushIngredient method
	
	
	//method to remove last ingredient from the meal
	//method also subtracts last ingredients nutritional 
	// totals from running ingredient totals
	// by calling subtractFromTotals() method
	public void popIngredient () {
		subtractFromTotals();
		this.ingredients.remove(this.ingredients.size() - 1); 
	}// end of popIngredient method
	
//	//clearMeal method to empty meal using popIngredient() method
//	public void clearMeal () {
//		int numIngredients = this.ingredients.size();
//		for (int i = 0; i < numIngredients; i++) {
//			popIngredient ();
//		}
//		this.setFinalized(false);
//	}// end of clearMeal method
	
	//method to calculate total nutritional totals for meal
	private void calculateTotals () {
		int currentIndex = this.ingredients.size() - 1;		
		//uses Ingredient class getTotalxxxx() method
		totalCalories = round(this.totalCalories + 
				this.ingredients.get(currentIndex).getTotalCals(), 1);
		totalFat = round(this.totalFat + 
				this.ingredients.get(currentIndex).getTotalFat(), 1);
		totalSatFat = round(this.totalSatFat + 
				this.ingredients.get(currentIndex).getTotalSatFat(), 1);
		totalTransFat = round(this.totalTransFat + 
				this.ingredients.get(currentIndex).getTotalTransFat(), 1);
		totalCholesterol = round(totalCholesterol + 
				this.ingredients.get(currentIndex).getTotalCholesterol(), 1);
		totalSodium = round(this.totalSodium + 
				this.ingredients.get(currentIndex).getTotalSodium(), 1);
		totalCarbs = round(this.totalCarbs + 
				this.ingredients.get(currentIndex).getTotalCarbs(), 1);
		totalFiber = round(this.totalFiber + 
				this.ingredients.get(currentIndex).getTotalFiber(), 1);
		totalSugar = round(this.totalSugar + 
				this.ingredients.get(currentIndex).getTotalSugar(), 1);
		totalProtein = round(this.totalProtein + 
				this.ingredients.get(currentIndex).getTotalProtein(), 1);
	}// end of calculateTotals method
	
	//method to subtract last ingredients nutritional totals from
	// nutritional totals for meal
	private void subtractFromTotals () {
		int currentIndex = this.ingredients.size() - 1;		
		//uses Ingredient class getTotalxxxx() method
		totalCalories = round(this.totalCalories - 
				this.ingredients.get(currentIndex).getTotalCals(), 1);
		totalFat = round(this.totalFat - 
				this.ingredients.get(currentIndex).getTotalFat(), 1);
		totalSatFat = round(this.totalSatFat - 
				this.ingredients.get(currentIndex).getTotalSatFat(), 1);
		totalTransFat = round(this.totalTransFat - 
				this.ingredients.get(currentIndex).getTotalTransFat(), 1);
		totalCholesterol = round(totalCholesterol - 
				this.ingredients.get(currentIndex).getTotalCholesterol(), 1);
		totalSodium = round(this.totalSodium - 
				this.ingredients.get(currentIndex).getTotalSodium(), 1);
		totalCarbs = round(this.totalCarbs - 
				this.ingredients.get(currentIndex).getTotalCarbs(), 1);
		totalFiber = round(this.totalFiber - 
				this.ingredients.get(currentIndex).getTotalFiber(), 1);
		totalSugar = round(this.totalSugar - 
				this.ingredients.get(currentIndex).getTotalSugar(), 1);
		totalProtein = round(this.totalProtein - 
				this.ingredients.get(currentIndex).getTotalProtein(), 1);
	}// end of subtractFromTotals method
	
	//getter method to return total calories for the meal	
	public double getTotalCals () {
		return this.totalCalories;
	}
	
	//getter method to return total fat for the meal	
	public double getTotalFat () {
		return this.totalFat;
	}
	
	//getter method to return total saturated fat for the meal	
	public double getTotalSatFat () {
		return this.totalSatFat;
	}
	
	//getter method to return total trans fat for the meal	
	public double getTotalTransFat () {
		return this.totalTransFat;
	}
	
	//getter method to return total cholesterol for the meal	
	public double getTotalCholesterol () {
		return this.totalCholesterol;
	}
	
	//getter method to return total sodium for the meal	
	public double getTotalSodium () {
		return this.totalSodium;
	}
	
	//getter method to return total carbs for the meal	
	public double getTotalCarbs () {
		return this.totalCarbs;
	}
	
	//getter method to return total fiber for the meal	
	public double getTotalFiber () {
		return this.totalFiber;
	}
	
	//getter method to return total sugar for the meal	
	public double getTotalSugar () {
		return this.totalSugar;
	}
	
	//getter method to return total protein for the meal	
	public double getTotalProtein () {
		return this.totalProtein;
	}
	
	//getter method to return number of ingredients for the meal	
	public int getNumIngredients () {
		return this.ingredients.size(); 
	}
	
	
	//setter method to set finalized flag for meal
	public void setFinalized (boolean flag) {
		this.isFinalized = flag;
	}
	
	//getter method to return the meals finalized status
	public boolean getFinalized () {
		return this.isFinalized;
	}
	
	//getter method to return the name of the meal
	public String getName () {
		return this.mealName;
	}

	//setter method to set the name of the current meal (default is time stamp)
	public void setName (String name) {
		this.mealName = name;
	}
	
	//method to get a timestamp for the default name of the meal
	private String getTimestamp () {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
	
	//getter method to return a string of the ingredient
	// name (Long_Desc), the standard household serving amount
	// qty (ServingHousholdAmount), and the unit for the standard
	// serving amount (Msre_Desc)
	public String getIngredientInfo (int index) {
		return this.ingredients.get(index).getLong_Desc() + "\n\t-Serving size: " + 
				this.ingredients.get(index).getServingHouseholdAmount() + " " + 
				this.ingredients.get(index).getMsre_Desc() +
				"; Number of servings: " + this.ingredients.get(index).getNumServings();
	}// end of getIngredientInfo method
	
	//getter method to return a string of the total
	// nutritional info for the meal in a format
	// that is appropriate for the display area
	public String getNutrientString () {
		StringBuilder tempString = new StringBuilder();
		for (int i = 0; i < this.getNumIngredients(); i++) {
			tempString.append(i + 1 + ". " + this.getIngredientInfo(i) + "\n");
		}
		
		tempString.append("\nTotal Calories \t\t" + this.totalCalories + " calories\n" +
		"Total Fat \t\t" + this.totalFat + " g\n" +
		"Total Saturated Fat \t" + this.totalSatFat + " g\n" +
		"Total Trans Fat \t" + this.totalTransFat + " g\n" +
		"Total Cholesterol \t" + this.totalCholesterol + " mg\n" +
		"Total Sodium \t\t" + this.totalSodium + " mg\n" +
		"Total Carbs \t\t" + this.totalCarbs + " g\n" +
		"Total Fiber \t\t" + this.totalFiber + " g\n" +
		"Total Sugar \t\t" + this.totalSugar + " g\n" +
		"Total Protein \t\t" + this.totalProtein + " g");
		return tempString.toString();
	}// end of getNutrientString method
	
	// method to print the total nutritional info to the
	//  console (for testing)
	public void printAll () {
		System.out.println("Total Calories \t\t" + this.totalCalories + 
				" calories\n");
		System.out.println("Total Fat \t\t" + this.totalFat + " g\n");
		System.out.println("Total Saturated Fat \t" + this.totalSatFat + 
				" g\n");
		System.out.println("Total Trans Fat \t" + this.totalTransFat + 
				" g\n");
		System.out.println("Total Cholesterol \t" + this.totalCholesterol + 
				" mg\n");
		System.out.println("Total Sodium \t\t" + this.totalSodium + " mg\n");
		System.out.println("Total Carbs \t\t" + this.totalCarbs + " g\n");
		System.out.println("Total Fiber \t\t" + this.totalFiber + " g\n");
		System.out.println("Total Sugar \t\t" + this.totalSugar + " g\n");
		System.out.println("Total Protein \t\t" + this.totalProtein + " g");
	}// end of printAll method

	
	//method to round doubles to a specified precision
	private static double round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}// end of round method
	
}//end of Meal class
