package com.timelord.activity;

import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.timelord.dao.DatabaseHelper;

public abstract class BaseEntry extends OrmLiteBaseActivity<DatabaseHelper>
		implements OnClickListener {

	private Button saveButton;

	private EditText nameEdit;

	public EditText getNameEdit() {
		return nameEdit;
	}

	public void setNameEdit(EditText nameEdit) {
		this.nameEdit = nameEdit;
	}

	protected abstract int getSaveButtonId();

	protected abstract int getNameEditId();

	protected abstract int getContentView();

	protected abstract void fillData(String selectedItem);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getContentView());
		saveButton = (Button) findViewById(getSaveButtonId());
		saveButton.setOnClickListener(this);
		nameEdit = (EditText) findViewById(getNameEditId());

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		fillData(getSelectedItem());
	}

	protected String getSelectedItem() {
		return (String) getIntent().getSerializableExtra("selectedItem");
	}

	protected String getNameEntried() {
		return nameEdit.getText().toString();
	}

}