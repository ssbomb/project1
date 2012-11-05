package com.timelord;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.timelord.activity.BaseEntry;
import com.timelord.pojo.Activity;
import com.timelord.pojo.Category;

public class NewActivity extends BaseEntry {
	private Spinner categorySpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		categorySpinner = (Spinner) findViewById(R.id.categorySelect);
		List<CharSequence> categoryNames = new ArrayList<CharSequence>();
		List<?> categories = getHelper().getAllObjects(Category.class);
		for (Object category : categories) {
			categoryNames.add(((Category) category).getName());
		}
		ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<CharSequence>(
				this, android.R.layout.simple_spinner_dropdown_item,
				categoryNames);
		categorySpinner.setAdapter(arrayAdapter);
	}

	public void onClick(View v) {
		Activity activity = new Activity();
		activity.setName(getNameEntried());
		String categoryName = categorySpinner.getSelectedItem().toString();
		Category category = (Category) getHelper().getObjectByName(
				categoryName, Category.class);
		activity.setCategory(category);

		getHelper().saveObject(activity, Activity.class, getSelectedItem());
		finish();
	}

	@Override
	protected int getContentView() {
		return R.layout.new_activity;
	}

	@Override
	protected int getSaveButtonId() {
		return R.id.activitySaveButton;
	}

	@Override
	protected int getNameEditId() {
		return R.id.activityNameEdit;
	}

	@Override
	protected void fillData(String selectedItem) {
		if (selectedItem == null) {
			return;
		}
		Activity activity = (Activity) getHelper().getObjectByName(
				selectedItem, Activity.class);
		getNameEdit().setText(activity.getName());
		for (int i = 0; i < categorySpinner.getChildCount(); i++) {
			if (activity.getCategory().getName()
					.equals(categorySpinner.getItemAtPosition(i))) {
				categorySpinner.setSelection(i);
				break;
			}
		}
	}
}
