package com.timelord.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.SimpleAdapter;

import com.timelord.pojo.BaseEntity;

public class EntityAdapter {
	public EntityAdapter() {
	}

	public SimpleAdapter getEntityAdapter(Context context, List<?> list,
			int resource, int[] to) {
		String[] titles = new String[] { "name", "id" };
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		for (Iterator<?> it = list.iterator(); it.hasNext();) {
			HashMap<String, String> map = new HashMap<String, String>();
			BaseEntity baseEntity = (BaseEntity) it.next();
			map.put("name", baseEntity.getName());
			map.put("id", baseEntity.getId().toString());
			Log.d(EntityAdapter.class.getName(), baseEntity.getName() + " - "
					+ baseEntity.getId());
			data.add(map);
		}

		return new SimpleAdapter(context, data, resource, titles, to);
	}
}