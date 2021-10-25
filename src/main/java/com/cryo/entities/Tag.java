package com.cryo.entities;

import lombok.Data;

@Data
public class Tag extends MySQLDao {

	@MySQLDefault
	private final int id;
	private final String tag;
	private final String title;
	@MySQLRead("createdAt")
	private final String createdAt;
	@MySQLRead("updatedAt")
	private final String updatedAt;
}
