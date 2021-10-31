package com.cryo.loaders;

import com.cryo.NewWorldWikiBuilder;
import com.cryo.entities.ItemStats;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ItemStatsList {

	private static final String ITEM_STATS_PATH = "D:/workspace/github/NewWorldUnpacker/decompiled/sharedassets/springboardentitites/datatables/javelindata_itemdefinitions_weapons.json";

	private static HashMap<String, ItemStats> itemStats;

	public static void buildItemStats() {
		File file = new File(ITEM_STATS_PATH);
		itemStats = new HashMap<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			StringBuilder builder = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null) builder.append(line);

			reader.close();
			ArrayList<LinkedTreeMap<String, Object>> list = NewWorldWikiBuilder.getGson().fromJson(builder.toString(), ArrayList.class);

			for(LinkedTreeMap<String, Object> map : list) {

				ItemStats stats = new ItemStats(map);
				itemStats.put(stats.getWeaponID(), stats);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static ItemStats getStats(String weaponId) {
		return itemStats.get(weaponId);
	}
}
