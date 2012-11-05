package com.timelord.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;
import com.timelord.dao.DatabaseHelper;

public abstract class BaseList extends OrmLiteBaseListActivity<DatabaseHelper>
		implements OnClickListener {

	protected abstract int getContentView();

	protected abstract Button getButton();

	protected abstract Class<?> getEntryClass();

	protected abstract int getRowLayout();

	protected abstract int[] getRowId();

	protected abstract List<?> getRowObjects();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getContentView());
		getButton().setOnClickListener(this);
		refreshData();
	}

	protected void refreshData() {
		setListAdapter(new EntityAdapter().getEntityAdapter(this,
				getRowObjects(), getRowLayout(), getRowId()));
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		refreshData();
	}

	/**
	 * New <?> button clicked
	 */
	public void onClick(View view) {
		Intent i = new Intent(this, getEntryClass());
		startActivity(i);
	}

	public String getSelectedItem(View view) {
		RelativeLayout vwParentRow = (RelativeLayout) view.getParent();
		TextView child = (TextView) vwParentRow.getChildAt(0);
		return (String) child.getText();
	}
}