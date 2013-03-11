package com.cmput301w13t09.cmput301project.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.cmput301w13t09.cmput301project.IngredientController;
import com.cmput301w13t09.cmput301project.IngredientModel;
import com.cmput301w13t09.cmput301project.R;

/**
 * @author Kyle, Marcus, and Landre
 * Class: MyPantryView
 * MyPantryView is class that extends an Activity. This class shows all the ingredients stored in the
 * Pantry.data file and loads this with the IngredientController and displays it in a ListView. Also, My
 * PantryView provides a button of getting into AddNewIngredientView where you can add ingredients to MyPantry
 * 
 */
public class MyPantryView extends Activity {

	private ListAdapter ingredientListAdapter;
	private ListView ingredientListView;
	private int dialogNumber;
	private IngredientController ingredientController;
	private Button addIngredientButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_pantry_view);

		ingredientController = new IngredientController(this);
		addIngredientButton = (Button) findViewById(R.id.myPantryAddIngredientButton);
		ingredientListView = (ListView) findViewById(R.id.myPantryIngredientList);
		ingredientListAdapter = new ArrayAdapter<IngredientModel>(this,
				android.R.layout.simple_list_item_1,
				ingredientController.getIngredientList());
		ingredientListView.setAdapter(ingredientListAdapter);

		ingredientListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				dialogNumber = position;
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MyPantryView.this);
				String title = ingredientController
						.getIngredientListName(position);
				String message = ingredientController.getIngredient(position)
						.toDialogString();
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
				builder.setNeutralButton("Edit",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();

							}
						});
				builder.setPositiveButton("Delete",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								ingredientController.remove(dialogNumber);
								ingredientController.saveToFile();
								dialog.dismiss();
								updateList();

							}
						});

				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});

		addIngredientButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent addIngredient = new Intent(
							"activities.AddIngredient");
					startActivity(addIngredient);					
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});

	}
	protected void onPause(){
		super.onPause();
		ingredientController.saveToFile();
	}
	protected void onResume(){
		super.onResume();
		ingredientController.loadFromFile();
		updateList();
	}
	protected void updateList() {
		ingredientController.loadFromFile();
		ingredientListAdapter = new ArrayAdapter<IngredientModel>(this,
				android.R.layout.simple_list_item_1,
				ingredientController.getIngredientList());
		ingredientListView.setAdapter(ingredientListAdapter);
	}
}
