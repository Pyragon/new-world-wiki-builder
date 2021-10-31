package com.cryo.entities;

import lombok.Data;

@Data
public class AssetFolder extends MySQLDao {

	@MySQLDefault
	private final int id;
	private final String name;
	private final String slug;
	@MySQLRead("parentId")
	private final Integer parentId; //useless, nested folders don't seem to work for assets

}
