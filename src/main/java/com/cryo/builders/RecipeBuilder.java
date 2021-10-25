package com.cryo.builders;

import com.cryo.NewWorldWikiBuilder;
import com.cryo.entities.Recipe;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeBuilder extends Builder {

	private static final String RECIPE_FILE = NewWorldWikiBuilder.DECOMPILED_BASE_PATH+"sharedassets/springboardentitites/datatables/javelindata_crafting.json"; //more than just the one?

	protected static HashMap<String, Recipe> recipes;

	@Override
	public void build() {

	}

	public static List<Recipe> getUsedIn(String itemId) {
		List<Recipe> list = new ArrayList<>();
		for(Recipe recipe : recipes.values()) {
			for(int i = 0; i < recipe.getIngredients().length; i++)
				if(recipe.getIngredients()[i].equals(itemId))
					list.add(recipe);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static void loadRecipes() {

		recipes = new HashMap<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(RECIPE_FILE));
			String line;
			StringBuilder builder = new StringBuilder();
			while((line = reader.readLine()) != null) builder.append(line);

			ArrayList<LinkedTreeMap<String, Object>> list = NewWorldWikiBuilder.getGson().fromJson(builder.toString(), ArrayList.class);
			for(LinkedTreeMap<String, Object> map : list) {

				String recipeId = (String) map.get("RecipeID");
				Recipe recipe = new Recipe(map);
				recipes.put(recipeId, recipe);

			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
