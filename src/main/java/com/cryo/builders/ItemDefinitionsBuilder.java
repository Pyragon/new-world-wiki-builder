package com.cryo.builders;

import com.cryo.DBConnection;
import com.cryo.NewWorldWikiBuilder;
import com.cryo.WikiBuilder;
import com.cryo.entities.*;
import com.cryo.utils.Utils;
import com.google.gson.internal.LinkedTreeMap;
import de.neuland.pug4j.Pug4J;
import org.apache.commons.codec.digest.DigestUtils;
import org.postgresql.util.PGobject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.*;

public class ItemDefinitionsBuilder extends Builder {

	private static final String ITEM_DEFINITIONS_PATH = "D:/workspace/github/NewWorldUnpacker/decompiled/sharedassets/springboardentitites/datatables/";
	private static final String ITEM_ICONS_PATH = "D:/workspace/github/NewWorldUnpacker/decompiled/lyshineui/images/icons/items";

	private static HashMap<String, ItemDefinitions> itemDefinitions;

	private static String CSS;

	@Override
	public void build() {

		DBConnection connection = NewWorldWikiBuilder.getConnection();

		connection.delete("pages", "id != ? AND id != ?", 1, 13207);
		connection.delete("assets", "id != ? AND id != ?", 1, 2);
		connection.delete("assetData", "id != ? AND id != ?", 1, 2);

		try {
			for (ItemDefinitions defs : itemDefinitions.values()) {

				String name = defs.getName().replace("@", "");
				if (name.equals("")) continue;
				if (!WikiBuilder.localizationStrings.containsKey(name)) continue;
				name = WikiBuilder.localizationStrings.get(name);
				final String displayName = name;

				if(!displayName.equalsIgnoreCase("Runic Thread")) continue;

				String description = defs.getDescription().replace("@", "");
				description = WikiBuilder.localizationStrings.getOrDefault(description, "");

				String itemTypeName = defs.getItemTypeDisplayName().replace("@", "");
				itemTypeName = WikiBuilder.localizationStrings.getOrDefault(itemTypeName, defs.getItemType());

				HashMap<String, Object> model = new HashMap<>();

				model.put("item", itemDefinitions);

				String path = "items/" + itemTypeName.toLowerCase().replace(" ", "-") + "/" + displayName.replace(" ", "_").replace(":", "");

				String recipeId = defs.getCraftingRecipe();
				if (!recipeId.equals("")) {
					if (RecipeBuilder.recipes.containsKey(recipeId)) {
						Recipe recipe = RecipeBuilder.recipes.get(recipeId);
						if(recipe.getRecipeNameOverride().equals("")) continue;
						model.put("recipe", recipe);
						System.out.println("Put recipe in: " + displayName + ". Link: http://new-world.wiki/en/" + path);
					} else {
						System.err.println("Missing recipe: " + recipeId + " for Item: " + displayName);
						continue;
					}
				}

				File imageFile = new File(ITEM_ICONS_PATH + "/" + defs.getItemType().toLowerCase() + "/" + defs.getIconPath().toLowerCase() + ".png");
				if (!imageFile.exists()) {
					System.err.println("Cannot find icon for " + displayName + ". IconPath: " + defs.getIconPath() + " Path: " + imageFile.getPath());
					continue;
				}

				model.put("imgSrc", "http://new-world.wiki/" + imageFile.getName());

				String html;
				try {
					html = Pug4J.render("data/item_defs_template.pug", model);
				} catch(Exception e) {
					e.printStackTrace();
					continue;
				}

				FileInputStream in = new FileInputStream(imageFile);
				byte[] data = in.readAllBytes();

				in.close();

				Asset asset = new Asset(-1, imageFile.getName(), DigestUtils.sha1Hex(imageFile.getName()), ".png", "image", "image/png", data.length, null, "", "", null, 5);
				int id = connection.insert("assets", asset);

				AssetData assetData = new AssetData(id, data);
				connection.insert("assetData", assetData);

				ArrayList<Properties> props = new ArrayList<>();

				Properties nameProp = new Properties();
				nameProp.put("title", displayName);
				nameProp.put("anchor", "#" + displayName.toLowerCase().replace(" ", "-"));
				nameProp.put("children", new ArrayList<Properties>());

				Properties dropSources = new Properties();
				dropSources.put("title", "Sources");
				dropSources.put("anchor", "#sources");
				dropSources.put("children", new ArrayList<Properties>());

				Properties craftingRecipes = new Properties();
				craftingRecipes.put("title", "Crafting Recipes");
				craftingRecipes.put("anchor", "#crafting-recipes");
				craftingRecipes.put("children", new ArrayList<Properties>());

				props.add(nameProp);
				props.add(dropSources);
				props.add(craftingRecipes);

				String entriesJSON = NewWorldWikiBuilder.getGson().toJson(props);

				PGobject toc = new PGobject();
				toc.setType("json");
				toc.setValue(entriesJSON);

				PGobject json = new PGobject();
				json.setType("json");
				json.setValue("{\"js\":\"\",\"css\":\""+CSS+"\"}");

				connection.delete("pages", "path=?", path);

				Page pageDAO = new Page(-1, path, "hash", displayName, description, false, true, null, "", "", html, html, toc, "html", "", "", "code", "en", 5, 5, json);

				int pageId = connection.insert("pages", pageDAO);

				if (pageId == -1) return;
				PageTag itemTag = new PageTag(-1, pageId, 3);
				PageTag autoGeneratedTag = new PageTag(-1, pageId, 4);

				Tag tag = connection.selectClass("tags", "tag=?", Tag.class, itemTypeName.toLowerCase().replace(" ", "-"));
				int tagId;
				if (tag == null) {
					tag = new Tag(-1, itemTypeName.toLowerCase().replace(" ", "-"), itemTypeName.toLowerCase().replace(" ", "-"), Utils.getTimestamp(), Utils.getTimestamp());
					tagId = connection.insert("tags", tag);
				} else tagId = tag.getId();

				PageTag itemTypeTag = new PageTag(-1, pageId, tagId);

				connection.insert("pageTags", itemTag);
				connection.insert("pageTags", autoGeneratedTag);
				connection.insert("pageTags", itemTypeTag);

				System.out.println("Built: "+displayName+" Link: http://new-world.wiki/en/"+path);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public static HashMap<String, ItemDefinitions> getItemDefinitions() {
		return itemDefinitions;
	}

	public static void loadCSSTemplate() {
		File file = new File("data/style.css");

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			StringBuilder builder = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null) builder.append(line);
			reader.close();

			CSS = builder.toString().replace("}\s{", "}{");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadItemDefinitions() {

		File[] files = new File(ITEM_DEFINITIONS_PATH).listFiles();
		if (files == null) return;

		itemDefinitions = new HashMap<>();
		for (File file : files) {
			try {
				if (!file.getName().contains("itemdefinitions_master")) continue;

				BufferedReader reader = new BufferedReader(new FileReader(file));
				StringBuilder builder = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) builder.append(line);
				reader.close();

				List<LinkedTreeMap<String, Object>> list = NewWorldWikiBuilder.getGson().fromJson(builder.toString(), ArrayList.class);

				for (LinkedTreeMap<String, Object> map : list) {

					ItemDefinitions defs = new ItemDefinitions(map);
					itemDefinitions.put(defs.getName(), defs);
//					System.out.println(defs.getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
