/*Jeremy Lovelace, Chris Blackwell, David Espinosa, Bilal Mahmood
CPSC 4360 Spring 2019
Estimating Scores of Nutrition Facts for Meals on Restaurant Menus and Home
*/

/*
   This file is in charge of the GUI and putting everything together
   to make it work. The main method and start method is here, 
   so execution starts in this file.
 */

import java.util.ArrayList;
import org.controlsfx.control.textfield.TextFields;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox; 
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.WindowEvent;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;


public class GUI extends Application {
	// Meal object variable for user meals
	private Meal meal = new Meal();
	
	// StringBuilder variable for building the informational display string
	private StringBuilder displayString = new StringBuilder();
	
	// temp String variable for meal info in this order: index 0=NDB num;
	// index 1=name;index 2=grams;index 3=user servings;index 4=sequence number
	private String[] ingredientString = new String[5];
	
	// ArrayList variable of previously saved meals
	private ArrayList<Meal> readMeals = readFile();
	
	
	// main method to launch GUI
	public static void main(String[] args) {
		launch(args);
	}

	// sets up GUI and all its components
	// uses DBConnection to create a connection to the database
	public void start(Stage primaryStage) {
		
		// string variable for the main menu
		final String mainMenu = "Welcome to the Lamar Nutrition Calculator!\n\n" + 
				"Please begin building a meal\n\n";
		
		// window title
		primaryStage.setTitle("Lamar Nutrition");
		
		// new grid pane and it's settings
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER );
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(15, 15, 15, 15));
		Text scenetitle = new Text("Lamar Nutrition");
		scenetitle.setId("welcome-text");
		
		// db filename 
		String msAccessFile = "SR_Legacy.accdb";
		
		// new database connection
		DBConnection usdaDB = new DBConnection(msAccessFile);

		// query the database for the full ingredient info and save in ArrayList<String[]>
		ArrayList<String[]> productArray = usdaDB.executeQuery(
				  "SELECT FOOD_DES.NDB_No, "
				+ "FOOD_DES.Long_Desc, WEIGHT.Amount, WEIGHT.Msre_Desc, "
				+ "WEIGHT.Gm_Wgt, WEIGHT.Seq FROM FOOD_DES FULL OUTER JOIN " 
				+ "WEIGHT ON FOOD_DES.NDB_No=WEIGHT.NDB_No WHERE WEIGHT.Amount IS NOT NULL ORDER BY FOOD_DES.Long_Desc", 6); 

		
		// new dropdown for the ingredient list
		ComboBox<String> productComboBox = new ComboBox<String>();
		
		// number of ingredients in the list
		int numProducts = productArray.size();
		
		// loop through all the ingredients and concatenate the name and standard
		// serving size and add it to the dropdown list
		for (int i = 0; i < numProducts; i++) {
			productComboBox.getItems().add(productArray.get(i)[1] + " - " 
					+ productArray.get(i)[2] + " " + productArray.get(i)[3]
							+ " - " + productArray.get(i)[4] + " g");
		}
		productComboBox.setPrefWidth(1050);
		productComboBox.setEditable(true);
		
		// make dropdown autofill
		TextFields.bindAutoCompletion(productComboBox.getEditor(), 
				productComboBox.getItems()).setPrefWidth(1050);
		
		// label for dropdown
		Label productComboBoxLabel = new Label("Ingredient");
		
		// text box for the standard serving size in the db
		TextField servingSizeField = new TextField();
		servingSizeField.setPrefWidth(400);
		servingSizeField.setText("");
		
		// make textbox not editable
		servingSizeField.setEditable(false);
		
		// label for standard serving size text box
		Label servingSizeLabel = new Label("Serving Size");

		// String for all the options for the user serving amount
		// 0.25 - 20 in 0.25 increments
		String[] servingSizeOptionArray = new String[78];
		servingSizeOptionArray[0] = "0.25";
		double temp = 0.0;
		for (int i = 1; i < 78; i++) {
			temp += 0.25;
			servingSizeOptionArray[i] = Double.toString(temp + 0.25);       	
		}
		
		// spinner for user to select their serving size
		Spinner<String> numServings = new Spinner<String>(FXCollections 
				.observableArrayList(servingSizeOptionArray));
		numServings.setPrefWidth(100);   
		
		// set default serving size to 1
		numServings.getValueFactory().setValue("1.0");
		
		// label for spinner
		Label servingsLabel = new Label("Servings");     

		// ArrayList of Strings for the saved meal names
		ArrayList<String> savedMealsNames = new ArrayList<>();
		for (int i = 0; i < readMeals.size(); i++) {
			savedMealsNames.add(readMeals.get(i).getName());
		}
		
		// ComboBox for the user to select a saved meal from the 'serialized_data.ser' file
		ComboBox<String> savedMealsComboBox = new ComboBox<String>(FXCollections 
				.observableArrayList(savedMealsNames));
		savedMealsComboBox.setPrefWidth(200);

		// label for saved meals ComboBox dropdown
		Label savedMealsLabel = new Label("Saved meals");
		
	
		// add button and it's settings
		Button btnAdd = new Button("ADD");
		btnAdd.prefHeight(30);
		btnAdd.prefWidth(50);
		
		// delete button and it's setting
		Button btnDelete = new Button("DELETE");
		btnAdd.prefHeight(30);
		btnAdd.prefWidth(70);
		
		// save button and it's setting
		Button btnSave = new Button("SAVE");
		btnAdd.prefHeight(30);
		btnAdd.prefWidth(60);
		
		// load button and it's setting
		Button btnLoad = new Button("LOAD");
		btnAdd.prefHeight(30);
		btnAdd.prefWidth(60);

		// text area for the main display window and it's settings
		TextArea textDisplayArea = new TextArea();
		textDisplayArea.setId("text-display");
		textDisplayArea.setPrefWidth(900);
		textDisplayArea.setPrefHeight(450);
		textDisplayArea.setWrapText(true);
		textDisplayArea.setText(mainMenu);

		
		// ImageView variable for GUI jpg image
		ImageView img1 = new ImageView();
		
		// load image in try catch in case food.jpg is not in the class path
		try {
			// food picture image (in classpath [bin folder])
			img1 = new ImageView(new 
				Image(GUI.class.getResource("food.jpg").toExternalForm()));
		}
    	catch(NullPointerException ex) {   		
			// if jpg file not found, give error dialog and continue
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("JPG RESOURCE NOT FOUND");
			alert.setContentText("JPG file not found, verify food.jpg file is in the class path and restart program");
			alert.showAndWait();
    		System.out.println("Did not find image file.");  		
    	}
		
		// finalize button and it's settings
		Button btnFinalize = new Button("FINALIZE MEAL");
		btnFinalize.setPrefWidth(200);
		btnFinalize.setPrefHeight(75);

		// hbox to position the program title
		HBox hbTitle = new HBox(50);
		hbTitle.setAlignment(Pos.CENTER);
		hbTitle.getChildren().add(scenetitle);
		
		// hbox to position the ingredients dropdown and label
		HBox hbProducts = new HBox(30);
		hbProducts.setAlignment(Pos.CENTER_LEFT);
		hbProducts.getChildren().add(productComboBoxLabel);
		hbProducts.getChildren().add(productComboBox);
		
		// hbox to position the servings size info
		HBox hbServings = new HBox(18);
		hbServings.setAlignment(Pos.CENTER_LEFT);
		hbServings.getChildren().add(servingSizeLabel);
		hbServings.getChildren().add(servingSizeField);
		hbServings.getChildren().add(servingsLabel);
		hbServings.getChildren().add(numServings);
		hbServings.getChildren().add(btnAdd);
		hbServings.getChildren().add(btnDelete);
		
		// hbox to position the load and save buttons
		HBox hbLoadSaveButton = new HBox(20);
		hbLoadSaveButton.setAlignment(Pos.CENTER);
		hbLoadSaveButton.getChildren().add(btnLoad);
		hbLoadSaveButton.getChildren().add(btnSave);	
		
		// vbox to position the food pic and finalize button together
		VBox vbImageAndFinalize = new VBox(10);
		vbImageAndFinalize.setAlignment(Pos.BOTTOM_CENTER);
		vbImageAndFinalize.getChildren().add(savedMealsLabel);
		vbImageAndFinalize.getChildren().add(savedMealsComboBox);
		vbImageAndFinalize.getChildren().add(hbLoadSaveButton);
		vbImageAndFinalize.getChildren().add(img1);
		vbImageAndFinalize.getChildren().add(btnFinalize);
		
		// hbox to position the display area, food pic 
		// and finalize button together
		HBox hbDisplayAndImage = new HBox(25);
		hbDisplayAndImage.getChildren().add(textDisplayArea);
		hbDisplayAndImage.getChildren().add(vbImageAndFinalize);
		
		// vbox to position all the elements vertically 
		VBox vbProductsServing = new VBox(18);
		vbProductsServing.getChildren().add(hbTitle);
		vbProductsServing.getChildren().add(hbProducts);
		vbProductsServing.getChildren().add(hbServings);

		// add everything to the grid
		grid.add(vbProductsServing, 0, 0, 3, 2);
		grid.add(hbDisplayAndImage, 2, 2, 1, 1);

		
		// create the scene with the default size
		Scene scene = new Scene(grid, 1200, 800);
		primaryStage.setScene(scene);
		
		// use css to format the look of the window (css file in classpath [bin folder])
		// use try catch to load CSS file, if not found continue without it
		try {
			String CSSresource = GUI.class.getResource("stylesheet.css").toExternalForm();
			scene.getStylesheets().add(CSSresource);
		}
		catch (NullPointerException ex) {
			// if CSS file not found, give error dialog and continue
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("NO CSS FILE  LOADED");
			alert.setContentText("CSS formating file not found, verify Login.css file is in the class path and restart program");
			alert.showAndWait();
    		System.out.println("Did not find CSS file.");  		
    	}
		primaryStage.show();

		// if productArray size is zero, the database wasn't loaded. Show error and exit program
		if(productArray.size() == 0) {
// 			Alert alert = new Alert(AlertType.ERROR);
// 			alert.setTitle("Error Dialog");
// 			alert.setHeaderText("NO DATABASE LOADED");
// 			alert.setContentText("Please, verify the SR_Legacy.accdb file is in the class path and restart program");
// 			alert.showAndWait();		
// 			primaryStage.close();
		}
		
		
		// product selection listener
		productComboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue ov, String oldVal, String newVal) {

				int numProducts = productArray.size(); 
				
				// temp string for the selected ingredient db number
				String NDBnumber = "";
				
				// split the concatenated selection back into the ingredient name ([0[) and
				//  standard serving size ([1])
				String[] itemNameSplit = newVal.split(" - ");
				
				// loop through each product in array to find the db NBD number
				for (int i = 0; i < numProducts; i++) {
					// temp string of the standard servings size and unit
					//  to compare in order to find the choices seq number
					String tempMeasure = productArray.get(i)[2] + " " + 
							productArray.get(i)[3];
					
					// if the name and standard serving size is found then
					//  fill the mealString array with the NBD number ([0]),
					//  the name ([1]) and the seq number ([4])
					if (productArray.get(i)[1].equals(itemNameSplit[0]) && 
							itemNameSplit[1].equals(tempMeasure)) {
						NDBnumber = productArray.get(i)[0];
						ingredientString[0] = productArray.get(i)[0];
						ingredientString[1] = productArray.get(i)[1];
						ingredientString[4] = productArray.get(i)[5];
						// once found, exit loop
						break;
					}
				}
				
				// query db using the NDB num and seq number to get the matching standard
				//  serving size, unit of measure, and gram weight
				ArrayList<String[]> servingArray = usdaDB.executeQuery("SELECT WEIGHT.Amount, "
						+ "WEIGHT.Msre_Desc,  WEIGHT.Gm_Wgt FROM WEIGHT WHERE NDB_No='"
						+ NDBnumber + "' AND Seq='" + ingredientString[4] + "'", 3);
				
				// set serving size text field to the standard serving size and unit of measure
				servingSizeField.setText(servingArray.get(0)[0] + " " + servingArray.get(0)[1] + " - "
						+ servingArray.get(0)[2] + " g");
				
				// make mealString[2] the ingredient weight in grams
				ingredientString[2] = servingArray.get(0)[2];
			}    

		});

		// click Add button listener
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (meal.getFinalized()) {
					// create new meal object if previous meal has been finalized
					meal = new Meal();
				}
				// make mealString[3] the number of servings the user selected
				
				if (ingredientString[1] != null && !ingredientString[1].isEmpty()) {
					ingredientString[3] = (String) numServings.getValue();
					
					// add the ingredient to the meal object
					meal.pushIngredient(new Ingredient(ingredientString[1], ingredientString[0], 
							ingredientString[4], Double.parseDouble(ingredientString[3]), usdaDB));
					
					// clear the previous display string contents
					displayString.setLength(0);
					
					// loop through all ingredients in the meal creating a display string
					//  that shows each item and basic info
					for (int i = 0; i < meal.getNumIngredients(); i++) {
						displayString.append(i + 1 + ". " + meal.getIngredientInfo(i) + "\n");
					}
					
					// set the display area text to the display string previously created
					textDisplayArea.setText(displayString.toString());
					
				} else {
					// if user tried to add item without anything selected, give error message
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("NO INGREDIENT SELECTED");
					alert.setContentText("Please select an ingredient and trying adding again");
					alert.showAndWait();
				}
			}
		});
		

		// click Delete button listener
		btnDelete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				// if there is at least one ingredient in the meal, remove the last one
				if (meal.getNumIngredients() > 0 && !meal.getFinalized()) {
					
					// remove the last added ingredient
					meal.popIngredient();
					
					// clear the previous display string contents
					displayString.setLength(0);
					
					// loop through all ingredients in the meal creating a display string
					//  that shows each item and basic info
					for (int i = 0; i < meal.getNumIngredients(); i++) {
						displayString.append(i + 1 + ". " + meal.getIngredientInfo(i) + "\n");
					}
					
					// set the display area text to the display string previously created
					textDisplayArea.setText(displayString.toString()); 
				}
			}
		});
		
		
		// click Load button listener
		btnLoad.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				// if there is at least one ingredient in the meal, remove the last one
				String savedFileName = savedMealsComboBox.getValue();
				
				if (meal.getNumIngredients() > 0 && savedFileName != null && !savedFileName.isEmpty()) {
					// give warning about saving first
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("WARNING");
					alert.setHeaderText("Warning, the current meal is not saved!");
					alert.setContentText("Do you wish to continue loading (the current meal will be lost?");
					Optional<ButtonType> result = alert.showAndWait();
					
					// if user wants to continue loading the saved meal anyway
					if (result.get() == ButtonType.OK){
						
						// create new Meal object
						meal = new Meal();
						
						// loop through and append all meal info in loaded to the display string
						for (int i = 0; i < savedMealsNames.size(); i++) {
							if (readMeals.get(i).getName().equals(savedFileName)) {
								displayString.setLength(0);
								displayString.append(readMeals.get(i).getNutrientString());
								textDisplayArea.setText(displayString.toString()); 							
							}
						}
						
					} else {
					    // ... user chose CANCEL or closed the dialog
					}
				} else if (savedFileName == null || savedFileName.isEmpty()){
					// if user tried to load a saved meal without anything selected, give error message
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("NO MEAL CHOSEN");
					alert.setContentText("Please, choose a saved meal and try loading again");
					alert.showAndWait();
				
				} else {
					// loop through and append all meal info in loaded meal to the display string
					for (int i = 0; i < savedMealsNames.size(); i++) {
						if (readMeals.get(i).getName().equals(savedFileName)) {
							displayString.setLength(0);
							displayString.append(readMeals.get(i).getNutrientString());
							textDisplayArea.setText(displayString.toString()); 							
						}
					}						
				}
			}
		});
		
		
		// click Save button listener
		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// if there is at least one ingredient and the meal is finalized, do saved routine
				if (meal.getNumIngredients() > 0  && meal.getFinalized()) {
					// prompt user to enter a meal name (default is timestamp)
					TextInputDialog dialog = new TextInputDialog("meal name " + meal.getName());
					dialog.setWidth(100);
					dialog.setTitle("Get file save name");
					dialog.setHeaderText("Please name your meal");
					dialog.setContentText("Enter meal name:");
					
					// string for user input
					Optional<String> result = dialog.showAndWait();
					
					// set meal name to user input. if left blank, leave default
					if (result.isPresent()){
						meal.setName(result.get());
					} 
					
					// save the meal using saveResult method
					String saveResult = writeFile(meal, savedMealsNames);
					
					// add saved meal to list of saved meals
					savedMealsComboBox.getItems().addAll(meal.getName());
					
					// if meal was successfully saved, create new meal object and wipe display string
					if (saveResult.equals("saved")) {	
						meal = new Meal();
						displayString.setLength(0);
						textDisplayArea.setText(displayString.append(mainMenu).toString());
					} else {
						// if not saved, print error to console
						System.out.println("error saving");
					}
					
				} else {
					// give message that meals needs to me finalized before saving
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("MEAL NOT FINALIZED");
					alert.setContentText("You need to fianlize your meal before saving");
					alert.showAndWait();
				}
			}
		});


		// click Finalize button listener
		btnFinalize.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// if there is at least one ingredient in the meal, show all nutrient info
				if (meal.getNumIngredients() > 0  && !meal.getFinalized()) {
					
					// set the display area text to the nutrient info for the meal
					textDisplayArea.setText(meal.getNutrientString());
					
					// set Meal finalized flag to true										
					meal.setFinalized(true);
					
					// clear display string
					displayString.setLength(0);
					
				} else if (meal.getNumIngredients() == 0) {
					// if user tries to finalize an empty meal, give error message
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("NO INGREDIENTS ADDED");
					alert.setContentText("You need at least one ingredient added before finalizing");
					alert.showAndWait();
				}
			}
		});    

		// program exit listener
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				// close the database connection
				usdaDB.closeConnection();
				primaryStage.close();
			}
		});  
		

	}// end of start stage
	
	// this method reads the serialized data from 'serialized_data.ser'
	// and puts it in a ArrayList<Meal> for the rest of the program to use
	private ArrayList<Meal> readFile () {
		// temp array of saved Meal objects
		ArrayList<Meal> tempReadMeals = new ArrayList<Meal>();
		try {
			// file name for saved meals 
			String path = "serialized_data.ser";
			
			// create connection to the file
			FileInputStream file = new FileInputStream(path);
			ObjectInputStream inputStream = new ObjectInputStream(file);
			
			// load the read meals into the temp array
			tempReadMeals = (ArrayList<Meal>) inputStream.readObject();
			
			// close files and input stream when done
			inputStream.close(); 
            file.close(); 
		} // print error to console if file not found
    	catch(FileNotFoundException ex) {
    		System.out.println("Did not find file for serialized data.");
    	}
    	catch(IOException ex) {
    		ex.printStackTrace();
    	}
		catch(ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		// return the array of read meals
		return tempReadMeals;
	}
	
	// this method serializes meal data created by the user into the
	// 'serialized_data.ser' file
	private String writeFile (Meal meal, ArrayList<String> savedNames) {
		//  use try catch to catch any errors
		try {
			// output file for saving meals to
			FileOutputStream file = new FileOutputStream("serialized_data.ser");
			ObjectOutputStream os = new ObjectOutputStream(file);

			// Add the meal to the list
			readMeals.add(meal);
			savedNames.add(meal.getName());

			// Write to the storage file with [old meals] + [the new meal]
			os.writeObject(readMeals);
			os.close();
			
			// return message indicating a successful save
			return "saved";
							
		} // if file not found, catch error
    	catch(FileNotFoundException ex) {
    		System.out.println("Did not find file for serialized data.");
    	}
    	catch(IOException ex) {
    		ex.printStackTrace();
    	}
		
		// return message indicating an error during save
		return "error";
	}

}// end of GUI class
	