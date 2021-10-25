package com.cryo.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageTag extends MySQLDao {

	@MySQLDefault
	private final int id;
	private final int pageId;
	private final int tagId;
}
