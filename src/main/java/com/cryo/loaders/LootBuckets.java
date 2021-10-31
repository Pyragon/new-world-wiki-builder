package com.cryo.loaders;

import com.cryo.NewWorldWikiBuilder;
import com.cryo.entities.LootBucket;
import com.cryo.entities.Tooltip;
import com.google.gson.internal.LinkedTreeMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class LootBuckets {

	private static final String LOOT_BUCKET_PATH = "D:/workspace/github/NewWorldUnpacker/decompiled/sharedassets/springboardentitites/datatables/javelindata_itemdefinitions_tooltiplayout.json";

	private static HashMap<String, LootBucket> lootBuckets;

	public static void loadTooltips() {
//		File file = new File(LOOT_BUCKET_PATH);
//		lootBuckets = new HashMap<>();
//		try {
//			BufferedReader reader = new BufferedReader(new FileReader(file));
//			String line;
//			StringBuilder builder = new StringBuilder();
//			while((line = reader.readLine()) != null)
//				builder.append(line);
//			ArrayList<LinkedTreeMap<String, Object>> list = NewWorldWikiBuilder.getGson().fromJson(builder.toString(), ArrayList.class);
//
//			for(LinkedTreeMap<String, Object> map : list) {
//
//				LootBucket bucket = new LootBucket(map);
//				lootBuckets.put(tip.getItemId(), tip);
//
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	}

//	public static Tooltip getTooltip(String name) {
//		return tooltips.get(name);
//	}

}
