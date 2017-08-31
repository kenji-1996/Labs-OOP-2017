package sample;

import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecipeDSC
{
	public Connection connection;
	private  Statement statement;
	private  PreparedStatement preparedStatement;
	private String username = "root",password = "",url = "jdbc:mysql://localhost:3306/java?autoReconnect=true&useSSL=false";

	static final String COLUMN_RECIPEID = "recipeID";
	static final String COLUMN_QUANTITY = "quantity";
	static final String COLUMN_UNTSANDSTYLE = "unitsAndStyle";
	static final String COLUMN_ID = "ID";
	static final String COLUMN_NAME = "name";
	static final String COLUMN_SERVES = "serves";
	static final String COLUMN_STEPS = "steps";
	static final String COLUMN_REMARKS = "remarks";

	public static boolean useAlertForInput = false;

	public static String getInfoFromUser( String userInstructions)
	{
		if (useAlertForInput) {
			final TextInputDialog inputDlg = new TextInputDialog("");
			//inputDlg.initOwner(parent);
			inputDlg.setTitle("information Required");
			inputDlg.setContentText(userInstructions);
			inputDlg.setHeaderText(null);
			inputDlg.initModality(Modality.APPLICATION_MODAL);
			Optional<String> userResponce = inputDlg.showAndWait();
			while (!userResponce.isPresent())
				userResponce = inputDlg.showAndWait();
			return userResponce.get();

		}
		try {
			System.out.println(userInstructions);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			return br.readLine();
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		return "";
	}

	public  void connect() throws Exception
	{
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	public  void disconnect() throws SQLException
	{
		if (preparedStatement != null) preparedStatement.close();
		if (statement != null) statement.close();
		if (connection != null) connection.close();
	}

	public Recipe findRecipe(int id)
	{
		Recipe recipe = null;
		try {
			connect();

			String queryString = "select * from recipes where ID = ?";
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {    // MusicAlbum exists in database
				recipe = new Recipe(
						rs.getInt(COLUMN_ID),
						rs.getString(COLUMN_NAME),
						rs.getInt(COLUMN_SERVES),
						rs.getString(COLUMN_STEPS),
						rs.getString(COLUMN_REMARKS));
			}

			disconnect();


		} catch (Exception ex) { ex.printStackTrace(); }
		return recipe;
	}

	public Ingredient findIngredient(int id)
	{
		Ingredient ingredient = null;
		try {
			connect();

			String queryString = "select * from recipeingredients where id = ?";
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {    // MusicAlbum exists in database
				ingredient = new Ingredient(
						rs.getInt(COLUMN_ID),
						rs.getString(COLUMN_NAME),
						rs.getDouble(COLUMN_QUANTITY),
						rs.getString(COLUMN_UNTSANDSTYLE));
			}

			disconnect();


		} catch (Exception ex) { ex.printStackTrace(); }
		return ingredient;
	}

	/*
	 * TODO: 	This method should count the total number of Recipes in the database
	 * @return 	An int representing the number of Recipes
	 * @throws 	SQLException
	 */
	public int count()
	{
		int count = 0;
		try {
			connect();
			String queryString = "SELECT COUNT(*) AS rowcount FROM recipes";
			preparedStatement = connection.prepareStatement(queryString);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			count = rs.getInt("rowcount");
			rs.close();
			disconnect();
		} catch (Exception ex) { ex.printStackTrace(); }
		return count;
	}

	public List<Recipe> findAll()
	{
		List<Recipe> recipeList = new ArrayList<Recipe>();
		try {
			connect();

			String queryString = "select * from recipes";
			preparedStatement = connection.prepareStatement(queryString);
			ResultSet rs = preparedStatement.executeQuery();
			Recipe tmp;
			while (rs.next()) {
				tmp = new Recipe(
						rs.getInt(COLUMN_ID),
						rs.getString(COLUMN_NAME),
						rs.getInt(COLUMN_SERVES),
						rs.getString(COLUMN_STEPS),
						rs.getString(COLUMN_REMARKS));

				recipeList.add(tmp);
			}

			disconnect();
		} catch (Exception ex) { ex.printStackTrace(); }

		return recipeList;
	}

	public List<Ingredient> findAllIngredients(int recipeID)
	{
		List<Ingredient> ingredientList = new ArrayList<>();
		try {
			connect();

			String queryString = "select * from recipeingredients WHERE recipeID = ?";
			preparedStatement = connection.prepareStatement(queryString);
			preparedStatement.setInt(1,recipeID);
			ResultSet rs = preparedStatement.executeQuery();
			Ingredient tmp;
			while (rs.next()) {
				tmp = new Ingredient(
						rs.getInt(COLUMN_ID),
						rs.getString(COLUMN_NAME),
						rs.getDouble(COLUMN_QUANTITY),
						rs.getString(COLUMN_UNTSANDSTYLE));

				ingredientList.add(tmp);
			}

			disconnect();
		} catch (Exception ex) { ex.printStackTrace(); }
		return ingredientList;
	}

	public int addIngredient(int recipeID, String name, double quantity, String unitsAndStyle) {
		int result = -1;
		try {
			connect();
			String insertString = "insert into recipeingredients(recipeID,name,quantity,unitsAndStyle) values(?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(insertString,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, recipeID);
			preparedStatement.setString(2, name);
			preparedStatement.setDouble(3, quantity);
			preparedStatement.setString(4, unitsAndStyle);
			preparedStatement.executeUpdate();

			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()){
				result=rs.getInt(1);
			}

			System.out.println("Added new ingredient " + name);

			disconnect();
		} catch (Exception ex) { ex.printStackTrace(); }
		return result;
	}
	public void addIngredient(Ingredient ingredient) {
		try {
			addIngredient(ingredient.getID(),ingredient.getName(),ingredient.getQuantity(),ingredient.getUnitsAndStyle());
		} catch (Exception ex) { ex.printStackTrace(); }
	}

	public void updateIngredient(int ingredientID, String name, double quantity, String unitsAndStyle) {
		try {
			// pre-condition:
			Ingredient tmp = null;
			tmp = findIngredient(ingredientID);
			if (tmp == null) {
				String msg = "Ingredient doesn't exist";
				throw new Exception(msg);
				// note: throwing exception terminates this method here, returning to the calling method.
			}
			connect();

			String insertString =  "UPDATE recipeingredients set name = ?, quantity = ?, unitsAndStyle = ? WHERE ID = ?";
			preparedStatement = connection.prepareStatement(insertString);
			preparedStatement.setString(1, name);
			preparedStatement.setDouble(2, quantity);
			preparedStatement.setString(3, unitsAndStyle);
			preparedStatement.setInt(4, ingredientID);
			preparedStatement.executeUpdate();
			System.out.println("Updated ingredient " + name);

			disconnect();
		} catch (Exception ex) { ex.printStackTrace(); }
	}
	public void updateIngredient(Ingredient ingredient) {
		try {
			updateIngredient(ingredient.getID(),ingredient.getName(),ingredient.getQuantity(),ingredient.getUnitsAndStyle());
		} catch (Exception ex) { ex.printStackTrace(); }
	}

	public Ingredient deleteIngredient(int ingredientID)  {
		Ingredient existing = null;
		try {
			// pre-condition:
			existing = findIngredient(ingredientID);
			// id should EXIST in database in order to delete musicAlbum from database
			boolean pre = (existing != null);
			// if musicAlbum DOES NOT EXIST in database, throw exception
			if (!pre) {
				String msg = "Ingredient id " + ingredientID + " does not exist!";
				System.out.println("\nERROR: " + msg);
				throw new Exception(msg);
				// note: throwing exception terminates this method here, returning to the calling method.
			}

			// post-condition; given all pre-conditions are satisfied
			connect();

			String deleteString = "delete from recipeingredients where id = ? ";

			preparedStatement = connection.prepareStatement(deleteString);
			preparedStatement.setInt(1, ingredientID);
			preparedStatement.executeUpdate();
			System.out.println("Ingredient " + ingredientID + " removed.");

			disconnect();
		} catch (Exception ex) { ex.printStackTrace(); }
		return existing;
	}
	public void deleteIngredient(Ingredient ingredient)  {
		try {
			deleteIngredient(ingredient.getID());
		} catch (Exception ex) { ex.printStackTrace(); }
	}

	public int addRecipe(String name, int serves, String steps, String remarks)
	{
		int result = -1;
		try {
			connect();
			String insertString = "Insert into recipes (name, serves, steps, remarks) values(?,?,?,?)";
			preparedStatement = connection.prepareStatement(insertString,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, serves);
			preparedStatement.setString(3, steps);
			preparedStatement.setString(4, remarks);
			preparedStatement.executeUpdate();

			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()){
				result = rs.getInt(1);
			}

			System.out.println("Added new recipe " + name);

			disconnect();
		} catch (Exception ex) { ex.printStackTrace(); }
		return result;
	}

	public void addRecipe(Recipe recipe)
	{
		try {
			addRecipe(recipe.getName(),recipe.getServes(),recipe.getSteps(),recipe.getRemarks());
		} catch (Exception ex) { ex.printStackTrace(); }
	}

	public int updateRecipe(int id,String name, int serves, String steps, String remarks)
	{
		int result = -1;
		try {
			// pre-condition:
			Recipe tmp = null;
			tmp = findRecipe(id);
			if (tmp == null) {
				String msg = "Recipe doesn't exist";
				throw new Exception(msg);
				// note: throwing exception terminates this method here, returning to the calling method.
			}
			connect();
			//update users set num_points = ? where first_name = ?
			String insertString = "UPDATE recipes set name = ?, serves = ?, steps = ?, remarks = ? WHERE ID = ?";
			preparedStatement = connection.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, serves);
			preparedStatement.setString(3, steps);
			preparedStatement.setString(4, remarks);
			preparedStatement.setInt(5,id);
			preparedStatement.executeUpdate();
			result = id;

			disconnect();
		} catch (Exception ex) { ex.printStackTrace(); }
		return result;
	}

	public Recipe deleteRecipe(int id)
	{
		Recipe existing = null;
		try {
			// pre-condition:
			existing = findRecipe(id);
			// id should EXIST in database in order to delete musicAlbum from database
			boolean pre = (existing != null);
			// if musicAlbum DOES NOT EXIST in database, throw exception
			if (!pre) {
				String msg = "Recipe id " + id + " does not exist!";
				System.out.println("\nERROR: " + msg);
				throw new Exception(msg);
				// note: throwing exception terminates this method here, returning to the calling method.
			}

			// post-condition; given all pre-conditions are satisfied
			connect();

			String deleteString = "delete from recipes where id = ? ";

			preparedStatement = connection.prepareStatement(deleteString);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			System.out.println("Recipe " + id + " removed.");

			disconnect();
		} catch (Exception ex) { ex.printStackTrace(); }
		return existing;
	}

	public void deleteRecipe(Recipe recipe)
	{
		try {
			deleteRecipe(recipe.getID());
		} catch (Exception ex) { ex.printStackTrace(); }
	}

	// This method provide a few basic tests of the  DSC class
	//

	public void setUseAlertForInput(boolean value)
	{
		useAlertForInput = value;
	}

	public static void main(String [] args) throws Exception
	{
		RecipeDSC dsc = new RecipeDSC();
		String url = getInfoFromUser("Enter your URL or leave blank for default.");
		String username = getInfoFromUser("Enter your username or leave blank for default.");
		String password = getInfoFromUser("Enter your password or leave blank for default.");
		dsc.connect();
		dsc.setUseAlertForInput(false); //this changes the program to use command prompt instead

		System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_ Test 1 _-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");

		List<Recipe> list = dsc.findAll();
		for(Recipe r : list) {
			System.out.println(r + "\n");
		}
		System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_ Test 2 _-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");

		Recipe recipe = dsc.findRecipe(4);
		System.out.println(recipe);
		System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_ Test 3 _-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");

		recipe = dsc.findRecipe(100);
		System.out.println(recipe);
		System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_ Test 4 _-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");

		int ID = dsc.addRecipe("name 200", 100, "step 1 , 2, 3, 4", "easy");
		System.out.println("ID: " + ID);
		System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_- Test 5 _-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");

		int ingredientID = dsc.addIngredient(ID, "ingredient", 20, "grams");
		System.out.println("ingredientID: " + ingredientID);
		System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_ Test 6 _-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");

		recipe = dsc.findRecipe(3);
		System.out.println(recipe);
		recipe.setName("Drunken chicken zoo");
		recipe.setServes(100);
		Ingredient i = new Ingredient();
		i.setName("Drunken chicken");
		i.setQuantity(10);
		i.setUnitsAndStyle("");
		recipe.addIngredient(i);
		i = new Ingredient();
		i.setName("RICE");
		i.setQuantity(100);
		i.setUnitsAndStyle("kg");
		recipe.addIngredient(i);

		recipe.setSteps("\n1. Cook chicken\n2.Cook rice");
		recipe.setRemarks("Enjoy the festival!");

		System.out.println(">>> updated recipe: " + recipe);
		System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_ Test 7 _-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");


		dsc.updateRecipe(recipe.getID(),"Update test",2,"JUSTDOIT","FUKYEAHBUD");
		recipe = dsc.findRecipe(12);
		System.out.println(">>> updated recipe from database: " + recipe);
		System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
		//
	}
}
