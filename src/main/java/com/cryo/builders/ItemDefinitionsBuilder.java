package com.cryo.builders;

import com.cryo.NewWorldWikiBuilder;
import com.cryo.WikiBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ItemDefinitionsBuilder extends Builder {

	private static String ITEM_DEFINITIONS_PATH = "D:/workspace/github/NewWorldUnpacker/decompiled/sharedassets/springboardentitites/datatables/";

	private static String TEMPLATE_FILE;

	@Override
	@SuppressWarnings("unchecked")
	public void build() {

		File[] files = new File(ITEM_DEFINITIONS_PATH).listFiles();
		if(files == null) return;

		for(File file : files) {

			try {
				if (!file.getName().contains("itemdefinitions_master")) continue;

				BufferedReader reader = new BufferedReader(new FileReader(file));
				StringBuilder builder = new StringBuilder();
				String line;
				while((line = reader.readLine()) != null)
					builder.append(line);
				reader.close();

				List<LinkedTreeMap<String, Object>> list = NewWorldWikiBuilder.getGson().fromJson(builder.toString(), ArrayList.class);

				for(LinkedTreeMap<String, Object> map : list) {

					String displayName = (String) map.get("ItemTypeDisplayName");
					if(displayName.equals("")) continue;
					if(!WikiBuilder.localizationStrings.containsKey(displayName)) continue;
					displayName = WikiBuilder.localizationStrings.get(displayName);

					String description = (String) map.get("Description");
					if(description.equals("")) continue;
					if(WikiBuilder.localizationStrings.containsKey(displayName))
						description = WikiBuilder.localizationStrings.get(description);

					Double weight = (Double) map.get("Weight");
					Double minGS = (Double) map.get("MinGearScore");
					Double maxGS = (Double) map.get("MaxGearScore");
					Double durability = (Double) map.get("Durability");
					Boolean isRepairable = (Boolean) map.get("IsRepairable");
					Boolean isSalvageable = (Boolean) map.get("IsSalvageable");
					Double teir = (Double) map.get("Teir");
					String itemType = (String) map.get("ItemType");
					Boolean bindOnEquip = (Boolean) map.get("BindOnEquip");
					Boolean bindOnPickup = (Boolean) map.get("BindOnPickup");

					String[] itemClasses = ((String) map.get("ItemClass")).split("\\+");

					//Get audio files

					String page = TEMPLATE_FILE
							.replace("{name}", displayName)
							.replace("{description}", description)
							.replace("{weight}", Double.toString(weight))
							.replace("{minGS}", Double.toString(minGS))
							.replace("{maxGS}", Double.toString(maxGS))
							.replace("{durability}", Double.toString(durability))
							.replace("{teir}", Double.toString(teir))
							.replace("{itemType}", itemType)
							.replace("{repairable}", isRepairable ? "Yes" : "No")
							.replace("{salvageable}", isSalvageable ? "Yes" : "No")
							.replace("{itemClasses}", String.join(", ", itemClasses))
							.replace("{bindOnEquip}", bindOnEquip ? "Yes" : "No")
							.replace("{bindOnPickup}", bindOnPickup ? "Yes" : "No");

				}

			} catch(Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void loadTemplateFile() {

		File templateFile = new File("data/item_defs_template.txt");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(templateFile));
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) builder.append(line);
			reader.close();

			TEMPLATE_FILE = builder.toString();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
