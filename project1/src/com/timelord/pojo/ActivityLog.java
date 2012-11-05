package com.timelord.pojo;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DatabaseFieldForeign;
import com.j256.ormlite.field.DatabaseFieldSimple;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "activityLogs")
public class ActivityLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseFieldSimple(canBeNull = false)
	@DatabaseFieldForeign(foreign = true)
	private Activity activity;

	@DatabaseField(dataType = DataType.DATE, canBeNull = false)
	private Date start;

	@DatabaseField(dataType = DataType.DATE, canBeNull = true)
	private Date end;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
}
