package com.cryo.entities;

import lombok.Data;

@Data
public class RecipeItem {

	private final ItemDefinitions defs;
	private final String type;
	private final int quantity;
}
