package com.timelord;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.timelord.R;
import com.timelord.activity.BaseList;
import com.timelord.pojo.Category;

public class ListCategory extends BaseList {

	@Override
	protected int getContentView() {
		return R.layout.categories;
	}

	@Override
	protected Button getButton() {
		return (Button) findViewById(R.id.newCategoryButton);
	}

	public void editCategoryHandler(View view) {
		Intent i = new Intent(this, getEntryClass());
		i.putExtra("selectedItem", getSelectedItem(view));
		startActivity(i);
	}

	public void deleteCategoryHandler(View view) {
		String name = getSelectedItem(view);
		Category category = (Category) getHelper().getObjectByName(name,
				Category.class);
		getHelper().deleteObject(category, Category.class);
		refreshData();
	}

	@Override
	protected Class<?> getEntryClass() {
		return NewCategory.class;
	}

	@Override
	protected int getRowLayout() {
		return R.layout.category_row;
	}

	@Override
	protected List<?> getRowObjects() {
		return getHelper().getAllObjects(Category.class);
	}

	@Override
	protected int[] getRowId() {
		return new int[] { R.id.categoryRow };
	}
}
