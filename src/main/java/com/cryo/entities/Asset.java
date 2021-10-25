package com.cryo.entities;

import lombok.Data;

@Data
public class Asset extends MySQLDao {

	@MySQLDefault
	private final int id;
	private final String fileName;
	private final String hash;
	private final String ext;
	private final String kind;
	private final String mime;
	private final int fileSize;
	private final String metadata;
	private final String createdAt;
	private final String updatedAt;
	private final Integer folderId; //might need to change to non-primitive
	private final int authorId;
}
