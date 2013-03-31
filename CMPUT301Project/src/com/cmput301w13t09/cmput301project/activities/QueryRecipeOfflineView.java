package com.cmput301w13t09.cmput301project.activities;

import com.cmput301w13t09.cmput301project.IngredientController;
import com.cmput301w13t09.cmput301project.R;
import com.cmput301w13t09.cmput301project.RecipeController;
import com.cmput301w13t09.cmput301project.Models.RecipeListModel;
import com.cmput301w13t09.cmput301project.Models.RecipeModel;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class QueryRecipeOfflineView extends Activity {
	private ListAdapter recipeListAdapter;
	private ListView recipeListView;
	private IngredientController ingredController;
	private int dialogNumber;
	private RecipeListModel queryrecipelist;
	private RecipeController recipeController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_recipe_offline_view);
		recipeController = new RecipeController(this);
		ingredController = new IngredientController(this);
		queryrecipelist = recipeController.getQueryRecipeList(ingredController);
		recipeListView = (ListView) findViewById(R.id.queryRecipeOfflinelistView);
		recipeListAdapter = new ArrayAdapter<RecipeModel>(this,
				android.R.layout.simple_list_item_1, queryrecipelist);

		recipeListView.setAdapter(recipeListAdapter);
		recipeListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				dialogNumber = position;
				AlertDialog.Builder builder = new AlertDialog.Builder(
						QueryRecipeOfflineView.this);
				String title = queryrecipelist.get(position).getRecipeName();
				String message = queryrecipelist.get(position).getRecipeDesc();
				builder.setMessage(message);
				builder.setTitle(title);

				builder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();

							}
						});
				builder.setNeutralButton("View",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								try {
									Intent viewRecipe = new Intent(
											"activities.RecipeOnlineView");
									viewRecipe.putExtra("Recipe",
											queryrecipelist.get(dialogNumber));
									startActivity(viewRecipe);
								} catch (Throwable throwable) {
									throwable.printStackTrace();
								}
								dialog.dismiss();

							}
						});
				builder.setPositiveButton("Delete",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								recipeController.remove(recipeController
										.findRecipe(queryrecipelist.get(
												dialogNumber).getRecipeName()));
								recipeController.saveToFile();
								dialog.dismiss();
								updateList();

							}
						});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
	}

	protected void updateList() {
		recipeController.loadFromFile();
		queryrecipelist = recipeController.getQueryRecipeList(ingredController);
		recipeListAdapter = new ArrayAdapter<RecipeModel>(this,
				android.R.layout.simple_list_item_1, queryrecipelist);
		recipeListView.setAdapter(recipeListAdapter);
	}

}
