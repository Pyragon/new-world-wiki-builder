package com.cryo.entities;

import lombok.Data;

import java.util.List;

@Data
public class TOCEntry {

	private final String title;
	private final String anchor;
	private final List<Object> children;
}
