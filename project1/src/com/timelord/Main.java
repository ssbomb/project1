package com.timelord;

import com.timelord.ListActivity;
import com.timelord.ListCategory;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class Main extends TabActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, ListActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("activities")
				.setIndicator("Activities",
						res.getDrawable(R.drawable.ic_tab_activities))
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, ListCategory.class);
		spec = tabHost
				.newTabSpec("categories")
				.setIndicator("Categories",
						res.getDrawable(R.drawable.ic_tab_categories))
				.setContent(intent);
		tabHost.addTab(spec);

	}
}
