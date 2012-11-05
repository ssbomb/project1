package com.timelord.dao;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.timelord.R;
import com.timelord.pojo.Activity;
import com.timelord.pojo.ActivityLog;
import com.timelord.pojo.BaseEntity;
import com.timelord.pojo.Category;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "wisely";

	private static final int DATABASE_VERSION = 1;

	private Dao<Activity, Integer> activityDao = null;

	private Dao<Category, Integer> categoryDao = null;

	private Dao<ActivityLog, Integer> activityLogDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION,
				R.raw.ormlite_config);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, ActivityLog.class);
			TableUtils.createTable(connectionSource, Activity.class);
			TableUtils.createTable(connectionSource, Category.class);

			Dao<Category, Integer> categoryDao = getCategoryDao();
			Category category = new Category();
			category.setName("entertainment");
			categoryDao.create(category);
			// here we try inserting data in the on-create as a test
			Dao<Activity, Integer> activityDao = getActivityDao();
			// create some entries in the onCreate
			Activity activity = new Activity();
			activity.setName("watch muvee");
			activity.setCategory(category);
			activityDao.create(activity);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	private Dao<Activity, Integer> getActivityDao() throws SQLException {
		if (activityDao == null) {
			activityDao = getDao(Activity.class);
		}
		return activityDao;
	}

	private Dao<ActivityLog, Integer> getActivityLogDao() throws SQLException {
		if (activityLogDao == null) {
			activityLogDao = getDao(ActivityLog.class);
		}
		return activityLogDao;
	}

	private Dao<Category, Integer> getCategoryDao() throws SQLException {
		if (categoryDao == null) {
			categoryDao = getDao(Category.class);
		}
		return categoryDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		activityDao = null;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Activity.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	public BaseEntity getObjectByName(String name, Class<?> classType) {
		try {
			if (classType == Category.class) {
				Category category = (Category) getCategoryDao().queryForEq(
						"name", name).get(0);
				return category;
			}
			if (classType == Activity.class) {
				Activity activity = (Activity) getActivityDao().queryForEq(
						"name", name).get(0);
				return activity;
			}
		} catch (Exception e) {
			Log.e(DatabaseHelper.class.getName(), e.getMessage(), e);
		}
		return null;
	}

	public List<?> getAllObjects(Class<?> classType) {
		try {
			if (classType == Category.class) {
				return getCategoryDao().queryForAll();
			}
			if (classType == Activity.class) {
				return getActivityDao().queryForAll();
			}
		} catch (Exception e) {
			Log.e(DatabaseHelper.class.getName(), e.getMessage(), e);
		}
		return null;
	}

	public boolean saveObject(BaseEntity baseEntity, Class<?> classType,
			String name) {
		try {
			if (classType == Category.class) {
				if (name == null) {
					getCategoryDao().create((Category) baseEntity);
					return true;
				} else {
					Category category = (Category) getObjectByName(name,
							Category.class);
					category.setName(baseEntity.getName());
					getCategoryDao().update(category);
					return true;
				}
			}
			if (classType == Activity.class) {
				if (name == null) {
					getActivityDao().create((Activity) baseEntity);
					return true;
				} else {
					Activity activity = (Activity) getObjectByName(name,
							Activity.class);
					activity.setName(baseEntity.getName());
					activity.setCategory(((Activity) baseEntity).getCategory());
					getActivityDao().update(activity);
					return true;
				}
			}
		} catch (Exception e) {
			Log.e(DatabaseHelper.class.getName(), e.getMessage(), e);
		}
		return false;
	}

	public boolean saveLog(ActivityLog activityLog) {
		try {
			getActivityLogDao().create(activityLog);
			return true;
		} catch (Exception e) {
			Log.e(DatabaseHelper.class.getName(), e.getMessage(), e);
		}
		return false;
	}

	public boolean updateLog(ActivityLog activityLog) {
		try {
			PreparedQuery<ActivityLog> preparedQuery = getActivityLogDao()
					.queryBuilder().orderBy("start", false).where()
					.isNull("end").prepare();
			ActivityLog previousLog = getActivityLogDao().queryForFirst(
					preparedQuery);
			previousLog.setEnd(activityLog.getEnd());
			getActivityLogDao().update(previousLog);
			return true;
		} catch (Exception e) {
			Log.e(DatabaseHelper.class.getName(), e.getMessage(), e);
		}
		return false;
	}

	public boolean deleteObject(BaseEntity baseEntity, Class<?> classType) {
		try {
			if (classType == Category.class) {
				Category category = (Category) getObjectByName(
						baseEntity.getName(), Category.class);
				getCategoryDao().delete(category);
				return true;
			}
			if (classType == Activity.class) {
				Activity activity = (Activity) getObjectByName(
						baseEntity.getName(), Activity.class);
				getActivityDao().delete(activity);
				return true;
			}
		} catch (Exception e) {
			Log.e(DatabaseHelper.class.getName(), e.getMessage(), e);
		}
		return false;
	}
}
