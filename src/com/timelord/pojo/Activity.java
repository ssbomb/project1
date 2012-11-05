package com.timelord.pojo;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseFieldForeign;
import com.j256.ormlite.field.DatabaseFieldSimple;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "activities")
public class Activity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DatabaseFieldSimple(canBeNull = false)
	@DatabaseFieldForeign(foreign = true)
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
