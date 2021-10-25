package com.cryo.entities;

import lombok.Data;

@Data
public class AssetData extends MySQLDao {

	private final int id;
	private final byte[] data;

}
