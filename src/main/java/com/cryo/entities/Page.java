package com.cryo.entities;

import lombok.Data;
import org.postgresql.util.PGobject;

@Data
public class Page extends MySQLDao {

	@MySQLDefault
	private final int id;
	private final String path;
	private final String hash;
	private final String title;
	private final String description;
	private final boolean isPrivate;
	private final boolean isPublished;
	private final String privateNS;
	private final String publishStartDate;
	private final String publishEndDate;
	private final String content;
	private final String render;
	private final PGobject toc;
	private final String contentType;
	private final String createdAt;
	private final String updatedAt;
	private final String editorKey;
	private final String localeCode;
	private final Integer authorId;
	private final int creatorId;
	private final PGobject json;

}
