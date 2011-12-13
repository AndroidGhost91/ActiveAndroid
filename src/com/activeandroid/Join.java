package com.activeandroid;

import android.text.TextUtils;


public class Join {
	public static enum JoinType {
		LEFT, OUTER, INNER, CROSS
	}

	private From mFrom;
	private Class<? extends Model> mType;
	private String mAlias;
	private JoinType mJoinType;
	private String mOn;
	private String[] mUsing;

	public Join(From from, Class<? extends Model> type, JoinType joinType) {
		mFrom = from;
		mType = type;
		mJoinType = joinType;
	}

	public Join as(String alias) {
		mAlias = alias;
		return this;
	}

	public From on(String on) {
		mOn = on;
		return mFrom;
	}

	public From on(String on, String... args) {
		mOn = on;
		mFrom.addArguments(args);
		return mFrom;
	}

	public From using(String... columns) {
		mUsing = columns;
		return mFrom;
	}

	String toSql() {
		StringBuilder sql = new StringBuilder();

		if (mJoinType != null) {
			sql.append(mJoinType.toString() + " ");
		}

		sql.append("JOIN " + ReflectionUtils.getTableName(mType) + " ");

		if (mAlias != null) {
			sql.append("AS " + mAlias + " ");
		}

		if (mOn != null) {
			sql.append("ON " + mOn + " ");
		}
		else if (mUsing != null) {
			sql.append("USING (" + TextUtils.join(", ", mUsing) + ") ");
		}

		return sql.toString();
	}
}