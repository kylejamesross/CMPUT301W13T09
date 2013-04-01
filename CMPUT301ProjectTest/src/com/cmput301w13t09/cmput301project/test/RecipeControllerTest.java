package com.cmput301w13t09.cmput301project.test;

import org.junit.Before;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.cmput301w13t09.cmput301project.activities.CreateNewRecipeView;
import com.cmput301w13t09.cmput301project.controllers.RecipeController;
import com.cmput301w13t09.cmput301project.models.IngredientListModel;
import com.cmput301w13t09.cmput301project.models.InstructionListModel;
import com.cmput301w13t09.cmput301project.models.RecipeModel;

public class RecipeControllerTest extends
		ActivityInstrumentationTestCase2 {
	RecipeController rContr;
	private TextView result;

	public RecipeControllerTest() {
		super("com.cmput301w13t09.cmput301project.activities", CreateNewRecipeView.class);

	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		//CreateNewRecipeView createNewRV = getActivity();
		rContr = new RecipeController(getActivity());
	}

	@Test
	public void testRecipeController() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddRecipe() {

		// no photos or ingredients
		RecipeModel recipe1 = new RecipeModel("Italian Food",
				"Use fancy sounding ingredients", null, null);
		// no recipe name
		RecipeModel recipe2 = new RecipeModel(null, "Stir for 15 minutes",
				new IngredientListModel(), new InstructionListModel());
		// Same recipe name as 1
		RecipeModel recipe1b = new RecipeModel("Italian Food",
				"Use fancy sounding ingredients", null, null);

		// cannot add a recipe without recipe name
		int oldLength = rContr.getLength();
		rContr.addRecipe(recipe2);
		int newLength = rContr.getLength();
		assertTrue(newLength == oldLength);

		// RecipeList should contain the recipe
		oldLength = rContr.getLength();
		rContr.addRecipe(recipe1);
		newLength = rContr.getLength();
		assertTrue(newLength == oldLength + 1);

		// Two recipes cannot have the same name
		int l = rContr.getLength();
		rContr.addRecipe(recipe1b);
		assertTrue(rContr.getLength() == l);

	}

	@Test
	public void testGetRecipeListName() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetRecipeListDesc() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetRecipeingredientList() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetRecipePhotoList() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetRecipeList() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testRemove() {
		RecipeModel a = new RecipeModel("Italian Food",
				"Use fancy sounding ingredients", null, null);
		RecipeModel b = new RecipeModel("Bacon Cake", "Its canadian eh", null,
				null);
		rContr.addRecipe(a);
		rContr.addRecipe(b);
		int oldLength = rContr.getLength();
		rContr.remove(rContr.getLength() - 1);
		int newLength = rContr.getLength();
		assertTrue(newLength == oldLength - 1);

	}

	@Test
	public void testLoadFromFile() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSaveToFile() {
		fail("Not yet implemented"); // TODO
	}

}
