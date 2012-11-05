package com.timelord;

import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.timelord.activity.BaseList;
import com.timelord.pojo.Activity;
import com.timelord.pojo.ActivityLog;

public class ListActivity extends BaseList {

	private Activity ongoingActivity;

	private Button startButton;

	@Override
	protected int getContentView() {
		return R.layout.activities;
	}

	@Override
	protected List<?> getRowObjects() {
		return getHelper().getAllObjects(Activity.class);
	}

	@Override
	protected Button getButton() {
		return (Button) findViewById(R.id.newActivityButton);
	}

	public void startActivityHandler(View view) {
		if (startButton == null) {
			startButton = (Button) findViewById(R.id.startActivityBtn);
		}
		String activityName = getSelectedItem(view);
		if (getOngoingActivity() == null
				|| !activityName.equals(getOngoingActivity().getName())) {
			setOngoingActivity((Activity) getHelper().getObjectByName(
					activityName, Activity.class));
			ActivityLog activityLog = new ActivityLog();
			activityLog.setActivity(getOngoingActivity());
			activityLog.setStart(new Date(System.currentTimeMillis()));
			getHelper().saveLog(activityLog);
			startButton.setText("Stop");
		} else {
			ActivityLog activityLog = new ActivityLog();
			activityLog.setEnd(new Date(System.currentTimeMillis()));
			getHelper().updateLog(activityLog);
			setOngoingActivity(null);
			startButton.setText("Start");
		}
	}

	public Activity getOngoingActivity() {
		return ongoingActivity;
	}

	public void setOngoingActivity(Activity ongoingActivity) {
		this.ongoingActivity = ongoingActivity;
	}

	public void editActivityHandler(View view) {
		Intent i = new Intent(this, getEntryClass());
		i.putExtra("selectedItem", getSelectedItem(view));
		startActivity(i);
	}

	public void deleteActivityHandler(View view) {
		String name = getSelectedItem(view);
		Activity activity = (Activity) getHelper().getObjectByName(name,
				Activity.class);
		getHelper().deleteObject(activity, Activity.class);
		refreshData();
	}

	@Override
	protected Class<?> getEntryClass() {
		return NewActivity.class;
	}

	@Override
	protected int getRowLayout() {
		return R.layout.activity_row;
	}

	@Override
	protected int[] getRowId() {
		return new int[] { R.id.activityRow };
	}
}
