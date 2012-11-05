package com.timelord;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.timelord.activity.BaseEntry;
import com.timelord.pojo.Category;

public class NewCategory extends BaseEntry {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onClick(View v) {
		Category category = new Category();
		category.setName(getNameEntried());
		Log.i(NewCategory.class.getName(), getNameEntried());
		getHelper().saveObject(category, Category.class, getSelectedItem());
		finish();
	}

	@Override
	protected int getContentView() {
		return R.layout.new_category;
	}

	@Override
	protected int getSaveButtonId() {
		return R.id.categorySaveButton;
	}

	@Override
	protected int getNameEditId() {
		return R.id.categoryNameEdit;
	}

	@Override
	protected void fillData(String selectedItem) {
		if (selectedItem == null) {
			return;
		}
		Category category = (Category) getHelper().getObjectByName(
				selectedItem, Category.class);
		getNameEdit().setText(category.getName());
	}

}
